package com.example.teknosepet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoriActivity extends AppCompatActivity {

    VeritabaniYardimcisi db; // Yeni veritabanı sınıfımız
    RecyclerView recyclerView;
    LinearLayout layoutBos;
    ArrayList<Product> favoriListesi; // Artık tek bir Product listesi var
    FavoriAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori);

        db = new VeritabaniYardimcisi(this);

        // XML bileşenlerini bağla
        recyclerView = findViewById(R.id.recyclerFavori);
        layoutBos = findViewById(R.id.layoutFavoriBos);
        ImageView btnKapat = findViewById(R.id.btnFavoriKapat);

        btnKapat.setOnClickListener(v -> finish());

        // Verileri Getir
        verileriGetir();
    }

    private void verileriGetir() {
        // 1. KONTROL: Kullanıcı giriş yapmamışsa direkt boş ekran göster
        if (!MainActivity.KULLANICI_GIRIS_YAPTI_MI) {
            layoutBos.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        // 2. VERİ ÇEKME: Veritabanından favorileri al
        favoriListesi = db.favorileriGetir();

        // 3. GÖRÜNÜM GÜNCELLEME: Boş mu dolu mu?
        bosMuDoluMuKontrolEt();
    }

    // Bu metodu Adapter içinden de çağıracağız (ürün silinince liste boşaldı mı diye baksın)
    public void bosMuDoluMuKontrolEt() {
        if (favoriListesi == null || favoriListesi.isEmpty()) {
            // Liste boşsa
            layoutBos.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            // Liste doluysa
            layoutBos.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            // Adaptörü hazırla ve bağla
            adapter = new FavoriAdapter(this, favoriListesi); // Artık activity'i göndermeye gerek yok, context yetiyor
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}