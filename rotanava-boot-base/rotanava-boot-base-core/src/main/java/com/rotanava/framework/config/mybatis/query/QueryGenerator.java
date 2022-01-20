package com.rotanava.framework.config.mybatis.query;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.constant.enums.QueryRuleEnum;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.SearchDTO;
import com.rotanava.framework.model.SearchRuleDTO;
import com.rotanava.framework.model.bo.SysSearchConfig;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.SpringContextUtils;
import com.rotanava.framework.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 条件构造器
 * @author: richenLi
 * @create: 2021-03-11 16:32
 **/
@Log4j2
@Component
public class QueryGenerator {
    @DubboReference
    CommonApi commonApi;


    /**排序方式*/
    public static final String ORDER_TYPE = "order";
    public static final String ORDER_TYPE_ASC = "ASC";
    public static final String ORDER_TYPE_DESC = "DESC";
    public static final String ORDER_TYPE_ASC_2 = "ascend";
    public static final String ORDER_TYPE_DESC_2 = "descend";
    private static String[] sqlValidParam = new String[]{"%", "or", "OR", "`", "and", "AND"};

    /**
     * @description : param传参方式
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public static <T> QueryWrapper<T> initQueryWrapper() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String sort = request.getParameter("sort");
        String sortColumn = request.getParameter("sortColumn");
        String queryRuleListStr = request.getParameter("queryRuleList");
        JSONArray jsonArray = JSONArray.parseArray(queryRuleListStr);
        List<SearchRuleDTO> searchRuleDTOS = jsonArray.toJavaList(SearchRuleDTO.class);
        String superMatchType = request.getParameter("superMatchType");
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setSort(sort);
        baseDTO.setSortColumn(sortColumn);
        baseDTO.setSuperMatchType(superMatchType);
        baseDTO.setQueryRuleList(searchRuleDTOS);
        return initQueryWrapper(baseDTO);
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
        Matcher matcher = pattern.matcher("or 1=1");
        if (matcher.find()) {
            log.info("非法");
        }
    }

    /**
     * @description : body 传参方式
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public static <T> QueryWrapper<T> initQueryWrapper(SearchDTO searchDTO) {


        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        //排序
        if (!StringUtil.isNullOrEmpty(searchDTO.getSortColumn())){
            final String sort = searchDTO.getSort();
            if (sort.equals(ORDER_TYPE_DESC) || sort.equals(ORDER_TYPE_DESC_2)){
                queryWrapper.orderByDesc(searchDTO.getSortColumn());
            }
            if (sort.equals(ORDER_TYPE_ASC) || sort.equals(ORDER_TYPE_ASC_2)){
                queryWrapper.orderByAsc(searchDTO.getSortColumn());
            }
        }
        if (searchDTO.getQueryRuleList() == null) {
            return queryWrapper;
        }



        if (StringUtil.isNullOrEmpty(searchDTO.getSearchCode())) {
            return queryWrapper;
        }


        for (SearchRuleDTO searchRuleDTO :  searchDTO.getQueryRuleList()) {
            String value = searchRuleDTO.getValue();
            Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                log.error("非法请求参数,{}", value);
                throw new CommonException(SysErrorCode.SYS_ERROR_08);
            }
//            for (String validParam : sqlValidParam) {
//                if (value.contains(validParam)) {
//                    log.error("非法请求参数,{}", value);
//                    throw new CommonException(SysErrorCode.SYS_ERROR_08);
//                }
//            }
        }

        //获取当前的页面id
        String  searchCode = searchDTO.getSearchCode();
        CommonApi commonApi = getSysSearchConfigMapper();
        //key 为reqName value为SysSearchConfig 对象
        HashMap<String, SysSearchConfig> searchConfigMap = parseSearchList(commonApi.getConfigByPageId(searchCode));

        for (SearchRuleDTO searchRuleDTO : searchDTO.getQueryRuleList()) {
            SysSearchConfig sysSearchConfig = searchConfigMap.get(searchRuleDTO.getFiled());

            if ("OR".equals(searchDTO.getSuperMatchType())){
                queryWrapper.or(andWrapper -> {
                    addEasyQuery(andWrapper, sysSearchConfig.getDbColumn(), QueryRuleEnum.getByValue(searchRuleDTO.getRule()), searchRuleDTO.getValue());

                });
            }else {
                queryWrapper.and(andWrapper -> {
                    addEasyQuery(andWrapper, sysSearchConfig.getDbColumn(), QueryRuleEnum.getByValue(searchRuleDTO.getRule()), searchRuleDTO.getValue());
                });
            }
        }

        return queryWrapper;
    }

    public static <T> LambdaQueryWrapper<T> initLambdaQueryWrapper() {
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper();
        return queryWrapper.lambda();
    }

    public static <T> LambdaQueryWrapper<T> initLambdaQueryWrapper(BaseDTO baseDTO) {
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        return queryWrapper.lambda();
    }


    private static HashMap<String, SysSearchConfig> parseSearchList(List<SysSearchConfig> configByPageId) {
        HashMap<String, SysSearchConfig> map = new HashMap<>();
        for (SysSearchConfig sysSearchConfig : configByPageId) {
            map.put(sysSearchConfig.getReqName(), sysSearchConfig);
        }
        return map;
    }

    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value) {
        if (value == null || rule == null || StringUtil.isNullOrEmpty(value)) {
            return;
        }
        //name = BaseUtil.camelToUnderline(name);
        log.info("--查询规则-->" + name + " " + rule.getValue() + " " + value);
        switch (rule) {
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GE:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LE:
                queryWrapper.le(name, value);
                break;
            case EQ:
                queryWrapper.eq(name, value);
                break;
            case NE:
                queryWrapper.ne(name, value);
                break;
            case IN:
                if (value instanceof String) {
                    queryWrapper.in(name, (Object[]) value.toString().split(","));
                } else if (value instanceof String[]) {
                    queryWrapper.in(name, (Object[]) value);
                } else if (value.getClass().isArray()) {
                    queryWrapper.in(name, (Object[]) value);
                } else {
                    queryWrapper.in(name, value);
                }
                break;
            case LIKE:
                queryWrapper.like(name, value);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(name, value);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(name, value);
                break;
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }

    public static CommonApi getSysSearchConfigMapper() {
        return SpringContextUtils.getBean(QueryGenerator.class).commonApi;
    }


}
