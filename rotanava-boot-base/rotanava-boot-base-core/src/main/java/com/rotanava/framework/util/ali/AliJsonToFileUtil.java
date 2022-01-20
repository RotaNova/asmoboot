package com.rotanava.framework.util.ali;

import com.alibaba.fastjson.JSONObject;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-10-15 16:09
 **/
@Component
public class AliJsonToFileUtil {

    @Autowired
    FileUploadUtil fileUploadUtil;

    public String jsonToFile(Object obj){
        //将json写入文件，并上传到oss
        ByteArrayInputStream inputStream = new ByteArrayInputStream(JSONObject.toJSONString(obj).getBytes());
        UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.DEVICE_BUCKET, inputStream, ".rn");
        String url = fileUploadUtil.getObjUrl(uploadResultBean.getBucketName(),uploadResultBean.getObjName());
        return url;
    }
}
