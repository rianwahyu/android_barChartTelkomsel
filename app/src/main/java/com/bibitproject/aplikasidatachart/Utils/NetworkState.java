package com.bibitproject.aplikasidatachart.Utils;

public class NetworkState {

    static String url = "http://192.168.1.8/alarmData/";
    //static String url = "http://138.138.39.130/alarmData/";
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
