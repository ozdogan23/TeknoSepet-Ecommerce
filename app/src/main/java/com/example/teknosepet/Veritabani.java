package com.example.teknosepet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Veritabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TeknoSepetDB";
    private static final int DATABASE_VERSION = 4; // Versiyon 4

    private static final String TABLO_SEPET = "sepet";
    private static final String TABLO_FAVORI = "favoriler";
    private static final String TABLO_SIPARIS = "siparisler";
    private static final String TABLO_KULLANICI = "kullanicilar";

    public Veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabloları Oluştur
        db.execSQL("CREATE TABLE " + TABLO_SEPET + "(id INTEGER PRIMARY KEY AUTOINCREMENT, urun_adi TEXT, fiyat TEXT, resim_id INTEGER)");
        db.execSQL("CREATE TABLE " + TABLO_FAVORI + "(id INTEGER PRIMARY KEY AUTOINCREMENT, urun_adi TEXT, fiyat TEXT, resim_id INTEGER)");
        db.execSQL("CREATE TABLE " + TABLO_SIPARIS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, urun_adi TEXT, fiyat TEXT, resim_id INTEGER, tarih TEXT, durum TEXT)");

        // Kullanıcı Tablosu (Email Anahtar Kelimedir)
        db.execSQL("CREATE TABLE " + TABLO_KULLANICI + "(email TEXT PRIMARY KEY, sifre TEXT, ad_soyad TEXT, telefon TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_SEPET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_FAVORI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_SIPARIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_KULLANICI);
        onCreate(db);
    }

    // --- MEVCUT METOTLAR (Sepet, Favori, Sipariş) ---
    public void sepeteEkle(String ad, String f, int r) { SQLiteDatabase db=this.getWritableDatabase(); ContentValues v=new ContentValues(); v.put("urun_adi",ad); v.put("fiyat",f); v.put("resim_id",r); db.insert(TABLO_SEPET,null,v); db.close(); }
    public boolean sepetBosMu() { SQLiteDatabase db=this.getReadableDatabase(); Cursor c=db.rawQuery("SELECT COUNT(*) FROM "+TABLO_SEPET,null); int s=0; if(c.moveToFirst()) s=c.getInt(0); c.close(); return s==0; }
    public void sepetiTemizle() { SQLiteDatabase db=this.getWritableDatabase(); db.execSQL("DELETE FROM "+TABLO_SEPET); db.close(); }
    public void favoriyeEkle(String ad, String f, int r) { SQLiteDatabase db=this.getWritableDatabase(); ContentValues v=new ContentValues(); v.put("urun_adi",ad); v.put("fiyat",f); v.put("resim_id",r); db.insert(TABLO_FAVORI,null,v); db.close(); }
    public boolean favoriBosMu() { SQLiteDatabase db=this.getReadableDatabase(); Cursor c=db.rawQuery("SELECT COUNT(*) FROM "+TABLO_FAVORI,null); int s=0; if(c.moveToFirst()) s=c.getInt(0); c.close(); return s==0; }
    public void siparisEkle(String ad, String f, int r, String t, String d) { SQLiteDatabase db=this.getWritableDatabase(); ContentValues v=new ContentValues(); v.put("urun_adi",ad); v.put("fiyat",f); v.put("resim_id",r); v.put("tarih",t); v.put("durum",d); db.insert(TABLO_SIPARIS,null,v); db.close(); }
    public boolean siparisVarMi() { SQLiteDatabase db=this.getReadableDatabase(); Cursor c=db.rawQuery("SELECT COUNT(*) FROM "+TABLO_SIPARIS,null); int s=0; if(c.moveToFirst()) s=c.getInt(0); c.close(); return s==0; }

    // --- KULLANICI İŞLEMLERİ (YENİ) ---

    // 1. Yeni Kullanıcı Ekleme
    public boolean kullaniciEkle(String email, String sifre, String ad, String tel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("email", email); v.put("sifre", sifre); v.put("ad_soyad", ad); v.put("telefon", tel);
        long r = db.insert(TABLO_KULLANICI, null, v);
        db.close();
        return r != -1;
    }

    // 2. Bilgileri Getir (Ad ve Tel döner)
    public String[] kullaniciBilgileriniGetir(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLO_KULLANICI + " WHERE email=?", new String[]{email});
        String[] bilgiler = new String[2];
        if (c.moveToFirst()) {
            bilgiler[0] = c.getString(c.getColumnIndexOrThrow("ad_soyad"));
            bilgiler[1] = c.getString(c.getColumnIndexOrThrow("telefon"));
        }
        c.close(); db.close();
        return bilgiler;
    }

    // 3. Bilgileri Güncelle
    public boolean kullaniciGuncelle(String email, String yeniAd, String yeniTel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("ad_soyad", yeniAd); v.put("telefon", yeniTel);
        long r = db.update(TABLO_KULLANICI, v, "email=?", new String[]{email});
        db.close();
        return r != -1;
    }

    // 4. Şifre Doğru mu? (Değiştirirken eski şifreyi sormak için)
    public boolean sifreDogruMu(String email, String girilenSifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLO_KULLANICI + " WHERE email=? AND sifre=?", new String[]{email, girilenSifre});
        boolean sonuc = c.getCount() > 0;
        c.close(); db.close();
        return sonuc;
    }

    // 5. Şifre Değiştir
    public boolean sifreDegistir(String email, String yeniSifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("sifre", yeniSifre);
        long r = db.update(TABLO_KULLANICI, v, "email=?", new String[]{email});
        db.close();
        return r != -1;
    }
    // --- FAVORİ LİSTELEME VE SİLME (YENİ EKLENDİ) ---

    // 1. Tüm Favorileri Getir
    public Cursor favorileriGetir() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Tüm favori ürünleri çekiyoruz
        return db.rawQuery("SELECT * FROM " + TABLO_FAVORI, null);
    }

    // 2. Favoriden Sil (ID'ye göre)
    public void favoridenSil(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLO_FAVORI, "id=?", new String[]{id});
        db.close();
    }
}