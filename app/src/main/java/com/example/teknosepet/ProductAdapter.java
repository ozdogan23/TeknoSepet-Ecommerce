package com.example.teknosepet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<Product> productList;
    private VeritabaniYardimcisi db;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.db = new VeritabaniYardimcisi(context);
    }

    // Arama fonksiyonu için gerekli
    public void listeyiGuncelle(ArrayList<Product> yeniListe) {
        this.productList = yeniListe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // row_product.xml dosyasını bağlıyoruz
        View view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Listede görünen temel bilgiler
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice() + " ₺");
        holder.imgProduct.setImageResource(product.getImageResource());

        // "İncele" butonuna veya resme basınca Detay Penceresini aç
        holder.btnInspect.setOnClickListener(v -> detayPenceresiniAc(product));
        holder.itemView.setOnClickListener(v -> detayPenceresiniAc(product));
    }

    // --- POPUP (DIALOG) PENCERESİNİ AÇAN FONKSİYON ---
    private void detayPenceresiniAc(Product product) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product); // dialog_product.xml bağlıyoruz

        // Arka planı şeffaf yap (Kartın köşeleri yuvarlak görünsün diye)
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Dialog içindeki elemanları bul
        ImageView imgDetail = dialog.findViewById(R.id.imgDetail);
        TextView txtDetail = dialog.findViewById(R.id.txtDetail);
        TextView txtPrice = dialog.findViewById(R.id.txtPrice);
        TextView txtStock = dialog.findViewById(R.id.txtStock);
        ImageButton btnCartPop = dialog.findViewById(R.id.btnCart);
        ImageButton btnFavPop = dialog.findViewById(R.id.btnFavorite);

        // Verileri doldur
        if (imgDetail != null) imgDetail.setImageResource(product.getImageResource());
        if (txtPrice != null) txtPrice.setText(product.getPrice() + " ₺");

        if (txtDetail != null) {
            String tamAciklama = product.getName() + "\n\n" + product.getDescription();
            txtDetail.setText(tamAciklama);
        }

        // Stok Kontrolü
        if (txtStock != null) {
            if (product.getStock() > 0) {
                txtStock.setText("Stok: " + product.getStock() + " Adet");
                txtStock.setTextColor(Color.parseColor("#00B894")); // Yeşil
            } else {
                txtStock.setText("Stok Tükendi");
                txtStock.setTextColor(Color.RED);
            }
        }

        // Favori İkonu Durumu (Açılışta)
        if (btnFavPop != null) {
            if (MainActivity.KULLANICI_GIRIS_YAPTI_MI && product.isFavorite()) {
                btnFavPop.setImageResource(R.drawable.ic_favorite);
                btnFavPop.setColorFilter(Color.RED); // Dolu kalp efekti
            } else {
                btnFavPop.setImageResource(R.drawable.ic_favorite);
                btnFavPop.setColorFilter(Color.GRAY); // Boş kalp efekti
            }
        }

        // --- SEPETE EKLEME ---
        if (btnCartPop != null) {
            btnCartPop.setOnClickListener(v -> {
                if (!MainActivity.KULLANICI_GIRIS_YAPTI_MI) {
                    Toast.makeText(context, "Sepete eklemek için giriş yapın.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (product.getStock() > 0) {
                    db.sepeteEkle(product.getId(), product.getName(), product.getPrice(), product.getImageResource());

                    // ESKİ TOAST SİLİNDİ -> YENİ HAVALI PENCERE GELDİ
                    basariliIslemDialoguGoster("Ürün Sepetinize\nEklendi.");

                    dialog.dismiss(); // Ürün detay penceresini kapat
                } else {
                    Toast.makeText(context, "Üzgünüz, stok tükenmiş.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // --- FAVORİYE EKLEME ---
        if (btnFavPop != null) {
            btnFavPop.setOnClickListener(v -> {
                if (!MainActivity.KULLANICI_GIRIS_YAPTI_MI) {
                    Toast.makeText(context, "Favorilere eklemek için giriş yapın.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean yeniDurum = !product.isFavorite();
                product.setFavorite(yeniDurum);
                db.favoriGuncelle(product.getId(), yeniDurum);

                if (yeniDurum) {
                    btnFavPop.setColorFilter(Color.RED);

                    // ESKİ TOAST SİLİNDİ -> YENİ HAVALI PENCERE GELDİ
                    basariliIslemDialoguGoster("Ürün Favori Listenize\nEklendi.");

                } else {
                    btnFavPop.setColorFilter(Color.GRAY);
                    // Çıkarma işleminde küçük yazı kalabilir, çok rahatsız etmez
                    Toast.makeText(context, "Favorilerden Çıkarıldı", Toast.LENGTH_SHORT).show();
                }
            });
        }

        dialog.show();
    }

    // --- YENİ EKLENEN ÖZEL ONAY PENCERESİ FONKSİYONU ---
    private void basariliIslemDialoguGoster(String mesaj) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success); // Hazırladığımız yeşil tikli tasarım

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Mesajı ayarla
        TextView txtMesaj = dialog.findViewById(R.id.txtDialogMessage);
        if (txtMesaj != null) {
            txtMesaj.setText(mesaj);
        }

        // Tamam butonu
        Button btnTamam = dialog.findViewById(R.id.btnDialogOk);
        if (btnTamam != null) {
            btnTamam.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder Sınıfı
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice;
        Button btnInspect;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProductIcon);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtProductPrice);
            btnInspect = itemView.findViewById(R.id.btnInspect);
        }
    }
}