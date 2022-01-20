package com.rotanava.boot.system.module.system.impl.sysdepartment;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.DeptRoleManagementService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysRoleManagementService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.DeptDeleteStatus;
import com.rotanava.boot.system.api.module.constant.DeptManageStatus;
import com.rotanava.boot.system.api.module.constant.DeptStatus;
import com.rotanava.boot.system.api.module.constant.DeptType;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.constant.UserSysRole;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentTree;
import com.rotanava.boot.system.api.module.system.bean.SysUserLabel;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserDepartment;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.AddDepartmentDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.UpdateDepartmentDTO;
import com.rotanava.boot.system.api.module.system.vo.DeptInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.boot.system.module.dao.SysDepartRoleMapper;
import com.rotanava.boot.system.module.dao.SysDepartRoleUserMapper;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysRoleMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.boot.system.module.system.mq.MqTransactionalMessageSender;
import com.rotanava.boot.system.module.system.mq.SysDepartmentListenter;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 16:44
 */
@Slf4j
@Validated
@Service
@DubboService
public class SysDepartmentServiceImpl implements SysDepartmentService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleManagementService sysRoleManagementService;

    @Autowired
    private DeptRoleManagementService deptRoleManagementService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartRoleUserMapper sysDepartRoleUserMapper;

    @Autowired
    private MqTransactionalMessageSender mqTransactionalmessageSender;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer addDepartment(AddDepartmentDTO departmentDTO, int userId) {
        final SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setDeptName(departmentDTO.getDeptName());
        sysDepartment.setDeptNameEn(departmentDTO.getDeptNameEn());
        sysDepartment.setDeptCode(departmentDTO.getDeptCode());
        if (departmentDTO.getDeptValidTime() == null) {
            departmentDTO.setDeptValidTime(4102329600000L);
        }
        sysDepartment.setDeptValidTime(new Date(departmentDTO.getDeptValidTime()));
        sysDepartment.setDeptDescription(departmentDTO.getDeptDescription());
        sysDepartment.setDeptManagerPhone(departmentDTO.getDeptManagerPhone());
        sysDepartment.setDeptAddress(departmentDTO.getDeptAddress());
        sysDepartment.setDeptFax(departmentDTO.getDeptFax());
        sysDepartment.setDeptOrder(departmentDTO.getDeptOrder());
        sysDepartment.setDeptDeleteStatus(DeptDeleteStatus.NOT_DELETED.getStatus());
        sysDepartment.setCreateBy(userId);
        sysDepartment.setCreateTime(new Date());
        sysDepartment.setDeptType(departmentDTO.getDeptType());

        if (departmentDTO.getDeptValidTime() < System.currentTimeMillis()) {
            sysDepartment.setDeptStatus(DeptStatus.EXPIRED.getStatus());
        } else {
            sysDepartment.setDeptStatus(DeptStatus.NORMAL.getStatus());
        }

        checkParams(null, sysDepartment);

        if (sysDepartment.getDeptType().equals(DeptType.SUB_DEPARTMENT.getType())) {
            final Integer parentDeptId = departmentDTO.getParentDeptId();
            sysDepartment.setParentDeptId(parentDeptId);
            if (parentDeptId == null || sysDepartmentMapper.selectById(parentDeptId) == null) {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_43);
            }
        } else {
            sysDepartment.setParentDeptId(0);
        }

        sysDepartmentMapper.insert(sysDepartment);

        insertOrUpdateSysUserDepartment(true, sysDepartment.getId(), departmentDTO.getSysUserIdList());


        //发送部门的有效期
        mqTransactionalmessageSender.insertMqTransactionalMessage(SysDepartmentListenter.EXPIRATION_TIME_QUEUE
                , Convert.toStr(sysDepartment.getId()), Math.max(0, sysDepartment.getDeptValidTime().getTime() - System.currentTimeMillis()));

        return sysDepartment.getId();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateDepartment(UpdateDepartmentDTO departmentDTO, int userId, boolean isMyDepartment) {
        final Integer deptId = departmentDTO.getDeptId();
        final SysDepartment sysDepartment = sysDepartmentMapper.selectById(deptId);
        if (sysDepartment != null) {
            sysDepartment.setDeptName(departmentDTO.getDeptName());
            sysDepartment.setDeptDescription(departmentDTO.getDeptDescription());
            sysDepartment.setDeptManagerPhone(departmentDTO.getDeptManagerPhone());
            sysDepartment.setDeptAddress(departmentDTO.getDeptAddress());
            sysDepartment.setDeptFax(departmentDTO.getDeptFax());
            sysDepartment.setUpdateBy(userId);
            sysDepartment.setUpdateTime(new Date());
            sysDepartment.setDeptOrder(departmentDTO.getDeptOrder());

            if (departmentDTO.getDeptValidTime() == null) {
                departmentDTO.setDeptValidTime(4102329600000L);
            }

            if (departmentDTO.getDeptValidTime() < System.currentTimeMillis()) {
                sysDepartment.setDeptStatus(DeptStatus.EXPIRED.getStatus());
            } else {
                sysDepartment.setDeptStatus(DeptStatus.NORMAL.getStatus());
            }

            if (!isMyDepartment) {
                sysDepartment.setDeptValidTime(new Date(departmentDTO.getDeptValidTime()));
            }

            checkParams(deptId, sysDepartment);
            sysDepartmentMapper.updateById(sysDepartment);

            if (!isMyDepartment) {
                insertOrUpdateSysUserDepartment(false, sysDepartment.getId(), departmentDTO.getSysUserIdList());

                //发送部门的有效期
                mqTransactionalmessageSender.insertMqTransactionalMessage(SysDepartmentListenter.EXPIRATION_TIME_QUEUE
                        , Convert.toStr(sysDepartment.getId()), Math.max(0, sysDepartment.getDeptValidTime().getTime() - System.currentTimeMillis()));
            }

        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }

    /**
     * 功能: 检查参数
     * 作者: zjt
     * 日期: 2021/3/20 16:15
     * 版本: 1.0
     */
    public void checkParams(Integer deptId, SysDepartment sysDepartment) {
        final String deptName = sysDepartment.getDeptName();
        final String deptCode = sysDepartment.getDeptCode();


        final Integer deptNameList = sysDepartmentMapper.findIdByDeptName(deptName);
        doCheck(deptId, deptNameList != null ? Lists.newArrayList(deptNameList) : Collections.emptyList(), "部门名称重复");
        final Integer deptCodeList = sysDepartmentMapper.findIdByDeptCode(deptCode);
        doCheck(deptId, deptCodeList != null ? Lists.newArrayList(deptCodeList) : Collections.emptyList(), "部门编号重复");

//        final Date deptValidTime = sysDepartment.getDeptValidTime();
//        if (deptValidTime.getTime() < System.currentTimeMillis()) {
//            throw new CommonException(new ErrorCode(100100, "有效期应该大于现在时间"));
//        }

    }

    /**
     * 功能: 异常就抛出 throwStr
     * 作者: zjt
     * 日期: 2021/3/20 14:16
     * 版本: 1.0
     */
    private void doCheck(Integer deptId, List<Integer> deptIdList, String throwStr) {
        if (deptId != null) {
            deptIdList.remove(deptId);
        }

        if (!deptIdList.isEmpty()) {
            throw new CommonException(new ErrorCode(100100, throwStr));
        }
    }

    /**
     * 功能: 插入用户关联部门表
     * 作者: zjt
     * 日期: 2021/3/15 14:11
     * 版本: 1.0
     */
    private void insertOrUpdateSysUserDepartment(boolean isInsert, int deptId, List<Integer> sysUserIdList) {

        if (sysUserIdList == null) {
            sysUserIdList = Lists.newArrayList();
        }

        if (isInsert) {
            for (Integer sysUserId : sysUserIdList) {
                final SysUser sysUser = sysUserService.findSysUserById(sysUserId);
                if (sysUser.getUserSysrole().equals(UserSysRole.ADMINISTRATOR.getType())) {
                    final SysUserDepartment sysUserDepartment = new SysUserDepartment();
                    sysUserDepartment.setSysUserId(sysUserId);
                    sysUserDepartment.setSysDepartmentId(deptId);
                    sysUserDepartment.setDeptManage(DeptManageStatus.ADMINISTRATOR.getStatus());
                    sysUserDepartmentMapper.insert(sysUserDepartment);
                } else {
                    throw new CommonException(AuthErrorCode.AUTH_ERROR_04);
                }
            }
        } else {
            final List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.findBySysDepartmentId(deptId);

            //该部门的成员
            final List<Integer> sysUserDepartmentIds = sysUserDepartmentMapper.findSysUserIdBySysDepartmentId(deptId);

            //判断是否人员id 属于该部门下的人员
            if (!sysUserDepartmentIds.containsAll(sysUserIdList)) {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_54);
            }

            for (SysUserDepartment sysUserDepartment : sysUserDepartmentList) {
                if (sysUserDepartment.getDeptManage().equals(DeptManageStatus.ADMINISTRATOR.getStatus())) {
                    sysUserDepartment.setDeptManage(DeptManageStatus.NOT_ADMINISTRATOR.getStatus());
                    sysUserDepartmentMapper.updateById(sysUserDepartment);
                }
            }


            for (Integer sysUserId : sysUserIdList) {
                final SysUser sysUser = sysUserService.findSysUserById(sysUserId);
                if (sysUser.getUserSysrole().equals(UserSysRole.ADMINISTRATOR.getType())) {

                    boolean needInsert = true;

                    for (SysUserDepartment sysUserDepartment : sysUserDepartmentList) {
                        final Integer sysDepartmentId = sysUserDepartment.getSysDepartmentId();
                        final Integer userId = sysUserDepartment.getSysUserId();
                        if (sysDepartmentId == deptId && userId.equals(sysUserId)) {
                            sysUserDepartment.setDeptManage(DeptManageStatus.ADMINISTRATOR.getStatus());
                            sysUserDepartmentMapper.updateById(sysUserDepartment);
                            needInsert = false;
                        }
                    }

                    if (needInsert) {
                        final SysUserDepartment sysUserDepartment = new SysUserDepartment();
                        sysUserDepartment.setSysUserId(sysUserId);
                        sysUserDepartment.setSysDepartmentId(deptId);
                        sysUserDepartment.setDeptManage(DeptManageStatus.ADMINISTRATOR.getStatus());
                        sysUserDepartmentMapper.insert(sysUserDepartment);
                    }


                } else {
                    throw new CommonException(AuthErrorCode.AUTH_ERROR_04);
                }
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteBatchDepartment(Collection<Integer> deptIdList, int userId) {
        final List<Integer> deleteDeptIdList = Lists.newArrayList();

        for (Integer deptId : deptIdList) {
            final List<Integer> underDeptIdList = getUnderDeptIdList(deptId);
            deleteDeptIdList.addAll(underDeptIdList);
            deleteDeptIdList.add(deptId);
        }

        if (!CollectionUtils.isEmpty(deleteDeptIdList)) {
            sysDepartmentMapper.deleteBatchIds(deleteDeptIdList);
            sysUserDepartmentMapper.deleteBySysDepartmentIdIn(deleteDeptIdList);
            deptRoleManagementService.deleteDeptRole(sysDepartRoleMapper.findIdBySysDepartmentIdIn(deleteDeptIdList));
        }
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Tree<Integer>> getDepartmentList(Integer deptId) {
        final List<SysDepartment> sysDepartmentList = sysDepartmentMapper.findall();
        final List<Tree<Integer>> treeList = TreeUtil.build(sysDepartmentList, 0, (sysDepartment, treeNode) -> {
            final Integer departmentId = sysDepartment.getId();
            treeNode.setId(departmentId);
            treeNode.setParentId(sysDepartment.getParentDeptId());
            treeNode.setName(sysDepartment.getDeptName());
            treeNode.setWeight(sysDepartment.getDeptOrder());
            treeNode.putExtra("deptId", departmentId);
            final SysDepartmentTree sysDepartmentTree = new SysDepartmentTree();
            BeanUtils.copyProperties(sysDepartment, sysDepartmentTree);
            treeNode.putExtra("sysDepartmentTree", sysDepartmentTree);
            final boolean isNormal = sysDepartment.getDeptStatus().equals(DeptStatus.NORMAL.getStatus());
            treeNode.putExtra("isNormal", isNormal);
        });

        if (deptId == null || deptId == 0) {
            return treeList;
        }

        return getUnderDeptIdList(deptId, treeList);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Tree<Integer>> getMyDepartmentList(int userId, Integer deptId) {
        final List<SysDepartment> sysDepartmentList = sysDepartmentMapper.findDepartmentByUserId(userId);
        final List<Tree<Integer>> departmentTreeList = getDepartmentList(deptId);

        final List<SysDepartmentTree> newSysDepartmentList = Lists.newArrayList();

        doGetMyDepartmentList(userId, sysDepartmentList, departmentTreeList, newSysDepartmentList, false, false);

        final List<Integer> newSysDepartmentIdList = newSysDepartmentList.stream().map(SysDepartmentTree::getId).collect(Collectors.toList());

        return TreeUtil.build(newSysDepartmentList, 0, (sysDepartment, treeNode) -> {
            final Integer departmentId = sysDepartment.getId();
            treeNode.setId(departmentId);
            treeNode.setParentId(newSysDepartmentIdList.contains(sysDepartment.getParentDeptId()) ? sysDepartment.getParentDeptId() : 0);
            treeNode.setName(sysDepartment.getDeptName());
            treeNode.setWeight(sysDepartment.getDeptOrder());
            treeNode.putExtra("deptId", departmentId);
            treeNode.putExtra("canOperating", sysDepartment.getIsAdmin());
            final boolean isNormal = sysDepartmentMapper.selectById(departmentId).getDeptStatus().equals(DeptStatus.NORMAL.getStatus());
            treeNode.putExtra("isNormal", isNormal);
        });
    }

    /**
     * 功能: 获取下级我的部门列表
     * 作者: zjt
     * 日期: 2021/3/25 17:33
     * 版本: 1.0
     */
    private void doGetMyDepartmentList(int userId, List<SysDepartment> sysDepartmentList, List<Tree<Integer>> departmentTreeList, List<SysDepartmentTree> newSysDepartmentList, boolean canAdd, boolean isAdmin) {
        for (Tree<Integer> departmentTree : departmentTreeList) {
            final Integer departmentId = departmentTree.getId();
            final List<Tree<Integer>> children = departmentTree.getChildren();

            boolean canAddCopy = canAdd;
            boolean isAdminCopy = isAdmin;

            if (sysDepartmentList.stream().map(SysDepartment::getId).collect(Collectors.toList()).contains(departmentId) || canAddCopy) {
                final SysDepartmentTree sysDepartmentTree = (SysDepartmentTree) departmentTree.get("sysDepartmentTree");
                final Integer departmentTreeId = sysDepartmentTree.getId();
                final SysUserDepartment sysUserDepartment = sysUserDepartmentMapper.findAllBySysUserIdAndSysDepartmentId(userId, departmentTreeId);
                if (sysUserDepartment != null && sysUserDepartment.getDeptManage().equals(DeptManageStatus.ADMINISTRATOR.getStatus())) {
                    isAdminCopy = true;
                }
                sysDepartmentTree.setIsAdmin(isAdminCopy);
                newSysDepartmentList.add(sysDepartmentTree);
                canAddCopy = true;
            }

            if (children != null && !children.isEmpty()) {
                doGetMyDepartmentList(userId, sysDepartmentList, children, newSysDepartmentList, canAddCopy, isAdminCopy);
            }

        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeptInfoVO getDeptInfo(int deptId) {
        final SysDepartment sysDepartment = sysDepartmentMapper.selectById(deptId);
        if (sysDepartment != null) {
            final DeptInfoVO deptInfo = new DeptInfoVO();
            deptInfo.setDeptId(sysDepartment.getId());
            deptInfo.setDeptFax(sysDepartment.getDeptFax());
            deptInfo.setDeptType(sysDepartment.getDeptType());
            deptInfo.setDeptName(sysDepartment.getDeptName());
            deptInfo.setDeptCode(sysDepartment.getDeptCode());
            deptInfo.setDeptOrder(sysDepartment.getDeptOrder());
            deptInfo.setDeptNameEn(sysDepartment.getDeptNameEn());
            deptInfo.setDeptAddress(sysDepartment.getDeptAddress());
            deptInfo.setParentDeptId(sysDepartment.getParentDeptId());
            deptInfo.setDeptDescription(sysDepartment.getDeptDescription());
            deptInfo.setDeptManagerPhone(sysDepartment.getDeptManagerPhone());
            deptInfo.setDeptValidTime(sysDepartment.getDeptValidTime().getTime());
            deptInfo.setParentDeptName(sysDepartment.getParentDeptId() == 0 ? "无上级部门" : sysDepartmentMapper.findDeptNameById(sysDepartment.getParentDeptId()));

            final List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.findBySysDepartmentId(sysDepartment.getId());

            final List<SysUserLabel> sysUserList = sysUserDepartmentList.stream()
                    .filter(sysUserDepartment -> sysUserDepartment.getDeptManage().equals(DeptManageStatus.ADMINISTRATOR.getStatus()))
                    .map(sysUserDepartment -> {
                        final Integer sysUserId = sysUserDepartment.getSysUserId();
                        final SysUserLabel sysUserLabel = new SysUserLabel();
                        sysUserLabel.setSysUserId(sysUserId);
                        sysUserLabel.setSysUserName(sysUserMapper.findUserNameById(sysUserId));
                        return sysUserLabel;
                    }).collect(Collectors.toList());

            deptInfo.setSysUserList(sysUserList);
            return deptInfo;
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void departmentLinkUser(int deptId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            final Integer count = sysUserDepartmentMapper.countBySysDepartmentIdAndSysUserId(deptId, sysUserId);
            if (count == 0) {
                final SysUserDepartment sysUserDepartment = new SysUserDepartment();
                sysUserDepartment.setDeptManage(deptId);
                sysUserDepartment.setSysDepartmentId(deptId);
                sysUserDepartment.setSysUserId(sysUserId);
                sysUserDepartmentMapper.insert(sysUserDepartment);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void departmentUnlinkUser(int deptId, Collection<Integer> sysUserIdList) {
        for (Integer sysUserId : sysUserIdList) {
            sysUserDepartmentMapper.deleteBySysDepartmentIdAndSysUserId(deptId, sysUserId);
            final List<Integer> sysDepartRoleIdList = sysDepartRoleMapper.findIdBySysDepartmentIdIn(Collections.singletonList(deptId));
            for (Integer sysDepartRoleId : sysDepartRoleIdList) {
                deptRoleManagementService.deleteDeptRoleAuthorizedUser(sysDepartRoleId, Collections.singletonList(sysUserId));
            }
        }
    }

    @Override
    public IPage<SysUserItemVO> getListSysUser(GetListSysUserDeptRoleDTO getListSysUserDeptRoleDTO) {
        final QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(getListSysUserDeptRoleDTO);
        queryWrapper.eq("b.user_delete_status", UserDeleteStatus.NOT_DELETED.getStatus());
        queryWrapper.eq("a.depart_role_id", getListSysUserDeptRoleDTO.getDeptRoleId());
        final IPage<SysUser> sysUserIPage = sysDepartRoleMapper.queryUserByRole(queryWrapper, PageUtils.startPage(getListSysUserDeptRoleDTO));
        return sysUserService.getUserItemVOIPage(sysUserIPage, null);
    }

    /**
     * 功能: 获取下级id
     * 作者: zjt
     * 日期: 2021/3/5 18:17
     * 版本: 1.0
     */
    private List<Integer> getUnderDeptIdList(int deptId) {
        final List<Integer> underDeptIdList = Lists.newArrayList();
        final List<Tree<Integer>> departmentTree = getDepartmentList(deptId);
        addUnderDeptId(deptId, null, underDeptIdList, departmentTree);
        return underDeptIdList;
    }

    /**
     * 功能: 获取下级及本机的树
     * 作者: zjt
     * 日期: 2021/3/5 18:17
     * 版本: 1.0
     */
    private List<Tree<Integer>> getUnderDeptIdList(int deptId, List<Tree<Integer>> treeList) {
        final List<Tree<Integer>> underTreeList = Lists.newArrayList();
        addUnderDeptTree(deptId, underTreeList, treeList);
        return underTreeList;
    }

    /**
     * 功能: bfs搜索 deptId下的tree
     * 作者: zjt
     * 日期: 2021/3/6 11:03
     * 版本: 1.0
     */
    private void addUnderDeptTree(int deptId, List<Tree<Integer>> underTreeList, List<Tree<Integer>> departmentTree) {
        for (Tree<Integer> tree : departmentTree) {
            final Integer id = tree.getId();

            if (deptId == id) {
                underTreeList.add(tree);
                return;
            }

            if (tree.getChildren() != null && !tree.getChildren().isEmpty()) {
                addUnderDeptTree(deptId, underTreeList, tree.getChildren());
            }
        }
    }

    /**
     * 功能: bfs搜索 deptId下的id
     * 作者: zjt
     * 日期: 2021/3/6 11:03
     * 版本: 1.0
     */
    private void addUnderDeptId(int deptId, Boolean isUnderDeptId, List<Integer> underDeptIdList, List<Tree<Integer>> departmentTree) {
        for (Tree<Integer> tree : departmentTree) {
            int deptIdCopy = deptId;
            final Integer id = tree.getId();
            if (isUnderDeptId != null && isUnderDeptId) {
                underDeptIdList.add(id);
                deptIdCopy = id;
            }

            if (tree.getChildren() != null && !tree.getChildren().isEmpty()) {
                addUnderDeptId(deptIdCopy, deptIdCopy == id, underDeptIdList, tree.getChildren());
            }
        }
    }

    @Override
    public List<SysDepartment> getAllDept() {

        return sysDepartmentMapper.findall();
    }
}