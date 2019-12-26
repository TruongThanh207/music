package com.example.music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Model.BaiHat;
import com.example.music.R;

import java.util.ArrayList;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> mangbaihat;

    public PlayNhacAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_play_baihat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.tenbaihat.setText(baiHat.getTenBaiHat());
        holder.tencasi.setText(baiHat.getCaSi());
        holder.index.setText(position + 1 + ".");
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView index, tenbaihat, tencasi;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tencasi = itemView.findViewById(R.id.tencasibaihatplaynhac);
            tenbaihat = itemView.findViewById(R.id.tenbaihatplaynhac);
            index = itemView.findViewById(R.id.playnhacindex);
        }
    }
}
