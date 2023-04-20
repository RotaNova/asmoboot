package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

/**
 * 获取授权信息
 *
 * @author weiqiangmiao
 * @date 2022/06/30
 */
@Data
public class GetAuthInfoVO {

    /**
     * 系统时间
     */
    private Long systemTime;

    /**
     * 系统有效期
     */
    private Long systemValidityPeriod;

    /**
     * 许可证情况
     */
    private boolean licenseValid;

    /**
     * 使用环境: 0-过期 1-试用 2-已授权
     */
    private Integer curEnv;



}
