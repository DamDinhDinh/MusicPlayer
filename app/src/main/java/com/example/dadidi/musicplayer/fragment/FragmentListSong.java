package com.example.dadidi.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dadidi.musicplayer.MainActivity;
import com.example.dadidi.musicplayer.R;
import com.example.dadidi.musicplayer.adapter.ListSongAdapter;
import com.example.dadidi.musicplayer.model.Song;

import java.util.ArrayList;

public class FragmentListSong  extends Fragment{
    private MainActivity mainActivity;

    private ListView lvListSong;
    private ArrayList<Song> listSong;
    private ListSongAdapter listSongAdapter;
    public FragmentListSong() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        mainActivity = (MainActivity) getActivity();
        listSong = mainActivity.getListSong();

        view = inflater.inflate(R.layout.fragment_list_song, container, false);
        lvListSong = view.findViewById(R.id.lvListSong);
        listSongAdapter = new ListSongAdapter(mainActivity, R.layout.item_list_song, listSong);
        lvListSong.setAdapter(listSongAdapter);

        return view;
    }

    public void notifyListSongChange(){
        listSong = mainActivity.getListSong();
        listSongAdapter.notifyDataSetChanged();
    }
}
