package com.rotanava.framework.async;


import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class ThreadPoolUtil {

    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    private static ThreadPoolExecutor threadPool;

    private static final String THREAD_NAME = "rotanava";

    /**
     * 无返回值直接执行
     * Runnable 线程对象
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }


    /**
     * 多线程获取返回值
     *
     * @param callable
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }


    /**
     * dcs获取线程池
     *
     * @return 线程池对象
     */
//    private static ThreadPoolExecutor getThreadPool() {
//        if (threadPool != null) {
//            return threadPool;
//        } else {
//            synchronized (ThreadPoolUtil.class) {
//                if (threadPool == null) {
//                    threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, 32, 30, TimeUnit.SECONDS,
//                            new LinkedBlockingQueue<>(128), r -> {
//                        Thread t = new Thread(Thread.currentThread().getThreadGroup(), r, THREAD_NAME + "-" + THREAD_COUNT);
//                        if (t.isDaemon()) {
//                            t.setDaemon(false);
//                        }
//                        if (t.getPriority() != Thread.NORM_PRIORITY) {
//                            t.setPriority(Thread.NORM_PRIORITY);
//                        }
//                        return t;
//                    }, new ThreadPoolExecutor.CallerRunsPolicy());
//                }
//                return threadPool;
//            }
//        }
//    }
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    final int availableProcessors = Runtime.getRuntime().availableProcessors();
                    threadPool = new ThreadPoolExecutor(availableProcessors, availableProcessors * 2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(availableProcessors * 10), r -> {
                        final Thread thread = new Thread(Thread.currentThread().getThreadGroup(), r, THREAD_NAME + "-" + THREAD_COUNT);
                        THREAD_COUNT.incrementAndGet();
                        return thread;
                    }, new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
            }
        }
    }


}


