package com.rotanava.framework.util;


//import com.google.api.client.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Log4j2
public class ImageUtil {

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static BufferedImage getImageInfo(String chart) {
        try {
            if (StringUtils.isNotEmpty(chart)) {
                byte[] decoder = new BASE64Decoder().decodeBuffer(chart);
                InputStream is = new ByteArrayInputStream(decoder);
                return ImageIO.read(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    public static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (Exception e) {
            log.error("BufferedImage 编码转换为 base64 出错 原因 = {}", e.getMessage());
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return png_base64;
    }





    /**
     * base64转输入流
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static InputStream base64ToInputStream(String base64) {
        InputStream in = null;
        //将字符串转换为byte数组
        byte[] bytes = new byte[0];
        try {
            bytes = new BASE64Decoder().decodeBuffer(base64.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //转化为输入流
        in = new ByteArrayInputStream(bytes);
        return in;
    }

    // 图片转化成base64字符串
    public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }


    public static void getPhoto(HttpServletResponse response, String path) throws Exception {
        File file = new File(path);
        FileInputStream fis;
        fis = new FileInputStream(file);

        long size = file.length();
        byte[] temp = new byte[(int) size];
        fis.read(temp, 0, (int) size);
        fis.close();
        byte[] data = temp;
        response.setContentType("image/jpg");
        data = Base64.encodeBase64(data);
        OutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
        out.close();

    }




    /**
     * @Description： base64字符串转化成图片
     * @param: imgStr
     * @Return:
     */
    public static String SaveBase64Image(String imgStr, String photoname, String location, String directory) {
        //对字节数组字符串进行Base64解码并生成图片
        //图像数据为空
        if (imgStr == null)
            return null;


        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            photoname = photoname + ".jpg";
            String path = location;
            //生成jpeg图片

            String imagePath = path + directory;
            File tempFile = new File(imagePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            //新生成的图片
            String imgFilePath = tempFile.getPath() + File.separator + photoname;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return directory + photoname;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void outMP4(HttpServletResponse response, String importPath) throws IOException {
        //读取路径下面的文件
        File file = new File(importPath);
        response.setContentType("video/mpeg4");

        //读取指定路径下面的文件
        InputStream in = new FileInputStream(file);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        //创建存放文件内容的数组
        byte[] buff = new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while ((n = in.read(buff)) != -1) {
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff, 0, n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }

    /**
     * <p>将文件转成base64 字符串</p>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) {
        try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void outImage(HttpServletResponse response, String importPath) throws IOException {
        //读取路径下面的文件
        File file = new File(importPath);
        String ext = null;
        if (file.getName().indexOf(".") != -1) {
            ext = file.getName().substring(file.getName().indexOf("."));
        } else {
            return;
        }
        //判断图片格式,设置相应的输出文件格式
        if (ext.equals("jpg")) {
            response.setContentType("image/jpeg");
        } else if (ext.equals("JPG")) {
            response.setContentType("image/jpeg");
        } else if (ext.equals("png")) {
            response.setContentType("image/png");
        } else if (ext.equals("PNG")) {
            response.setContentType("image/png");
        }

        //读取指定路径下面的文件
        InputStream in = new FileInputStream(file);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        //创建存放文件内容的数组
        byte[] buff = new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while ((n = in.read(buff)) != -1) {
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff, 0, n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }

    public static String saveVideoFile(InputStream inputStream, String path) {

        OutputStream os = null;
        String imagePath = path;
        try {
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(imagePath);

            os = new FileOutputStream(tempFile.getPath());
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imagePath;
    }

    public static String saveFile(InputStream inputStream, String path,String fileName) {

        OutputStream os = null;
//        String fileName = UUID.randomUUID().toString() + ".jpg";
        String imagePath = path;
        try {
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(imagePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static String approvalFile(MultipartFile filecontent, String path) {

        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = UUID.randomUUID().toString() + ".jpg";
//        String directory = DateUtil.getYear() + "\\" + DateUtil.getWeekDay() + "\\";
        String imagePath = path;
        try {
            inputStream = filecontent.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Linux 文件保存的路径
//            path ="/root/tmp/";
//            path = "D:\\test\\";
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;


            // 输出的文件流保存到本地文件
            File tempFile = new File(imagePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static String approvalFile(InputStream inputStream, String path) {

        OutputStream os = null;
        String fileName = UUID.randomUUID().toString() + ".jpg";
//        String directory = DateUtil.getYear() + "\\" + DateUtil.getWeekDay() + "\\";
        String imagePath = path;
        try {
            // Linux 文件保存的路径
//            path ="/root/tmp/";
//            path = "D:\\test\\";
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;


            // 输出的文件流保存到本地文件
            File tempFile = new File(imagePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    //计算base64图片的字节数(单位:字节)
    //传入的图片base64是去掉头部的data:image/png;base64,字符串
    public static Integer imageSize(String imageBase64Str) {

        //1.找到等号，把等号也去掉(=用来填充base64字符串长度用)
        Integer equalIndex = imageBase64Str.indexOf("=");
        if (imageBase64Str.indexOf("=") > 0) {
            imageBase64Str = imageBase64Str.substring(0, equalIndex);
        }
        //2.原来的字符流大小，单位为字节
        Integer strLength = imageBase64Str.length();
        System.out.println("imageBase64Str Length = " + strLength);
        //3.计算后得到的文件流大小，单位为字节
        Integer size = strLength - (strLength / 8) * 2;
        return size;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytesToKB(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 1, BigDecimal.ROUND_DOWN).floatValue();
        if (returnValue > 1) {
            return (returnValue + "MB");
        }

        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 1, BigDecimal.ROUND_DOWN).floatValue();
        return (returnValue + "KB");
    }

    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }


    /**
     * 获取多边形的中心
     * @param str
     * @return
     */
    public static Point2D.Double getCenterOfGravityPoint(List<Point2D.Double> mPoints) {
        double area = 0.0;//多边形面积
        double Gx = 0.0, Gy = 0.0;// 重心的x、y
        for (int i = 1; i <= mPoints.size(); i++) {
            double iLat = mPoints.get(i % mPoints.size()).getX();
            double iLng = mPoints.get(i % mPoints.size()).getY();
            double nextLat = mPoints.get(i - 1).getX();
            double nextLng = mPoints.get(i - 1).getY();
            double temp = (iLat * nextLng - iLng * nextLat) / 2.0;
            area += temp;
            Gx += temp * (iLat + nextLat) / 3.0;
            Gy += temp * (iLng + nextLng) / 3.0;
        }
        Gx = Gx / area;
        Gy = Gy / area;

        Point2D.Double point = new Point2D.Double(Gx,Gy);
        return point;
    }





    /****
     * 判断点是否在多边形内（不包含边界）
     *
     * @author richenLi
     * @param point 点
     * @param polygon 多边形
     * @return
     */
    public static boolean checkWithGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {


        GeneralPath p = new GeneralPath();
        if (polygon.size() <= 0) {
            return false;
        }
        // 初始化起点坐标
        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
//        polygon.remove(0);


        for (Point2D.Double d : polygon) {

            // 遍历点，并按遍历的顺序画线
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);

        p.closePath();
        return p.contains(point);
    }


    /**
     * @description: 点在矩形
     * @param {*} x0 校验
     * @param {*} y0 校验
     * @param {*} x1
     * @param {*} x2
     * @param {*} y1
     * @param {*} y2
     * @return {*}
     */
    public static boolean isPointInTwo(Point2D.Double point, List<Point2D.Double> polygon) {
        Double x0 = point.x;
        Double y0 = point.y;
        Double  x1 = polygon.get(0).x;
        Double x2 = polygon.get(2).x;
        Double y1 = polygon.get(0).y;
        Double y2 =  polygon.get(1).y;

        if (
                x0 > Math.min(x1, x2) &&
                        x0 < Math.max(x1, x2) &&
                        y0 > Math.min(y1, y2) &&
                        y0 < Math.max(y1, y2)
        ) {
            //在
            return true;
        } else {
            // 不在
            return false;
        }


    }
    /**
     * @description: 点在矩形
     * @param {*} x0 校验
     * @param {*} y0 校验
     * @param {*} x1
     * @param {*} x2
     * @param {*} y1
     * @param {*} y2
     * @return {*}
     */
    public static boolean isPointInTwo(Double x0, Double y0,Double  x1,Double x2, Double y1, Double y2) {


        if (
                x0 > Math.min(x1, x2) &&
                        x0 < Math.max(x1, x2) &&
                        y0 > Math.min(y1, y2) &&
                        y0 < Math.max(y1, y2)
        ) {
            //在
            return true;
        } else {
            // 不在
            return false;
        }


    }

    /****
     * 判断点是否在多边形内（不包含边界）
     *
     * @author richenLi
     * @param point 点
     * @param polygon 多边形
     * @return
     */
    public static boolean checkWithGeneralPath(Double x,Double y ,Double w ,Double h, List<Point2D.Double> polygon) {


        GeneralPath p = new GeneralPath();
        if (polygon.size() <= 0) {
            return false;
        }
        // 初始化起点坐标
        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        for (Point2D.Double d : polygon) {

            // 遍历点，并按遍历的顺序画线
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);

        p.closePath();
        return p.contains(x,y,w,h);
    }


    public static Integer base64GetWidth(String image) {
        try {
            InputStream inputStream = ImageUtil.base64ToInputStream(image);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return bufferedImage.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Integer base64GetHeight(String image) {
        try {
            InputStream inputStream = ImageUtil.base64ToInputStream(image);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return bufferedImage.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double StandardDiviation(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return Math.sqrt(dVar/m);
    }

    public static double[][] img2color(BufferedImage img){
        double[][] doubles = new double[3][];
        try {
            int[] colors = new int[img.getWidth() * img.getHeight()];

            img.getRGB(0, 0, img.getWidth(), img.getHeight(), colors, 0, img.getWidth());

            double[] red = new double[colors.length];

            double[] green = new double[colors.length];

            double[] blue = new double[colors.length];

            for (int i = 0; i < colors.length; i++) {

                Color color = new Color(colors[i]);

                red[i] = (double)color.getRed();

                green[i] =(double) color.getGreen();

                blue[i] = (double)color.getBlue();
            }


            doubles[0]=red;
            doubles[1]=green;
            doubles[2]=blue;
        }catch (Exception e){
            e.printStackTrace();
        }
        return doubles;
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();

        int h = img.getHeight();

        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());

        Graphics2D g = dimg.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,

                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);

        g.dispose();

        return dimg;

    }
}
