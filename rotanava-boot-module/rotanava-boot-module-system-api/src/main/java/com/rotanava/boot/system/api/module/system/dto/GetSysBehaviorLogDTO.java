package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-10 17:23
 **/
@Data
public class GetSysBehaviorLogDTO extends BaseDTO {

    private String content;

    private String operateBy;

    private String operateType;
}
