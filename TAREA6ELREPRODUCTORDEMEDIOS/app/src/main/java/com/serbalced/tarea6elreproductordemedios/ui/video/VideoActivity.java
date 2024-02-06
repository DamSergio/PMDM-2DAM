package com.serbalced.tarea6elreproductordemedios.ui.video;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import com.serbalced.tarea6elreproductordemedios.R;

public class VideoActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    MediaPlayer mp;
    public static MediaController mc;
    public static VideoView videoView;
    public static String uri;
    public static boolean isWebm;
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.clVideo));

        videoView = findViewById(R.id.videoView);
        videoView.setMediaController(mc);

        Uri videoUri;
        if (isWebm) {
            int resourceId = getResources().getIdentifier(uri, "raw", getPackageName());
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
        } else {
            videoUri = Uri.parse(uri);
        }
        videoView.setVideoURI(videoUri);

        h = new Handler(Looper.getMainLooper());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mc.show();
                    }
                });
            }
        });
    }

    @Override
    public void start() {
        if (!mp.isPlaying())
            mp.start();
    }

    @Override
    public void pause() {
        if (mp.isPlaying())
            mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mp.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mp.getAudioSessionId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mc.show(0);
        return super.onTouchEvent(event);
    }
}