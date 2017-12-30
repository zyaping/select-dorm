package com.example.shirley.selectdorm.bean;
import com.google.gson.annotations.SerializedName;

public class RemainData {
    @SerializedName("5")
    private String m5;
    @SerializedName("13")
    private String m13;
    @SerializedName("14")
    private String m14;
    @SerializedName("8")
    private String m8;
    @SerializedName("9")
    private String m9;

    @Override
    public String toString() {
        return "RemainData{" +
                "m5='" + m5 + '\'' +
                ", m13='" + m13 + '\'' +
                ", m14='" + m14 + '\'' +
                ", m8='" + m8 + '\'' +
                ", m9='" + m9 + '\'' +
                '}';
    }

    public String getM5() {
        return m5;
    }

    public void setM5(String m5) {
        this.m5 = m5;
    }

    public String getM13() {
        return m13;
    }

    public void setM13(String m13) {
        this.m13 = m13;
    }

    public String getM14() {
        return m14;
    }

    public void setM14(String m14) {
        this.m14 = m14;
    }

    public String getM8() {
        return m8;
    }

    public void setM8(String m8) {
        this.m8 = m8;
    }

    public String getM9() {
        return m9;
    }

    public void setM9(String m9) {
        this.m9 = m9;
    }
}
