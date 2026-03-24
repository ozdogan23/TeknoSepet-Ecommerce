package com.example.teknosepet;

public class Kategori {
    private String kategoriAdi;
    private int resimId;

    public Kategori(String kategoriAdi, int resimId) {
        this.kategoriAdi = kategoriAdi;
        this.resimId = resimId;
    }

    // Adapter bu isimleri arıyor, o yüzden birebir böyle olmalı:
    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public int getResimId() {
        return resimId;
    }
}