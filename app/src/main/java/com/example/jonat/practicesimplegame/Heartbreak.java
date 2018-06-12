package com.example.jonat.practicesimplegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Heartbreak {
    private Bitmap bitmap;
    private Utility utility=new Utility();
    private int x;
    private int y;

    public Heartbreak(Context context){
        bitmap = BitmapFactory.decodeResource(
                context.getResources(),R.drawable.heartbreak);
        bitmap = utility.getResizedBitmap(bitmap,50,50);
        x=-250;
        y=-250;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void draw(Canvas canvas){

        canvas.drawBitmap(bitmap, x,y,null);
    }
}
