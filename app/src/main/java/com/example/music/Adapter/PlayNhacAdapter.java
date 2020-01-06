package com.example.music.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.CreateNotification;
import com.example.music.Fragment.Fragment_Disk;
import com.example.music.Model.BaiHat;
import com.example.music.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.music.Acticity.PlayNhacActivity.*;
import static com.example.music.Acticity.PlayNhacActivity.mediaPlayer;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> mangbaihat;
    Fragment_Disk fragment_disk;



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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()|| mediaPlayer!=null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    fragment_disk = (Fragment_Disk) adapterplay.getItem(0);
                    CreateNotification.createNotification(context, mangbaihat.get(getAdapterPosition()),
                            R.drawable.ic_pause_black_24dp, getAdapterPosition(), mangbaihat.size()-1);
                    new playnhac().execute(mangbaihat.get(getAdapterPosition()).getLinkBaiHat());

                    position = getAdapterPosition();
                    fragment_disk.Playnhac(PlayNhacActivity.mangbaihat.get(position).getHinhBaiHat());
                    PlayNhacActivity.tencasi.setText(mangbaihat.get(getAdapterPosition()).getCaSi());
                    PlayNhacActivity.tenbaihat.setText(mangbaihat.get(getAdapterPosition()).getTenBaiHat());
                }
            });
        }
    }
    public class playnhac extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.setDataSource(s);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            //TimeSong();
            //UpdateTime();
        }
    }
}
