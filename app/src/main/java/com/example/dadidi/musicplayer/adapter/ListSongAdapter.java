package com.example.dadidi.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dadidi.musicplayer.MainActivity;
import com.example.dadidi.musicplayer.R;
import com.example.dadidi.musicplayer.model.Song;

import java.util.ArrayList;

public class ListSongAdapter extends BaseAdapter{
    private MainActivity context;
    private int layout;
    private ArrayList<Song> listSong;

    public ListSongAdapter(MainActivity context, int layout, ArrayList<Song> listSong) {
        this.context = context;
        this.layout = layout;
        this.listSong = listSong;
    }

    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int position) {
        return listSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listSong.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Song song = (Song) getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvTitle;
        TextView tvArtistName;
        ImageButton imgBtnSongMenu;
        LinearLayout layoutSongTitleAndArtist;

        convertView = inflater.inflate(layout, null);
        tvTitle = convertView.findViewById(R.id.tvSongTitleItem);
        tvArtistName = convertView.findViewById(R.id.tvSongArtistItem);
        imgBtnSongMenu = convertView.findViewById(R.id.imgBtnSongMenuItem);
        layoutSongTitleAndArtist = convertView.findViewById(R.id.layoutSongNameAndArtist);

        tvTitle.setText(song.getTitle());
        tvArtistName.setText(song.getArtist());

        imgBtnSongMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Song menu", Toast.LENGTH_SHORT).show();
            }
        });

        layoutSongTitleAndArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.playSong(position);
            }
        });
        return convertView;
    }
}
