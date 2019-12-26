package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.music.Adapter.ListtheloaiAdapter;
import com.example.music.Model.ChuDe;
import com.example.music.Model.TheLoai;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiActivity extends AppCompatActivity {

    ChuDe chuDe;
    Toolbar toolbartheloai;
    RecyclerView recyclerViewtheloai;
    ListtheloaiAdapter listtheloaiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai);

        Getintent();

        init();

        Getdata();

    }

    private void Getdata() {

           Dataservice dataservice = APIservice.getService();
           Call<List<TheLoai>> callback = dataservice.Getalltheloai(chuDe.getIdChuDe());
           callback.enqueue(new Callback<List<TheLoai>>() {
               @Override
               public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                   ArrayList<TheLoai> theloaiofchude = (ArrayList<TheLoai>) response.body();
                   listtheloaiAdapter = new ListtheloaiAdapter(DanhSachTheLoaiActivity.this, theloaiofchude);
                   recyclerViewtheloai.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiActivity.this, 2));
                   recyclerViewtheloai.setAdapter(listtheloaiAdapter);
                   Log.d("AAA", theloaiofchude.get(0).getTenTheLoai());

               }

               @Override
               public void onFailure(Call<List<TheLoai>> call, Throwable t) {

               }
           });
    }

    private void init() {
        toolbartheloai = (Toolbar)findViewById(R.id.toolbartheloai);
        recyclerViewtheloai =(RecyclerView) findViewById(R.id.alltheloai);
        setSupportActionBar(toolbartheloai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbartheloai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Getintent() {

        Intent intent = getIntent();
        if(intent.hasExtra("chude"))
        {
            chuDe = (ChuDe) intent.getSerializableExtra("chude");

        }
    }
}
