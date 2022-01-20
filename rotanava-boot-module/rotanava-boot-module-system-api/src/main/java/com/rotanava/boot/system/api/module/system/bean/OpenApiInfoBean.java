package com.rotanava.boot.system.api.module.system.bean;

import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-20 17:38
 **/
@Data
public class OpenApiInfoBean {

    private OpenApi openApi;

    private List<OpenApiParam> requestParam ;

    private List<OpenApiParam> resultParam;

    public void addRequestParam(OpenApiParam openApiParam){
        if (requestParam==null){
            requestParam = new ArrayList<>();
        }
        requestParam.add(openApiParam);
    }

    public void addResultParam(OpenApiParam openApiParam){
        if (resultParam==null){
            resultParam = new ArrayList<>();
        }
        resultParam.add(openApiParam);
    }



    /**
     * @description : 获取执行语句的参数
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public Map<String,Object> getSqlPramMap(JSONObject json){
        Map<String,Object> map = new HashMap<>();
        if (requestParam==null){
            return map;
        }
        for (OpenApiParam openApiParam : requestParam) {
            if (json.containsKey(openApiParam.getCodeAlias())){
                map.put(openApiParam.getParamCode(),json.get(openApiParam.getCodeAlias()));
            }
        }
        return map;
    }

    /**
     * @description : 解析返回的格式
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public List<Map> getResultMap(List<JSONObject> list){
        List<Map> resultList = new ArrayList<>();
        for (JSONObject jsonObject : list) {
            Map resultMap = new HashMap();
            for (OpenApiParam openApiParam : resultParam) {
                Object obj = jsonObject.get(openApiParam.getParamCode());
                if (obj!=null){
                    resultMap.put(openApiParam.getCodeAlias(),obj);
                }
            }
            resultList.add(resultMap);
        }
        return resultList;

    }

}
