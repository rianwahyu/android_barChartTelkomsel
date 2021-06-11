package com.bibitproject.aplikasidatachart.Staff.Model;

public class Monitoring {
    String reg, cri, maj, min;


    public Monitoring(String reg, String cri, String maj, String min) {
        this.reg = reg;
        this.cri = cri;
        this.maj = maj;
        this.min = min;
    }

    public String getReg() {
        return reg;
    }

    public String getCri() {
        return cri;
    }

    public String getMaj() {
        return maj;
    }

    public String getMin() {
        return min;
    }
}
