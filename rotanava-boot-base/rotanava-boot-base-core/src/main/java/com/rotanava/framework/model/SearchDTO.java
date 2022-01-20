package com.rotanava.framework.model;

import com.rotanava.framework.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索dto
 *
 * @author WeiQiangMiao
 * @date 2021-04-15
 */
@Data
public class SearchDTO implements Serializable {

    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String sort;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String sortColumn;

    /**
     * 高级搜索
     */
    @ApiModelProperty(value = "高级搜索 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private List<SearchRuleDTO> queryRuleList;

    /**
     * 搜索匹配
     */
    @ApiModelProperty(value = "搜索匹配 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String superMatchType;

    /**
     * 高级搜索编码
     */
    @ApiModelProperty(value = "高级搜索编码 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String searchCode;


    public boolean getSearchCodeIsNull(String key) {
        if (this.queryRuleList!=null) {
            for (SearchRuleDTO searchRuleDTO : queryRuleList) {
                if (key.equals(searchRuleDTO.getFiled())) {
                    return StringUtil.isNullOrEmpty(searchRuleDTO.getValue());
                }
            }
        }
        return true;
    }

    public SearchRuleDTO getSearchCode(String key) {
        if (this.queryRuleList!=null) {
            for (SearchRuleDTO searchRuleDTO : queryRuleList) {
                if (key.equals(searchRuleDTO.getFiled())) {
                    return searchRuleDTO;
                }
            }
        }
        return null;
    }

    public void setSearchCodeValue(String key, String rule, String value) {

        SearchRuleDTO searchRuleDTO = new SearchRuleDTO();
        searchRuleDTO.setFiled(key);
        searchRuleDTO.setRule(rule);
        searchRuleDTO.setValue(value);
        if (this.queryRuleList == null) {
            this.queryRuleList = new ArrayList<>();
        }
        this.queryRuleList.add(searchRuleDTO);
    }
}
