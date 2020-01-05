package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.example.music.Adapter.ListbaihatAdapter;
import com.example.music.Adapter.PlayNhacAdapter;
import com.example.music.CreateNotification;
import com.example.music.Model.Album;
import com.example.music.Model.BaiHat;
import com.example.music.Model.CustomTheloai;
import com.example.music.Model.Playlist;
import com.example.music.Model.Quangcao;
import com.example.music.Model.TheLoai;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatActivity extends AppCompatActivity{

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachbaihat;
    FloatingActionButton floatingActionButton;
    Quangcao quangcao;

    Playlist playlist;
    Album album;

    boolean isPlaying = false;
    TheLoai theLoai;
    ImageView imageViewbaihat;
    int position = 0;
    ArrayList<BaiHat> arrayBaihat;
    ListbaihatAdapter listbaihatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);

        StrictMode.ThreadPolicy mode = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(mode);

        DataIntent();



        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.coolapsingtoolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbardanhsach);
        recyclerViewDanhsachbaihat = (RecyclerView) findViewById(R.id.recycleviewdanhsachbaihat);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingaction);
        imageViewbaihat = (ImageView) findViewById(R.id.imgdanhdachbaihat);
        floatingActionButton.setEnabled(true);

        setSupportActionBar(toolbar); collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        if (quangcao != null && !quangcao.getTenBaiHat().equals("")) {
            SetValue(quangcao.getHinhBaiHat());
            GetData(quangcao.getIdQuangCao());
        }

        if (playlist != null && !playlist.getTen().equals("")) {
            SetValue(playlist.getIcon());
            GetDataPlaylist(playlist.getIsPlaylist());
        }



        if (album != null && !album.getTenAlbum().equals("")) {
            SetValue(album.getHinhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }

        if (theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            SetValue(theLoai.getHinhTheLoai());
            GetDataTheLoaiofChude();
        }
    }



    private void GetDataTheLoaiofChude() {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheotheloai(theLoai.getIdTheLoai());
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayBaihat = (ArrayList<BaiHat>) response.body();
                listbaihatAdapter = new ListbaihatAdapter(DanhSachBaiHatActivity.this, arrayBaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(listbaihatAdapter);
                click();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataAlbum(String idAlbum) {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheoalbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayBaihat = (ArrayList<BaiHat>) response.body();
                listbaihatAdapter = new ListbaihatAdapter(DanhSachBaiHatActivity.this, arrayBaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(listbaihatAdapter);
                click();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }



    private void GetDataPlaylist(String isPlaylist) {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheoplaylist(isPlaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayBaihat = (ArrayList<BaiHat>) response.body();
                listbaihatAdapter = new ListbaihatAdapter(DanhSachBaiHatActivity.this, arrayBaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(listbaihatAdapter);
                click();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }


    private void SetValue(String hinh) {
        collapsingToolbarLayout.setTitle(" ");
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imageViewbaihat);

    }

    private void GetData(String idquangcao) {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheoquangcao(idquangcao);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayBaihat = (ArrayList<BaiHat>) response.body();
                listbaihatAdapter = new ListbaihatAdapter(DanhSachBaiHatActivity.this, arrayBaihat);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(listbaihatAdapter);
                click();


            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }


    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                quangcao = (Quangcao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("playlist")) {
                playlist = (Playlist) intent.getSerializableExtra("playlist");

            }

            if (intent.hasExtra("Album")) {
                album = (Album) intent.getSerializableExtra("Album");

            }
            if (intent.hasExtra("theloaiofchude")) {
                theLoai = (TheLoai) intent.getSerializableExtra("theloaiofchude");

            }

        }
    }

    private void click() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNotification.createNotification(DanhSachBaiHatActivity.this, arrayBaihat.get(position), R.drawable.ic_pause_black_24dp, position, arrayBaihat.size()-1);
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbainhac", arrayBaihat);
                startActivity(intent);

            }
        });
    }
}

