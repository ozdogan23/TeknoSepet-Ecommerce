package com.example.teknosepet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TabletActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        db = new VeritabaniYardimcisi(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 1. Veritabanından Tabletleri Çek
        productList = db.kategoriyeGoreGetir("Tablet");

        // 2. Eğer liste boşsa verileri yükle ve tekrar çek
        if (productList.isEmpty()) {
            verileriYukle();
            productList = db.kategoriyeGoreGetir("Tablet");
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Geri Butonu
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void verileriYukle() {
        // 1️⃣ iPad Pro 12.9 M2
        db.urunEkle("iPad Pro 12.9 M2", 35000, R.drawable.tablet1, 12,
                "Ekran: 12.9\" Liquid Retina XDR (Mini-LED)\nİşlemci: Apple M2\nRAM: 8GB\nDepolama: 128GB\nKamera: 12MP + LiDAR\nBatarya: 9720 mAh\nOS: iPadOS",
                "Tablet");

        // 2️⃣ Samsung Galaxy Tab S9
        db.urunEkle("Samsung Galaxy Tab S9", 25000, R.drawable.tablet2, 10,
                "Ekran: 11\" Dynamic AMOLED 2X\nİşlemci: Snapdragon 8 Gen 2\nRAM: 8GB\nDepolama: 256GB\nBatarya: 8400 mAh\nKalem: S-Pen destekli\nOS: Android",
                "Tablet");

        // 3️⃣ Lenovo Tab P12 Pro
        db.urunEkle("Lenovo Tab P12 Pro", 20000, R.drawable.tablet3, 7,
                "Ekran: 12.6\" AMOLED\nİşlemci: Snapdragon 870\nRAM: 6GB\nDepolama: 128GB\nSes: JBL Quad Speaker\nBatarya: 10200 mAh\nOS: Android",
                "Tablet");

        // 4️⃣ Huawei MatePad Pro
        db.urunEkle("Huawei MatePad Pro 12.6", 22000, R.drawable.tablet4, 6,
                "Ekran: 12.6\" OLED\nİşlemci: Kirin 9000E\nRAM: 8GB\nDepolama: 256GB\nKalem: M-Pencil 2\nBatarya: 10050 mAh\nOS: HarmonyOS",
                "Tablet");

        // 5️⃣ iPad Pro 11 M1
        db.urunEkle("iPad Pro 11 M1", 30000, R.drawable.tablet5, 9,
                "Ekran: 11\" Liquid Retina\nİşlemci: Apple M1\nRAM: 8GB\nDepolama: 128GB\nKamera: 12MP\nBatarya: 7538 mAh\nOS: iPadOS",
                "Tablet");

        // 6️⃣ Xiaomi Pad 6 Pro
        db.urunEkle("Xiaomi Pad 6 Pro", 18000, R.drawable.tablet6, 11,
                "Ekran: 11\" IPS LCD 144Hz\nİşlemci: Snapdragon 8+ Gen 1\nRAM: 8GB\nDepolama: 256GB\nSes: Quad Speaker\nBatarya: 8600 mAh\nOS: Android",
                "Tablet");

        // 7️⃣ Samsung Galaxy Tab A9+
        db.urunEkle("Samsung Galaxy Tab A9+", 14000, R.drawable.tablet7, 15,
                "Ekran: 11\" TFT LCD\nİşlemci: Snapdragon 695\nRAM: 6GB\nDepolama: 128GB\nBatarya: 7040 mAh\nOS: Android",
                "Tablet");

        // 8️⃣ Lenovo Tab M10 Plus
        db.urunEkle("Lenovo Tab M10 Plus", 12000, R.drawable.tablet8, 18,
                "Ekran: 10.6\" IPS LCD\nİşlemci: MediaTek Helio G80\nRAM: 4GB\nDepolama: 128GB\nSes: Dolby Atmos\nBatarya: 7700 mAh\nOS: Android",
                "Tablet");

        // 9️⃣ Huawei MatePad 11
        db.urunEkle("Huawei MatePad 11", 16500, R.drawable.tablet9, 8,
                "Ekran: 11\" IPS LCD 120Hz\nİşlemci: Snapdragon 865\nRAM: 6GB\nDepolama: 128GB\nKalem: M-Pencil\nBatarya: 7250 mAh\nOS: HarmonyOS",
                "Tablet");

        // 🔟 Apple iPad 10. Nesil
        db.urunEkle("Apple iPad 10. Nesil", 17000, R.drawable.tablet10, 14,
                "Ekran: 10.9\" Liquid Retina\nİşlemci: A14 Bionic\nRAM: 4GB\nDepolama: 64GB\nKamera: 12MP\nBatarya: 7606 mAh\nOS: iPadOS",
                "Tablet");
    }
}