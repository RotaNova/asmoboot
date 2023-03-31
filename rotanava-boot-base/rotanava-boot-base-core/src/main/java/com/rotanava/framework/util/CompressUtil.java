package com.rotanava.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class CompressUtil {
    public static int BUFFER_SIZE = 2048;

    public static boolean zipPath(String sourcePath, String zipFilePath) {
        boolean result = false;
        ZipArchiveEntry entry = null;
        ZipArchiveOutputStream zaos = null;
        InputStream is = null;
        try {
            File path = new File(sourcePath);
            if (path.exists() && path.isDirectory()) {
                File zipFile = new File(zipFilePath);
                zaos = new ZipArchiveOutputStream(zipFile);
                File[] fileList = path.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    File file = fileList[i];
                    if (file.isDirectory()) {
                        zipPathFile(file.getAbsolutePath(), "", zaos);
                    } else {
                        entry = new ZipArchiveEntry(file, file.getName());
                        zaos.setUseZip64(Zip64Mode.AsNeeded);
                        zaos.putArchiveEntry(entry);
                        is = new BufferedInputStream(new FileInputStream(file));
                        byte[] buffer = new byte[1024 * 5];
                        int len = -1;
                        while((len = is.read(buffer)) != -1) {
                            zaos.write(buffer, 0, len);
                        }
                        is.close();
                        zaos.closeArchiveEntry();
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(zaos != null) {
                    zaos.close();
                }
                if (is != null)
                    is.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static void zipPathFile(String sourcePath, String parentPath, ZipArchiveOutputStream zaos) throws Exception {
        File path = new File(sourcePath);
        ZipArchiveEntry entry = null;
        if(StringUtils.isBlank(parentPath)) {
            entry = new ZipArchiveEntry(path, path.getName());
        } else {
            entry = new ZipArchiveEntry(path, parentPath + File.separator + path.getName());
        }

        zaos.setUseZip64(Zip64Mode.AsNeeded);
        zaos.putArchiveEntry(entry);
        zaos.closeArchiveEntry();

        File[] fileList = path.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            File file = fileList[i];
            if (file.isDirectory()) {
                if(StringUtils.isBlank(parentPath)) {
                    zipPathFile(file.getAbsolutePath(), path.getName(), zaos);
                } else {
                    zipPathFile(file.getAbsolutePath(), parentPath + File.separator + path.getName(), zaos);
                }
            } else {
                if(StringUtils.isBlank(parentPath)) {
                    entry = new ZipArchiveEntry(file, path.getName() + File.separator + file.getName());
                } else {
                    entry = new ZipArchiveEntry(file, parentPath + File.separator + path.getName() + File.separator + file.getName());
                }
                zaos.setUseZip64(Zip64Mode.AsNeeded);
                zaos.putArchiveEntry(entry);
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024 * 5];
                int len = -1;
                while((len = is.read(buffer)) != -1) {
                    zaos.write(buffer, 0, len);
                }
                is.close();
                zaos.closeArchiveEntry();
            }
        }
    }

    public static void zip(File[] files, String zipFilePath) {
        if (files != null && files.length > 0) {
            ZipArchiveOutputStream zaos = null;
            try {
                File zipFile = new File(zipFilePath);
                zaos = new ZipArchiveOutputStream(zipFile);
                zaos.setUseZip64(Zip64Mode.AsNeeded);
                for(File file : files) {
                    if(file != null) {
                        ZipArchiveEntry zipArchiveEntry  = new ZipArchiveEntry(file,file.getName());
                        zaos.putArchiveEntry(zipArchiveEntry);
                        InputStream is = null;
                        try {
                            is = new BufferedInputStream(new FileInputStream(file));
                            byte[] buffer = new byte[1024 * 5];
                            int len = -1;
                            while((len = is.read(buffer)) != -1) {
                                zaos.write(buffer, 0, len);
                            }
                            zaos.closeArchiveEntry();
                        } catch(Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            if(is != null)
                                is.close();
                        }
                    }
                }
                zaos.finish();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(zaos != null) {
                        zaos.close();
                    }
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<String> unZip(String filePath, String destDir) throws Exception {
        File zipFile = new File(filePath);
        if(StringUtils.isBlank(destDir)) {
            destDir = zipFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<>();

        try {

            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {

                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                    fileNames.add(entry.getName());
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())));
                        IOUtils.copy(is, os);
                        fileNames.add(entry.getName());
                    } catch (Exception e){
                        log.error("解压{}失败",entry.getName());
                    } finally {
                        if(os != null){
                            IOUtils.closeQuietly(os);
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }

        return fileNames;
    }


    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
//            zipFile = new ZipFile(srcFile, Charset.forName("GBK"));  //含有中文 使用java.util.zip才需要指定这个，后面即使指定了也还是有问题，所以放弃这个改成Ant.jar
            Enumeration<?> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = (ZipArchiveEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}