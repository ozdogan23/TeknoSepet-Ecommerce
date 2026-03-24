package com.example.teknosepet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SpeakerActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        db = new VeritabaniYardimcisi(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 1. Veritabanından Speaker kategorisini çek
        productList = db.kategoriyeGoreGetir("Speaker");

        // 2. Eğer liste boşsa verileri yükle ve tekrar çek
        if (productList.isEmpty()) {
            verileriYukle();
            productList = db.kategoriyeGoreGetir("Speaker");
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Geri Butonu
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void verileriYukle() {
        // 1️⃣ Sinema Ses Sistemi
        db.urunEkle("Sony HT-S40R Sinema Sistemi", 24500, R.drawable.sessis1, 6,
                "Tür: Ev Sinema Ses Sistemi\nKanal: 5.1 Surround\nGüç: 600W\nBağlantı: HDMI ARC, Bluetooth",
                "Speaker");

        // 2️⃣ Toplantı & Konferans Ses Sistemi
        db.urunEkle("Jabra Speak 750 Konferans Hoparlörü", 18500, R.drawable.sessis2, 10,
                "Kullanım: Toplantı & Konferans\nMikrofon: 360°\nBağlantı: USB & Bluetooth\nUyumluluk: Zoom, Teams",
                "Speaker");

        // 3️⃣ König Ses Sistemi
        db.urunEkle("König KN-SPE300 Ses Sistemi", 9800, R.drawable.sessis3, 14,
                "Tür: Taşınabilir Ses Sistemi\nGüç: 120W\nBağlantı: Bluetooth, AUX\nKullanım: Ev & Açık Alan",
                "Speaker");

        // 4️⃣ Düğün Ses Sistemi
        db.urunEkle("Behringer Europort PPA2000BT", 42500, R.drawable.sessis4, 4,
                "Kullanım: Düğün & Organizasyon\nGüç: 2000W\nMikser: Dahili\nBluetooth: Var",
                "Speaker");

        // 5️⃣ Prodüktör / Stüdyo Monitörü
        db.urunEkle("Yamaha HS8 Stüdyo Monitörü", 19500, R.drawable.sessis5, 8,
                "Kullanım: Prodüktör & Stüdyo\nGüç: 120W\nFrekans Aralığı: 38Hz–30kHz\nAktif Hoparlör",
                "Speaker");

        // 6️⃣ Bilgisayar Hoparlörü
        db.urunEkle("Logitech Z333 Bilgisayar Hoparlörü", 6200, R.drawable.sessis6, 20,
                "Tür: 2.1 Hoparlör Sistemi\nGüç: 40W RMS\nSubwoofer: Var\nBağlantı: 3.5mm Jack",
                "Speaker");

        // 7️⃣ Bluetooth Hoparlör
        db.urunEkle("JBL Flip 6 Bluetooth Hoparlör", 7200, R.drawable.sessis7, 25,
                "Tür: Bluetooth Hoparlör\nGüç: 30W\nBatarya: 12 Saat\nSuya Dayanıklılık: IP67",
                "Speaker");

        // 8️⃣ USB + FM Radyolu Hoparlör
        db.urunEkle("Philips TAR3306 USB FM Hoparlör", 3900, R.drawable.sessis8, 18,
                "Özellik: USB & FM Radyo\nBağlantı: Bluetooth\nGüç: 10W\nKullanım: Ev & Ofis",
                "Speaker");

        // 9️⃣ Mogolala Hoparlör
        db.urunEkle("Mogolala PartyBox X100", 8600, R.drawable.sessis9, 7,
                "Tür: Party Hoparlör\nRGB Işık: Var\nBatarya: 10 Saat\nKaraoke Desteği",
                "Speaker");

        // 🔟 Talon Bilgisayar Hoparlörü
        db.urunEkle("Talon T-300 Bilgisayar Hoparlörü", 2800, R.drawable.sessis10, 22,
                "Tür: Masaüstü Hoparlör\nGüç: 12W\nBağlantı: USB & AUX\nKompakt Tasarım",
                "Speaker");
    }
}