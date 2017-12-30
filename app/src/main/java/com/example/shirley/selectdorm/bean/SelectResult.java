package com.example.shirley.selectdorm.bean;

public class SelectResult {
    private String errcode;

    @Override
    public String toString() {
        return "SelectResult{" +
                "errorcode='" + errcode + '\'' +
                '}';
    }

    public String getError_code() {
        return errcode;
    }

    public void setError_code(String error_code) {
        this.errcode = error_code;
    }
}
