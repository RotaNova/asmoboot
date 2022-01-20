package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 重复校验数据模型
 *
 * @author WeiQiangMiao
 * @date 2021-05-08
 */
@Data
public class DuplicateCheckDTO implements Serializable {

	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 字段名
	 */
	private String fieldName;
	
	/**
	 * 字段值
	 */
	private String fieldVal;

	/**
	 * 表id
	 */
	private String tableId;
}