package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-30 17:04
 **/
@Data
public class IndexConfigVO {
    private String bannerUrl;

    private String logoUrl;

    /**
     * banner是否可关闭选项
     */
    private Integer bannerCloseOption;
}
