package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClusterResultVO {

    private List<BigDecimal> value;
    private String min_value;
    private String max_value;
    private String avg_value;
    private String sum_value;
    private String fee;
    private String resource_unit;
    private String currency_unit;

}
