package com.rotanava.boot.system.api;

import cn.hutool.core.lang.tree.Tree;
import com.rotanava.boot.system.api.module.system.dto.AddSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.AddSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.vo.SysApiPermissionVO;
import com.rotanava.boot.system.api.module.system.vo.SysPagePermissionVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.model.SearchDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理服务
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Validated
public interface ManagePermissionService {


    /**
     * 保存系统页面权限 返回
     *
     * @param addSysPagePermissionDTO 添加系统页面权限dto
     * @return
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Integer saveSysPagePermission(@Validated AddSysPagePermissionDTO addSysPagePermissionDTO, int userId);

    /**
     * 删除系统页面权限
     *
     * @param deleteSysPagePermissionDTO 删除系统页面权限dto
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void deleteSysPagePermission(@Validated DeleteSysPagePermissionDTO deleteSysPagePermissionDTO);

    /**
     * 更新页面系统权限
     *
     * @param updateSysPagePermissionDTO 更新系统页面权限dto
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateSysPagePermission(@Validated UpdateSysPagePermissionDTO updateSysPagePermissionDTO);

    /**
     * 获取系统页面权限
     *
     * @param sysPageId 系统页面id
     * @return {@link BaseVO<SysPagePermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysPagePermissionVO> getSysPagePermission(@NotNull(message = "系统页面id不能为空") Integer sysPageId);

    /**
     * 获取系统页面权限列表
     *
     * @param searchDTO searchDTO
     * @return {@link BaseVO<SysPagePermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysPagePermissionVO> listSysPagePermissions(SearchDTO searchDTO);

    /**
     * 保存系统接口
     *
     * @param addSysApiPermissionDTO 添加系统接口权限dto
     * @author WeiQiangMiao
     * @date 2021-03-05
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void saveSysApiPermission(@Validated AddSysApiPermissionDTO addSysApiPermissionDTO, int userId);

    /**
     * 删除系统api许可
     *
     * @param deleteSysApiPermissionDTO 删除系统接口
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void deleteSysApiPermission(@Validated DeleteSysApiPermissionDTO deleteSysApiPermissionDTO);

    /**
     * 更新系统接口
     *
     * @param updateSysApiPermissionDTO 修改系统接口资源
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateSysApiPermission(@Validated UpdateSysApiPermissionDTO updateSysApiPermissionDTO);

    /**
     * 获得系统接口资源
     *
     * @param sysApiId 系统接口资源
     * @return {@link BaseVO<SysApiPermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysApiPermissionVO> getSysApiPermission(@NotNull(message = "系统接口id不能为空") Integer sysApiId);

    /**
     * 获取系统接口资源列表
     *
     * @param baseDTO baseDTO
     * @return {@link <BaseVO<SysApiPermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysApiPermissionVO> listSysApiPermissions(@Validated BaseDTO baseDTO);

    /**
     * 系统导航栏页面权限列表
     *
     * @param sysPageModuleTypeId 系统页面模块类型id
     * @return {@link List<Tree<Integer>> }
     * @author WeiQiangMiao
     * @date 2021-04-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<Tree<Integer>> listSysPagePermissionsNavigationBar(@Valid @NotNull(message = "页面模块类型id不能为空") Integer sysPageModuleTypeId);

    /**
     * sys列表页面权限使用api
     *
     * @param sysPageModuleTypeId 系统页面模块类型id
     * @return {@link BaseVO<SysPagePermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-04-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SysPagePermissionVO> listSysPagePermissionsUseApi(Integer sysPageModuleTypeId);

     /**
      * 功能: 找出  apiUrl 数量
      * 作者: zjt
      * 日期: 2021/7/16 11:21
      * 版本: 1.0
      */
    Integer countByApiUrl(String apiUrl);

    /**
     * 获取页面json
     *
     * @param sysPageId 系统页面id
     * @return @return {@link String }
     * @author weiqiangmiao
     * @date 2021/09/24
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getPageJson(Integer sysPageId);


    /**
     * 导入excel
     *
     * @param file 文件
     * @return
     * @author weiqiangmiao
     * @date 2021/10/14
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void importExcel(MultipartFile file) throws Exception;

    /**
     * 导出excel
     *
     * @return
     * @author weiqiangmiao
     * @date 2021/10/14
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void exportExcel();


    /**
     * 得到模具
     *
     * @return @return {@link String }
     * @author weiqiangmiao
     * @date 2021/10/15
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getMould();

    /**
     * 批量导出excel
     *
     * @param sysPageIds 系统页面id
     * @return
     * @author weiqiangmiao
     * @date 2021/10/15
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void batchExportExcel( List<Integer> sysPageIds);
}
