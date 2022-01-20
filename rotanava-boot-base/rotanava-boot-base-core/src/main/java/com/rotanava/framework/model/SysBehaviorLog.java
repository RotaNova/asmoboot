package com.rotanava.framework.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysBehaviorLog implements Serializable {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer tableInfoId;
  private Integer syslogBehaviorType;
  private Integer syslogType;
  private Date syslogTime;
  private String syslogContent;
  private String operateBy;
  private String operateIp;
  private Integer operateType;
  private String operateCostTime;
  private Date operateCreateTime;
  private String operateMethod;
  private String operateUrl;
  private String operateParam;
  private Integer operateRequestType;



}
