package com.example.teknosepet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SepetActivity extends AppCompatActivity {

    // İKİ VERİTABANINI DA TANIMLIYORUZ
    VeritabaniYardimcisi dbUrun; // Ürünler, Sepet ve Stok işlemleri için (Benim verdiğim)
    Veritabani dbKullanici;      // Kullanıcı şifre kontrolü için (Senin dosyan)

    LinearLayout layoutBos;
    LinearLayout layoutDolu;
    RecyclerView recyclerView;
    TextView txtToplamTutar;
    Button btnSiparisOnayla;
    SepetAdapter adapter;
    ArrayList<Product> sepetListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);

        // İKİSİNİ DE BAŞLATIYORUZ
        dbUrun = new VeritabaniYardimcisi(this);
        dbKullanici = new Veritabani(this);

        layoutBos = findViewById(R.id.layoutSepetBos);
        layoutDolu = findViewById(R.id.layoutSepetDolu);
        recyclerView = findViewById(R.id.recyclerViewSepet);
        txtToplamTutar = findViewById(R.id.txtToplamTutar);
        btnSiparisOnayla = findViewById(R.id.btnSiparisOnayla);
        ImageView btnKapat = findViewById(R.id.btnSepetKapat);

        btnKapat.setOnClickListener(v -> finish());

        Button btnBasla = findViewById(R.id.btnAlisveriseBasla);
        btnBasla.setOnClickListener(v -> {
            Intent intent = new Intent(SepetActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Sipariş butonuna basınca DİYALOG AÇILACAK
        if (btnSiparisOnayla != null) {
            btnSiparisOnayla.setOnClickListener(v -> {
                sifreDogrulamaDialoguGoster();
            });
        }

        sepetDurumunuGuncelle();
    }

    // --- ŞİFRE SORMA PENCERESİ ---
    private void sifreDogrulamaDialoguGoster() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_siparis_onay);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtEmail = dialog.findViewById(R.id.editDialogEmail);
        EditText edtSifre = dialog.findViewById(R.id.editDialogSifre);
        Button btnOnayla = dialog.findViewById(R.id.btnDialogOnayla);

        // Eğer kullanıcı giriş yapmışsa e-postasını otomatik doldur (Opsiyonel)
        if (MainActivity.AKTIF_KULLANICI_EMAIL != null) {
            edtEmail.setText(MainActivity.AKTIF_KULLANICI_EMAIL);
        }

        btnOnayla.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String sifre = edtSifre.getText().toString().trim();

            if (email.isEmpty() || sifre.isEmpty()) {
                Toast.makeText(SepetActivity.this, "Lütfen bilgilerinizi girin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- KRİTİK NOKTA: SENİN VERİTABANINDAN KONTROL EDİYORUZ ---
            // Veritabani.java dosyanın içindeki "sifreDogruMu" metodunu kullanıyoruz.
            boolean sifreDogruMu = dbKullanici.sifreDogruMu(email, sifre);

            if (sifreDogruMu) {
                // Şifre doğruysa -> Benim veritabanımdaki sipariş işlemini yap
                dialog.dismiss();
                siparisiTamamla();
            } else {
                Toast.makeText(SepetActivity.this, "Hatalı E-Posta veya Şifre!", Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();
    }

    // --- SİPARİŞİ TAMAMLAMA ---
    private void siparisiTamamla() {
        String tarih = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        // VeritabaniYardimcisi (Benim dosyam) stok düşer ve sepeti temizler
        dbUrun.siparisiOnaylaVeStokDusur(tarih);

        Toast.makeText(this, "Siparişiniz alındı! Teşekkürler.", Toast.LENGTH_LONG).show();
        sepetDurumunuGuncelle();
    }

    public void sepetDurumunuGuncelle() {
        // Giriş kontrolü
        if (!MainActivity.KULLANICI_GIRIS_YAPTI_MI) {
            layoutBos.setVisibility(View.VISIBLE);
            if(layoutDolu != null) layoutDolu.setVisibility(View.GONE);
            return;
        }

        // Sepeti getir (Benim veritabanımdan)
        sepetListesi = dbUrun.sepetiGetir();

        if (sepetListesi.isEmpty()) {
            layoutBos.setVisibility(View.VISIBLE);
            if(layoutDolu != null) layoutDolu.setVisibility(View.GONE);
        } else {
            layoutBos.setVisibility(View.GONE);
            if(layoutDolu != null) layoutDolu.setVisibility(View.VISIBLE);

            adapter = new SepetAdapter(this, sepetListesi, this);
            if(recyclerView != null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }
            toplamFiyatiHesapla();
        }
    }

    private void toplamFiyatiHesapla() {
        int toplam = 0;
        for (Product p : sepetListesi) {
            toplam += p.getPrice();
        }
        if (txtToplamTutar != null) {
            txtToplamTutar.setText(toplam + " ₺");
        }
    }
}