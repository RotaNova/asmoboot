package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新用户基本信息
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Data
public class UserBasicInfoVO implements Serializable {

    /**
     * 用户id
     */
    private Integer id;


    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户帐户名
     */
    private String userAccountName;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @Dict(dicCode = "user_sex")
    private Integer userSex;

    /**
     * 系统用户地址信息
     */
    private String userAddress;

    /**
     * 系统用户生日信息
     */
    private Long userBirthday;

    /**
     * 系统用户有效日期
     */
    private Long userValidTime;

    /**
     * 用户照片url
     */
    private String userPhotoUrl;

    /**
     * 用户安全的电话
     */
    private String userSafePhone;

    /**
     * 用户安全的电子邮件
     */
    private String userSafeEmail;

    /**
     * 用户微信
     */
    private String userWechat;
}
