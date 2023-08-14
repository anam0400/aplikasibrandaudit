package com.aplikasi.brand_audit;

public class RIwayat {
    private String deskripsi;
    private Integer photo;

    public RIwayat() {

    }
    public RIwayat(String deskripsi, Integer photo) {
        this.deskripsi = deskripsi;
        this.photo = photo;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }
}
