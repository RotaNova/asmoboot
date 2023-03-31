package com.rotanava.framework.util;

import cn.hutool.core.io.FileUtil;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import org.apache.http.util.TextUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Zip4jUtil {


//    public String extractAll(MultipartFile file,String path) throws Exception {
//
//        if (!new File(path).exists()) {
//            if (!new File(path).mkdirs()) {
//                return "上传文件失败，无法创建临时文件夹";
//            }
//        }
//
//        File dest = new File(path + "/" + file.getOriginalFilename());
//        file.transferTo(dest);
//        /* 解压 */
//        try {
//            ZipFile zip = new ZipFile(dest);
//            zip.setCharset(StandardCharsets.UTF_8);
//            System.out.println("begin unpack zip file....");
//            zip.getFileHeaders().forEach(v -> {
//                String extractedFile = getFileNameFromExtraData(v);
//                try {
//                    zip.extractFile(v, path, extractedFile);
//                } catch (ZipException e) {
//                    System.out.println("解压失败 ：" + extractedFile);
//                    e.printStackTrace();
//                    return;
//                }
//                System.out.println("解压成功 ：" + extractedFile);
//            });
//            System.out.println("unpack zip file success");
//        } catch (ZipException e) {
//            if (!new File(path).mkdirs()) {
//                return "解压失败";
//            }
//        }
//        return "success";
//    }

    public static String getFileNameFromExtraData(FileHeader fileHeader) {
        if (fileHeader.getExtraDataRecords() != null) {
            for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
                long identifier = extraDataRecord.getHeader();
                if (identifier == 0x7075) {
                    byte[] bytes = extraDataRecord.getData();
                    ByteBuffer buffer = ByteBuffer.wrap(bytes);
                    byte version = buffer.get();
                    assert (version == 1);
                    int crc32 = buffer.getInt();
                    System.out.println("使用：fileHeader.getExtraDataRecords() ");
                    return new String(bytes, 5, buffer.remaining(), StandardCharsets.UTF_8);
                }
            }
        }
        return fileHeader.getFileName();
    }

    public static void unZip(String srcFilePath, String destFilePath, String password) {
        try {
            net.lingala.zip4j.ZipFile zFile = new net.lingala.zip4j.ZipFile(srcFilePath);
            zFile.setCharset(StandardCharsets.UTF_8);
            List<FileHeader> headers = zFile.getFileHeaders();
            if (isRandomCode(headers)) {//判断文件名是否有乱码，有乱码，将编码格式设置成GBK
                zFile.close();
                zFile = new net.lingala.zip4j.ZipFile(srcFilePath);
                zFile.setCharset(Charset.forName("GBK"));
            }
            if (!zFile.isValidZipFile()) {
                throw new ZipException("压缩文件不合法,可能被损坏.");
            }
            if (zFile.isEncrypted() && !TextUtils.isEmpty(password)) {//加密zip，且输入的密码不为空，直接进行解密。
                zFile.setPassword(password.toCharArray());
            }
            File destDir = new File(destFilePath);
            if (!destDir.getParentFile().exists()) {
                destDir.mkdir();
            }
            zFile.extractAll(destFilePath);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    //待解压的文件名是否乱码
    public static boolean isRandomCode(List<FileHeader> fileHeaders) {
        for (FileHeader fileHeader : fileHeaders) {
            boolean canEnCode = Charset.forName("GBK").newEncoder().canEncode(fileHeader.getFileName());
            //canEnCode为true，表示不是乱码。false.表示乱码。是乱码则需要重新设置编码格式
            if (!canEnCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件字符集判断
     * @param filepath
     * @param charset
     * @return
     * @throws FileNotFoundException
     */
    private static boolean testEncoding(String filepath, Charset charset) throws FileNotFoundException {
        InputStream fis = new FileInputStream(new File(filepath));
        ZipInputStream zis = new ZipInputStream(fis, charset);
        try {
            LocalFileHeader localFileHeader;
            while ((localFileHeader = zis.getNextEntry()) != null) {
            }
        } catch (Exception e) {
            return false;
        }finally {
            try {
                zis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
