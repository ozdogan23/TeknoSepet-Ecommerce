package com.example.teknosepet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoriAdapter extends RecyclerView.Adapter<FavoriAdapter.FavoriHolder> {

    Context context;
    ArrayList<Product> favoriListesi; // Artık Product listesi tutuyoruz
    VeritabaniYardimcisi db;

    public FavoriAdapter(Context context, ArrayList<Product> favoriListesi) {
        this.context = context;
        this.favoriListesi = favoriListesi;
        db = new VeritabaniYardimcisi(context); // Yeni veritabanı yardımcısı
    }

    @NonNull
    @Override
    public FavoriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Senin tasarım dosyanı (row_favori) kullanmaya devam ediyoruz
        View view = LayoutInflater.from(context).inflate(R.layout.row_favori, parent, false);
        return new FavoriHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriHolder holder, int position) {
        Product product = favoriListesi.get(position);

        // Verileri Product modelinden çekiyoruz
        holder.txtAd.setText(product.getName());
        holder.txtFiyat.setText(product.getPrice() + " ₺"); // Fiyat int olduğu için stringe çevirdik
        holder.imgResim.setImageResource(product.getImageResource());

        // --- SİLME İŞLEMİ ---
        holder.btnSil.setOnClickListener(v -> {
            // 1. Veritabanında favori durumunu kaldır (False yap)
            db.favoriGuncelle(product.getId(), false);

            // 2. Listeden sil ve ekrana yansıt
            favoriListesi.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoriListesi.size());

            Toast.makeText(context, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show();

            // Eğer liste tamamen boşaldıysa Activity'deki "boş ekran" fonksiyonunu tetikle
            if (favoriListesi.isEmpty() && context instanceof FavoriActivity) {
                ((FavoriActivity) context).bosMuDoluMuKontrolEt();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriListesi.size();
    }

    // Senin tanımladığın Holder sınıfı aynen kaldı
    class FavoriHolder extends RecyclerView.ViewHolder {
        TextView txtAd, txtFiyat;
        ImageView imgResim, btnSil;

        public FavoriHolder(@NonNull View itemView) {
            super(itemView);
            txtAd = itemView.findViewById(R.id.txtFavAd);
            txtFiyat = itemView.findViewById(R.id.txtFavFiyat);
            imgResim = itemView.findViewById(R.id.imgFavResim);
            btnSil = itemView.findViewById(R.id.btnFavSil);
        }
    }
}