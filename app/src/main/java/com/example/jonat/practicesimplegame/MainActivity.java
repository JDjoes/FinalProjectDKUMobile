package com.example.jonat.practicesimplegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.binus.dku.hanback.Handler;
import com.binus.dku.hanback.NewLEDHandler;
import com.binus.dku.hanback.TextLCDHandler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // NewLEDHandler.ndkPlay(0);
        // TextLCDHandler.printMsg("Candy Pop");
        Handler.ndkPlay(0);
        Handler.printMsg("Candy Pop");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(new GameView(this));
    }


}
