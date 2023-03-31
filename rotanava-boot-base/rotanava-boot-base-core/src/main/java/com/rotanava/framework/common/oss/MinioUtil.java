package com.rotanava.framework.common.oss;


import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.oss.model.MinioResultBean;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.StringUtil;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * minio工具类
 */
@Log4j2
@Component
public class MinioUtil implements InitializingBean {

    private MinioClient client;

    @Value("${minio.endpoint}")
    public String endpoint;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secretKey}")
    public String secretKey;

    @Value("${rotanava.uploadType}")
    private String uploadType;

    @Value("${minio.externalUrl:}")
    private String externalUrl;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (client == null && CommonConstant.UPLOAD_TYPE_MINIO.equals(uploadType)) {
            try {
                initClient();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("minio初始化失败，失败异常为{}",e);
            }
        }
    }

    public void initClient() throws Exception{
        client = new MinioClient(endpoint, accessKey, secretKey);

    }




    public static MinioUtil getMinioUtil(String endpoint,String accessKey, String secretKey) {
        MinioUtil minioUtil = new MinioUtil();
        minioUtil.setEndpoint(endpoint);
        minioUtil.setAccessKey(accessKey);
        minioUtil.setAccessKey(secretKey);
        try {
            minioUtil.initClient();
            return minioUtil;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("minio初始化失败，失败异常为{}", e);
        }

       return null;
    }






    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
    }


    public void setBucketPolicy(String bucketName,String policy){
        try {
//            client.setBucketPolicy(bucketName, policy);
            String bucketPolicy = client.getBucketPolicy(bucketName);
            System.out.println();
        }catch (Exception e){
            log.error("minio setBucketPolicy error",e);
        }
    }


    public void setBucketPolicy(String bucketName){
        setBucketPolicy(bucketName,"readonly");
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {

        return client.listBuckets();
    }

    @SneakyThrows
    public boolean isExitBucket(String bucketsName) {
        return client.bucketExists(bucketsName);
    }


    public boolean isExitObject(String bucketsName, String objName) {
        try {
            InputStream input = client.getObject(bucketsName, objName);
            if (input == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SneakyThrows
    public InputStream getObjectStream(String bucketsName, String objName) {
        return client.getObject(bucketsName, objName);
    }

    @SneakyThrows
    public MinioResultBean upload(String bucketsName, String objName, InputStream inputStream, String contentType) {
        //不存在就创建
        createBucket(bucketsName);
//        client.putObject(bucketsName, objName, inputStream, new PutObjectOptions(inputStream.available(), -1));
        client.putObject(bucketsName, objName, inputStream, contentType);
        MinioResultBean minioResultBean = new MinioResultBean();
        minioResultBean.setBucketName(bucketsName);
        minioResultBean.setObjName(objName);
        return minioResultBean;
    }

    /**
     * 获取文件url
     *
     * @param bucketsName
     * @param objName
     * @param type
     * @param time        秒
     * @return
     */
    @SneakyThrows
    private String getObjetcUrl(String bucketsName, String objName, int type, int time) {
        String url = null;
        try {
            if (type == 1) {
                url = client.getObjectUrl(bucketsName, objName);
            } else {
                url = client.presignedGetObject(bucketsName, objName, time);
            }
        }catch (Exception e){
            e.getMessage();
            return null;
        }

        return parseOutUrl(url);
    }

    public String parseOutUrl(String url){
        if (StringUtil.isNullOrEmpty(externalUrl)){
            return url;
        }
        int i = url.indexOf("/", 8);

        String substring = url.substring(0, i);

        String replace = url.replace(substring, externalUrl);

        return replace;
    }



    @SneakyThrows
    public String getObjetcUrl(String bucketsName, String objName) {
        return getObjetcUrl(bucketsName, objName, 1, 0);
    }

    @SneakyThrows
    public String getTimeUrl(String bucketsName, String objName, int time) {
        return getObjetcUrl(bucketsName, objName, 2, time);
    }

    /**
     * @param inputStream
     * @param suffix
     * @return
     * @Title: uploadImage
     * @Description:上传图片
     */
    @SneakyThrows
    public MinioResultBean uploadImage(String bucketName, String objName, InputStream inputStream, String suffix) {
        return upload(bucketName, objName, inputStream, "image/jpeg");
    }

    @SneakyThrows
    public MinioResultBean uploadImage(String bucketName, InputStream inputStream, String suffix) {
        String objName = BaseUtil.getUId() + suffix;
        return uploadImage(bucketName, objName, inputStream, "image/jpeg");
    }

    /**
     * @param inputStream
     * @param suffix
     * @return
     * @Title: uploadImage
     * @Description:上传视频
     */
    @SneakyThrows
    public MinioResultBean uploadVideo(String bucketName, InputStream inputStream, String suffix) {
        String objName = BaseUtil.getUId() + suffix;
        return uploadVideo(bucketName, objName, inputStream);
    }

    /**
     * @param inputStream
     * @return
     * @Title: uploadImage
     * @Description:上传视频
     */
    @SneakyThrows
    public MinioResultBean uploadVideo(String bucketName, String objName, InputStream inputStream) {
        return upload(bucketName, objName, inputStream, "video/mp4");
    }

    /**
     * @param inputStream
     * @param suffix
     * @return
     * @Title: uploadFile
     * @Description:上传文件
     */
    @SneakyThrows
    public MinioResultBean uploadFile(String bucketName, InputStream inputStream, String suffix) {
        String objName = BaseUtil.getUId() + suffix;
        return upload(bucketName, objName, inputStream, "application/octet-stream");
    }

    /**
     * @param inputStream
     * @param suffix
     * @return
     * @Title: uploadFile
     * @Description:上传文件
     */
    @SneakyThrows
    public MinioResultBean uploadFile(String bucketName,String objectName, InputStream inputStream, String suffix) {
        String objName = objectName + suffix;
        return upload(bucketName, objName, inputStream, "application/octet-stream");
    }

    /**
     * @param bucketName
     * @param text
     * @return
     * @Title: uploadString
     * @Description:上传字符串
     */
    @SneakyThrows
    public MinioResultBean uploadString(String bucketName, String text) {
        InputStream inputStream = new ByteArrayInputStream(text.getBytes());
        String objName = BaseUtil.getUId();
        MinioResultBean minioResultBean = upload(bucketName, objName, inputStream, "text/html");
        return minioResultBean;
    }


    @SneakyThrows
    public void rmObject(String bucketName, String objName) {
        client.removeObject(bucketName, objName);
    }

    @SneakyThrows
    public void rmObjectList(String bucketName, String objName) {
        client.removeObject(bucketName, objName);
    }


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
