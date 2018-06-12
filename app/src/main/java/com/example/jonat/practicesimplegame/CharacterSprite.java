package com.example.jonat.practicesimplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.Vector;


public class CharacterSprite {
    private Bitmap image;
    private int x,y;
    private int xVelocity = 20;
    private Utility utility=new Utility();
    private int width, height;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp){
        image = bmp;

        image = utility.getResizedBitmap(image,180,170);
        this.width=image.getWidth();
        this.height=image.getHeight();
        x = screenWidth / 2 - this.width / 2;
        y = screenHeight - this.height;

        // x = 100;
        // y = 100;

        Log.i("Character Sprite", "X: " + x);
        Log.i("Character Sprite", "Y: " + y);


    }


    public void draw(Canvas canvas){

        canvas.drawBitmap(image, x,y,null);
    }

    public void update(){
//        x += xVelocity;
//        y += yVelocity;
//        if((x > screenWidth - image.getWidth()) || (x<0)){
//            xVelocity = xVelocity * -1;
//        }
//        if ((y > screenHeight - image.getHeight()) || (y <0)) {
//            yVelocity = yVelocity * -1;
//        }
    }

    public void moveLeft(){
        if (this.x - this.xVelocity < 0) return;

        this.x -= this.xVelocity;
    }

    public void moveRight(){
        if (this.x + this.width + this.xVelocity > this.screenWidth) return;

        this.x += this.xVelocity;
    }

    public void handleKeyUp(int keyCode) {
        Log.i("Character Sprite", "Key Code: " + keyCode);

        switch (keyCode){
            case 11:
                moveLeft();
                break;
            case 13:
                moveRight();
                break;
        }
    }

    public Vector<Food> checkCollision(Vector<Food> foods, Heart heart) {
        for (Food f : foods){
            if (x < f.getWidth() + f.getX()
                    && x + width > f.getX()
                    && y < f.getHeight() + f.getY()
                    && y + height > f.getY()){
                f.destroy();
                heart.setX(f.getX());
                heart.setY(f.getY());
                //foods.remove(f);
                GameView.score += 5;
            }
        }

        return foods;
    }

    public Vector<Obstacle> checkCollisionOb(Vector<Obstacle> obstacles){
        for (Obstacle o : obstacles){
            if (x < o.getWidth() + o.getX()
                    && x + width > o.getX()
                    && y < o.getHeight() + o.getY()
                    && y + height > o.getY()){
                o.destroy();
                //obstacles.remove(o);
                GameView.score -= 5;
                GameView.life --;
                GameView.obHitScore++;
            }
        }
        return obstacles;
    }
}
