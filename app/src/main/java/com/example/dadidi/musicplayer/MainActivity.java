package com.example.dadidi.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dadidi.musicplayer.fragment.FragmentListSong;
import com.example.dadidi.musicplayer.fragment.FragmentLyricPlayingSong;
import com.example.dadidi.musicplayer.fragment.FragmentPlayingSong;
import com.example.dadidi.musicplayer.model.Song;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> listSong;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<Fragment> listFragment;
    private FragmentListSong fragmentListSong;
    private FragmentPlayingSong fragmentPlayingSong;
    private FragmentLyricPlayingSong fragmentLyricPlayingSong;

    private TextView tvTimePlayingSong;
    private TextView tvTimePlayingSongTotal;
    private SeekBar seekBarPlaying;
    private ImageButton imgBtnPlay;
    private ImageButton imgBtnPause;
    private ImageButton imgBtnSkipPrevious;
    private ImageButton imgBtnSkipNext;
    private ImageButton imgBtnShuffle;
    private ImageButton imgBtnRepeatList;
    private ImageButton imgBtnRepeatOne;

    private MediaPlayer mediaPlayer;
    private int curPosPlaying;
    private int songPlayingIndex = -1;


    private boolean isShuffleOn = false;
    private boolean isRepeatOneOn = false;
    private boolean isRepeatListOn = false;


    private final int READ_EXTERNAL_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission. READ_EXTERNAL_STORAGE}, READ_EXTERNAL_REQUEST_CODE);

        tvTimePlayingSong = findViewById(R.id.tvTimePlayingSong);
        tvTimePlayingSongTotal = findViewById(R.id.tvTimePlayingSongTotal);
        seekBarPlaying = findViewById(R.id.seekbarPlaying);
        imgBtnPlay = findViewById(R.id.imgBtnPlay);
        imgBtnPause = findViewById(R.id.imgBtnPause);
        imgBtnSkipPrevious = findViewById(R.id.imgBtnSkipPrevious);
        imgBtnSkipNext = findViewById(R.id.imgBtnSkipNext);
        imgBtnShuffle = findViewById(R.id.imgBtnShuffle);
        imgBtnRepeatList = findViewById(R.id.imgBtnRepeatList);
        imgBtnRepeatOne = findViewById(R.id.imgBtnRepeatOne);

        listSong = new ArrayList<>();


        listFragment = new ArrayList<>();
        fragmentListSong = new FragmentListSong();
        fragmentPlayingSong = new FragmentPlayingSong();
        fragmentLyricPlayingSong = new FragmentLyricPlayingSong();
        listFragment.add(fragmentListSong);
        listFragment.add(fragmentPlayingSong);
        listFragment.add(fragmentLyricPlayingSong);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new com.example.dadidi.musicplayer.adapter.PagerAdapter(getSupportFragmentManager(), listFragment);
        viewPager.setAdapter(pagerAdapter);

        mediaPlayer = new MediaPlayer();



        imgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songPlayingIndex > -1){
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.seekTo(curPosPlaying);
                        mediaPlayer.start();
                        imgBtnPlay.setVisibility(View.GONE);
                        imgBtnPause.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        imgBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    curPosPlaying = mediaPlayer.getCurrentPosition();
                    imgBtnPause.setVisibility(View.GONE);
                    imgBtnPlay.setVisibility(View.VISIBLE);
                }
            }
        });

        imgBtnSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songPlayingIndex < listSong.size() - 1){
                    playSong(songPlayingIndex + 1);
                }else if (songPlayingIndex == listSong.size() - 1){
                    if (isRepeatListOn){
                        playSong(0);
                    }
                }
            }
        });

        imgBtnSkipPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songPlayingIndex > 0){
                    playSong(songPlayingIndex - 1);
                }else if (songPlayingIndex == 0){
                    if (isRepeatListOn){
                        playSong(listSong.size() - 1);
                    }
                }
            }
        });

        seekBarPlaying.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarPlaying.getProgress());
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isRepeatOneOn){
                    playSong(curPosPlaying);
                }else{
                    imgBtnSkipNext.performClick();
                }
            }
        });

        imgBtnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffleOn){
                    listSong = new ArrayList<>();
                    getAllSong();
                    fragmentListSong.notifyListSongChange();
                    Log.i("SHUFFLE", "turn off shuffle");
                    isShuffleOn = false;
                }else{
                    Collections.shuffle(listSong);
                    fragmentListSong.notifyListSongChange();
                    Log.i("SHUFFLE", "turn on shuffle");
                    isShuffleOn = true;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_EXTERNAL_REQUEST_CODE: {
                if(grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        getAllSong();
                        fragmentListSong.notifyListSongChange();
                    }
                }else {
                    Toast.makeText(this, "Need permission for reed external!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default: break;
        }
    }

     void getAllSong(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String projection[] = {MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
        };
        String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursorSong = null;

        try{
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursorSong = getContentResolver().query(uri, projection, selection, null, sortOrder);
            if (cursorSong != null){
                if (cursorSong.moveToFirst()){
                    do{
                        String title = cursorSong.getString(0);
                        String artist = cursorSong.getString(1);
                        String path = cursorSong.getString(2);
                        String displayName = cursorSong.getString(3);
                        String songDuration = cursorSong.getString(4);
                        if (path != null && path.endsWith(".mp3")){
                            Song song = new Song(title, artist, path);
                            listSong.add(song);
                        }
                    }while (cursorSong.moveToNext());
                }
            }

            for (int i = 0; i < listSong.size(); i++){
                Log.i("SONG", listSong.get(i).getTitle());
                Log.i("SONG", listSong.get(i).getPath());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Song> getListSong(){
        return listSong;
    }

    public void playSong(int position){
        Song song = listSong.get(position);
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();

        }
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(song.getPath());
        }catch (IOException e){
            Toast.makeText(this, "Song not found!", Toast.LENGTH_SHORT).show();
        }

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "Song not found!", Toast.LENGTH_SHORT).show();
        }

        mediaPlayer.start();
        songPlayingIndex = position;
        imgBtnPlay.setVisibility(View.GONE);
        imgBtnPause.setVisibility(View.VISIBLE);
        tvTimePlayingSongTotal.setText(changeTimeFormat(mediaPlayer.getDuration()));
        seekBarPlaying.setMax(mediaPlayer.getDuration());
        updateTimeAndSeekBarProgressPlaying();
        fragmentPlayingSong.notifyPlayingSongChanged();
        fragmentLyricPlayingSong.notifyPlayingSongChanged();
    }


    public Song getPlayingSong(){
        Log.i("PLAYING", "pos "+songPlayingIndex);
        if (songPlayingIndex == -1){
            return null;
        }
        return listSong.get(songPlayingIndex);
    }


    public String changeTimeFormat(int time){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(time);
    }


    public void updateTimeAndSeekBarProgressPlaying(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTimePlayingSong.setText(changeTimeFormat(mediaPlayer.getCurrentPosition()));
                seekBarPlaying.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
}
