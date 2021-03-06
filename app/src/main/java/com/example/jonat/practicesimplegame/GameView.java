package com.example.jonat.practicesimplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.binus.dku.hanback.Handler;
import com.binus.dku.hanback.LEDHandler;
import com.binus.dku.hanback.NewHandler;
import com.binus.dku.hanback.NewLEDHandler;

import java.util.Random;
import java.util.Vector;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SongThread sThread;
    private CharacterSprite characterSprite;
    private Utility utility=new Utility();
    private Vector<Food> foods;
    private Vector<Obstacle> obstacles;
    private int foodInterval = 0;
    private int obsInterval = 0;
    public static int score = 0;
    public static int life = 3;
    public static int obHitScore = 0;
    private Context context;
    private MediaPlayer mediaPlayer;
    int screenX;

    private boolean isGameOver;
    private Boom boom;
    private Heart heart;
    public static int flagHeart=0;
    public static int flagHeartBreak=0;
    private Heartbreak heartbreak;
    private Bitmap background;

    public GameView(Context context){
        super(context);
        this.context = context;
        this.screenX=  screenX;

        isGameOver = false;
        background = BitmapFactory.decodeResource(getResources(),R.drawable.castle);
        background=utility.getResizedBitmap(background,Resources.getSystem().getDisplayMetrics().widthPixels,Resources.getSystem().getDisplayMetrics().heightPixels);


        mediaPlayer = MediaPlayer.create(getContext(), R.raw.twice);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        sThread = new SongThread(mediaPlayer);
        setFocusable(true);
        heart = new Heart(context);
        heartbreak = new Heartbreak(context);
        boom = new Boom(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();

        sThread.start();

        foods = new Vector<Food>();
        obstacles = new Vector<Obstacle>();
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.tzuyucartoon),
                BitmapFactory.decodeResource(getResources(),R.drawable.tzuyucartoonmoveleft));
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
        if(flagHeart!=0){
            flagHeart--;

        }
        else {
            heart.setX(-800);
            heart.setY(-800);
        }
//        heart.setX(-800);
//        heart.setY(-800);
        if(flagHeartBreak!=0){
            flagHeartBreak--;

        }
        else {
            heartbreak.setX(-800);
            heartbreak.setY(-800);
        }
//        heartbreak.setY(-800);
//        heartbreak.setY(-800);
        if(obHitScore ==3){
            isGameOver = true;
            foods.clear();
            obstacles.clear();
            NewHandler.ndkPlay(0x1FF);
            return;
        }
        if (foodInterval++ %100 == 0){
            Random r = new Random();
            Food food;
            switch (r.nextInt(5)){
                case 0:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 4);
                    break;
                case 1:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.bluecandy), 3);
                    break;
                case 2:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.candy), 2);
                    break;
                default:
                    food = new Food(BitmapFactory.decodeResource(getResources(),R.drawable.cottoncandy), 4);
                    break;
            }

            foods.add(food);
        }


        if(obsInterval++ %190 == 0) {
            Random r = new Random();
            Obstacle obstacle;
            switch (r.nextInt(5)) {
                case 0:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obs1), 2);
                    break;

                default:
                    obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obs1), 2);

            }
            obstacles.add(obstacle);
        }




        for (Food f : foods){
            f.update();
            if(f.checkBoundary()){

                f.destroy();
                //foods.remove(f);
                //score -= 30;
            }
        }

        for (Obstacle o : obstacles){
            o.update();
            if(o.checkBoundary()){
                o.destroy();
                //obstacles.remove(o);
            }
        }




        int f_amount = foods.size();
        foods = characterSprite.checkCollision(foods,heart);
        // score += 5 * (f_amount - foods.size());

        int o_amount = obstacles.size();
        obstacles = characterSprite.checkCollisionOb(obstacles,heartbreak);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {
           // canvas.drawColor(Color.MAGENTA);
            canvas.drawBitmap(background, 0, 0, null);

            characterSprite.draw(canvas);

            for (Food f : foods) {
                f.draw(canvas);
            }

            for(Obstacle o: obstacles){
                o.draw(canvas);
            }

            heart.draw(canvas);
            heartbreak.draw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("SCORE: " + score, Resources.getSystem().getDisplayMetrics().widthPixels - 170, 40, paint);
            canvas.drawText("LIFE: " + life, Resources.getSystem().getDisplayMetrics().widthPixels - 170, 75, paint);
            NewHandler.printMsg("Score: " + score);

            if(isGameOver){
                paint.setTextSize(75);
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
        if (isGameOver) return true;
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


