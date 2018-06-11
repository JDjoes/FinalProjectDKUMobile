package com.example.jonat.practicesimplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private Vector<Obstacle> obstacles;
    private int foodInterval = 0;

    private int obsInterval = 0;
    public static int score = 0;
    private Context context;
    int screenX;
    private boolean isGameOver;
    private Boom boom;
    private Bitmap background;

    public GameView(Context context){
        super(context);
        this.context = context;
        this.screenX=  screenX;
        isGameOver = false;
        background = BitmapFactory.decodeResource(getResources(),R.drawable.castle);
        background=getResizedBitmap(background,Resources.getSystem().getDisplayMetrics().widthPixels,Resources.getSystem().getDisplayMetrics().heightPixels);




        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        boom = new Boom(context);
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);


        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();

        foods = new Vector<Food>();
        obstacles = new Vector<Obstacle>();
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
        if(score <0){
            isGameOver = true;
            foods.clear();
            obstacles.clear();
            return;
        }
        if (foodInterval++ %60 == 0){
            Random r = new Random();
            Food food;
            switch (r.nextInt(5)){
                case 0:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 10);
                    break;
                case 1:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.bluecandy), 7);
                    break;
                case 2:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.redcandy), 5);
                    break;
                default:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 9);
                    break;
            }

            foods.add(food);
        }

        if(obsInterval++ %60 == 0){
            Random r = new Random();
            Obstacle obstacle;
            switch(r.nextInt(5)){
                case 0:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.obs1), 10);
                    break;
                case 1:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.obs2),7);
                    break;
                case 2:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.obs3),6);
                    break;
                default:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.obs1),10);
            }
            obstacles.add(obstacle);
        }



        for (Food f : foods){
            f.update();
            if(f.checkBoundary()){
                f.destroy();
                foods.remove(f);
                //score -= 30;
            }
        }

        for (Obstacle o : obstacles){
            o.update();
            if(o.checkBoundary()){
                o.destroy();
                obstacles.remove(o);
            }
        }




        int f_amount = foods.size();
        foods = characterSprite.checkCollision(foods);
        // score += 5 * (f_amount - foods.size());

        int o_amount = obstacles.size();
        obstacles = characterSprite.checkCollisionOb(obstacles);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.MAGENTA);
            //Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
            //background = Bitmap.createScaledBitmap(
            //        background, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            canvas.drawBitmap(background, 0, 0, null);

            characterSprite.draw(canvas);

            for (Food f : foods) {
                f.draw(canvas);
            }

            for(Obstacle o: obstacles){
                o.draw(canvas);
            }


            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("SCORE: " + score, Resources.getSystem().getDisplayMetrics().widthPixels - 200, 40, paint);

            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos =(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);

            }
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isGameOver) return super.onKeyUp(keyCode, event);
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


