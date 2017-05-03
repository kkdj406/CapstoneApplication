package com.example.capstoneapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SaveLoad {
    public static void openImage(Activity act, int ActID){
        Intent intentIG = new Intent(Intent.ACTION_GET_CONTENT);
        intentIG.setType("image/*");
        intentIG.setAction(Intent.ACTION_GET_CONTENT);
        intentIG.addCategory(Intent.CATEGORY_OPENABLE);
        act.startActivityForResult(intentIG,ActID);
    }

//    public static Bitmap loadByteImage(int activityID,int ActID,int resultCode, Intent intent){
//        Bitmap bmOriginal;
//
//        if(activityID==ActID && resultCode==Activity.RESULT_OK){
//            try{
//            InputStream stream = getContentResolver().openInputStream(
//                    intent.getData());
//            bmOriginal = BitmapFactory.decodeStream(stream);
//            stream.close(); }
//            catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return bmOriginal;
//            //imageView.setImageBitmap(bitmap);
//
//        }
//
//        return null;
//    }
//    public static Uri loadByteImage(Activity act,int activityID,int ActID,int resultCode, Intent intent){
//        Bitmap bmOriginal;
//
//        if(activityID==ActID && resultCode==Activity.RESULT_OK){
//            Log.v("koreanmagpie","loadByteImage1");
//            Uri imgUri = intent.getData();
//            if(imgUri == null){
//                Log.v("koreanmagpie","loadByteImage2");
//            }
//
//            Log.v("koreanmagpie",imgUri.toString());
//            return imgUri;
//
//        }
//
//        return null;
//    }
    public static byte[] getByteArrayImage(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = baos.toByteArray();

        return imageInByte;
    }


    public static boolean storeByteImage(byte[] imageData) {

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

            myImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

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
