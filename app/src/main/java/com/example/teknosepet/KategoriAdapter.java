package com.example.teknosepet;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class KategoriAdapter extends ArrayAdapter<Kategori> {

    public KategoriAdapter(Context context, ArrayList<Kategori> kategoriListesi) {
        super(context, 0, kategoriListesi);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_kategori, parent, false);
        }

        Kategori mevcutKategori = getItem(position);

        ImageView imgResim = convertView.findViewById(R.id.imgKategoriResim);
        TextView txtAd = convertView.findViewById(R.id.txtKategoriAdi);

        if (mevcutKategori != null) {

            imgResim.setImageResource(mevcutKategori.getResimId());
            txtAd.setText(mevcutKategori.getKategoriAdi());

            // ---------- ANİMASYON ----------
            imgResim.clearAnimation();

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgResim, "scaleX", 1.0f, 1.08f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgResim, "scaleY", 1.0f, 1.08f);

            scaleX.setDuration(1200);
            scaleY.setDuration(1200);
            scaleX.setRepeatCount(ObjectAnimator.INFINITE);
            scaleY.setRepeatCount(ObjectAnimator.INFINITE);
            scaleX.setRepeatMode(ObjectAnimator.REVERSE);
            scaleY.setRepeatMode(ObjectAnimator.REVERSE);
            scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.start();

            // ---------- TIKLAMA ----------
            convertView.setOnClickListener(v -> {

                String kategoriAdi = mevcutKategori.getKategoriAdi().toUpperCase();
                Intent intent = null;

                switch (kategoriAdi) {

                    case "BİLGİSAYARLAR":
                        intent = new Intent(getContext(), ComputerActivity.class);
                        break;

                    case "TELEFONLAR":
                        intent = new Intent(getContext(), PhoneActivity.class);
                        break;

                    case "TABLETLER":
                        intent = new Intent(getContext(), TabletActivity.class);
                        break;

                    case "SES SİSTEMLERİ":
                        intent = new Intent(getContext(), SpeakerActivity.class);
                        break;

                    case "AKSESUARLAR":
                        intent = new Intent(getContext(), AccessoryActivity.class);
                        break;
                }

                if (intent != null) {
                    getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }
}
