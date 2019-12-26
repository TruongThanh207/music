package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.music.Adapter.ListchudeAdapter;
import com.example.music.Model.ChuDe;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachChuDeActivity extends AppCompatActivity {

    RecyclerView recyclerViewallchude;
    Toolbar toolbarchude;
    ListchudeAdapter listchudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_chu_de);
        init();

        Getdata();
    }

    private void Getdata() {

        Dataservice dataservice = APIservice.getService();
        Call<List<ChuDe>> callback = dataservice.Getdanhsachchude();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> danhsachchude = (ArrayList<ChuDe>) response.body();
                listchudeAdapter = new ListchudeAdapter(DanhSachChuDeActivity.this, danhsachchude);
                recyclerViewallchude.setLayoutManager(new GridLayoutManager(DanhSachChuDeActivity.this, 1));
                recyclerViewallchude.setAdapter(listchudeAdapter);


            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewallchude= (RecyclerView) findViewById(R.id.recycleviewchude);
        toolbarchude=(Toolbar) findViewById(R.id.toolbarchude);
        setSupportActionBar(toolbarchude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chủ Đề");

        toolbarchude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
