package com.example.videoplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    VideoView vw;
    ArrayList<Integer> videoList = new ArrayList<>();
    int currVideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vw = findViewById(R.id.vidVw);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        videoList.add(R.raw.avengers);
        videoList.add(R.raw.office);
        setVideo(videoList.get(0));
    }

    public void setVideo(int id)
    {
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
    }

    public void onCompletion(MediaPlayer mediaPlayer)
    {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Do you want to replay this video or play the next?");
        obj.show();
    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }else {
                ++currVideo;
                if (currVideo == videoList.size())
                    currVideo = 0;
                setVideo(videoList.get(currVideo));
            }
        }
    }
}