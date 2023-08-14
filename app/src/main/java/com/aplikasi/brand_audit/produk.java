package com.aplikasi.brand_audit;

public class produk {
    private String Name;
    private String Tempat;
    private Integer photo;
    private String Jumlah;

    public produk() {
    }


    public produk(String name, String tempat,String jumlah, Integer photo) {
        Name = name;
        Tempat = tempat;
        this.photo = photo;
        this.Jumlah=jumlah;


    }

    public String getName() {
        return Name;
    }

    public String getTempat() {
        return Tempat;
    }

    public Integer getPhoto() {
        return photo;
    }

    public String getJumlah() {
        return Jumlah;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTempat(String tempat) {
        Tempat = tempat;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public void setJumlah(String jumlah) {
        Jumlah = jumlah;
    }
}

