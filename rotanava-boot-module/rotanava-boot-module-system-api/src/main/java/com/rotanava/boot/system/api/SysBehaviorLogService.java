package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rotanava.boot.system.api.module.system.bo.SysBehaviorLog;
import com.rotanava.boot.system.api.module.system.dto.ListUserLoginInfoDTO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;

import javax.validation.Valid;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-10 17:08
 **/
public interface SysBehaviorLogService extends IService<SysBehaviorLog> {


    /**
     * 用户登录信息日志列表
     *
     * @param baseDTO 基地dto
     * @return {@link BaseVO<UserLoginInfoVO> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<UserLoginInfoVO> listUserLoginInfoLog(@Valid BaseDTO baseDTO);

}
