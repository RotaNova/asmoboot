package com.rotanava.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseDTO extends SearchDTO implements Serializable {

//    @NotNull
    @ApiModelProperty(value = "一页多少数量")
    private Integer pageNum;





//    @NotNull
    @ApiModelProperty(value = "第几页")
    private Integer pageSize;

    private String[] dateArray;
    private String beginTime;
    private String endTime;
    private String today;

}
