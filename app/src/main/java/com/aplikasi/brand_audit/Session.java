package com.aplikasi.brand_audit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;
    public String url           = "http://192.168.217.165/api_brand_audit";
//    public String url           = "https://2db2-140-213-51-36.ngrok.io/brand-audit";
    public String urlAPI        = url + "/api/v1/";
    public String urlAssets     = url + "/assets/images/";
    private static String msg           = "";
    private static String kode_brand    = "";
    private static String dataSampah    = "";
    private static String dataLokasi    = "";
    private static String dataPerusahaan= "";
    private static String dataProdukJenis="";


    public static String getKode_brand() {
        return kode_brand;
    }

    public static void setKode_brand(String kode_brand) {
        Session.kode_brand = kode_brand;
    }

    public static String getDataSampah() {
        return dataSampah;
    }

    public static void setDataSampah(String dataSampah) {
        Session.dataSampah = dataSampah;
    }

    public static String getDataLokasi() {
        return dataLokasi;
    }

    public static void setDataLokasi(String dataLokasi) {
        Session.dataLokasi = dataLokasi;
    }

    public static String getDataPerusahaan() {
        return dataPerusahaan;
    }

    public static void setDataPerusahaan(String dataPerusahaan) {
        Session.dataPerusahaan = dataPerusahaan;
    }

    public static String getDataProdukJenis() {
        return dataProdukJenis;
    }

    public static void setDataProdukJenis(String dataProdukJenis) {
        Session.dataProdukJenis = dataProdukJenis;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        Session.msg = msg;
    }

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLokasi(String lokasi) {
        prefs.edit().putString("lokasi", lokasi).commit();
    }

    public String getLokasi() {
        String lokasi = prefs.getString("lokasi","");
        return lokasi;
    }

    public void setLatitude(String latitude) {
        prefs.edit().putString("latitude", latitude).commit();
    }

    public String getLatitude() {
        String latitude = prefs.getString("latitude","");
        return latitude;
    }

    public void setLongitude(String longitude) {
        prefs.edit().putString("longitude", longitude).commit();
    }

    public String getLongitude() {
        String longitude = prefs.getString("longitude","");
        return longitude;
    }
}
