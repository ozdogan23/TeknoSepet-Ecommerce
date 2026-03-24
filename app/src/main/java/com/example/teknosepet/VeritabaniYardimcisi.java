package com.example.teknosepet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class VeritabaniYardimcisi extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MagazaAppFinal.db";
    private static final int DATABASE_VERSION = 1;

    // --- TABLOLAR ---
    public static final String TABLO_URUNLER = "urunler";
    public static final String TABLO_SEPET = "sepet";
    public static final String TABLO_SIPARISLER = "siparisler";
    public static final String TABLO_KULLANICILAR = "kullanicilar";

    // --- SÜTUNLAR ---
    private static final String COL_ID = "id";
    private static final String COL_AD = "urun_adi";
    private static final String COL_FIYAT = "fiyat";
    private static final String COL_RESIM = "resim_id";
    private static final String COL_STOK = "stok";
    private static final String COL_ACIKLAMA = "aciklama";
    private static final String COL_KATEGORI = "kategori";
    private static final String COL_IS_FAV = "is_favori";

    // Sepet & Sipariş Sütunları
    private static final String COL_URUN_ID_REF = "urun_id";
    private static final String COL_TARIH = "tarih";
    private static final String COL_ICERIK = "icerik";
    private static final String COL_DURUM = "durum";

    // Kullanıcı Sütunları
    private static final String COL_KUL_EMAIL = "email";
    private static final String COL_KUL_SIFRE = "sifre";
    private static final String COL_KUL_AD = "ad_soyad";
    private static final String COL_KUL_TEL = "telefon";

    public VeritabaniYardimcisi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO_URUNLER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_AD + " TEXT, " + COL_FIYAT + " INTEGER, " + COL_RESIM + " INTEGER, " + COL_STOK + " INTEGER, " + COL_ACIKLAMA + " TEXT, " + COL_KATEGORI + " TEXT, " + COL_IS_FAV + " INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE " + TABLO_SEPET + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_URUN_ID_REF + " INTEGER, " + COL_AD + " TEXT, " + COL_FIYAT + " INTEGER, " + COL_RESIM + " INTEGER)");
        db.execSQL("CREATE TABLE " + TABLO_SIPARISLER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TARIH + " TEXT, " + COL_ICERIK + " TEXT, " + COL_DURUM + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLO_KULLANICILAR + " (" + COL_KUL_EMAIL + " TEXT PRIMARY KEY, " + COL_KUL_SIFRE + " TEXT, " + COL_KUL_AD + " TEXT, " + COL_KUL_TEL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_URUNLER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_SEPET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_SIPARISLER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_KULLANICILAR);
        onCreate(db);
    }

    // --- ARAMA FONKSİYONU ---
    public ArrayList<Product> urunAra(String arananKelime) {
        ArrayList<Product> bulunanUrunler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sorgu = "SELECT * FROM " + TABLO_URUNLER + " WHERE " + COL_AD + " LIKE '%" + arananKelime + "%'";
        Cursor cursor = db.rawQuery(sorgu, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String ad = cursor.getString(cursor.getColumnIndexOrThrow(COL_AD));
                int fiyat = cursor.getInt(cursor.getColumnIndexOrThrow(COL_FIYAT));
                int resim = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RESIM));
                int stok = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STOK));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(COL_ACIKLAMA));
                int favDurumu = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_FAV));
                bulunanUrunler.add(new Product(id, ad, fiyat, resim, aciklama, stok, favDurumu == 1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bulunanUrunler;
    }

    // --- SİPARİŞ İPTAL ---
    public void siparisIptalEt(int siparisId, String siparisIcerigi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] urunler = siparisIcerigi.split(",");
        for (String urunAdi : urunler) {
            String temizAd = urunAdi.trim();
            if (!temizAd.isEmpty()) {
                db.execSQL("UPDATE " + TABLO_URUNLER + " SET " + COL_STOK + " = " + COL_STOK + " + 1 WHERE " + COL_AD + " = ?", new String[]{temizAd});
            }
        }
        ContentValues values = new ContentValues();
        values.put(COL_DURUM, "İptal Edildi");
        db.update(TABLO_SIPARISLER, values, COL_ID + " = ?", new String[]{String.valueOf(siparisId)});
        db.close();
    }

    // --- DİĞER STANDART METODLAR ---
    public void urunEkle(String ad, int fiyat, int resim, int stok, String aciklama, String kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AD, ad); values.put(COL_FIYAT, fiyat); values.put(COL_RESIM, resim); values.put(COL_STOK, stok); values.put(COL_ACIKLAMA, aciklama); values.put(COL_KATEGORI, kategori); values.put(COL_IS_FAV, 0);
        db.insert(TABLO_URUNLER, null, values); db.close();
    }

    public void sepeteEkle(int urunId, String ad, int fiyat, int resim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_URUN_ID_REF, urunId); values.put(COL_AD, ad); values.put(COL_FIYAT, fiyat); values.put(COL_RESIM, resim);
        db.insert(TABLO_SEPET, null, values); db.close();
    }

    public ArrayList<Product> sepetiGetir() {
        ArrayList<Product> sepetListesi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_SEPET, null);
        if (cursor.moveToFirst()) { do { int urunId = cursor.getInt(1); String ad = cursor.getString(2); int fiyat = cursor.getInt(3); int resim = cursor.getInt(4); sepetListesi.add(new Product(urunId, ad, fiyat, resim, "", 1, false)); } while (cursor.moveToNext()); }
        cursor.close(); db.close(); return sepetListesi;
    }

    public void siparisiOnaylaVeStokDusur(String tarih) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_URUN_ID_REF + ", " + COL_AD + " FROM " + TABLO_SEPET, null);
        StringBuilder icerik = new StringBuilder();
        while (cursor.moveToNext()) { int urunId = cursor.getInt(0); String urunAdi = cursor.getString(1); icerik.append(urunAdi).append(", "); db.execSQL("UPDATE " + TABLO_URUNLER + " SET " + COL_STOK + " = " + COL_STOK + " - 1 WHERE " + COL_ID + " = " + urunId); }
        cursor.close();
        ContentValues values = new ContentValues(); values.put(COL_TARIH, tarih); values.put(COL_ICERIK, icerik.toString()); values.put(COL_DURUM, "Hazırlanıyor");
        db.insert(TABLO_SIPARISLER, null, values); db.execSQL("DELETE FROM " + TABLO_SEPET); db.close();
    }

    public ArrayList<Siparis> siparisleriGetir() {
        ArrayList<Siparis> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_SIPARISLER + " ORDER BY " + COL_ID + " DESC", null);
        if (cursor.moveToFirst()) { do { int id = cursor.getInt(0); String tarih = cursor.getString(1); String icerik = cursor.getString(2); String durum = cursor.getString(3); liste.add(new Siparis(id, icerik, "Ödendi", 0, tarih, durum)); } while (cursor.moveToNext()); }
        cursor.close(); db.close(); return liste;
    }

    public boolean kullaniciEkle(String e, String s, String a, String t) { SQLiteDatabase db=this.getWritableDatabase(); ContentValues v=new ContentValues(); v.put(COL_KUL_EMAIL,e); v.put(COL_KUL_SIFRE,s); v.put(COL_KUL_AD,a); v.put(COL_KUL_TEL,t); long r=db.insert(TABLO_KULLANICILAR,null,v); db.close(); return r!=-1; }
    public boolean kullaniciDogrula(String e, String s) { SQLiteDatabase db=this.getReadableDatabase(); Cursor c=db.rawQuery("SELECT * FROM "+TABLO_KULLANICILAR+" WHERE "+COL_KUL_EMAIL+"=? AND "+COL_KUL_SIFRE+"=?",new String[]{e,s}); boolean r=c.getCount()>0; c.close(); db.close(); return r; }
    public void favoriGuncelle(int urunId, boolean isFav) { SQLiteDatabase db = this.getWritableDatabase(); ContentValues values = new ContentValues(); values.put(COL_IS_FAV, isFav ? 1 : 0); db.update(TABLO_URUNLER, values, COL_ID + " = ?", new String[]{String.valueOf(urunId)}); db.close(); }
    public ArrayList<Product> favorileriGetir() { ArrayList<Product> fav = new ArrayList<>(); SQLiteDatabase db = this.getReadableDatabase(); Cursor c = db.rawQuery("SELECT * FROM " + TABLO_URUNLER + " WHERE " + COL_IS_FAV + " = 1", null); if (c.moveToFirst()) { do { int id = c.getInt(0); String ad = c.getString(1); int f = c.getInt(2); int r = c.getInt(3); int s = c.getInt(4); String ack = c.getString(5); fav.add(new Product(id, ad, f, r, ack, s, true)); } while (c.moveToNext()); } c.close(); db.close(); return fav; }
    public ArrayList<Product> kategoriyeGoreGetir(String k) { ArrayList<Product> l = new ArrayList<>(); SQLiteDatabase db = this.getReadableDatabase(); Cursor c = db.rawQuery("SELECT * FROM " + TABLO_URUNLER + " WHERE " + COL_KATEGORI + " = ?", new String[]{k}); if (c.moveToFirst()) { do { int id = c.getInt(0); String ad = c.getString(1); int f = c.getInt(2); int r = c.getInt(3); int s = c.getInt(4); String ack = c.getString(5); int fav = c.getInt(7); l.add(new Product(id, ad, f, r, ack, s, fav == 1)); } while (c.moveToNext()); } c.close(); db.close(); return l; }
    public void sepettenSil(int urunId) { SQLiteDatabase db = this.getWritableDatabase(); db.execSQL("DELETE FROM " + TABLO_SEPET + " WHERE " + COL_URUN_ID_REF + " = " + urunId); db.close(); }

    // --- YENİ EKLENEN ŞİFRE DEĞİŞTİRME METODU ---
    public boolean sifreDegistir(String email, String eskiSifre, String yeniSifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        // 1. Önce eski şifre doğru mu diye kontrol et
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_KULLANICILAR + " WHERE " + COL_KUL_EMAIL + "=? AND " + COL_KUL_SIFRE + "=?", new String[]{email, eskiSifre});

        if (cursor.getCount() > 0) {
            // Eski şifre doğru, güncelle
            ContentValues values = new ContentValues();
            values.put(COL_KUL_SIFRE, yeniSifre);
            db.update(TABLO_KULLANICILAR, values, COL_KUL_EMAIL + "=?", new String[]{email});
            cursor.close();
            db.close();
            return true;
        } else {
            // Eski şifre yanlış
            cursor.close();
            db.close();
            return false;
        }
    }
}