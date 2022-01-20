package com.rotanava.framework.model.bo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * sys_search_config
 * @author 
 */
@Data
public class SysSearchConfig implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 客户端标题
     */
    private String title;


    /**
     * 前端请求名
     */
    private String reqName;

    /**
     * 数据字段
     */
    private String dbColumn;

    /**
     * 页面id
     */
    private Integer pageId;


    /**
     * 高级搜索编码
     */
//    @TableField("`search_code`")
    private String searchCode;

    /**
     * 默认搜索规则
     */
    private String defaultRule;

    /**
     * 字典名 此字段不为空，则为选择框
     */
    private String dictName;

    /**
     * 自定义预搜索sql查询语句 此字段不为空时，则提供预搜索功能
     */
    private String sqlText;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 输入框类型 1文本输入框 2数字输入框 3日期输入框
     */
    private Integer inputType;

    /**
     * 最大输入
     */
    @TableField("`max_size`")
    private Integer maxSize;


    /**
     * 最小输入
     */
    @TableField("`min_size`")
    private Integer minSize;

    private static final long serialVersionUID = 1L;
}