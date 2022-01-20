package com.rotanava.framework.code;




public class CommonException extends BaseException {



    public CommonException(ErrorCode errorCode) {
        super.errorCode = errorCode;
//        super(codeType);
    }


    @Override
    protected void syncRun(Exception e) {

    }
}
