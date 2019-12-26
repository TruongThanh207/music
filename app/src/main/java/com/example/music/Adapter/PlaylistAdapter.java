package com.example.music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music.Model.Playlist;
import com.example.music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder
    {
        TextView texttenplaylist;
        ImageView imgbackgroundplaylist, imgiconplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            LayoutInflater inflater=  LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.body_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.imgbackgroundplaylist = convertView.findViewById(R.id.imageviewbgplaylist);
            viewHolder.imgiconplaylist = convertView.findViewById(R.id.imageiconplaylist);
            viewHolder.texttenplaylist = convertView.findViewById(R.id.textviewtenplaylist);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        assert playlist != null;
        Picasso.with(getContext()).load(playlist.getHinhNen()).into(viewHolder.imgbackgroundplaylist);
        Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.imgiconplaylist);
        viewHolder.texttenplaylist.setText(playlist.getTen());

        return convertView;
    }
}
