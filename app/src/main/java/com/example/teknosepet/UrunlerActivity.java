package com.example.teknosepet;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UrunlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunler); // Tasarım dosyasını bağladık

        // Ana sayfadan gönderilen kategori ismini alıyoruz (Intent ile)
        String gelenKategoriAdi = getIntent().getStringExtra("kategoriIsmi");

        // Başlığa yazdırıyoruz
        TextView txtBaslik = findViewById(R.id.txtKategoriBaslik);
        txtBaslik.setText(gelenKategoriAdi);

        // --- GERİ DÖN BUTONU ---
        ImageView btnGeri = findViewById(R.id.btnGeriDonUrunler);
        btnGeri.setOnClickListener(v -> {
            finish(); // Bu sayfayı kapatır, önceki sayfaya döner
        });

        // --- ALT MENÜ TIKLAMALARI ---

        // Ana Sayfa: Bu sayfayı kapatıp ana menüye döner
        LinearLayout btnAnaSayfa = findViewById(R.id.btnAnaSayfaGit);
        btnAnaSayfa.setOnClickListener(v -> {
            // Ana sayfaya dönmek için activity'i kapatmak yeterli
            finish();
        });

        // Sepetim ve Favori için şimdilik Toast mesajı veya boş bırakabiliriz
        // (İleride bunları da Activity yapacaksan Intent ile açacağız)
    }
}