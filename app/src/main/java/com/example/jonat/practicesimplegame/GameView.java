package com.example.jonat.practicesimplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.Random;
import java.util.Vector;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    private Vector<Food> foods;
    private int foodInterval = 0;
    public static int score = 0;


    public GameView(Context context){
        super(context);


        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();

        foods = new Vector<Food>();
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.tzuyucartoon));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
    boolean retry = true;
    while(retry){
        try{
            thread.setRunning(false);
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        retry = false;
    }
    }

    public void update(){
        // characterSprite.update();

        if (foodInterval++ %60 == 0){
            Random r = new Random();
            Food food;
            switch (r.nextInt(5)){
                case 0:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 7);
                    break;
                case 1:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.bluecandy), 5);
                    break;
                case 2:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.redcandy), 3);
                    break;
                default:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 7);
                    break;
            }

            foods.add(food);
        }

        for (Food f : foods){
            f.update();
            if(f.checkBoundary()){
                f.destroy();
                foods.remove(f);
                score -= 30;
            }
        }

        int f_amount = foods.size();
        foods = characterSprite.checkCollision(foods);
        // score += 5 * (f_amount - foods.size());
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.MAGENTA);
            //Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
            //background = Bitmap.createScaledBitmap(
            //        background, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            //canvas.drawBitmap(background, 0, 0, null);

            characterSprite.draw(canvas);

            for (Food f : foods) {
                f.draw(canvas);
            }

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("SCORE: " + score, Resources.getSystem().getDisplayMetrics().widthPixels - 200, 40, paint);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        characterSprite.handleKeyUp(keyCode);
        //Toast.makeText(context, "Keycode: " + keyCode, Toast.LENGTH_LONG).show();
        return super.onKeyUp(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();

        int eventAction = motionEvent.getAction();

        if (eventAction == MotionEvent.ACTION_DOWN) {
            if (x > Resources.getSystem().getDisplayMetrics().widthPixels / 2) {

                characterSprite.moveRight();
            } else

                characterSprite.moveLeft();
        }
        return true;
    }
}


