package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClusterDataVO {

    private String resultType;

    private List<ClusterResultVO> result;


}
