package com.example.capstoneapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by hanee on 2017-05-07.
 */

class Paper extends View {

    Paint paint = new Paint();
    Path path = new Path();

    private Canvas canvas;
    float y ;
    float x ;

    public Paper(Context context) {
        super(context);
    }

    public Paper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, paint);
    }
    public void drawBm(Bitmap bm){
        setBackgroundDrawable(new BitmapDrawable(bm));
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();
        Log.v("tlqkf","onTouch");

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();

        return true;
    }

}
