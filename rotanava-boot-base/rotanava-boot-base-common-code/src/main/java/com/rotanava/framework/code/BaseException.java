package com.rotanava.framework.code;


import lombok.Data;

@Data
public abstract class BaseException extends RuntimeException {

    protected ErrorCode errorCode;

//    public BaseException(CodeType codeType){
//        this.codeType=codeType;
//    }

    protected void after(Exception e){

    }

    protected  void before(Exception e){

    }

    protected abstract   void syncRun(Exception e);

    public void run(){
                after(this);
                syncRun(this);
                before(this);

    }
}
