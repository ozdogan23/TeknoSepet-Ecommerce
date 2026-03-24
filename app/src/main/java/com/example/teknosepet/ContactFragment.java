package com.example.teknosepet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getContext());
        text.setText("📞 İletişim Sayfası\n(Bize Ulaşın)");
        text.setTextSize(24);
        text.setGravity(android.view.Gravity.CENTER);
        return text;
    }
}