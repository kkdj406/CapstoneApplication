package com.example.capstoneapplication;

import android.graphics.Bitmap;
import android.graphics.Matrix;



/**
 * Created by hanee on 2017-05-04.
 */

public class ImageProc {
    public static void rotImage(Bitmap bm,int angle, Paper p){
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
        p.setRotation(90);
        //return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

    }
}