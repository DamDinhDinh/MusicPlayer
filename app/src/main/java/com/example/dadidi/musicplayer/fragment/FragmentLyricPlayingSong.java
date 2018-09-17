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

import com.example.dadidi.musicplayer.MainActivity;
import com.example.dadidi.musicplayer.R;
import com.example.dadidi.musicplayer.model.Song;


public class FragmentLyricPlayingSong extends Fragment{

    private MainActivity mainActivity;
    private TextView tvLyricError;
    private Song song;
    private TextView tvSongTitle;
    private TextView tvSongArtist;
    private ImageButton imgBtnMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        mainActivity = (MainActivity) getActivity();
        song = mainActivity.getPlayingSong();

        view = inflater.inflate(R.layout.fragment_lyric_playing_song, container, false);

        tvLyricError = view.findViewById(R.id.tvLyricError);
        tvSongTitle = view.findViewById(R.id.tvSongTitlePlayingLyric);
        tvSongArtist = view.findViewById(R.id.tvSongArtistPlayingLyric);

        if (song != null){
            tvSongTitle.setText(song.getTitle());
            tvSongArtist.setText(song.getArtist());
        }

        return view;
    }

    public void notifyPlayingSongChanged(){
        if (mainActivity == null){
            return;
        }
        song = mainActivity.getPlayingSong();
        if (song != null){
            tvSongTitle.setText(song.getTitle());
            tvSongArtist.setText(song.getArtist());
        }
        Log.i("PlAYINGLYRIC", "null");
    }
}
