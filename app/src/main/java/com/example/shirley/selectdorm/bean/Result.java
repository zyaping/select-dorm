package com.example.shirley.selectdorm.bean;

public class Result {
    private String errcode;
    private ResultData data;

    @Override
    public String toString() {
        return "Result{" +
                "errcode='" + errcode + '\'' +
                ", data=" + data +
                '}';
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public ResultData getData() {
        return data;
    }

    public void setData(ResultData data) {
        this.data = data;
    }
}