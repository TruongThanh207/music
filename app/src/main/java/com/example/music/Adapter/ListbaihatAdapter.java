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
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.DanhSachBaiHatActivity;
import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.CreateNotification;
import com.example.music.Model.BaiHat;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListbaihatAdapter extends RecyclerView.Adapter<ListbaihatAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> mangbaihat;
    NotificationManager notificationManager;
    PlayNhacActivity playNhacActivity;
    int position=0;

    public ListbaihatAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_danh_sach_bai_hat, parent, false);
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
        BaiHat baihat = mangbaihat.get(position);
        holder.texttencasi.setText(baihat.getCaSi());
        holder.texttenbaihat.setText(baihat.getTenBaiHat());
        holder.txtindex.setText(position + 1 + "");

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgluotthich;
        TextView txtindex, texttencasi, texttenbaihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgluotthich = itemView.findViewById(R.id.imgluotthich);
            txtindex = itemView.findViewById(R.id.textdanhsach);
            texttenbaihat = itemView.findViewById(R.id.texttenbaihat);
            texttencasi = itemView.findViewById(R.id.texttencasi);


            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CreateNotification.createNotification(context, mangbaihat.get(getAdapterPosition()), R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);
                        Intent  intent = new Intent(context, PlayNhacActivity.class);
                        intent.putExtra("bainhac", mangbaihat.get(getPosition()));
                        context.startActivity(intent);
                    }
            });



//// thêm user kiem tra => mở check imgluotthich
            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgluotthich.setImageResource(R.drawable.iconloved);
                    Dataservice dataservice = APIservice.getService();
                    Call<String> callback = dataservice.Updatelike("1", mangbaihat.get(getPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq = response.body();
                            if (kq.equals("ok")) {
                                Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    imgluotthich.setEnabled(false);

                }
            });
        }
    }
}