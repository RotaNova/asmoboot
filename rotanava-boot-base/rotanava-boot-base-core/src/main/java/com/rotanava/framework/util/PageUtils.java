package com.rotanava.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rotanava.framework.model.BaseDTO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 分页工具类
 * @author: richenLi
 * @create: 2021-03-08 16:23
 **/
public class PageUtils {

    public static <T> IPage<T> startPage() {

        // 从本地线程中获取请求
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();

        int pageNum = 1;
        int pageSize = 10;

        String pageNumStr = request.getParameter("pageNum");
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        IPage<T> page = new Page<>(pageNum, pageSize);
        return page;
    }

    public static <T> IPage<T> startPage(BaseDTO baseDTO) {
        int pageNum = 1;
        int pageSize = 10;

        if (!StringUtil.isNullOrEmpty(baseDTO.getPageNum())) {
            if (baseDTO.getPageNum() > 0) {
                pageNum = baseDTO.getPageNum();
            }
        }
        if (!StringUtil.isNullOrEmpty(baseDTO.getPageSize())) {
            if (baseDTO.getPageSize() > 0) {
                pageSize = baseDTO.getPageSize();
            }
        }
        IPage<T> page = new Page<>(pageNum, pageSize);
        return page;
    }

    public static <T> IPage<T> startPage(JSONObject json) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setPageNum(json.getIntValue("pageNum"));
        baseDTO.setPageSize(json.getIntValue("pageSize"));
        return startPage(baseDTO);
    }

    /**
     * 功能: 转换ipage的数据
     * 作者: zjt
     * 日期: 2021/3/9 11:58
     * 版本: 1.0
     */
    public static <T> IPage<T> conversionIpageRecords(IPage iPage, Class<?> conversionClass, List<T> records) {
        iPage.setRecords(records);
        return iPage;
    }


    /**
     * 功能: 创建空列表
     * 作者: zjt
     * 日期: 2021/10/9 14:19
     * 版本: 1.0
     */
    public static IPage createBlankPageItem() {
        return new Page();
    }
}
