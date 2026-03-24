package com.example.teknosepet;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private int imageResource; // Resim ID'si
    private String description;
    private int stock;
    private boolean isFavorite;

    // --- 1. YENİ KURUCU (Veritabanı İçin - 7 Özellikli) ---
    // VeritabanıYardimcisi bunu kullanıyor
    public Product(int id, String name, int price, int imageResource, String description, int stock, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.description = description;
        this.stock = stock;
        this.isFavorite = isFavorite;
    }

    // --- 2. ESKİ KURUCU (Elle Yazdığın Listeler İçin - 5 Özellikli) ---
    // PhoneActivity, TabletActivity vb. bunu kullanıyor. (Bozulmaması için ekledik)
    public Product(String name, int imageResource, String description, int stock, int price) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.id = -1; // Veritabanında değilse ID -1 olsun
        this.isFavorite = false;
    }

    // --- GETTER METOTLARI (Verilere Ulaşmak İçin Şart) ---
    // "getPrice" hatasını bunlar çözecek
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getImageResource() { return imageResource; } // "image" hatasını bu çözecek
    public String getDescription() { return description; }
    public int getStock() { return stock; }
    public boolean isFavorite() { return isFavorite; }

    // --- SETTER METOTLARI (Değişiklik Yapmak İçin) ---
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setStock(int stock) { this.stock = stock; }
}