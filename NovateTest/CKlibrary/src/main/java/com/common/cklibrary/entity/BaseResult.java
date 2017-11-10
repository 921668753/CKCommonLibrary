package com.common.cklibrary.entity;

/**
 * Created by ruitu on 2017/6/17.
 */

public class BaseResult<T> {


    /**
     * result : {}
     * code : 2000
     * message : OK
     */

    private T result;
    private int status;
    private String msg;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
