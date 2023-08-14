package com.aplikasi.brand_audit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ScanActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    Session sess;
    getData getPost;
    Vibrator v;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        sess = new Session(this);
        getPost = new getData();
        bundle = new Bundle();
        //v=(Vibrator)arg0.getSystemService(Context.VIBRATOR_SERVICE);
        v=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ScanActivity.this, new String[] {Manifest.permission.CAMERA}, 123);
        } else {
            startScanning();
        }
    }

    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(result.getText());
                        Log.e("E", "Hasil "+result.getText());
//                        Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        scanNow(result.getText());
//                        Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
//                        View cToast = getLayoutInflater().inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_layout_id));
//                        Toast toast = new Toast(getApplicationContext());
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(cToast);
//                        toast.show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sess.getMsg().equals("")){
                    Toast.makeText(ScanActivity.this, sess.getMsg(), Toast.LENGTH_LONG).show();
                    sess.setMsg("");
                }
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    public void scanNow(final String kode_brand)
    {
        new Thread(new Runnable(){

            @Override
            public void run() {
                // Do network action in this function
                try {
                    RequestBody formBodys = new FormBody.Builder()
                            .add("kode_brand", kode_brand)
                            .build();
                    String cekData = getPost.Auth(sess.urlAPI + "_getProduk?select=one", formBodys);
                    JSONObject cekDatas = new JSONObject(cekData);
                    JSONObject getDatas = new JSONObject(cekDatas.getString("data"));
                    if(getDatas.getInt("foundBrandsCount") > 0) {
                        JSONObject getProduct = new JSONObject(getDatas.getString("result"));
                        RequestBody formBody = new FormBody.Builder()
                                .add("produk_id", getProduct.getString("id"))
                                .add("jumlah", "1")
                                .add("berat", "0.5")
                                .add("lokasi", sess.getLokasi())
                                .add("latitude", sess.getLatitude())
                                .add("longitude", sess.getLongitude())
                                .add("catatan", "")
                                .build();
                        String response = getPost.pushData(sess.urlAPI+"_getKumpulSampah", formBody);
//                        sess.setMsg("Data fixed." + response);
                        JSONObject result = new JSONObject(response);
//                        System.out.println("Hasil scan : " + result);
                        if (result.getString("status").equals("200")) {
                            sess.setMsg(result.getString("message"));
                        }else{
                            sess.setMsg(result.getString("message"));
                        }
                        v.vibrate(500);
                    }else{
                        Intent i = new Intent(ScanActivity.this, MainActivity.class);
                        i.putExtra("kode_brand", kode_brand);
                        i.putExtra("toFragment", "inputData");
//                        bundle.putString("kode_brand", kode_brand);
//                        Fragment fragment = new InputFragment();
//                        fragment.setArguments(bundle);
//                        getFragmentPage(new InputFragment());
//                        Fragment mFragment = null;
//                        mFragment = new InputFragment();
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.main_container, mFragment).commit();
//                        sess.setMsg("Produk tidak terdaftar di database.");
                        startActivity(i);
                    }

                } catch (Exception e) {
                    sess.setMsg("Anda tidak dapat terhubung ke server kami." + e.getMessage());
                    System.out.println("Errornya Berandas Fragment : " +e.getMessage());
                }
            }
        }).start();
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
}