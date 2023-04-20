package com.rotanava.boot.system.util;

import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.StringUtil;
import org.apache.commons.collections.SetUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-12-14 16:27
 **/
@Component
public class IPRegionUtil {

    private static Searcher globalSearcher;

    static {
        initIPRegion();
    }

    public static void initIPRegion(){
        byte[] cBuff;
        try {
            ClassPathResource resource = new ClassPathResource("ip2region.xdb");
            String path = null;
            if (!BaseUtil.isLinux()){
                path = resource.getFile().getCanonicalPath();
            }else {
                path = "/isc/ip2region.xdb";
            }


            cBuff = Searcher.loadContentFromFile(path);
            globalSearcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getRegionByIp(String ip) {
        String regionText = "未知IP";
        try {
            if (globalSearcher==null){
                initIPRegion();
            }
            String region = globalSearcher.search(ip.trim());
            if (StringUtil.isNullOrEmpty(region)) {
                return regionText;
            }
            if (region.contains("内网")) {
                return "内网IP";
            }
            StringBuffer sb = new StringBuffer();
            String[] split = region.split("\\|");
            for (String str : split) {
                if ("0".equals(str)) {
                    continue;
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            regionText="错误IP";
        }
        return regionText;
    }

    public static void main(String[] args) {
        System.out.println(getRegionByIp("27.155.140.150"));
    }
}
