package com.example.teknosepet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        db = new VeritabaniYardimcisi(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 1. Veritabanından Telefonları Çek
        productList = db.kategoriyeGoreGetir("Phone");

        // 2. Eğer liste boşsa (Uygulama ilk kez kurulduysa) verileri yükle
        if (productList.isEmpty()) {
            verileriYukle();
            // Yükledikten sonra tekrar çek
            productList = db.kategoriyeGoreGetir("Phone");
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Geri Butonu
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void verileriYukle() {
        // 1️⃣ iPhone 16
        db.urunEkle("iPhone 16", 79999, R.drawable.telefon1, 12,
                "Ekran: 6.1\" Super Retina XDR\nİşlemci: Apple A18\nRAM: 8 GB\nDepolama: 256 GB\nKamera: 48 MP\nBatarya: 3500 mAh",
                "Phone");

        // 2️⃣ Xiaomi 15T Pro
        db.urunEkle("Xiaomi 15T Pro", 42999, R.drawable.telefon2, 18,
                "Ekran: 6.67\" AMOLED 144Hz\nİşlemci: Dimensity 9300+\nRAM: 12 GB\nDepolama: 512 GB\nKamera: 50 MP Leica\nBatarya: 5000 mAh",
                "Phone");

        // 3️⃣ iPhone 15
        db.urunEkle("iPhone 15", 55999, R.drawable.telefon3, 20,
                "Ekran: 6.1\" OLED\nİşlemci: Apple A16 Bionic\nRAM: 6 GB\nDepolama: 128 GB\nKamera: 48 MP\nBatarya: 3279 mAh",
                "Phone");

        // 4️⃣ Redmi 13C
        db.urunEkle("Xiaomi Redmi 13C", 9999, R.drawable.telefon4, 35,
                "Ekran: 6.74\" IPS LCD\nİşlemci: Helio G85\nRAM: 6 GB\nDepolama: 128 GB\nKamera: 50 MP\nBatarya: 5000 mAh",
                "Phone");

        // 5️⃣ Samsung Galaxy Z Fold 7
        db.urunEkle("Samsung Galaxy Z Fold 7", 99999, R.drawable.telefon5, 6,
                "Ekran: 7.6\" Dynamic AMOLED 2X\nİşlemci: Snapdragon 8 Gen 4\nRAM: 12 GB\nDepolama: 512 GB\nKamera: 50 MP\nBatarya: 4600 mAh",
                "Phone");

        // 6️⃣ Redmi Note 14
        db.urunEkle("Redmi Note 14", 16999, R.drawable.telefon6, 22,
                "Ekran: 6.67\" AMOLED\nİşlemci: Snapdragon 7s Gen 2\nRAM: 8 GB\nDepolama: 256 GB\nKamera: 108 MP\nBatarya: 5100 mAh",
                "Phone");

        // 7️⃣ Samsung Galaxy S25 FE
        db.urunEkle("Samsung Galaxy S25 FE", 29999, R.drawable.telefon7, 15,
                "Ekran: 6.4\" AMOLED 120Hz\nİşlemci: Exynos 2500\nRAM: 8 GB\nDepolama: 256 GB\nKamera: 50 MP\nBatarya: 4700 mAh",
                "Phone");

        // 8️⃣ Xiaomi 14 Pro
        db.urunEkle("Xiaomi 14 Pro", 45999, R.drawable.telefon8, 10,
                "Ekran: 6.73\" AMOLED LTPO\nİşlemci: Snapdragon 8 Gen 3\nRAM: 12 GB\nDepolama: 512 GB\nKamera: 50 MP Leica\nBatarya: 4880 mAh",
                "Phone");

        // 9️⃣ OMIX X6
        db.urunEkle("OMIX X6", 7499, R.drawable.telefon9, 40,
                "Ekran: 6.56\" IPS LCD\nİşlemci: MediaTek G25\nRAM: 4 GB\nDepolama: 64 GB\nKamera: 13 MP\nBatarya: 5000 mAh",
                "Phone");

        // 🔟 Samsung Galaxy A Fold
        db.urunEkle("Samsung Galaxy A Fold", 34999, R.drawable.telefon10, 9,
                "Ekran: 7.2\" AMOLED Katlanabilir\nİşlemci: Snapdragon 7 Gen 1\nRAM: 8 GB\nDepolama: 256 GB\nKamera: 48 MP\nBatarya: 4500 mAh",
                "Phone");
    }
}