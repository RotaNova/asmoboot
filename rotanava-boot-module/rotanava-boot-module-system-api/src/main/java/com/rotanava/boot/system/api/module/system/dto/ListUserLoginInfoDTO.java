package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户登录信息dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListUserLoginInfoDTO extends BaseDTO {


    /**
     * 登录的用户名
     */
    private String loginName;

    /**
     * 登录的IP地址
     */
    private String loginIp;

    /**
     * 登录的物理地址
     */
    private String loginLocation;

}
