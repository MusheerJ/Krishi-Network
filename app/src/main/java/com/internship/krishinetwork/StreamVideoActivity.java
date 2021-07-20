package com.internship.krishinetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.internship.krishinetwork.databinding.ActivitySplashScreenBinding;
import com.internship.krishinetwork.databinding.ActivityStreamVideoBinding;

import java.util.Random;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class StreamVideoActivity extends AppCompatActivity {

    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playbackPosition = 0;
    ActivityStreamVideoBinding binding;
    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStreamVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Streaming Videos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        initPlayer();

        binding.shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.SDK_INT >= 24) {
                    releasePlayer();
                }
                progressDialog.show();
                playWhenReady = true;
                currentWindow = 0;
                playbackPosition = 0;
                initPlayer();

            }
        });

    }

    private void initPlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        String[] Videos = {"IEF6mw7eK4s", "8CEJoCr_9UI", "344u6uK9qeQ", "3-nM3yNi3wg", "RlcY37n5j9M"};
        Random random = new Random();
        int index = random.nextInt(Videos.length);
        String generatedUrl = "https://www.youtube.com/watch?v=" + Videos[index];
        binding.vdoPlayer.setPlayer(player);
        playYouTubeVdo(generatedUrl);

    }

    private void playYouTubeVdo(String youtubeURL) {
        new YouTubeExtractor(this) {

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {

                if (ytFiles != null) {

                    int vdoTag = 135;  //420p
                    int audioTag = 140;
                    MediaSource audioSource = new ProgressiveMediaSource.Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));

                    MediaSource vdoSource = new ProgressiveMediaSource.Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(vdoTag).getUrl()));

                    player.setMediaSource(new MergingMediaSource(true, vdoSource, audioSource), true);


                    player.prepare();
                    player.setPlayWhenReady(playWhenReady);
                    player.seekTo(currentWindow, playbackPosition);
                    player.play();
                    progressDialog.dismiss();
                    binding.vdoPlayer.setVisibility(View.VISIBLE);

                }
            }
        }.extract(youtubeURL, false, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 24 || player == null) {
            initPlayer();
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        binding.vdoPlayer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initPlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();

        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getContentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}