package com.aplikasi.brand_audit;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.aplikasi.brand_audit.MainActivity;

public class splash_screen extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 100; // Waktu tampilan splash screen dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Buat penundaan dan jalankan aktivitas berikutnya setelah waktu tampilan splash screen selesai
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(splash_screen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
