package com.example.teknosepet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.splashscreen.SplashScreen;


public class MainActivity extends AppCompatActivity {

    public static boolean KULLANICI_GIRIS_YAPTI_MI = false;
    public static String AKTIF_KULLANICI_EMAIL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // İlk açılışta Ana Sayfayı göster
        if (savedInstanceState == null) {
            fragmentDegistir(new AnaSayfaFragment());
        }
    }

    // --- SAYFA DEĞİŞTİRME METODU ---
    // Fragmentlar (AnaSayfa, Login vb.) burayı kullanacak
    public void fragmentDegistir(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}