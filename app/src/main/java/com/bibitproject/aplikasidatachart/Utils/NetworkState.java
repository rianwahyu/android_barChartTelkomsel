package com.bibitproject.aplikasidatachart.Utils;

public class NetworkState {
    /*static String url = "https://bibittechno.000webhostapp.com/simas/";*/
    static String url = "http://192.168.43.194/alarmData/";
    static String urlPhoto =  url+"photo/";
    static String urlPdf =  url+"pdf/";

    public static String getUrl() {
        return url;
    }

    public static String getUrlPhoto() {
        return urlPhoto;
    }

    public static String getUrlPdf() {
        return urlPdf;
    }
}
