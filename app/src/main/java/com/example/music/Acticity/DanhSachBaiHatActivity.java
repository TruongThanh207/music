package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.music.Adapter.ListbaihatAdapter;
import com.example.music.Model.Album;
import com.example.music.Model.BaiHat;
import com.example.music.Model.ChuDe;
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

public class DanhSachBaiHatActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachbaihat;
    FloatingActionButton floatingActionButton;
    Quangcao quangcao;
    CustomTheloai customTheloai;
    Playlist playlist;
    Album album;
    TheLoai theLoai;
    ImageView imageViewbaihat;
    ArrayList<BaiHat> arrayBaihat;
    ListbaihatAdapter listbaihatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);

        DataIntent();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.coolapsingtoolbar);
        toolbar =(Toolbar) findViewById(R.id.toolbardanhsach);
        recyclerViewDanhsachbaihat = (RecyclerView) findViewById(R.id.recycleviewdanhsachbaihat);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingaction);
        imageViewbaihat =(ImageView) findViewById(R.id.imgdanhdachbaihat);
        floatingActionButton.setEnabled(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);



        if(quangcao!=null && !quangcao.getTenBaiHat().equals(""))
        {
            SetValue(quangcao.getTenBaiHat(), quangcao.getHinhBaiHat());
            GetData(quangcao.getIdQuangCao());
        }

        if(playlist!=null && !playlist.getTen().equals(""))
        {
            SetValue(playlist.getTen(), playlist.getIcon());
            GetDataPlaylist(playlist.getIsPlaylist());
        }

        if(customTheloai!=null && !customTheloai.getTenTheLoai().equals(""))
        {
            SetValue(customTheloai.getTenTheLoai(), customTheloai.getHinhTheLoai());
            GetDataTheLoai(customTheloai.getIdTheLoai());
        }

        if(album!=null && !album.getTenAlbum().equals(""))
        {
            SetValue(album.getTenAlbum(), album.getHinhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }

        if(theLoai!=null && !theLoai.getTenTheLoai().equals(""))
        {
            SetValue(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            GetDataTheLoaiofChude(theLoai.getIdTheLoai());
        }
    }

    private void GetDataTheLoaiofChude(String idTheLoai) {
        Dataservice dataservice= APIservice.getService();
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
        Dataservice dataservice= APIservice.getService();
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

    private void GetDataTheLoai(String idTheLoai) {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Getdanhsachbaihattheotheloai(idTheLoai);
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

    private void SetValue(String ten, String hinh) {
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
        if(intent != null)
        {
            if(intent.hasExtra("banner"))
            {
                quangcao = (Quangcao) intent.getSerializableExtra("banner");
            }
           if(intent.hasExtra("playlist"))
            {
                playlist = (Playlist) intent.getSerializableExtra("playlist");

            }
            if(intent.hasExtra("theloai"))
            {
                customTheloai = (CustomTheloai) intent.getSerializableExtra("theloai");

            }
            if(intent.hasExtra("Album"))
            {
                album = (Album) intent.getSerializableExtra("Album");

            }
            if(intent.hasExtra("theloaiofchude"))
            {
                theLoai = (TheLoai) intent.getSerializableExtra("theloaiofchude");

            }

        }
    }
    private void click()
    {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbainhac", arrayBaihat);
                startActivity(intent);
            }
        });
    }
}
