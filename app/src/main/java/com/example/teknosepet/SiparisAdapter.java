package com.example.teknosepet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SiparisAdapter extends RecyclerView.Adapter<SiparisAdapter.SiparisHolder> {

    Context context;
    ArrayList<Siparis> siparisListesi;
    VeritabaniYardimcisi db;
    SiparislerimActivity activity;

    public SiparisAdapter(Context context, ArrayList<Siparis> siparisListesi, SiparislerimActivity activity) {
        this.context = context;
        this.siparisListesi = siparisListesi;
        this.activity = activity;
        this.db = new VeritabaniYardimcisi(context);
    }

    @NonNull
    @Override
    public SiparisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_siparis, parent, false);
        return new SiparisHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiparisHolder holder, int position) {
        Siparis siparis = siparisListesi.get(position);

        holder.txtUrunAdi.setText(siparis.getUrunAdi());
        holder.txtFiyat.setText(siparis.getFiyat());
        holder.txtTarih.setText(siparis.getTarih());
        holder.txtDurum.setText(siparis.getDurum());

        // --- GÖRÜNÜM AYARI ---
        if (siparis.getDurum().equals("İptal Edildi")) {
            // İptalse: Kırmızı yazı, Buton YOK
            holder.txtDurum.setTextColor(Color.RED);
            holder.btnIptal.setVisibility(View.GONE);
        } else {
            // Normalse: Turuncu yazı, Buton VAR
            holder.txtDurum.setTextColor(context.getResources().getColor(android.R.color.holo_orange_dark));
            holder.btnIptal.setVisibility(View.VISIBLE);
        }

        // --- İPTAL BUTONU ---
        holder.btnIptal.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Siparişi İptal Et")
                    .setMessage("Sipariş durumu 'İptal Edildi' olarak güncellenecek ve stoklar geri yüklenecek. Emin misiniz?")
                    .setPositiveButton("Evet", (dialog, which) -> {

                        // 1. Veritabanında güncelle (Silme yok)
                        db.siparisIptalEt(siparis.getId(), siparis.getUrunAdi());

                        // 2. Modeldeki veriyi anlık değiştir
                        siparis.setDurum("İptal Edildi");

                        // 3. Sadece bu satırı yenile (Tüm listeyi değil)
                        notifyItemChanged(position);

                        Toast.makeText(context, "Sipariş İptal Edildi", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hayır", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() { return siparisListesi.size(); }

    class SiparisHolder extends RecyclerView.ViewHolder {
        TextView txtUrunAdi, txtFiyat, txtTarih, txtDurum;
        ImageView imgResim;
        Button btnIptal;

        public SiparisHolder(@NonNull View itemView) {
            super(itemView);
            txtUrunAdi = itemView.findViewById(R.id.txtSiparisUrunAdi);
            txtFiyat = itemView.findViewById(R.id.txtSiparisFiyat);
            txtTarih = itemView.findViewById(R.id.txtSiparisTarih);
            txtDurum = itemView.findViewById(R.id.txtSiparisDurum);
            imgResim = itemView.findViewById(R.id.imgSiparisResim);
            btnIptal = itemView.findViewById(R.id.btnSiparisIptal);
        }
    }
}