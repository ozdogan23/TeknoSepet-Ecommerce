package com.example.teknosepet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;

public class AnaSayfaFragment extends Fragment {

    // Arama için gerekli değişkenler
    VeritabaniYardimcisi db;
    RecyclerView recyclerAramaSonuc;
    ProductAdapter aramaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ana_sayfa, container, false);

        // Veritabanını başlat
        db = new VeritabaniYardimcisi(requireContext());

        // --- 1. ARAMA BİLEŞENLERİNİ TANIMLA (YENİ EKLENDİ) ---
        EditText editSearch = view.findViewById(R.id.editSearch);
        recyclerAramaSonuc = view.findViewById(R.id.recyclerAramaSonuc);

        // Arama sonuçları yan yana 2'li görünsün
        recyclerAramaSonuc.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // --- 2. KATEGORİ LİSTESİ (SENİN MEVCUT KODUN) ---
        ListView listView = view.findViewById(R.id.listKategoriler);
        ArrayList<Kategori> liste = new ArrayList<>();

        liste.add(new Kategori("BİLGİSAYARLAR", R.drawable.bilgisayar));
        liste.add(new Kategori("TABLETLER", R.drawable.tablet));
        liste.add(new Kategori("TELEFONLAR", R.drawable.telefon));
        liste.add(new Kategori("SES SİSTEMLERİ", R.drawable.ses));

        // Aksesuar resmi kontrolü
        if (getContext() != null && getContext().getResources().getIdentifier("aksesuar", "drawable", getContext().getPackageName()) != 0) {
            liste.add(new Kategori("AKSESUARLAR", R.drawable.aksesuar));
        }

        KategoriAdapter adapter = new KategoriAdapter(requireContext(), liste);
        listView.setAdapter(adapter);

        // Listeye Tıklayınca Ürünler Sayfasına Git
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Kategori tiklananKategori = liste.get(position);
            Intent intent = new Intent(requireContext(), UrunlerActivity.class);
            intent.putExtra("kategoriIsmi", tiklananKategori.getKategoriAdi());
            startActivity(intent);
        });

        // --- 3. ARAMA MANTIĞI (YENİ EKLENDİ) ---
        // Yazı yazıldığında Kategori Listesini GİZLE, Arama Sonuçlarını GÖSTER
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String aranan = s.toString().trim();

                if (aranan.isEmpty()) {
                    // Yazı yoksa -> Kategorileri GÖSTER, Arama Sonucunu GİZLE
                    listView.setVisibility(View.VISIBLE);
                    recyclerAramaSonuc.setVisibility(View.GONE);
                } else {
                    // Yazı varsa -> Kategorileri GİZLE, Arama Sonucunu GÖSTER
                    listView.setVisibility(View.GONE);
                    recyclerAramaSonuc.setVisibility(View.VISIBLE);

                    aramayiBaslat(aranan);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        // --- 4. ALT BAR (Sepetim Butonu) (SENİN MEVCUT KODUN) ---
        LinearLayout btnSepetAlt = view.findViewById(R.id.btnSepetimAlt);
        if (btnSepetAlt != null) {
            btnSepetAlt.setOnClickListener(v -> {
                startActivity(new Intent(requireContext(), SepetActivity.class));
            });
        }

        // --- 5. MENÜ PANELİ (SAĞ ÜST KÖŞE) (SENİN MEVCUT KODUN) ---
        ImageView btnProfile = view.findViewById(R.id.btnProfileMenu);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                // Menüyü oluştur
                BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
                View menuView = getLayoutInflater().inflate(R.layout.layout_menu_panel, null);

                // Panelleri bul
                LinearLayout panelGirisYok = menuView.findViewById(R.id.panelGirisYok);
                LinearLayout panelGirisVar = menuView.findViewById(R.id.panelGirisVar);

                // Giriş Durumunu Kontrol Et
                if (MainActivity.KULLANICI_GIRIS_YAPTI_MI) {
                    panelGirisYok.setVisibility(View.GONE);
                    panelGirisVar.setVisibility(View.VISIBLE);
                } else {
                    panelGirisYok.setVisibility(View.VISIBLE);
                    panelGirisVar.setVisibility(View.GONE);
                }

                // --- TIKLAMA İŞLEMLERİ ---

                // Giriş Yap / Üye Ol
                menuView.findViewById(R.id.menu_login).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    ((MainActivity) getActivity()).fragmentDegistir(new LoginFragment());
                });
                menuView.findViewById(R.id.menu_register).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    ((MainActivity) getActivity()).fragmentDegistir(new RegisterFragment());
                });

                // Hesap Bilgileri & Şifre
                menuView.findViewById(R.id.menu_hesap_bilgi).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    ((MainActivity) getActivity()).fragmentDegistir(new HesapBilgiFragment());
                });
                menuView.findViewById(R.id.menu_sifre_degistir).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    ((MainActivity) getActivity()).fragmentDegistir(new SifreDegistirFragment());
                });

                // Sepet - Sipariş - Favori (Activity Açarlar)
                menuView.findViewById(R.id.menu_sepet).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    startActivity(new Intent(requireContext(), SepetActivity.class));
                });

                menuView.findViewById(R.id.menu_siparislerim).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    startActivity(new Intent(requireContext(), SiparislerimActivity.class));
                });

                menuView.findViewById(R.id.menu_favori).setOnClickListener(v1 -> {
                    dialog.dismiss();
                    startActivity(new Intent(requireContext(), FavoriActivity.class));
                });

                // Çıkış Yap
                menuView.findViewById(R.id.menu_cikis).setOnClickListener(v1 -> {
                    MainActivity.KULLANICI_GIRIS_YAPTI_MI = false;
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Çıkış Yapıldı", Toast.LENGTH_SHORT).show();
                });

                // --- İLETİŞİM ---
                View btnIletisim = menuView.findViewById(R.id.menu_contact);
                if (btnIletisim != null) {
                    btnIletisim.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        startActivity(new Intent(requireContext(), IletisimActivity.class));
                    });
                }

                dialog.setContentView(menuView);
                dialog.show();
            });
        }

        // Favori butonunu bul ve tıklama özelliği ver (SENİN KODUN)
        View btnFavoriGit = view.findViewById(R.id.nav_fav);
        if (btnFavoriGit != null) {
            btnFavoriGit.setOnClickListener(v -> {
                startActivity(new android.content.Intent(getContext(), FavoriActivity.class));
            });
        }

        return view;
    }

    // --- ARAMA İÇİN YARDIMCI METOD (YENİ EKLENDİ) ---
    private void aramayiBaslat(String kelime) {
        // Veritabanından ara
        ArrayList<Product> sonuclar = db.urunAra(kelime);

        if (aramaAdapter == null) {
            aramaAdapter = new ProductAdapter(getContext(), sonuclar);
            recyclerAramaSonuc.setAdapter(aramaAdapter);
        } else {
            aramaAdapter.listeyiGuncelle(sonuclar);
        }
    }
}