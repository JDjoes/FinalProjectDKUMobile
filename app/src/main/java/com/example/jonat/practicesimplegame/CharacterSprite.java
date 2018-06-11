package com.example.jonat.practicesimplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Vector;


public class CharacterSprite {
    private Bitmap image;
    private int x,y;
    private int xVelocity = 30;
    private int yVelocity = 5;
    private int width, height;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp){
        image = bmp;
        height = 250;
        width = 250;

        x = screenWidth / 2 - width / 2;
        y = screenHeight - height;

        // x = 100;
        // y = 100;

        Log.i("Character Sprite", "X: " + x);
        Log.i("Character Sprite", "Y: " + y);

        image = Bitmap.createScaledBitmap(
                image, width, height, false);
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

    public Vector<Food> checkCollision(Vector<Food> foods) {
        for (Food f : foods){
            if (x < f.getWidth() + f.getX()
                    && x + width > f.getX()
                    && y < f.getHeight() + f.getY()
                    && y + height > f.getY()){
                f.destroy();
                foods.remove(f);
                GameView.score += 5;
            }
        }

        return foods;
    }
}
