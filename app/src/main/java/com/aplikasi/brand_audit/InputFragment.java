package com.aplikasi.brand_audit;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;

public class InputFragment  extends Fragment implements TextWatcher {

    TextInputLayout kode_brand, perusahaan_baru, merk_brand, pilih_perusahaan, pilih_jenis;
    TextInputEditText txtperusahaan_baru;
    TextView tv_perusahaanbaru;

    private AutoCompleteTextView Pencarian, PencarianProdukJenis;


    Button btnInput;
    Bundle bundle;
    String kode_brands = "";
    Session sess;
    getData getPost;
    JSONArray dataArray, dataArrayProdukJenis;
    Vibrator v;


    //Daftar Item Menggunakan Array
    private ArrayList arrayList         = new ArrayList<String>();
    private ArrayList arrayProdukJenis  = new ArrayList<String>();
//    private String[] listItem = {"Laptop", "Komputer", "Televisi", "Android", "Pensil", "Tas"};

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view           = inflater.inflate(R.layout.fragment_input, container, false);
        kode_brand          = view.findViewById(R.id.input_kode_brand);
        merk_brand          = view.findViewById(R.id.input_merk);
        pilih_perusahaan    = view.findViewById(R.id.pilih_perusahaan);
        pilih_jenis         = view.findViewById(R.id.pilih_jenis);
        perusahaan_baru     = view.findViewById(R.id.input_perusahaan_baru);

        tv_perusahaanbaru   = view.findViewById(R.id.tv_perusahaan_barus);
        txtperusahaan_baru  = view.findViewById(R.id.txtinput_perusahaan_baru);

        Pencarian           = view.findViewById(R.id.autoCompleteinput);
        PencarianProdukJenis= view.findViewById(R.id.autoComplete_txt);

        btnInput            = view.findViewById(R.id.btn_input);

        getPost = new getData();
        sess = new Session(getContext());
        kode_brands = sess.getKode_brand();
//        showToast("Kode Brand = "+kode_brands);
        kode_brand.getEditText().setText(kode_brands);

        v=(Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);


        getPerusahaan();
        getJenisProduk();
        //Membuat Listener untuk menangani kejadian pada AutoCompleteTextView
        Pencarian.addTextChangedListener(this);
        PencarianProdukJenis.addTextChangedListener(this);
        //Membuat Adapter untuk mengatur bagaimana Item/Konten itu tampil

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
        ArrayAdapter arrayAdapterJenisProduk = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayProdukJenis);

        Pencarian.setAdapter(arrayAdapter);
        PencarianProdukJenis.setAdapter(arrayAdapterJenisProduk);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getPerusahaan();
                        getJenisProduk();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                showToast(merk_brand.getEditText().getText().toString());
            }
        });
        return view;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
    }

    public void afterTextChanged(Editable editable)
    {
        //Method ini dipanggil setelah edittext selesai diubah
        cekTambah(Pencarian .getText().toString());

    }

    public void showToast(final String toast)
    {
        Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
    }

//    public void Tv_perusahaan_baru (String)

    public void saveData()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String nama_perusahaan = "";
                    if (Pencarian.getText().toString().equals("Tambah Perusahaan")){
                        nama_perusahaan = perusahaan_baru.getEditText().getText().toString();

                    }else{
                        nama_perusahaan = Pencarian.getText().toString();
                    }
                    FormBody formBody = new FormBody.Builder()
                            .add("kode_brand", kode_brand.getEditText().getText().toString())
                            .add("merk_brand", merk_brand.getEditText().getText().toString())
                            .add("perusahaan", nama_perusahaan)
                            .add("jenis_produk", PencarianProdukJenis.getText().toString())
                            .build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.pushData(sess.urlAPI + "_getProduk", formBody);
                    JSONObject result = new JSONObject(getData);

                    sess.setMsg(result.getString("message"));
                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        v.vibrate(1000);
        clear();
        showToast("Produk berhasil ditambahkan");
    }

    public void clear()
    {

        kode_brand.getEditText().setText("");
        merk_brand.getEditText().setText("");
    }

    public void cekTambah(String nama_perusahaan)
    {
//        showToast(nama_perusahaan);
        if (nama_perusahaan.equals("Tambah perusahaan")) {
            showToast(nama_perusahaan);
            tv_perusahaanbaru.setVisibility(View.VISIBLE);
            perusahaan_baru.setVisibility(View.VISIBLE);
            txtperusahaan_baru.setVisibility(View.VISIBLE);
        }else{
            tv_perusahaanbaru.setVisibility(View.INVISIBLE);
            perusahaan_baru.setVisibility(View.INVISIBLE);
            txtperusahaan_baru.setVisibility(View.INVISIBLE);
        }
    }

    public void getPerusahaan()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody formBody = new FormBody.Builder().add("jenis_instansi", "perusahaan").build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.Auth(sess.urlAPI + "_getInstansi?select=all", formBody);
                    JSONObject result = new JSONObject(getData);

                    sess.setDataPerusahaan(result.getString("data"));

                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        Log.e("e", "Datanya Perusahaan = " + sess.getDataPerusahaan());
        if (sess.getDataPerusahaan() != null) {
            try {
                JSONObject getDatas = new JSONObject(sess.getDataPerusahaan());

                if (getDatas.getInt("foundBrandsCount") > 0) {
//                        Log.e("e", "Testing 2 "+getDatas.getString("result"));
                    dataArray = getDatas.getJSONArray("result");
                    Log.e("e", "================[ S T A R T ]=================");
//                    arrayList.add("Tambah perusahaan");

                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataobj = null;
                        dataobj = dataArray.getJSONObject(i);
                        arrayList.add(dataobj.getString("nama_instansi"));

                        Log.e("e", "Data Instansi " + dataobj.getString("nama_instansi"));

                    }
                    Log.e("e", "================[ E N D ]=================");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
//                        ListProduk.add(new produk(getProduct.getString("perusahaan"), "PT.Abal-Abal", R.drawable.list));
        }

    }

    public void getJenisProduk()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody formBody = new FormBody.Builder().add("row", "irwan").build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                    String getData = getPost.Auth(sess.urlAPI + "_getProdukJenis?select=all", formBody);
                    JSONObject result = new JSONObject(getData);

                    sess.setDataProdukJenis(result.getString("data"));

                } catch (Exception e) {
                    Log.e("e", "Error message " + e);
                }
            }
        }).start();

        Log.e("e", "Datanya Produk Jenis = " + sess.getDataProdukJenis());
        if (sess.getDataProdukJenis() != null) {
            try {
                JSONObject getDatas = new JSONObject(sess.getDataProdukJenis());

                if(getDatas.getInt("foundBrandsCount") > 0) {
//                        Log.e("e", "Testing 2 "+getDatas.getString("result"));
                    dataArrayProdukJenis = getDatas.getJSONArray("result");
                    Log.e("e", "================[ S T A R T ]=================");
                    for (int i = 0; i < dataArrayProdukJenis.length(); i++) {

                        JSONObject dataobj = null;
                        dataobj = dataArrayProdukJenis.getJSONObject(i);
                        arrayProdukJenis.add(dataobj.getString("jenis_produk"));

                        Log.e("e", "Data Produk Jenis " + dataobj.getString("jenis_produk"));

                    }
                    Log.e("e", "================[ E N D ]=================");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
