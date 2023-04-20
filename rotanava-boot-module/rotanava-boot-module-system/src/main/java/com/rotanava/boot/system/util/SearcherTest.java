package com.rotanava.boot.system.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class SearcherTest {
    public static void main(String[] args) throws IOException {
        // 1、创建 searcher 对象
        File file = new File("classpath:\\ip2region.xdb");

        ClassPathResource resource = new ClassPathResource("ip2region.xdb");
        String path = resource.getFile().getCanonicalPath();

        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(path);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", path, e);
            return;
        }
        String ip = "202.181.14.23";
        // 2、查询
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(Searcher.checkIP(ip));
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }

        // 3、关闭资源
        searcher.close();

        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
    }
}