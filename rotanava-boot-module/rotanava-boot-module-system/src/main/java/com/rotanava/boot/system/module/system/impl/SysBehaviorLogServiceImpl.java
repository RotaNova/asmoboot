package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rotanava.boot.system.api.SysBehaviorLogService;
import com.rotanava.boot.system.api.module.constant.LoginStatus;
import com.rotanava.boot.system.api.module.system.bo.SysBehaviorLog;
import com.rotanava.boot.system.api.module.system.bo.SysReport;
import com.rotanava.boot.system.api.module.system.bo.UserLoginInfo;
import com.rotanava.boot.system.api.module.system.dto.ListUserLoginInfoDTO;
import com.rotanava.boot.system.api.module.system.vo.SysReportVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.boot.system.module.dao.SysBehaviorLogMapper;
import com.rotanava.boot.system.module.dao.UserLoginInfoMapper;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-10 17:08
 **/
@Service
public class SysBehaviorLogServiceImpl  extends ServiceImpl<SysBehaviorLogMapper, SysBehaviorLog> implements SysBehaviorLogService {

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    @Override
    public BaseVO<UserLoginInfoVO> listUserLoginInfoLog(@Valid BaseDTO baseDTO) {


        QueryWrapper<UserLoginInfo> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        queryWrapper.orderByDesc("id");

        List<UserLoginInfoVO> userLoginInfoList = new ArrayList<>();
        IPage<UserLoginInfo> page = userLoginInfoMapper.selectPage(PageUtils.startPage(baseDTO), queryWrapper);
        for (UserLoginInfo userLoginInfo : page.getRecords()) {
            UserLoginInfoVO userLoginInfoVO = new UserLoginInfoVO();
            BeanUtils.copyProperties(userLoginInfo, userLoginInfoVO);
            userLoginInfoVO.setLoginTime(userLoginInfo.getLoginTime().getTime());

            if(LoginStatus.SUCCESS.getStatus().equals(userLoginInfo.getLoginStatus())){
                Date date = userLoginInfo.getOfflineTime() != null ? userLoginInfo.getOfflineTime() : new Date();
                userLoginInfoVO.setOnlineTime(DateUtil.betweenMs(userLoginInfo.getLoginTime(), date));
            }

            userLoginInfoList.add(userLoginInfoVO);
        }

        return BuildUtil.buildListVO(page.getTotal(), userLoginInfoList);
    }

}
