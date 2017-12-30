package com.example.shirley.selectdorm.bean;

public class ResultData {
    private String errmsg;

    @Override
    public String toString() {
        return "ResultData{" +
                "errmsg='" + errmsg + '\'' +
                '}';
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
