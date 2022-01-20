package com.rotanava.boot.system.api.module.constant;

/**
 * 页面删除状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-08
 */
public enum PageDeleteStatus {
    //0-已删除;
    DELETED(0),
    //1-未删除
    NOT_DELETED(1);

    //页面资源删除状态 0-已删除;1-未删除

    private Integer status;

    PageDeleteStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
