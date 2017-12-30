package com.example.shirley.selectdorm.bean;

public class Remain {
    private String errcode;
    private RemainData data;

    @Override
    public String toString() {
        return "Remain{" +
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

    public RemainData getData() {
        return data;
    }

    public void setData(RemainData data) {
        this.data = data;
    }
}
