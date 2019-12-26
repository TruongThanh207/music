package com.example.music.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.music.Adapter.SearchAdapter;
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
    RecyclerView recyclerViewtimkiem;
    TextView textViewnodata;
    SearchAdapter searchAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        toolbartimkiem = view.findViewById(R.id.toolbartimkiem);
        recyclerViewtimkiem = view.findViewById(R.id.recyctimkiem);
        textViewnodata = view.findViewById(R.id.txtkhongcodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbartimkiem);
        toolbartimkiem.setTitle("");
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menutimkiem);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetDataSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

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
                    textViewnodata.setVisibility(View.GONE);
                    recyclerViewtimkiem.setVisibility(View.VISIBLE);
                }
                else
                {
                    recyclerViewtimkiem.setVisibility(View.GONE);
                    textViewnodata.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
