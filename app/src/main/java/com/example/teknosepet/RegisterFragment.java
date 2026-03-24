package com.example.teknosepet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Bunu ekledik
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {

    Veritabani db;
    EditText ad, email, tel, sifre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        db = new Veritabani(requireContext());

        // XML'deki ID'lerle eşleştirme
        ad = view.findViewById(R.id.regAdSoyad);
        email = view.findViewById(R.id.regEmail);
        tel = view.findViewById(R.id.regTel);
        sifre = view.findViewById(R.id.regSifre);
        Button btnKayit = view.findViewById(R.id.btnKayitOl);

        // --- YENİ EKLENEN KISIM: Geri Dön Tuşu ---
        ImageView btnBack = view.findViewById(R.id.imgBack);
        btnBack.setOnClickListener(v -> {
            // Geri tuşuna basınca Login (Giriş) ekranına döner
            if (getActivity() != null) {
                ((MainActivity) getActivity()).fragmentDegistir(new LoginFragment());
            }
        });
        // -----------------------------------------

        btnKayit.setOnClickListener(v -> {
            String strAd = ad.getText().toString().trim();
            String strEmail = email.getText().toString().trim();
            String strTel = tel.getText().toString().trim();
            String strSifre = sifre.getText().toString().trim();

            if(strAd.isEmpty() || strEmail.isEmpty() || strSifre.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen tüm bilgileri doldurun!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean sonuc = db.kullaniciEkle(strEmail, strSifre, strAd, strTel);

            if(sonuc) {
                Toast.makeText(getContext(), "Kayıt Başarılı!", Toast.LENGTH_LONG).show();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).fragmentDegistir(new LoginFragment());
                }
            } else {
                Toast.makeText(getContext(), "Bu e-posta zaten kayıtlı!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}