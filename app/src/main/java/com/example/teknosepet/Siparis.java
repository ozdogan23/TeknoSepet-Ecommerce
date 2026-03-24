package com.example.teknosepet;

public class Siparis {
    private int id;
    private String urunAdi;
    private String fiyat;
    private int resimId;
    private String tarih;
    private String durum;

    public Siparis(int id, String urunAdi, String fiyat, int resimId, String tarih, String durum) {
        this.id = id;
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
        this.resimId = resimId;
        this.tarih = tarih;
        this.durum = durum;
    }

    public int getId() { return id; }
    public String getUrunAdi() { return urunAdi; }
    public String getFiyat() { return fiyat; }
    public int getResimId() { return resimId; }
    public String getTarih() { return tarih; }
    public String getDurum() { return durum; }

    // --- YENİ EKLENEN KISIM ---
    public void setDurum(String durum) {
        this.durum = durum;
    }
}