package com.example.jonat.practicesimplegame;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartMenu extends Activity{
    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        // buttonPlay = (ImageButton)findViewById(R.id.buttonPlay);
        // buttonPlay.setOnClickListener(this);
    }

    public void doMyClick(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

}
