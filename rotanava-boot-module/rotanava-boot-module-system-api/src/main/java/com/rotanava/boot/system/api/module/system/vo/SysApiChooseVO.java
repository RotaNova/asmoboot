package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;


/**
 * 接口资源选择vo
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class SysApiChooseVO implements Serializable {

    /**
     * 系统页面资源
     */
    private Integer sysPageId;

    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 页面类型 0增 1删 2改 3查
     */
    @Dict(dicCode = "ability_type")
    private Integer abilityType;

    /**
     * 是否选择
     */
    private Boolean choose;

    /**
     * 是否全员可见 0-否 1-是
     */
    private Integer visibleToAll;

}