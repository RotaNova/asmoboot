package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.DeviceAuthService;
import com.rotanava.boot.system.api.SystemAuthService;
import com.rotanava.boot.system.api.module.system.bean.SystemAuthBean;
import com.rotanava.boot.system.api.module.system.bean.SystemTemporaryBean;
import com.rotanava.boot.system.api.module.system.vo.GetAuthInfoVO;
import com.rotanava.framework.util.DESUtil;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.FileUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@Service
@Log4j2
public class SystemAuthServiceImpl implements SystemAuthService {

    @Autowired
    DeviceAuthService deviceAuthService;


    @Override
    public void downloadMachineCode(HttpServletResponse response) {


        String signKey = deviceAuthService.getSignKey();
        byte[] data = signKey.getBytes();
        try {
            String fileName = String.format("%s_auth.rn", Date8Util.format(new Date(), "yyyyMMddHHmmss"));

            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.addHeader("Content-Type", "application/octet-stream;charset=UTF-8");
            response.addHeader("fileName", fileName);
            response.addHeader("Access-Control-Expose-Headers", "fileName");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public GetAuthInfoVO getAuthInfo() {
        GetAuthInfoVO getAuthInfoVO = new GetAuthInfoVO();
        getAuthInfoVO.setSystemTime(System.currentTimeMillis());


        SystemAuthBean authBean = deviceAuthService.getAuthBean();
        SystemTemporaryBean systemTemporaryBean = deviceAuthService.getSystemTemporaryBean();

        if (authBean != null) {
            getAuthInfoVO.setLicenseValid(true);
        }

        if (deviceAuthService.isExpiration()) {
            getAuthInfoVO.setCurEnv(0);
        } else if (authBean != null) {
            getAuthInfoVO.setCurEnv(2);
        } else if (systemTemporaryBean != null) {
            getAuthInfoVO.setCurEnv(1);
        }

        Date authEndTime = deviceAuthService.getAuthEndTime();
        if (authEndTime != null) {
            getAuthInfoVO.setSystemValidityPeriod(authEndTime.getTime());
        }


        return getAuthInfoVO;
    }

    @Override
    public void systemAuthService(MultipartFile file) {
        String rsaSecretKey = null;
        try {
            InputStream inputStream = file.getInputStream();
            rsaSecretKey = FileUtil.readInputStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取文件失败");
            return;
        }
        deviceAuthService.decodeAuth(rsaSecretKey);

    }

    @Override
    public String getAiAuthInfo() {

        SystemAuthBean authBean = deviceAuthService.getAuthBean();
        if (authBean == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        json.put("aiSecretKey", authBean.getAiSecretKey());
        json.put("aiQuota", authBean.getAiQuota());


        String format = Date8Util.format(new Date(), "yyyyMMdd");
        String encrypt = DESUtil.Encrypt(json.toJSONString(), format);
        return encrypt;
    }


}
