package com.rotanava.boot.system.api.module.system.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-04-01 16:59
 */
@Data
public class SysAnnouncementTreeItem implements Serializable {
    
    private String id;

    /**
     * 配置名称
     */
    private String configName;

    private String pid;

}