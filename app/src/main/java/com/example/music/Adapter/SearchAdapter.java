package com.example.music.Adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.DanhSachBaiHatActivity;
import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.CreateNotification;
import com.example.music.Fragment.Fragment_play_baihat;
import com.example.music.Model.BaiHat;
import com.example.music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> arraysearch;


    int position = 0;

    public SearchAdapter(Context context, ArrayList<BaiHat> arraysearch) {
        this.context = context;
        this.arraysearch = arraysearch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_tim_kiem, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = arraysearch.get(position);
        holder.tencasitimkiem.setText(baiHat.getCaSi());
        holder.tenbaihattimkiem.setText(baiHat.getTenBaiHat());
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imgbailat);
    }

    @Override
    public int getItemCount() {
        return arraysearch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgbailat;
        TextView tencasitimkiem, tenbaihattimkiem;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgbailat = itemView.findViewById(R.id.imgtimkiembaihat);
            tenbaihattimkiem= itemView.findViewById(R.id.tenbaihattimkiem);
            tencasitimkiem= itemView.findViewById(R.id.tencasitimkiem);
           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateNotification.createNotification(context, arraysearch.get(getAdapterPosition()), R.drawable.ic_pause_black_24dp, position, arraysearch.size()-1);
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("bainhac", arraysearch.get(getPosition()) );
                    context.startActivity(intent);


                }
            });

        }
    }
}
