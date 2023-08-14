package com.aplikasi.brand_audit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FloatingActionButton fab;
    Intent intent;
    Bundle bundle;
    Session sess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.scanner);
        intent = getIntent();
        bundle = new Bundle();
        sess = new Session(this);

        if (intent.getStringExtra("toFragment") != null){
//        Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show();
//        if (intent != null){
            if (intent.getStringExtra("toFragment").equals("inputData")) {
            Toast.makeText(this, intent.getStringExtra("kode_brand"), Toast.LENGTH_SHORT).show();
//                bundle.putString("kode_brand", intent.getStringExtra("kode_brand"));
//                Fragment frag = new InputFragment();
//                frag.setArguments(bundle);
                sess.setKode_brand(intent.getStringExtra("kode_brand"));
                getFragmentPage(new InputFragment());
            }
        }else {
            //Menampilkan halaman Fragment yang pertama kali muncul
            getFragmentPage(new HomeFragment());

        }
//        getSupportFragmentManager().beginTransaction().add(R.id.main_container, new TopupFragment()).commit();

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,ScanActivity.class);
                startActivity(i);
            }
        });


        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()){
                    case R.id.home_fragment:
                        fragment = new HomeFragment();
                        break;

                    case R.id.history_fragment:
                        fragment = new RiwayatFragment();
                        break;
                }
                return getFragmentPage(fragment);
            }
        });
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    void textShow(String text){

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }

}