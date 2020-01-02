package com.example.music.Adapter;

import android.app.Activity;
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

import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.CreateNotification;
import com.example.music.Model.BaiHat;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class BaiHatHotAdapter extends RecyclerView.Adapter<BaiHatHotAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> baihatArraylist;
    PlayNhacActivity playNhacActivity;
    NotificationManager notificationManager;
    int position=0;


    public BaiHatHotAdapter(Context context, ArrayList<BaiHat> baihatArraylist) {
        this.context = context;
        this.baihatArraylist = baihatArraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_baihat_hot, parent, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();

        }
        return new ViewHolder(view);

    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = playNhacActivity.getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = baihatArraylist.get(position);
        holder.tenbaihat.setText(baihat.getTenBaiHat());
        holder.tencasi.setText(baihat.getCaSi());
        Picasso.with(context).load(baihat.getHinhBaiHat()).into(holder.hinhbaihat);
    }

    @Override
    public int getItemCount() {
        return baihatArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView hinhbaihat, luotthich;
        TextView tenbaihat, tencasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            luotthich = itemView.findViewById(R.id.imgyeuthich);
            hinhbaihat = itemView.findViewById(R.id.imgbaihathot);
            tenbaihat = itemView.findViewById(R.id.tenbaihathot);
            tencasi = itemView.findViewById(R.id.tencasihot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateNotification.createNotification(context, baihatArraylist.get(getAdapterPosition()), R.drawable.ic_pause_black_24dp, 0, 0);
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("bainhac", baihatArraylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            luotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    luotthich.setImageResource(R.drawable.iconloved);
                    Dataservice dataservice = APIservice.getService();
                    Call<String> callback = dataservice.Updatelike("1", baihatArraylist.get(getPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq = response.body();
                            if(kq.equals("ok"))
                            {
                                Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    luotthich.setEnabled(false);
                }

            });
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent = new Intent(context, PlaynhacActivity.class);
                    intent.putExtra("bainhac", baihatArraylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });*/
        }
    }
}
