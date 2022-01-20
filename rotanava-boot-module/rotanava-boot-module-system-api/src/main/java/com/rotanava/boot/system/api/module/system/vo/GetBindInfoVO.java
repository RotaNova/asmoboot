package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

/**
 * 得到绑定信息签证官
 *
 * @author weiqiangmiao
 * @date 2021/12/08
 */
@Data
public class GetBindInfoVO {

    private String phone;

    private String email;

    private Integer passwordStrength;
}
