package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 17:22
 */
@Data
public class AnnouncementConfigListVO implements Serializable {

    @ApiModelProperty(value = "大标签名称")
    @Dict(dicCode = "ann_category")
    private Integer type;


    private List<AnnouncementConfigVO> announcementConfigVOList;
}