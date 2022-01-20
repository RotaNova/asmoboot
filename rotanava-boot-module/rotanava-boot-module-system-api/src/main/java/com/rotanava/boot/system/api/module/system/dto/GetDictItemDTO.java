package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 得到dict项dto
 *
 * @author weiqiangmiao
 * @date 2021/11/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetDictItemDTO extends BaseDTO {

    /**
     * dict id
     */
    private Integer dictId;

}
