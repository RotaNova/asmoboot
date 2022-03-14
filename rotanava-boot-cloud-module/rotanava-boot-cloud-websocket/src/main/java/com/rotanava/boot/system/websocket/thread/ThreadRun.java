package com.rotanava.boot.system.websocket.thread;

public abstract class ThreadRun<T,E> implements Runnable{
    private T param;
    private E param2;
    public ThreadRun(T param,E param2){
        this.param=param;
        this.param2=param2;
    }
    @Override
    public void run() {
        try {
             execute(param,param2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void execute(T param, E param2) throws Exception;



    }
