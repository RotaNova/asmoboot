package com.rotanava.framework.model;


import lombok.Data;

import java.util.Date;

@Data
public class SysRequestLog {

  private Integer id;
  private Integer tableInfoId;
  private Integer requestMethod;
  private Date requestTime;
  private String requestIp;
  private String requestUrl;
  private Integer requestStat;
  private Integer requestCostTime;
  private String requestName;
  private String requestParam;




}
