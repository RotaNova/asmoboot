package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClusterResultsVO {


    private String metric_name;

    private ClusterDataVO data;

}
