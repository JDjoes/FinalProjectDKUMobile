package com.example.jonat.practicesimplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Food {
    private Bitmap image;
    private int x,y;
    private int yVelocity = 5;
    private int width, height;
    private int point = 0;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Food(Bitmap bmp, int speed){
        image = bmp;
        height = 100;
        width = 100;

        Random r = new Random();

        x = r.nextInt(screenWidth - this.width);
        y = 0;
        yVelocity = speed;
        point = yVelocity - 2;

        image = Bitmap.createScaledBitmap(
                image, width, height, false);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x,y,null);
    }

    public void update(){
        y += yVelocity;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void destroy() {
        this.x = 800;
        this.y = 800;
    }

    public boolean checkBoundary() {
        if (this.y + this.height > this.screenHeight)
            return true;
        return false;
    }

}
