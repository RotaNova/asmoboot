package com.rotanava.framework.model.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-15 14:25
 **/
@Data
public class SearchRuleVO {

    /**
     * 高级搜索配置id
     */
    private Integer id;

    /**
     * 客户端标题
     */
    private String title;

    /**
     * 输入框类型 1文本输入框 2数字输入框 3日期输入框
     */
    @Dict(dicCode = "searchInputType")
    private Integer inputType;


    /**
     * 高级搜索编码
     */
    private String searchCode;



    /**
     * 是否支持预搜索
     */
    private boolean ispPreview;


    /**
     * 选择框内容
     */
    private List<SearchOptionVO> option = new ArrayList<>();


    /**
     * 请求名
     */
    private String reqName;


    /**
     * 最大输入
     */
    private Integer maxSize;


    /**
     * 最小输入
     */
    private Integer minSize;

    /**
     * 默认搜索规则
     */
    private String defaultRule;


    /**
     * 这个字段前端需要缓存
     */
    private String viewText;


    /**
     * 排序字段
     */
    private Integer sort;



    public void addOption(SearchOptionVO searchOptionVO){
        this.option.add(searchOptionVO);
    }

}
