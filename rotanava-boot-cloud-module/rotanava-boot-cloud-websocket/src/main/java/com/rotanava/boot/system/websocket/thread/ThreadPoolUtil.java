package com.rotanava.boot.system.websocket.thread;



import java.util.concurrent.*;

public class ThreadPoolUtil {

    public static ThreadPoolExecutor threadPool;

    /**
     * 无返回值直接执行
     * ThreadRun<?,?> 可以传递两个参数给新线程使用
     * @param runnable
     */
    public  static void execute(ThreadRun<?,?> runnable){
        getThreadPool().execute(runnable);
    }

    /**
     * 无返回值直接执行
     * ThreadRun<?,?> 可以传递两个参数给新线程使用
     * @param runnable
     */
    public  static void execute(Runnable runnable){
        getThreadPool().execute(runnable);
    }


    /**
     * 多线程获取返回值
     * @param callable
     */
    public  static <T> Future<T> submit(Callable<T> callable){
        return   getThreadPool().submit(callable);
    }


    /**
     * dcs获取线程池
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
            }
        }
    }
}
