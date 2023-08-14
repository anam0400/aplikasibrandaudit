package com.aplikasi.brand_audit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapterproduk extends  RecyclerView.Adapter<Adapterproduk.ProdukViewHolder> {

    Context context;
    List<produk> ListProduk;

    public Adapterproduk(Context context, List<produk> listProduk) {
        this.context = context;
        this.ListProduk = listProduk;
    }

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(context).inflate(R.layout.itemcard,parent,false);
        ProdukViewHolder vHolder= new ProdukViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder, int position) {

        holder.tv_name.setText(ListProduk.get(position).getName());
        holder.tv_tempat.setText(ListProduk.get(position).getTempat());
        holder.tv_jumlah.setText(ListProduk.get(position).getJumlah());
//        holder.img.setImageDrawable(ListProduk.get(position).getPhoto());
        holder.img.setImageResource(ListProduk.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
            return ListProduk.size();
    }

    public static class ProdukViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private TextView tv_tempat;
        private ImageView img;
        private TextView tv_jumlah;
        public ProdukViewHolder(View itemView){
            super(itemView);
            tv_name=(TextView) itemView.findViewById(R.id.tv_namaproduk);
            tv_tempat=(TextView) itemView.findViewById(R.id.tv_tempatproduk);
            tv_jumlah=(TextView) itemView.findViewById(R.id.tv_jumlah);
            img=(ImageView) itemView.findViewById(R.id.image_list);
        }
    }
}
