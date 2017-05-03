package com.example.capstoneapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveLoad {
    public static void openImage(Activity act, int ActID){
        Intent intentIG = new Intent(Intent.ACTION_GET_CONTENT);
        intentIG.setType("image/*");
        act.startActivityForResult(intentIG,ActID);
    }
    public static Uri loadByteImage(int activityID,int ActID,int resultCode, Intent intent){

        if(activityID==ActID && resultCode==Activity.RESULT_OK){
            Uri imgUri = intent.getData();
            return imgUri;

        }
        return null;
    }


    public static boolean storeByteImage(byte[] imageData, int quality, String expName) {

        File sdImageMainDirectory = new File("/sdcard/myImages");
        FileOutputStream fileOutputStream = null;
        String nameFile=Double.toString(Math.random());
        try {

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0,
                    imageData.length,options);


            fileOutputStream = new FileOutputStream(
                    sdImageMainDirectory.toString() +"/" + nameFile + ".jpg");


            BufferedOutputStream bos = new BufferedOutputStream(
                    fileOutputStream);

            myImage.compress(Bitmap.CompressFormat.JPEG, quality, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

}
