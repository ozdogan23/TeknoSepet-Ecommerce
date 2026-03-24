package com.example.teknosepet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AccessoryActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory);

        db = new VeritabaniYardimcisi(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 1. Veritabanından Accessory kategorisini çek
        productList = db.kategoriyeGoreGetir("Accessory");

        // 2. Eğer liste boşsa verileri yükle ve tekrar çek
        if (productList.isEmpty()) {
            verileriYukle();
            productList = db.kategoriyeGoreGetir("Accessory");
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Geri Butonu
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void verileriYukle() {
        // 1️⃣ Akıllı Saat - Apple Watch
        db.urunEkle("Apple Watch Series 9", 14500, R.drawable.aksesuar1, 12,
                "Ekran: 45mm Retina OLED\nSensörler: Nabız, SpO2, ECG\nBağlantı: GPS + Bluetooth\nPil: 18 Saat",
                "Accessory");

        // 2️⃣ Akıllı Saat - Samsung
        db.urunEkle("Samsung Galaxy Watch 6", 10500, R.drawable.aksesuar2, 18,
                "Ekran: 44mm Super AMOLED\nSensörler: Nabız, Uyku, Stres\nİşletim Sistemi: Wear OS\nPil: 40 Saat",
                "Accessory");

        // 3️⃣ Akıllı Saat - Huawei
        db.urunEkle("Huawei Watch GT 4", 8500, R.drawable.aksesuar3, 20,
                "Ekran: 46mm AMOLED\nSpor Modu: 100+\nPil: 14 Gün\nSuya Dayanıklılık: 5 ATM",
                "Accessory");

        // 4️⃣ Kulaklık - Sony
        db.urunEkle("Sony WH-1000XM5", 13500, R.drawable.aksesuar4, 9,
                "Tür: Kulak Üstü Bluetooth\nÖzellik: Aktif Gürültü Engelleme (ANC)\nPil: 30 Saat\nHızlı Şarj: Var",
                "Accessory");

        // 5️⃣ Kulaklık - AirPods
        db.urunEkle("Apple AirPods Pro 2", 11500, R.drawable.aksesuar5, 15,
                "Tür: True Wireless\nÖzellik: ANC + Şeffaf Mod\nPil: 6 Saat (+ Kutu)\nUyumluluk: iOS",
                "Accessory");

        // 6️⃣ Kulaklık - JBL
        db.urunEkle("JBL Tour One M2", 9800, R.drawable.aksesuar6, 11,
                "Tür: Kulak Üstü Bluetooth\nSes: JBL Pro Sound\nPil: 50 Saat\nANC: Var",
                "Accessory");

        // 7️⃣ Mikrofon - HyperX
        db.urunEkle("HyperX QuadCast S", 7200, R.drawable.aksesuar7, 7,
                "Tür: USB Kondansatör Mikrofon\nRGB Aydınlatma\nKullanım: Yayın / Podcast\nShock Mount: Var",
                "Accessory");

        // 8️⃣ Set - Logitech Gaming
        db.urunEkle("Logitech G Gaming Combo", 6500, R.drawable.aksesuar8, 10,
                "Set İçeriği: Klavye + Mouse + Kulaklık\nAydınlatma: RGB\nKullanım: Oyuncu Seti\nBağlantı: USB",
                "Accessory");

        // 9️⃣ Klavye - Keychron
        db.urunEkle("Keychron K6 Mekanik Klavye", 5200, R.drawable.aksesuar9, 14,
                "Switch: Gateron Red\nBağlantı: Bluetooth + USB-C\nTasarım: Kompakt (%65)\nPil: 72 Saat",
                "Accessory");

        // 🔟 Mouse - Logitech MX
        db.urunEkle("Logitech MX Master 3S", 4800, R.drawable.aksesuar10, 16,
                "Tür: Kablosuz Mouse\nDPI: 8000\nBağlantı: Bluetooth / USB\nPil: 70 Gün",
                "Accessory");
    }
}