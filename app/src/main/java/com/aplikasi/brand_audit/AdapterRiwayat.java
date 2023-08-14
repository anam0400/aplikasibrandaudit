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

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.RiwayatViewHolder> {
    Context context;
    List <RIwayat> ListRiwayat;

    public AdapterRiwayat(Context context, List<RIwayat> listRiwayat) {
        this.context = context;
        ListRiwayat = listRiwayat;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.item_riwayat,parent,false);
        RiwayatViewHolder viewHolder = new RiwayatViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        holder.tv_Deskripsi.setText(ListRiwayat.get(position).getDeskripsi());
        holder.img.setImageResource(ListRiwayat.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return ListRiwayat.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_Deskripsi;
        private ImageView img;

        public RiwayatViewHolder(View itemView) {
            super(itemView);
            tv_Deskripsi=(TextView) itemView.findViewById(R.id.tv_riwayat);
            img=(ImageView) itemView.findViewById(R.id.image_list);

        }
    }
}
