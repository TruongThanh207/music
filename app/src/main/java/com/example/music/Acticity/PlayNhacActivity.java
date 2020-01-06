package com.example.music.Acticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.music.Adapter.ViewpagerPlay;
import com.example.music.Adapter.PlayNhacAdapter;
import com.example.music.Adapter.ViewpagerPlay;
import com.example.music.CreateNotification;
import com.example.music.Fragment.Fragment_Disk;
import com.example.music.Fragment.Fragment_play_baihat;
import com.example.music.Fragment.Fragment_tim_kiem;
import com.example.music.Model.BaiHat;
import com.example.music.R;
import com.example.music.Service.OnClearFromRecentService;
import com.example.music.Service.Playable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity implements Playable {

    Toolbar toolbarplaynhac;
    public TextView timesong;
    public TextView totaltimesong;
    public static TextView tencasi;
    public static TextView tenbaihat;
    SeekBar seekBar;
    ImageButton imgplay, imgpre,  imgnext,imgsuf, imgrepeat;


    ViewPager viewPagerplaynhac;
    Fragment_Disk fragment_disk;
    Fragment_play_baihat fragment_play_baihat;
    public static MediaPlayer mediaPlayer;

    NotificationManager notificationManager;
    public static int position=0;
    boolean isPlaying= true;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    boolean repeat = false;
    boolean suff = false;
    boolean next = false;


   public static ViewpagerPlay adapterplay;

    public final static ArrayList<BaiHat> mangbaihat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);

        StrictMode.ThreadPolicy mode = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(mode);

        Getintent();

        init();

        click();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        mediaPlayer.stop();
        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                R.drawable.ic_play_arrow_black_24dp, 1, 2);
        mangbaihat.clear();

    }

   /* private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }*/
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIOUS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    private void Getintent() {
        Intent intent = getIntent();



        if(intent!=null){


            if (intent.hasExtra("bainhac")) {
                BaiHat baiHat = intent.getParcelableExtra("bainhac");
                mangbaihat.add(baiHat);

            }
            if (intent.hasExtra("cacbainhac")) {
                ArrayList<BaiHat> arrayListExtra = intent.getParcelableArrayListExtra("cacbainhac");
                for(int i=0; i<arrayListExtra.size(); i++)
                    mangbaihat.add(arrayListExtra.get(i));
            }

        }

    }
    @Override
    public void onTrackPrevious() {
        if(mangbaihat.size()>0)
        {
            if(mediaPlayer.isPlaying()||mediaPlayer!=null)
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (position<=mangbaihat.size())
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


                CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                        R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);

                new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                tencasi.setText(mangbaihat.get(position).getCaSi());
                tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                UpdateTime();
            }

        }
    }

    @Override
    public void onTrackPlay() {
        mediaPlayer.start();
        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);
        imgplay.setImageResource(R.drawable.icpausepng);
        if (fragment_disk.objectAnimator!=null){
            fragment_disk.objectAnimator.resume();

        }
        isPlaying = true;
    }

    @Override
    public void onTrackPause() {
        mediaPlayer.pause();
        imgplay.setImageResource(R.drawable.icplay);
        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                R.drawable.ic_play_arrow_black_24dp, position, mangbaihat.size()-1);
        if (fragment_disk.objectAnimator!=null) {
            fragment_disk.objectAnimator.pause();

        }
        isPlaying = false;
    }

    @Override
    public void onTrackNext() {
        if(mangbaihat.size()>0)
        {
            if(mediaPlayer.isPlaying()||mediaPlayer!=null)
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (position<=mangbaihat.size())
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

                CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                        R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);

                new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                tencasi.setText(mangbaihat.get(position).getCaSi());
                tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                UpdateTime();
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
                /*if (mediaPlayer == null)
                {
                    new playnhac().execute(mangbaihat.get(position).getLinkBaiHat());
                fragment_disk.Playnhac(mangbaihat.get(position).getHinhBaiHat());
                imgplay.setImageResource(R.drawable.icpausepng);
                //getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                tencasi.setText(mangbaihat.get(position).getCaSi());
                tenbaihat.setText(mangbaihat.get(position).getTenBaiHat());
                }
                else */if( mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.icplay);
                    CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                            R.drawable.ic_play_arrow_black_24dp, position, mangbaihat.size()-1);
                    if (fragment_disk.objectAnimator!=null){
                        fragment_disk.objectAnimator.pause();

                    }

                }
                else
                {
                        mediaPlayer.start();
                    CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                            R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);
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
                    if (position<=mangbaihat.size())
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

                        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                                R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);

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
                        if (position<=mangbaihat.size())
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


                            CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                                    R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);

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


                    CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                            R.drawable.ic_play_arrow_black_24dp, 1, 2);

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
    public void UpdateTime(){
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
                        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(position),
                                R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
    }
}
