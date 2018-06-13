package com.example.jonat.practicesimplegame;

import android.media.MediaPlayer;
import android.util.Log;

import com.binus.dku.hanback.NewHandler;

import java.io.IOException;

class SongThread extends Thread{
    private MediaPlayer mediaPlayer;
    private boolean isOn = false;

    public SongThread(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public synchronized void run() {
        try {
            while (true){
                if (NewHandler.getSwitchValue() != 0){
                    // Log.i("SongThread", "" + NewHandler.getSwitchValue());
                    if (!isOn) {
                        this.mediaPlayer.start();
                        isOn = !isOn;
                    }
                }else{
                    if (isOn) {
                        this.mediaPlayer.pause();
                        isOn = !isOn;
                    }
                }
                // Thread.sleep(1000);
            }
        }catch (Exception e){
            Log.e("Song Thread", e.getMessage());
        }
        super.run();
    }
}
