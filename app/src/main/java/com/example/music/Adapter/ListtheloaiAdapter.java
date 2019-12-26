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
import com.example.music.Model.TheLoai;
import com.example.music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListtheloaiAdapter extends RecyclerView.Adapter<ListtheloaiAdapter.ViewHolder> {

    Context context;
    ArrayList<TheLoai> mangtheloai;

    public ListtheloaiAdapter(Context context, ArrayList<TheLoai> mangtheloai) {
        this.context = context;
        this.mangtheloai = mangtheloai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_danh_sach_the_loai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(mangtheloai.get(position).getHinhTheLoai()).into(holder.imgtheloai);
        holder.txttentheloai.setText(mangtheloai.get(position).getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return mangtheloai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgtheloai;
        TextView txttentheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgtheloai = itemView.findViewById(R.id.imgtheloai);
            txttentheloai= itemView.findViewById(R.id.texttentheloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("theloaiofchude", mangtheloai.get(getPosition()) );
                    context.startActivity(intent);
                }
            });
        }
    }
}
