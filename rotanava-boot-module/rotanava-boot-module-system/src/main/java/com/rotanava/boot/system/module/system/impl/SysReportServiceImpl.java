package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rotanava.boot.system.api.SysReportService;
import com.rotanava.boot.system.api.module.constant.ReportStatus;
import com.rotanava.boot.system.api.module.constant.ReportType;
import com.rotanava.boot.system.api.module.system.bo.SysReport;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.SysReportVO;
import com.rotanava.boot.system.module.dao.SysReportMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.PageUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 管理报告服务impl
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@Service
public class SysReportServiceImpl extends ServiceImpl<SysReportMapper, SysReport> implements SysReportService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSysReport(AddSysReportDTO addSysReportDTO) {
        if (ReportType.findByType(addSysReportDTO.getReportType()) == null) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }

        SysReport sysReport = new SysReport();
        BeanUtils.copyProperties(addSysReportDTO, sysReport);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysReport.setCreateBy(sysUser.getId());
        sysReport.setReporter(sysUser.getUserName());
        sysReport.setCreateTime(new Date());
        sysReport.setReportTime(new Date());
        sysReport.setReportStat(ReportStatus.PENDING.getStatus());
        sysReport.setReportCode(Convert.toStr(BaseUtil.getSnowflakeId()));

        baseMapper.insert(sysReport);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void cancelSysReport(Integer sysReportId) {
        updateReportStatus(sysReportId, ReportStatus.REVOKE.getStatus());
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysReport(UpdateSysReportDTO updateSysReportDTO) {
        SysReport sysReport = baseMapper.selectById(updateSysReportDTO.getSysReportId());

        if (ReportType.findByType(updateSysReportDTO.getReportType()) == null) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }
        if(sysReport.getReportStat() == null){
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }else if(ReportStatus.PROCESSED.getStatus().equals(sysReport.getReportStat()) || ReportStatus.TO_BE_CONFIRMED.getStatus().equals(sysReport.getReportStat()) ){
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }

        SysReport updateSysReport = new SysReport();
        BeanUtils.copyProperties(updateSysReportDTO, updateSysReport);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        updateSysReport.setId(updateSysReportDTO.getSysReportId());
        updateSysReport.setUpdateBy(sysUser.getId());
        updateSysReport.setUpdateTime(new Date());
        updateSysReport.setReportStat(ReportStatus.PENDING.getStatus());

        baseMapper.updateById(updateSysReport);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void confirmSysReport(Integer sysReportId) {
        SysReport sysReport = baseMapper.selectById(sysReportId);
        if (!ReportStatus.TO_BE_CONFIRMED.getStatus().equals(sysReport.getReportStat())) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysReport updateSysReport = new SysReport();
        updateSysReport.setId(sysReport.getId());
        updateSysReport.setUpdateBy(sysUser.getId());
        updateSysReport.setUpdateTime(new Date());
        updateSysReport.setReportStat(ReportStatus.PROCESSED.getStatus());

        baseMapper.updateById(updateSysReport);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseVO<SysReportVO> getSystemReport(Integer sysReportId) {
        SysReport sysReport = baseMapper.selectById(sysReportId);
        SysReportVO sysReportVO = new SysReportVO();
        BeanUtils.copyProperties(sysReport, sysReportVO);
        if (sysReport.getUpdateBy() != null) {
            SysUser sysUser = sysUserMapper.selectById(sysReport.getUpdateBy());
            sysReportVO.setUpdateUserName(sysUser.getUserName());
            sysReportVO.setUpdateUserAccountName(sysUser.getUserAccountName());
        }
        if(sysReport.getCreateBy() != null){
            SysUser sysUser = sysUserMapper.selectById(sysReport.getCreateBy());
            sysReportVO.setReporterUserAccountName(sysUser.getUserAccountName());
        }

        Optional.ofNullable(sysReport.getReportTime()).map(Date::getTime).ifPresent(sysReportVO::setReportTime);
        Optional.ofNullable(sysReport.getUpdateTime()).map(Date::getTime).ifPresent(sysReportVO::setUpdateTime);
        sysReportVO.setSysReportId(sysReport.getId());

        BaseVO<SysReportVO> baseVO = new BaseVO<>();
        baseVO.setData(sysReportVO);
        return baseVO;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseVO<SysReportVO> listPersonalSystemReport(BaseDTO baseDTO) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        QueryWrapper<SysReport> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        queryWrapper.eq("create_by",sysUser.getId());

        IPage<SysReport> page = page(PageUtils.startPage(baseDTO), queryWrapper);
        List<SysReport> sysReports = page.getRecords();
        List<SysReportVO> sysReportVOList = new ArrayList<>();
        for (SysReport sysReport : sysReports) {
            SysReportVO sysReportVO = new SysReportVO();
            BeanUtils.copyProperties(sysReport, sysReportVO);
            sysReportVO.setSysReportId(sysReport.getId());
            Optional.ofNullable(sysReport.getReportTime()).map(Date::getTime).ifPresent(sysReportVO::setReportTime);
            Optional.ofNullable(sysReport.getUpdateTime()).map(Date::getTime).ifPresent(sysReportVO::setUpdateTime);

            sysReportVOList.add(sysReportVO);
        }
        return BuildUtil.buildListVO(page.getTotal(), sysReportVOList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void rejectSystemReport(Integer sysReportId) {
        updateReportStatus(sysReportId, ReportStatus.OVERRULE.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void replySysReport(ReplySysReportDTO replySysReportDTO) {

        SysReport sysReport = baseMapper.selectById(replySysReportDTO.getSysReportId());

        if (!ReportStatus.PENDING.getStatus().equals(sysReport.getReportStat())) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_15);
        }

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysReport updateSysReport = new SysReport();
        updateSysReport.setId(replySysReportDTO.getSysReportId());
        updateSysReport.setUpdateBy(sysUser.getId());
        updateSysReport.setUpdateTime(new Date());
        updateSysReport.setReplyBody(replySysReportDTO.getReplyBody());
        updateSysReport.setReportStat(ReportStatus.TO_BE_CONFIRMED.getStatus());
        baseMapper.updateById(updateSysReport);


    }

    @Override
    @Transactional(readOnly = true)
    public BaseVO<SysReportVO> listAdminSystemReport(BaseDTO baseDTO) {
        QueryWrapper<SysReport> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        IPage<SysReport> sysReportPage = PageUtils.startPage(baseDTO);

        List<SysReport> sysReports = baseMapper.findAdminSystemReport(
                queryWrapper,
                sysReportPage
        );

        List<SysReportVO> sysReportVOList = new ArrayList<>();
        for (SysReport sysReport : sysReports) {
            SysReportVO sysReportVO = new SysReportVO();
            BeanUtils.copyProperties(sysReport, sysReportVO);

            sysReportVO.setSysReportId(sysReport.getId());
            Optional.ofNullable(sysReport.getUpdateTime()).map(Date::getTime).ifPresent(sysReportVO::setUpdateTime);
            Optional.ofNullable(sysReport.getReportTime()).map(Date::getTime).ifPresent(sysReportVO::setReportTime);
            sysReportVO.setSysReportId(sysReport.getId());
            sysReportVOList.add(sysReportVO);
        }

        return BuildUtil.buildListVO(sysReportPage.getTotal(), sysReportVOList);
    }

    /**
     * 更新报告的状态
     *
     * @param sysReportId sys报告id
     * @param reportStat  报告统计
     * @author WeiQiangMiao
     * @date 2021-03-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void updateReportStatus(int sysReportId, int reportStat) {
        SysReport sysReport = baseMapper.selectById(sysReportId);
        if (!ReportStatus.PENDING.getStatus().equals(sysReport.getReportStat())) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_51);
        }

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysReport updateSysReport = new SysReport();
        updateSysReport.setId(sysReportId);
        updateSysReport.setUpdateBy(sysUser.getId());
        updateSysReport.setUpdateTime(new Date());
        updateSysReport.setReportStat(reportStat);
        baseMapper.updateById(updateSysReport);
    }

}
