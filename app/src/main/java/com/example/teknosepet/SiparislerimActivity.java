package com.example.teknosepet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SiparislerimActivity extends AppCompatActivity {

    VeritabaniYardimcisi db;
    RecyclerView recyclerView;
    LinearLayout layoutBos;
    SiparisAdapter adapter;
    ArrayList<Siparis> siparisListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparislerim);

        db = new VeritabaniYardimcisi(this);

        // Bu ID'leri XML dosyanda tanımladığından emin ol
        recyclerView = findViewById(R.id.recyclerSiparislerim);
        layoutBos = findViewById(R.id.layoutSiparisBos);
        ImageView btnKapat = findViewById(R.id.btnSiparisKapat);

        if (btnKapat != null) btnKapat.setOnClickListener(v -> finish());

        verileriGetir();
    }

    private void verileriGetir() {
        siparisListesi = db.siparisleriGetir();
        bosMuDoluMuKontrolEt();
    }

    public void bosMuDoluMuKontrolEt() {
        if (siparisListesi == null || siparisListesi.isEmpty()) {
            layoutBos.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layoutBos.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new SiparisAdapter(this, siparisListesi, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}