/**
 * @Author: Greyfus
 * @Create: 2022-07-06 10:43
 * @Version: 1.0.0
 * @Description:compress the file into ZIP format,Unzip ZIP format
 * PS:JDK原生的ZipOutInputStream默认编码为UTF8,而中文默认编码为GBK,如果对中文文件压缩可能出现压缩失败的情况，
 * 采用org.apache.ant封装的ZipOutInputStream,通过setEncoding()方法可以解决这个问提。
 * JDK原生的ZipFile默认编码为UTF8，使用org.apache.ant封装的ZipFile,通过new ZipFile(srcZipFile, "GBK")构造函数
 * 可以解决中文文件解压后乱码的问题。
 */
package com.rotanava.framework.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class ZIPFileUtils {


    private static final Logger LOGGER = LoggerFactory.getLogger(ZIPFileUtils.class);

    private ZIPFileUtils() {

    }

    /**
     * @param srcDir            源文件路径
     * @param targetDir         压缩文件路径
     * @param maintainStructure 压缩时是否按照源文件结构模式压缩
     */
    public static void zipFile(String srcDir, String targetDir, boolean maintainStructure) {
        checkParameters(srcDir, targetDir);
        zipFile(new File(srcDir), new File(targetDir), maintainStructure);
    }

    /**
     * @param srcDir            源文件
     * @param targetDir         压缩文件
     * @param maintainStructure 压缩时是否按照源文件结构模式压缩
     */
    public static void zipFile(File srcDir, File targetDir, boolean maintainStructure) {
        try {
            zipFile(srcDir, new BufferedOutputStream(new FileOutputStream(targetDir)), maintainStructure);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void zipFile(File srcDir, OutputStream out, boolean maintainStructure) {

        if (!srcDir.exists()) {
            throw new RuntimeException(ZIPFileEnum.SOURCE_FILE_NOT_EXISTS.getMessage());
        }

        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            /**
             * JDK原生的ZipOutputStream默认的是编码是UTF,然而中文编码是GBK，导致原生的JDK提供的API有时候无法压缩中文文件夹
             *
             */
            long startTime = System.currentTimeMillis();
            zos.setEncoding("GBK");
            compress(srcDir, zos, srcDir.getName(), maintainStructure);
            LOGGER.info("ZIP files spend [{}] millisecond", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩文件具体流程
     *
     * @param srcDir
     * @param zos
     * @param entryName         记录每个文件的路径
     * @param maintainStructure
     */
    private static void compress(File srcDir, ZipOutputStream zos, String entryName, boolean maintainStructure) {

        if (srcDir.isFile()) {

            try (FileInputStream in = new FileInputStream(srcDir)) {

                zos.putNextEntry(new ZipEntry(entryName));
                int len;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1) {
                    zos.write(bytes, 0, len);
                }
                zos.closeEntry();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (srcDir.isDirectory()) {

            File[] listFiles = srcDir.listFiles();
            if (ArrayUtils.isEmpty(listFiles)) {
                /**
                 * 处理空文件夹压缩问题,JDK原生API无法处理中文件夹压缩
                 */
                try {
                    zos.putNextEntry(new ZipEntry(entryName + "/"));
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            for (File srcFile : listFiles) {
                if (maintainStructure) {
                    compress(srcFile, zos, entryName + File.separator + srcFile.getName(), maintainStructure);
                } else {
                    compress(srcFile, zos, srcFile.getName(), maintainStructure);
                }
            }
        }
    }

    /**
     * 解压文件
     *
     * @param srcZipFile zip文件路径
     * @param targetFile 解压后的存储地址
     */
    public static void unzipFile(String srcZipFile, String targetFile) {
        checkParameters(srcZipFile, targetFile);
        unzipFile(new File(srcZipFile), targetFile);
    }

    /**
     * 解压文件
     *
     * @param srcZipFile zip文件
     * @param targetDir  解压后的存储地址
     */
    public static void unzipFile(File srcZipFile, String targetDir) {
        if (!srcZipFile.exists()) {
            throw new RuntimeException(ZIPFileEnum.SOURCE_FILE_NOT_EXISTS.getMessage());
        }
        Project project = new Project();
        Expand expand = new Expand();
        expand.setProject(project);
        expand.setSrc(srcZipFile);
        expand.setOverwrite(true);
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        expand.setDest(file);
        expand.execute();
    }


    private static void checkParameters(String srcDir, String targetDir) {
        if (StringUtils.isEmpty(srcDir)) {
            throw new RuntimeException(ZIPFileEnum.SOURCE_FILE_IS_EMPTY.getMessage());
        }
        if (StringUtils.isEmpty(targetDir)) {
            throw new RuntimeException(ZIPFileEnum.TARGET_FILE_IS_EMPTY.getMessage());
        }
        if (!new File(srcDir).exists()) {
            throw new RuntimeException(ZIPFileEnum.SOURCE_FILE_NOT_EXISTS.getMessage());
        }
    }

    private enum ZIPFileEnum {

        SOURCE_FILE_IS_EMPTY("0001", "源文件为空！"),
        TARGET_FILE_IS_EMPTY("0002", "目标文件为空！"),
        SOURCE_FILE_NOT_EXISTS("0003", "源文件不存在！");

        private String code;
        private String message;

        ZIPFileEnum(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        unzipFile("C:\\Users\\weiqiangmiao\\Downloads\\钉钉\\新建文件夹\\老鼠检测22.zip","C:\\Users\\weiqiangmiao\\Downloads\\钉钉\\新建文件夹\\");

    }

}



