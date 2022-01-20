package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 添加账户安全设置dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
@Data
public class AddAccountSafeSettingDTO implements Serializable {


    /**
     * 手机安全保护类型:0-无:1-验证号码完整性;2-验证验证码
     */
    private Integer phoneSafeType;


    /**
     * 邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码
     */
    private Integer emailSafeType;


}