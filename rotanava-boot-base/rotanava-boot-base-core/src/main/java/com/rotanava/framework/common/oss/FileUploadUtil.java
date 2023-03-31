package com.rotanava.framework.common.oss;


import com.ejlchina.okhttps.HTTP;
import com.rotanava.aliyun.oss.cfg.EndpointType;
import com.rotanava.aliyun.oss.model.OssResultBean;
import com.rotanava.aliyun.oss.util.OssClientUtil;

import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.oss.model.MinioResultBean;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.ImageUtil;
import com.rotanava.framework.util.StringUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.SysErrorCode;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileUploadUtil {

    /**
     * 区分配置，minio  / oss
     */
    @Value("${rotanava.uploadType}")
    private String uploadType;
    @Autowired
    private MinioUtil minioUtil;

    @Value("${rotanava.ossAccessKey:ossAccessKey}")
    public String ossAccessKey;

    @Value("${rotanava.ossAccessKeySecret:ossAccessKeySecret}")
    public String ossAccessKeySecret;

    private ConcurrentHashMap<String, OssClientUtil> ossClientPool = new ConcurrentHashMap<>();


    public void setBucketPolicyReadOnly(String bucketName){


        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                minioUtil.setBucketPolicy(bucketName);
                break;
            case CommonConstant.UPLOAD_TYPE_OSS:
                break;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    public static FileUploadUtil getMinIoFileUploadUtil(String endpoint,String accessKey, String secretKey){
        MinioUtil minioUtil = MinioUtil.getMinioUtil(endpoint, accessKey, secretKey);
        if (minioUtil!=null){
            FileUploadUtil fileUploadUtil = new FileUploadUtil();
            fileUploadUtil.setMinioUtil(minioUtil);
            fileUploadUtil.setUploadType("minio");
            return fileUploadUtil;
        }

        return null;

    }

    /**
     * 获得oss操作对象
     *
     * @param bucketName
     * @return
     */
    private OssClientUtil getOssClient(String bucketName) {
        OssClientUtil clientUtil = ossClientPool.get(bucketName);
        if (clientUtil == null) {
            synchronized (this) {
                if (clientUtil == null) {
                    clientUtil = new OssClientUtil(EndpointType.HANG_ZHOU, bucketName,ossAccessKey,ossAccessKeySecret);
                }
            }
        }
        return clientUtil;
    }

    /**
     * 上传图片
     *
     * @param bucketName
     * @param inputStream
     * @return
     */
    public UploadResultBean uploadFile(String bucketName, InputStream inputStream, String suffix) {
        return uploadFile(bucketName, BaseUtil.getUId(),inputStream,suffix);
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param inputStream
     * @return
     */
    public UploadResultBean uploadFile(String bucketName,String objectName, InputStream inputStream, String suffix) {


        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                MinioResultBean minioResultBean = minioUtil.uploadFile(bucketName,objectName, inputStream, suffix);
                return UploadResultBean.builder().bucketName(minioResultBean.getBucketName()).objName(minioResultBean.getObjName()).build();
            case CommonConstant.UPLOAD_TYPE_OSS:
                objectName=objectName+suffix;
                OssClientUtil oss = getOssClient(bucketName);
                OssResultBean ossResultBean = oss.upLoadObject(objectName, inputStream);
                return UploadResultBean.builder().bucketName(ossResultBean.getBucketName()).objName(ossResultBean.getObjectName()).build();
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param inputStream
     * @return
     */
    public UploadResultBean uploadFile(String bucketName,String objectName, InputStream inputStream) {

       return uploadFile(bucketName,objectName,inputStream,"");
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param fileName
     * @param inputStream
     * @return
     */
    public UploadResultBean uploadImages(String bucketName, String fileName, InputStream inputStream) {
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                MinioResultBean minioResultBean = minioUtil.uploadImage(bucketName, fileName, inputStream, ".jpg");
                return UploadResultBean.builder().bucketName(minioResultBean.getBucketName()).objName(minioResultBean.getObjName()).build();
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                OssResultBean ossResultBean = oss.upLoadObject(fileName, inputStream);
                return UploadResultBean.builder().bucketName(ossResultBean.getBucketName()).objName(ossResultBean.getObjectName()).build();
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 上传图片
     *
     * @param bucketName
     * @param fileName
     * @param base64
     * @return
     */
    public UploadResultBean uploadImages(String bucketName, String fileName, String base64) {
        return uploadImages(bucketName, fileName, ImageUtil.base64ToInputStream(base64));
    }

    /**
     * 上传视频
     *
     * @param bucketName
     * @param fileName
     * @param inputStream
     * @return
     */
    public UploadResultBean uploadVideo(String bucketName, String fileName, InputStream inputStream, String suffix) {
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                MinioResultBean minioResultBean = minioUtil.uploadVideo(bucketName, fileName + "." + suffix, inputStream);
                return UploadResultBean.builder().bucketName(minioResultBean.getBucketName()).objName(minioResultBean.getObjName()).build();
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                OssResultBean ossResultBean = oss.upLoadObject(fileName, inputStream);
                return UploadResultBean.builder().bucketName(ossResultBean.getBucketName()).objName(ossResultBean.getObjectName()).build();
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 获取文件的临时链接
     *
     * @param bucketName
     * @param objName
     * @param time       分钟
     * @return
     */
    public String getObjUrl(String bucketName, String objName, int time) {
        if (StringUtil.isNullOrEmpty(bucketName) || StringUtil.isNullOrEmpty(objName)) {
//            throw new CommonException(ResultCode.SystemError.miniParamError);
            return null;
        }
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                return minioUtil.getTimeUrl(bucketName, objName, time * 60);
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                String url = oss.getObject(objName,time);
                return url;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 获取带样式的临时链接
     *
     * @param bucketName
     * @param objName
     * @param time       分钟
     * @return
     */
    public String getObjUrl(String bucketName, String objName,String style, int time) {
        if (StringUtil.isNullOrEmpty(bucketName) || StringUtil.isNullOrEmpty(objName)) {
//            throw new CommonException(ResultCode.SystemError.miniParamError);
            return null;
        }
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                return minioUtil.getTimeUrl(bucketName, objName, time * 60);
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                String url = oss.getObject(objName,style,1);
                return url;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }


    /**
     * 获取文件的临时链接
     *
     * @param bucketName
     * @param objName
     * @return
     */
    public String getObjUrl(String bucketName, String objName) {
//        if (StringUtil.isNullOrEmpty(bucketName) || StringUtil.isNullOrEmpty(objName)) {
//            throw new CommonException(ResultCode.SystemError.miniParamError);
//        }
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                return minioUtil.getObjetcUrl(bucketName, objName);
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                return oss.getObject(objName);
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objName
     * @return
     */
    public void rmObject(String bucketName, String objName) {
//        if (StringUtil.isNullOrEmpty(bucketName) || StringUtil.isNullOrEmpty(objName)) {
//            throw new CommonException(ResultCode.SystemError.miniParamError);
//        }
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                minioUtil.rmObject(bucketName, objName);
                break;
            case CommonConstant.UPLOAD_TYPE_OSS:
                OssClientUtil oss = getOssClient(bucketName);
                boolean flag = oss.rmFile(objName);
//                if (!flag) {
//                    throw new CommonException(ResultCode.FileUploadError.fileError);
//                }
                break;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objName
     * @return
     */
    public boolean isExitObject(String bucketName, String objName) {
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                return minioUtil.isExitObject(bucketName, objName);
            case CommonConstant.UPLOAD_TYPE_OSS:
                InputStream objectStream = getObjectStream(bucketName, objName);
                if (objectStream==null){
                    return false;
                }
                return true;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    @SneakyThrows
    public InputStream getObjectStream(String bucketName, String objName) {
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_MINIO:
                return minioUtil.getObjectStream(bucketName, objName);
            case CommonConstant.UPLOAD_TYPE_OSS:
                //oss官方没有提供获取文件流的sdk
                String url = getObjUrl(bucketName, objName);
                HTTP http = HTTP.builder()
                        .baseUrl(url)
                        .build();

                InputStream inputStream = http.sync("").get().getBody().toByteStream();

                return inputStream;
            default:
                throw new CommonException(SysErrorCode.SYS_ERROR_06);
        }
    }

    public InputStream getUrlStream(String url){
        HTTP http = HTTP.builder()
                .baseUrl(url)
                .build();
        InputStream inputStream = http.sync("").get().getBody().toByteStream();
        return inputStream;
    }


    public String getUrlBase64(String url){
        InputStream urlStream = getUrlStream(url);
        if (urlStream==null){
            return null;
        }else {
            return ImageUtil.getBase64FromInputStream(urlStream);
        }
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public void setMinioUtil(MinioUtil minioUtil) {
        this.minioUtil = minioUtil;
    }
}
