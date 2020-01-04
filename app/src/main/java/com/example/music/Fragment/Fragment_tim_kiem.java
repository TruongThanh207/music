package com.example.music.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.DanhSachTheLoaiActivity;
import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.Adapter.SearchAdapter;
import com.example.music.CreateNotification;
import com.example.music.Model.BaiHat;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_tim_kiem extends Fragment {
    View view;
    Toolbar toolbartimkiem;
    RecyclerView recyclerViewtimkiem, recycler10baihat;
    TextView textgoiy, textkhongtimthay;
    SearchAdapter searchAdapter;
    ImageView imageView;
    int position =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        toolbartimkiem = view.findViewById(R.id.toolbartimkiem);
        recyclerViewtimkiem = view.findViewById(R.id.recyctimkiem);
        recycler10baihat = view.findViewById(R.id.recyc10baihat);
        textgoiy = view.findViewById(R.id.goiy);
        imageView = view.findViewById(R.id.addlist);
        textkhongtimthay = view.findViewById(R.id.khongtimthay);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbartimkiem);
        toolbartimkiem.setTitle("");
        setHasOptionsMenu(true);

        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.Get10baihatrandom();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                final ArrayList<BaiHat> mangbaihat = (ArrayList<BaiHat>) response.body();
                searchAdapter = new SearchAdapter(getActivity(), mangbaihat);
                recycler10baihat.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler10baihat.setAdapter(searchAdapter);
                textkhongtimthay.setVisibility(View.GONE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateNotification.createNotification(getActivity(), mangbaihat.get(position), R.drawable.ic_pause_black_24dp, position, mangbaihat.size()-1);
                        Intent intent = new Intent(getActivity(), PlayNhacActivity.class);
                        intent.putExtra("cacbainhac", mangbaihat);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menutimkiem);
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setBackgroundColor(Color.WHITE);
                searchView.setQueryHint("Search");
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }
        });

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetDataSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //GetDataSearch(newText);

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void GetDataSearch(String nexwText)
    {
        Dataservice dataservice = APIservice.getService();
        Call<List<BaiHat>> callback = dataservice.SearchBaihat(nexwText);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat = (ArrayList<BaiHat>) response.body();
                if(mangbaihat.size()>0) {
                    searchAdapter = new SearchAdapter(getActivity(), mangbaihat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewtimkiem.setLayoutManager(linearLayoutManager);
                    recyclerViewtimkiem.setAdapter(searchAdapter);
                    recycler10baihat.setVisibility(View.GONE);
                    textkhongtimthay.setVisibility(View.GONE);
                    textgoiy.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    recyclerViewtimkiem.setVisibility(View.VISIBLE);
                }
                else
                {
                    textgoiy.setVisibility(View.VISIBLE);
                    textkhongtimthay.setVisibility(View.VISIBLE);
                    recyclerViewtimkiem.setVisibility(View.GONE);
                    recycler10baihat.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });

    }
}
