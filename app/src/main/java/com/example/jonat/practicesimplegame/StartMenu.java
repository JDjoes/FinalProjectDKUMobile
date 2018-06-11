package com.example.jonat.practicesimplegame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartMenu extends Activity{
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_menu);
    }

    public void gotoPlayActivity(View view) {
         Intent i = new Intent(this, MainActivity.class);
         this.startActivity(i);
    }
}
