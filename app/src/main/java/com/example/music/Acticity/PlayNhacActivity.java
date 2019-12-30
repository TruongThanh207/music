package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

//import com.example.music.Adapter.ViewpagerPlay;
import com.example.music.Adapter.PlayNhacAdapter;
import com.example.music.Adapter.ViewpagerPlay;
import com.example.music.Fragment.Fragment_Disk;
import com.example.music.Fragment.Fragment_play_baihat;
import com.example.music.Model.BaiHat;
import com.example.music.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity{

    Toolbar toolbarplaynhac;
    TextView timesong, totaltimesong, tencasi, tenbaihat;
    SeekBar seekBar;
    ImageButton imgplay, imgpre,  imgnext,imgsuf, imgrepeat;

    ViewPager viewPagerplaynhac;
    Fragment_Disk fragment_disk;
    Fragment_play_baihat fragment_play_baihat;
    MediaPlayer mediaPlayer;

    int position = 0;

    NotificationManager notificationManager;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    boolean repeat = false;
    boolean suff = false;
    boolean next = false;

    public static PlayNhacAdapter nhacAdapter;

   public static ViewpagerPlay adapterplay;

    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);

        StrictMode.ThreadPolicy mode = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(mode);

        Getintent();



        init();


        click();
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        mangbaihat.clear();
        finish();
        return;

    }

    private void Getintent() {
        Intent intent = getIntent();

        mangbaihat.clear();
        if(intent!=null){
            if (intent.hasExtra("bainhac")) {
                BaiHat baiHat = intent.getParcelableExtra("bainhac");
                mangbaihat.add(baiHat);

            }
            if (intent.hasExtra("cacbainhac")) {
                ArrayList<BaiHat> arrayListExtra = intent.getParcelableArrayListExtra("cacbainhac");
                mangbaihat = arrayListExtra;

            }
        }

    }





    private void click() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapterplay.getItem(1)!= null)
                {
                    if(mangbaihat.size()>0)
                    {
                        fragment_disk.Playnhac(mangbaihat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    }
                    else
                    {
                        handler.postDelayed(this, 300);
                    }

                }

            }
        }, 500);



        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.icplay);
                    if (fragment_disk.objectAnimator!=null){
                        fragment_disk.objectAnimator.pause();
                    }

                }
                else
                {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.icpausepng);
                    if (fragment_disk.objectAnimator!=null){
                        fragment_disk.objectAnimator.resume();

                    }

                }

            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat==false)
                {
                    if(suff == true)
                    {
                        suff = false;
                        imgrepeat.setImageResource(R.drawable.icrepeatedpng);
                        imgsuf.setImageResource(R.drawable.icoshufle);
                    }
                    imgrepeat.setImageResource(R.drawable.icrepeatedpng);
                    repeat=true;
                }
                else{
                    imgrepeat.setImageResource(R.drawable.icrepeatpng );
                    repeat = false;
                }
            }
        });
        imgsuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suff==false)
                {
                    if(repeat == true)
                    {
                        repeat = false;
                        imgrepeat.setImageResource(R.drawable.icrepeatpng);
                        imgsuf.setImageResource(R.drawable.icoshufled);
                    }
                    imgsuf.setImageResource(R.drawable.icoshufled);
                    suff=true;
                }
                else{

                    imgsuf.setImageResource(R.drawable.icoshufle);
                    suff = false;
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size()>0)
                {
                    if(mediaPlayer.isPlaying()||mediaPlayer!=null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position<mangbaihat.size())
                    {
                        imgplay.setImageResource(R.drawable.icpausepng);
                        position++;
                        if(repeat)
                        {
                            if(position==0){
                                position= mangbaihat.size();
                            }
                            position -=1;
                        }
                        if(suff)
                        {
                            Random random= new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index==position)
                            {
                                position = index-1;
                            }
                            position = index;
                        }
                        if(position>mangbaihat.size()-1)
                        {
                            position=0;
                        }

                        new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                        //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        tencasi.setText(mangbaihat.get(position).getCaSi());
                        tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }

            }
        });


        imgpre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mangbaihat.size()>0)
                    {
                        if(mediaPlayer.isPlaying()||mediaPlayer!=null)
                        {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (position<mangbaihat.size())
                        {

                            imgplay.setImageResource(R.drawable.icpausepng);
                            position--;
                            if(repeat)
                            {

                                position +=1;
                            }
                            else{
                                if(position<0){
                                    position = mangbaihat.size()-1;
                                }
                            }
                            if(suff)
                            {
                                Random random= new Random();
                                int index = random.nextInt(mangbaihat.size());
                                if(index==position)
                                {
                                    position = index-1;
                                }
                                position = index;
                            }

                            new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                            fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                            //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                            tencasi.setText(mangbaihat.get(position).getCaSi());
                            tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                            UpdateTime();
                        }
                    }
                }
        });
    }



    private void init() {


        tencasi = (TextView)findViewById(R.id.tencasititle);
        tenbaihat = (TextView)findViewById(R.id.tensongtitle);
        toolbarplaynhac = (Toolbar) findViewById(R.id.toolbarplaynhac);
        timesong = (TextView) findViewById(R.id.timesongprocess);
        totaltimesong = (TextView) findViewById(R.id.timesongtotal);
        imgnext = (ImageButton) findViewById(R.id.imgbuttonnext);
        imgplay = (ImageButton) findViewById(R.id.imgbuttonplay);
        imgpre = (ImageButton) findViewById(R.id.imgbuttonpre);
        imgrepeat = (ImageButton) findViewById(R.id.imgbuttonrepeat);
        imgsuf = (ImageButton) findViewById(R.id.shuffle);
        seekBar = (SeekBar) findViewById(R.id.song_progress);
        viewPagerplaynhac = (ViewPager) findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");


        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);

        fragment_disk = new Fragment_Disk();
        fragment_play_baihat = new Fragment_play_baihat();
        adapterplay = new ViewpagerPlay(getSupportFragmentManager());
        adapterplay.AddFragment(fragment_disk);
        adapterplay.AddFragment(fragment_play_baihat);
        viewPagerplaynhac.setAdapter(adapterplay);


        fragment_disk = (Fragment_Disk) adapterplay.getItem(0);
        if (mangbaihat.size() > 0) {

            //getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
            new playnhac().execute(mangbaihat.get(0).getLinkBaiHat());
            imgplay.setImageResource(R.drawable.icpausepng);
            tencasi.setText(mangbaihat.get(0).getCaSi());
            tenbaihat.setText(mangbaihat.get(0).getTenBaiHat());


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
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        totaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    timesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                            handler.postDelayed(this, 300);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                            next = true;
                        }
                    });

                }
            }
        }, 300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true)
                {
                    if (position<mangbaihat.size())
                    {
                        imgplay.setImageResource(R.drawable.icpausepng);
                        position++;
                        if(repeat)
                        {
                            if(position==0){
                                position= mangbaihat.size();
                            }
                            position -=1;
                        }
                        if(suff)
                        {
                            Random random= new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if(index==position)
                            {
                                position = index-1;
                            }
                            position = index;
                        }
                        if(position>mangbaihat.size()-1)
                        {
                            position=0;
                        }
                        new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                        //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        tencasi.setText(mangbaihat.get(position).getCaSi());
                        tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                    }
                    next = false;
                    handler1.removeCallbacks(this);
                }

                else
                {
                    handler1.postDelayed(this, 300);
                }
            }
        },0);
    }

}
