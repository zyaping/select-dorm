package com.example.shirley.selectdorm.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PerInfo implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private String errcode;
    private PerInfoData data;


    @Override
    public String toString() {
        return errcode + " " + data.toString();
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public PerInfoData getData() {
        return data;
    }

    public void setData(PerInfoData data) {
        this.data = data;
    }
}

