package com.aplikasi.brand_audit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class HomeFragment extends Fragment implements TextWatcher {
    View v;
    private RecyclerView rProduk;
    private List<produk> ListProduk;
    getData getPost;
    Session sess;
    JSONArray dataArray;
    private TextInputLayout Output;
    private AutoCompleteTextView Pencarian;
    private ArrayList arrayList = new ArrayList<String>();
    //Daftar Item Menggunakan Array
    Adapterproduk adapterproduk;
    SwipeRefreshLayout swipeRefreshLayout;



    public HomeFragment(){

//        getDataAll();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPost     = new getData();
        sess        = new Session(getContext());
        ListProduk  = new ArrayList<>();

        getDataAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home, container, false);

        rProduk             =   (RecyclerView)  v.findViewById(R.id.rv_produk);
        Output              =   v.findViewById(R.id.txtinputpantai);
        Pencarian           =   v.findViewById(R.id.autoComplete_txt);
        swipeRefreshLayout  =   (SwipeRefreshLayout)v.findViewById(R.id.refreshLayout);

        rProduk.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapterproduk   =   new Adapterproduk(getContext(),ListProduk);

        rProduk.setAdapter(adapterproduk);


        Pencarian.addTextChangedListener(this);


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getLokasi();
                        getDataAll();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

//        showToast(sess.getLokasi());

        if (!sess.getLokasi().equals("")){
            Pencarian.setText(sess.getLokasi());
        }

        getLokasi();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
        Pencarian.setAdapter(arrayAdapter);

        return v;

        }

    public void getLokasi(){
        arrayList.clear();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody formBody = new FormBody.Builder().add("status", "1").build();
//                  Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.Auth(sess.urlAPI + "_getLokasi?select=all", formBody);
                    JSONObject result = new JSONObject(getData);

                    sess.setDataLokasi(result.getString("data"));

                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        Log.e("e", "Datanya Lokasi = " + sess.getDataLokasi());
        if (sess.getDataLokasi() != null) {
            try {
                JSONObject getDatas = new JSONObject(sess.getDataLokasi());

                if(getDatas.getInt("foundBrandsCount") > 0) {
//                        Log.e("e", "Testing 2 "+getDatas.getString("result"));
                    dataArray = getDatas.getJSONArray("result");
                    Log.e("e", "================[ S T A R T ]=================");
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataobj = null;
                        dataobj = dataArray.getJSONObject(i);
                        arrayList.add(dataobj.getString("lokasi_nama"));

                        Log.e("e", "Data Lokasi " + dataobj.getString("lokasi_nama"));

                    }
                    Log.e("e", "================[ E N D ]=================");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//                        ListProduk.add(new produk(getProduct.getString("perusahaan"), "PT.Abal-Abal", R.drawable.list));
        }
    }

    public void getDataAll() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody formBody = new FormBody.Builder().add("group_by", "lokasi, produk_id").build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.Auth(sess.urlAPI + "_getKumpulSampah?select=all&limit=8&group_by=active&select=sum&column=jumlah", formBody);
                    JSONObject result = new JSONObject(getData);

                    sess.setDataSampah(result.getString("data"));

                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        Log.e("e", "Datanya Sampah = " + sess.getDataSampah());
        if (sess.getDataSampah() != null) {
            try {
                JSONObject getDatas = new JSONObject(sess.getDataSampah());

                if(getDatas.getInt("foundBrandsCount") > 0) {
//                        Log.e("e", "Testing 2 "+getDatas.getString("result"));
                    dataArray = getDatas.getJSONArray("result");
                    Log.e("e", "================[ S T A R T ]=================");
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataobj = null;
                        dataobj = dataArray.getJSONObject(i);
                        ListProduk.add(new produk(dataobj.getString("merk_brand").toString(),
                                dataobj.getString("perusahaan").toString(), dataobj.getString("jumlah"),
                                R.drawable.list));

                        Log.e("e", "Data Merk " + dataobj.getString("merk_brand") + "; Perusahaan " +
                                dataobj.getString("perusahaan") + "; Jumlah "+dataobj.getString("jumlah"));

                    }

                    Log.e("e", "================[ E N D ]=================");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//                        ListProduk.add(new produk(getProduct.getString("perusahaan"), "PT.Abal-Abal", R.drawable.list));
        }
    }


    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Method ini dipanggil sebelum edittext selesai diubah
//        showToast("Text Belum Diubah");
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Method ini dipanggil saat text pada edittext sedang diubah
//        Output.getEditText().setText("Test");
    }

    public void afterTextChanged(Editable editable) {
        //Method ini dipanggil setelah edittext selesai diubah
//        showToast("Text Selesai Diubah");
        getLocationByName(Pencarian.getText().toString());
//        showToast(Pencarian.getText().toString());
    }

    public void showToast(final String toast)
    {
        Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
    }

    public void getLocationByName(String lokasi){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody formBody = new FormBody.Builder().add("lokasi_nama", lokasi).build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.Auth(sess.urlAPI + "_getLokasi?select=one", formBody);
                    JSONObject result = new JSONObject(getData);

                    JSONObject getDatas = new JSONObject(result.getString("data"));

                    if(getDatas.getInt("foundBrandsCount") > 0) {
                        JSONObject dataLocation = new JSONObject(getDatas.getString("result"));

                        sess.setLokasi(dataLocation.getString("lokasi_nama"));
                        sess.setLatitude(dataLocation.getString("latitude"));
                        sess.setLongitude(dataLocation.getString("longitude"));
                    }

                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        Log.e("e", "Lokasi " + sess.getLokasi() + "; Latitude " + sess.getLatitude() + "; Longitude " + sess.getLongitude());
    }
}



