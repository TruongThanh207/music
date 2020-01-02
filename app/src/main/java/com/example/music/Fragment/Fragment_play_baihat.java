package com.example.music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Acticity.PlayNhacActivity;
import com.example.music.Adapter.PlayNhacAdapter;
import com.example.music.R;


public class Fragment_play_baihat extends Fragment {
    View view;
    RecyclerView recyclerViewplaybaihat;
    PlayNhacAdapter playnhacAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_baihat, container, false);
        recyclerViewplaybaihat = view.findViewById(R.id.recycplaybaihat);
        if(PlayNhacActivity.mangbaihat.size()>0)
        {
            playnhacAdapter = new PlayNhacAdapter(getActivity(), PlayNhacActivity.mangbaihat);
            recyclerViewplaybaihat.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaybaihat.setAdapter(playnhacAdapter);
        }






        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)  {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PlayNhacActivity.mangbaihat.remove(position);
                playnhacAdapter.notifyDataSetChanged();



            }
        });

        touchHelper.attachToRecyclerView(recyclerViewplaybaihat);
        return view;

    }
}
