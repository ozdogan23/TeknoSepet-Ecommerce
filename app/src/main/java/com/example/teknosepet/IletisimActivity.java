package com.example.teknosepet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IletisimActivity extends AppCompatActivity {

    EditText editKonu, editMesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);

        // Elemanları Bul
        ImageView btnKapat = findViewById(R.id.btnIletisimKapat);
        Button btnGonder = findViewById(R.id.btnIletisimGonder);
        editKonu = findViewById(R.id.editIletisimKonu);
        editMesaj = findViewById(R.id.editIletisimMesaj);

        // 1. KAPATMA BUTONU
        btnKapat.setOnClickListener(v -> finish()); // Activity olduğu için finish() yeterli

        // 2. GÖNDER BUTONU
        btnGonder.setOnClickListener(v -> {
            String konu = editKonu.getText().toString().trim();
            String mesaj = editMesaj.getText().toString().trim();

            if (konu.isEmpty() || mesaj.isEmpty()) {
                guzelUyariGoster("Lütfen konu ve mesajınızı yazın!", true);
            } else {
                // Sanki gönderilmiş gibi yapıyoruz
                guzelUyariGoster("Mesajınız iletildi! Teşekkürler.", false);

                // Kutuları temizle
                editKonu.setText("");
                editMesaj.setText("");
            }
        });
    }

    // --- ORTAK GÜZEL UYARI FONKSİYONU ---
    private void guzelUyariGoster(String mesaj, boolean hataMi) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.ozel_toast_tasarimi, null);

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(mesaj);

        LinearLayout container = layout.findViewById(R.id.toast_container);
        if (hataMi) {
            container.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D32F2F"))); // Kırmızı
        } else {
            container.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#388E3C"))); // Yeşil
        }

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}