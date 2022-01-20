package com.rotanava.boot.system.api.module.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户VO
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class AccessInfoVO implements Serializable {


    /**
     * 账号名
     */
    private String userAccountName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    @Dict(dicCode = "user_sex")
    private Integer userSex;

    /**
     * 用户的电子邮件
     */
    private String userEmail;

    /**
     * 用户的电话
     */
    private String userPhone;

    /**
     * 用户部门信息
     */
    List<UserDepartmentInfoVO> userDepartmentInfos;

}
