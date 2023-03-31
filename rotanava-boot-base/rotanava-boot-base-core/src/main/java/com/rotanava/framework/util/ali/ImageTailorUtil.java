package com.rotanava.framework.util.ali;


import cn.hutool.core.util.NumberUtil;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.ImageUtil;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-04-08 14:10
 **/
@Log4j2
public class ImageTailorUtil {

    /**
     * @param srcImg 源图片
     * @param output 图片输出流
     * @param rect   需要截取部分的坐标和大小
     */
    public static InputStream cutImage(InputStream fis, ByteArrayOutputStream output, Rectangle rect, String suffix) {
        try {
            long begin =System.currentTimeMillis();
//            String base64 = ImageUtil.getBase64FromInputStream(fis);
//            BufferedImage imageInfo = ImageUtil.getImageInfo(base64);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            long end =System.currentTimeMillis();
//            log.info("----------------------------初始化耗时{}ms-------------------",(end-begin));
            begin =System.currentTimeMillis();
            Thumbnails.of(fis)
                    .sourceRegion(rect.x, rect.y, rect.width, rect.height)
                    .size(rect.width, rect.height)
//                    .keepAspectRatio(false)
                    .toOutputStream(byteArrayOutputStream);
            end =System.currentTimeMillis();
            log.info("----------------------------裁剪图片耗时{}ms-------------------",(end-begin));
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return byteArrayInputStream;
        }catch (Exception e){
            e.printStackTrace();
        }


//        ImageInputStream iis = null;
//        try {
//
//            // 将FileInputStream 转换为ImageInputStream
//            iis = ImageIO.createImageInputStream(fis);
            // 根据图片类型获取该种类型的ImageReader
            ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
//
//
//            reader.setInput(iis, true);
//            ImageReadParam param = reader.getDefaultReadParam();
//            param.setSourceRegion(rect);
//
//            long begin =System.currentTimeMillis();
//            BufferedImage bi = reader.read(0, param);
//
//
//            ImageIO.write(bi, suffix, output);
//
//
//            ByteArrayInputStream fileInpustStream = new ByteArrayInputStream(output.toByteArray());
//            long end =System.currentTimeMillis();
////            log.info("----------------------------剪裁图片耗时{}ms-------------------",(end-begin));
//
//            return fileInpustStream;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fis != null) fis.close();
//                if (iis != null) iis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }



    public static InputStream cutImage(InputStream inputStream, String suffix, int x, int y, int width, int height, int n, int imageWidth, int imageHeight) {

        int xMin = (int) Math.round(Math.max(0, NumberUtil.sub(NumberUtil.add(x, NumberUtil.div(width, 2)), NumberUtil.mul(NumberUtil.div(n, 2), width))));
        int yMix = (int) Math.round(Math.max(0, NumberUtil.sub(NumberUtil.add(y, NumberUtil.div(height, 2)), NumberUtil.mul(NumberUtil.div(n, 2), height))));
        int xMax = (int) Math.round(Math.min(imageWidth, NumberUtil.add(NumberUtil.add(x, NumberUtil.div(width, 2)), NumberUtil.mul(NumberUtil.div(n, 2), width))));
        int yMax = (int) Math.round(Math.min(imageHeight, NumberUtil.add(NumberUtil.add(y, NumberUtil.div(height, 2)), NumberUtil.mul(NumberUtil.div(n, 2), height))));
        int w = Math.max(0, xMax - xMin);
        int h = Math.max(0, yMax - yMix);
        Rectangle rectangle = new Rectangle(xMin, yMix, w, h);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return cutImage(inputStream, byteArrayOutputStream, rectangle, suffix);
    }

    public static InputStream cutImage(InputStream inputStream, String suffix, int x, int y, int width, int height) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return cutImage(inputStream, byteArrayOutputStream, new Rectangle(x, y, width, height), suffix);
    }

    public static String getFileType(InputStream inputStream) {
        InputStream fis = inputStream;
        /**
         * 根据文件名称，获取后缀名的方式，但是不保险
         */
        String extension = "JPEG";
        try {
            byte[] bs = new byte[1];
            fis.read(bs);
            String type = Integer.toHexString(bs[0] & 0xFF);
            if ("ff".equalsIgnoreCase(type)) {
                extension = "JPEG";
            }
            if ("89".equalsIgnoreCase(type)) {
                extension = "PNG";
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extension;
    }


}
