package com.rotanava.framework.model;

import lombok.Data;

@Data
public class ReturnDTO {
    private int count;
    private Object data;
    private String[] dateArray;
}
