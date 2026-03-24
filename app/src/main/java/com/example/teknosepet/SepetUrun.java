package com.example.teknosepet;

public class SepetUrun {
    private String urunAdi;
    private String fiyat;
    private int resimId;

    public SepetUrun(String urunAdi, String fiyat, int resimId) {
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
        this.resimId = resimId;
    }

    public String getUrunAdi() { return urunAdi; }
    public String getFiyat() { return fiyat; }
    public int getResimId() { return resimId; }
}