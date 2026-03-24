package com.example.teknosepet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SifreDegistirFragment extends Fragment {

    Veritabani db;
    EditText editEski, editYeni, editTekrar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sifre_degistir, container, false);

        // Veritabanını başlat
        db = new Veritabani(requireContext());

        // XML elemanlarını bağla
        editEski = view.findViewById(R.id.inputEski);
        editYeni = view.findViewById(R.id.inputYeni);
        editTekrar = view.findViewById(R.id.inputTekrar);
        Button btnKaydet = view.findViewById(R.id.btnKaydetOnayla);
        ImageView btnKapat = view.findViewById(R.id.btnKapatX);

        // 1. GERİ DÖN BUTONU
        btnKapat.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).fragmentDegistir(new AnaSayfaFragment());
            }
        });

        // 2. KAYDET BUTONU
        btnKaydet.setOnClickListener(v -> {
            String eskiSifre = editEski.getText().toString().trim();
            String yeniSifre = editYeni.getText().toString().trim();
            String yeniTekrar = editTekrar.getText().toString().trim();

            // Aktif kullanıcının mailini MainActivity'den alıyoruz
            String aktifEmail = MainActivity.AKTIF_KULLANICI_EMAIL;

            // Boş alan kontrolü
            if (eskiSifre.isEmpty() || yeniSifre.isEmpty() || yeniTekrar.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Yeni şifreler uyuşuyor mu kontrolü
            if (!yeniSifre.equals(yeniTekrar)) {
                Toast.makeText(getContext(), "Yeni şifreler birbiriyle uyuşmuyor!", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- VERİTABANI İŞLEMLERİ BURADA BAŞLIYOR ---

            // ADIM 1: Eski şifre doğru mu?
            if (db.sifreDogruMu(aktifEmail, eskiSifre)) {

                // ADIM 2: Şifreyi güncelle
                boolean basarili = db.sifreDegistir(aktifEmail, yeniSifre);

                if (basarili) {
                    Toast.makeText(getContext(), "Şifreniz başarıyla güncellendi!", Toast.LENGTH_SHORT).show();

                    // İşlem bitince Ana Sayfaya yönlendir (İstersen giriş sayfasına da atabilirsin)
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).fragmentDegistir(new AnaSayfaFragment());
                    }
                } else {
                    Toast.makeText(getContext(), "Veritabanı hatası oluştu!", Toast.LENGTH_SHORT).show();
                }

            } else {
                // Eski şifre yanlışsa
                Toast.makeText(getContext(), "Mevcut şifrenizi yanlış girdiniz!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}