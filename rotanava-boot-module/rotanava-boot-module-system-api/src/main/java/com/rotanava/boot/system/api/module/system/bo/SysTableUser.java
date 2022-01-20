package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-07-02 11:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTableUser implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * 表格编码
     */
    @ApiModelProperty(value="表格编码")
    private String tableCode;

    /**
     * 前端json数据
     */
    @ApiModelProperty(value="前端json数据")
    private String tableData;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;


    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;




}
