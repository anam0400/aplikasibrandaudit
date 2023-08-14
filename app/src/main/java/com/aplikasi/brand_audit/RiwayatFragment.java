package com.aplikasi.brand_audit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class RiwayatFragment extends Fragment {

    View v;
    private RecyclerView rRiwayat;
    private List<RIwayat> ListRiwayat;
    getData getPost;
    Session sess;
    JSONArray dataArray;

    public RiwayatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_riwayat, container, false);
        rRiwayat = (RecyclerView) v.findViewById(R.id.rv_riwayat);

        AdapterRiwayat adapterRiwayat = new AdapterRiwayat(getContext(), ListRiwayat);
        rRiwayat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rRiwayat.setAdapter(adapterRiwayat);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getDataAll();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return v;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPost = new getData();
        sess = new Session(getContext());
        ListRiwayat = new ArrayList<>();
//        ListRiwayat.add(new RIwayat("Berhasil", R.drawable.time));

        getDataAll();
    }


    private void getDataAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        FormBody formBody = new FormBody.Builder().add("key", "irwan").build();
//                    Toast.makeText(getContext(), "Testing", Toast.LENGTH_LONG).show();
                        String getData = getPost.Auth(sess.urlAPI + "_getKumpulSampah?select=all", formBody);
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

                            ListRiwayat.add(new RIwayat("Berhasil Menambahkan produk " +
                                    dataobj.getString("merk_brand") + " dari " + dataobj.getString("perusahaan") +
                                    " di " + dataobj.getString("lokasi"), R.drawable.time));
//                            Log.e("e", "Data Merk " + dataobj.getString("merk_brand"));
                        }
                        Log.e("e", "================[ E N D ]=================");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                        ListProduk.add(new produk(getProduct.getString("perusahaan"), "PT.Abal-Abal", R.drawable.list));
            }
        }
    }

