package com.rotanava.boot.system.api.module.constant;

/**
 * 页面资源是否为叶子节点
 *
 * @author WeiQiangMiao
 * @date 2021-03-10
 */
public enum  PageLeafFlag {

    //页面资源是否为叶子节点:0-不是;1-是

    /**
     * 不是
     */
    IS_NOT(0),


    /**
     * 是的
     */
    YES(1)


    ;
    private Integer pageLeafFlag;

    PageLeafFlag(Integer pageLeafFlag) {
        this.pageLeafFlag = pageLeafFlag;
    }

    public Integer getPageLeafFlag() {
        return pageLeafFlag;
    }

    public void setPageLeafFlag(Integer pageLeafFlag) {
        this.pageLeafFlag = pageLeafFlag;
    }
}
