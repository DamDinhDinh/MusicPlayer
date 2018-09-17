package com.example.dadidi.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dadidi.musicplayer.MainActivity;
import com.example.dadidi.musicplayer.R;
import com.example.dadidi.musicplayer.model.Song;

public class FragmentPlayingSong extends Fragment {
    private MainActivity mainActivity;
    private Song song;
    private TextView tvSongTitle;
    private TextView tvSongArtist;
    private ImageButton imgBtnTimer;
    private ImageView imgSongImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        mainActivity = (MainActivity) getActivity();
        song = mainActivity.getPlayingSong();

        view = inflater.inflate(R.layout.fragment_playing_song, container, false);

        tvSongTitle = view.findViewById(R.id.tvSongTitlePlaying);
        tvSongArtist = view.findViewById(R.id.tvSongArtistPlaying);
        imgBtnTimer = view.findViewById(R.id.imgBtnTimer);
        imgSongImage = view.findViewById(R.id.imgSongImagePlaying);

        if (song != null){
            tvSongTitle.setText(song.getTitle());
            tvSongArtist.setText(song.getArtist());
        }

        return view;
    }

    public void notifyPlayingSongChanged(){
        song = mainActivity.getPlayingSong();
        if (song != null){
            tvSongTitle.setText(song.getTitle());
            tvSongArtist.setText(song.getArtist());
        }
        Log.i("PlAYING", "null");
    }
}
