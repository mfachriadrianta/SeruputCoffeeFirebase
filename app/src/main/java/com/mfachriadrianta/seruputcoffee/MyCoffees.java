package com.mfachriadrianta.seruputcoffee;

public class MyCoffees {

    String nama_coffee, lokasi;
    String jumlah_coffee;

    public MyCoffees() {
    }

    public MyCoffees(String nama_coffee, String lokasi, String jumlah_coffee) {
        this.nama_coffee = nama_coffee;
        this.lokasi = lokasi;
        this.jumlah_coffee = jumlah_coffee;
    }

    public String getNama_coffee() {
        return nama_coffee;
    }

    public void setNama_coffee(String nama_coffee) {
        this.nama_coffee = nama_coffee;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah_coffee() {
        return jumlah_coffee;
    }

    public void setJumlah_coffee(String jumlah_coffee) {
        this.jumlah_coffee = jumlah_coffee;
    }
}
