package com.example.teknosepet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HesapBilgiFragment extends Fragment {

    Veritabani db;
    EditText editAd, editEmail, editTel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hesap_bilgi, container, false);

        db = new Veritabani(requireContext());
        editAd = view.findViewById(R.id.editHesapAd);
        editEmail = view.findViewById(R.id.editHesapEmail);
        editTel = view.findViewById(R.id.editHesapTel);
        Button btnGuncelle = view.findViewById(R.id.btnHesapGuncelle);
        ImageView btnKapat = view.findViewById(R.id.btnKapat);

        // MEVCUT BİLGİLERİ GETİR
        String aktifEmail = MainActivity.AKTIF_KULLANICI_EMAIL;
        if (aktifEmail != null) {
            editEmail.setText(aktifEmail);
            String[] bilgiler = db.kullaniciBilgileriniGetir(aktifEmail);
            if (bilgiler[0] != null) editAd.setText(bilgiler[0]);
            if (bilgiler[1] != null) editTel.setText(bilgiler[1]);
        }

        // 1. GERİ DÖN BUTONU (ÇARPI)
        btnKapat.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).fragmentDegistir(new AnaSayfaFragment());
            }
        });

        // 2. GÜNCELLE BUTONU
        btnGuncelle.setOnClickListener(v -> {
            String yeniAd = editAd.getText().toString().trim();
            String yeniTel = editTel.getText().toString().trim();

            // BOŞLUK KONTROLÜ
            if (yeniAd.isEmpty() || yeniTel.isEmpty()) {
                // Burada "GÜZEL UYARI" veriyoruz (Kırmızı)
                guzelUyariGoster("Lütfen tüm alanları doldurun!", true);
                return;
            }

            if (aktifEmail != null) {
                boolean sonuc = db.kullaniciGuncelle(aktifEmail, yeniAd, yeniTel);
                if (sonuc) {
                    // BAŞARILI MESAJI (Yeşil)
                    guzelUyariGoster("Bilgileriniz güncellendi!", false);
                } else {
                    guzelUyariGoster("Bir hata oluştu!", true);
                }
            }
        });

        return view;
    }

    // --- İŞTE O "DAHA GÜZEL" UYARI KODU ---
    // (Bunu kopyalayıp LoginFragment veya RegisterFragment içine de koyabilirsin)
    private void guzelUyariGoster(String mesaj, boolean hataMi) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.ozel_toast_tasarimi, null);

        // Yazıyı ayarla
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(mesaj);

        // Rengi ayarla (Hata ise Kırmızı, Değilse Yeşil)
        LinearLayout container = layout.findViewById(R.id.toast_container);
        if (hataMi) {
            container.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D32F2F"))); // Kırmızı
        } else {
            container.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#388E3C"))); // Yeşil
        }

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}