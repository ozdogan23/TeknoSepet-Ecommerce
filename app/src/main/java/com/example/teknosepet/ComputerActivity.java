package com.example.teknosepet;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ComputerActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        // Standart Mavi Barı Gizle (Tablet tasarımı için şart)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new VeritabaniYardimcisi(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 1. Veritabanından "Computer" kategorisini çek
        productList = db.kategoriyeGoreGetir("Computer");

        // 2. Eğer liste boşsa (ilk açılışsa), verileri kaydet ve tekrar çek
        if (productList.isEmpty()) {
            verileriYukle();
            productList = db.kategoriyeGoreGetir("Computer");
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // --- GERİ BUTONU (Mor Buton) ---
        // XML içinde TextView'e 'btnBack' ID'si verdik, tıklayınca kapatacak.
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void verileriYukle() {
        // Senin attığın listedeki ürünleri veritabanına ekliyoruz
        db.urunEkle("Huawei MateBook D15", 24500, R.drawable.pc1, 5, "İşlemci: Intel Core i7\nRAM: 16 GB\nSSD: 512 GB", "Computer");
        db.urunEkle("MSI Modern 14", 21500, R.drawable.pc2, 12, "İşlemci: Intel Core i7\nRAM: 8 GB\nSSD: 512 GB", "Computer");
        db.urunEkle("Apple MacBook Pro M2", 48500, R.drawable.pc3, 7, "İşlemci: Apple M2\nRAM: 16 GB\nSSD: 512 GB", "Computer");
        db.urunEkle("HP Victus Gaming", 36500, R.drawable.pc4, 20, "İşlemci: Intel Core i7\nRTX 3060\nRAM: 16 GB", "Computer");
        db.urunEkle("Lenovo Legion 5", 33500, R.drawable.pc5, 15, "İşlemci: Ryzen 7\nRTX 3050 Ti\nRAM: 16 GB", "Computer");
        db.urunEkle("Dell XPS 15", 52500, R.drawable.pc6, 12, "İşlemci: Intel Core i7\n4K UHD Ekran\nRAM: 16 GB", "Computer");
        db.urunEkle("Lenovo IdeaPad 3", 14500, R.drawable.pc7, 28, "İşlemci: Intel Core i3\nRAM: 8 GB\nSSD: 256 GB", "Computer");
        db.urunEkle("Asus ZenBook 14", 39500, R.drawable.pc8, 10, "İşlemci: Intel Core i7\nOLED Ekran\nRAM: 16 GB", "Computer");
        db.urunEkle("MacBook Pro 16", 98500, R.drawable.pc9, 8, "İşlemci: M2 Pro\nRAM: 32 GB\nSSD: 1 TB", "Computer");
        db.urunEkle("Casper Nirvana X600", 22500, R.drawable.pc10, 3, "İşlemci: Intel Core i5\nRAM: 16 GB\nSSD: 512 GB", "Computer");
    }
}