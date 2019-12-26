package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.music.Adapter.ListplaylistAdapter;
import com.example.music.Model.Playlist;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachCacPlaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewlistplaylist;
    ListplaylistAdapter listplaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_playlist);
        toolbar = (Toolbar)findViewById(R.id.toolbarlistplaylist);
        recyclerViewlistplaylist = (RecyclerView)findViewById(R.id.recycleviewlistplaylist);

        Init();

        Getdata();
    }

    private void Getdata() {
        Dataservice dataservice = APIservice.getService();
        Call<List<Playlist>> callback = dataservice.GetListPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist = (ArrayList<Playlist>) response.body();
                listplaylistAdapter = new ListplaylistAdapter(DanhSachCacPlaylistActivity.this, mangplaylist);
                recyclerViewlistplaylist.setLayoutManager(new GridLayoutManager(DanhSachCacPlaylistActivity.this, 2));
                recyclerViewlistplaylist.setAdapter(listplaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("PlayLists");
        toolbar.setTitleTextColor(Color.BLACK);

    }
}
