package com.example.jonat.practicesimplegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Heart {
    private Bitmap bitmap;
    private Utility utility;
    private int x;
    private int y;

    public Heart(Context context){
        bitmap = BitmapFactory.decodeResource(
                context.getResources(),R.drawable.heart);

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
}
