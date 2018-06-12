package com.example.jonat.practicesimplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

public class Food {
    private Bitmap image;
    private Utility utility= new Utility();
    private int x,y;
    private int yVelocity = 20;
    private int width, height;
    private int point = 0;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Food(Bitmap bmp, int speed){
        image = bmp;
        height = 50;
        width = 50;

        Random r = new Random();

        x = r.nextInt(screenWidth - this.width);
        y = 0;
        yVelocity = speed;
        point = yVelocity - 2;

        image = utility.getResizedBitmap(image,width,height);
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
        this.x = -200;
        this.y = -200;
    }

    public boolean checkBoundary() {
        if (this.y +10+ this.height > this.screenHeight)
            return true;
        return false;
    }

}
