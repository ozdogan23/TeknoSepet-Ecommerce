package com.example.teknosepet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Eklendi
import android.widget.TextView;  // Eklendi
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    Veritabani db;
    EditText email, sifre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        db = new Veritabani(requireContext());

        // --- ID EŞLEŞTİRMELERİ (Yeni XML'e Göre Güncellendi) ---
        email = view.findViewById(R.id.etEmail);       // XML'deki yeni ID
        sifre = view.findViewById(R.id.etPassword);    // XML'deki yeni ID
        Button btnGiris = view.findViewById(R.id.btnLogin); // XML'deki yeni ID

        // Yeni eklenen Geri Tuşu ve Kayıt Linki
        ImageView btnBack = view.findViewById(R.id.imgBack);
        TextView tvKayitOl = view.findViewById(R.id.tvRegister);

        // --- 1. GERİ TUŞU İŞLEVİ ---
        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                // Geri basınca Ana Sayfaya döner
                ((MainActivity) getActivity()).fragmentDegistir(new AnaSayfaFragment());
            }
        });

        // --- 2. KAYIT OL LİNKİ İŞLEVİ ---
        tvKayitOl.setOnClickListener(v -> {
            if (getActivity() != null) {
                // "Hesabın yok mu?" yazısına basınca Kayıt Ekranına gider
                ((MainActivity) getActivity()).fragmentDegistir(new RegisterFragment());
            }
        });

        // --- 3. GİRİŞ YAP BUTONU (Eski mantık aynen korundu) ---
        btnGiris.setOnClickListener(v -> {
            String strEmail = email.getText().toString().trim();
            String strSifre = sifre.getText().toString().trim();

            if(strEmail.isEmpty() || strSifre.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen bilgileri girin.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Veritabanından kontrol et
            if(db.sifreDogruMu(strEmail, strSifre)) {
                // Şifre Doğru! Global değişkenleri güncelle
                MainActivity.KULLANICI_GIRIS_YAPTI_MI = true;
                MainActivity.AKTIF_KULLANICI_EMAIL = strEmail;

                Toast.makeText(getContext(), "Hoşgeldiniz!", Toast.LENGTH_SHORT).show();

                // Ana Sayfaya Dön
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).fragmentDegistir(new AnaSayfaFragment());
                }
            } else {
                Toast.makeText(getContext(), "E-posta veya şifre hatalı!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}