package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 更新用户基本信息
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Data
public class UpdateUserBasicInfoDTO implements Serializable {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 16,message = "昵称长度限制16个字符")
    private String userName;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @NotNull(message = "系统用户性别不能为空")
    private Integer userSex;

    /**
     * 系统用户地址信息
     */
    private String userAddress;

    /**
     * 系统用户生日信息
     */
    private Long userBirthday;

}
