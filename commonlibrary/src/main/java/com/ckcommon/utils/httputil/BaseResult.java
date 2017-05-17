package com.ckcommon.utils.httputil;

/**
 * Created by ruitu on 2016/11/17.
 */

public  class BaseResult<T> {


    /**
     * result : {}
     * code : 2000
     * message : OK
     */

    private T result;
    private int code;
    private String message;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
