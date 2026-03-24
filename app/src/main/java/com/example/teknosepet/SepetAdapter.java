package com.example.teknosepet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SepetAdapter extends RecyclerView.Adapter<SepetAdapter.SepetHolder> {

    Context context;
    ArrayList<Product> sepetListesi;
    VeritabaniYardimcisi db;
    SepetActivity activity; // Fiyatı güncellemek ve ekranı yenilemek için

    public SepetAdapter(Context context, ArrayList<Product> sepetListesi, SepetActivity activity) {
        this.context = context;
        this.sepetListesi = sepetListesi;
        this.activity = activity;
        this.db = new VeritabaniYardimcisi(context);
    }

    @NonNull
    @Override
    public SepetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Senin ürün tasarımını (item_product) kullanıyoruz
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new SepetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SepetHolder holder, int position) {
        Product product = sepetListesi.get(position);

        holder.txtName.setText(product.getName());
        holder.imgProduct.setImageResource(product.getImageResource());

        // Fiyatı göster (Yanına TL ekleyerek)
        holder.txtPrice.setText(product.getPrice() + " ₺");

        // Sepette "Sepete Ekle" ve "İncele" butonlarına gerek yok, gizliyoruz
        holder.btnCart.setVisibility(View.GONE);
        holder.btnDetail.setVisibility(View.GONE);

        // Kalp ikonunu "ÇÖP KUTUSU" olarak kullanacağız
        holder.btnFav.setImageResource(android.R.drawable.ic_menu_delete);

        // SİLME İŞLEMİ
        holder.btnFav.setOnClickListener(v -> {
            // 1. Veritabanından sil (Ürün ID'sine göre)
            db.sepettenSil(product.getId());

            // 2. Listeden çıkar
            sepetListesi.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, sepetListesi.size());

            // 3. Aktiviteyi uyar (Fiyatı tekrar hesapla, liste boşaldı mı bak)
            activity.sepetDurumunuGuncelle();

            Toast.makeText(context, "Ürün sepetten çıkarıldı", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return sepetListesi.size();
    }

    class SepetHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice; // txtPrice yoksa txtName altına ekleyebilirsin veya tasarımına göre düzenle
        ImageButton btnFav, btnCart;
        View btnDetail;

        public SepetHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            // item_product.xml içinde fiyat için ayrı bir TextView yoksa,
            // geçici olarak txtName'i kullanabiliriz ama doğrusu bir fiyat texti olmasıdır.
            // Eğer hata verirse buradaki ID'yi kontrol et:
            txtPrice = itemView.findViewById(R.id.txtName);

            btnFav = itemView.findViewById(R.id.btnFav); // Çöp kutusu olacak
            btnCart = itemView.findViewById(R.id.btnCart);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}