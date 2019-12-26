package com.example.music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.DanhSachBaiHatActivity;
import com.example.music.Model.Album;
import com.example.music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListalbumAdapter extends RecyclerView.Adapter<ListalbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> mangalbum;

    public ListalbumAdapter(Context context, ArrayList<Album> mangalbum) {
        this.context = context;
        this.mangalbum = mangalbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_danh_sach_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(mangalbum.get(position).getHinhAlbum()).into(holder.imgalbum);
        holder.txttenalbum.setText(mangalbum.get(position).getTenAlbum());
        holder.texttencasi.setText(mangalbum.get(position).getTenCaSiAlbum());
    }

    @Override
    public int getItemCount() {
        return mangalbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgalbum;
        TextView txttenalbum, texttencasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgalbum = itemView.findViewById(R.id.imgviewdanhsachalbum);
            txttenalbum= itemView.findViewById(R.id.texttendanhsachalbum);
            texttencasi = itemView.findViewById(R.id.texttencasialbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("Album", mangalbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
