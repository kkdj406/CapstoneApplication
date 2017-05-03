package com.example.capstoneapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    static final int ActID = 1;

    ImageView iv;
    Toolbar tb;
    FrameLayout previewFrame;
    FloatingActionButton fab;

    byte[] ib;
    Bitmap originalBm;

    String selectedImagePath;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = (Toolbar) findViewById(R.id.toolbar);
        iv = (ImageView)findViewById(R.id.iv);
        previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(tb);

        //카메라 설정
        final CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());
        previewFrame.addView(cameraView);

        //FAB설정

        /* floating button eventListener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Log.d("DJ","action_save");
            SaveLoad.saveBitmaptoJpeg(((BitmapDrawable)iv.getDrawable()).getBitmap());

            return true;

        }else if (id == R.id.action_load){
            Intent intentIG = new Intent(Intent.ACTION_GET_CONTENT);
            intentIG.setType("image/*");
            startActivityForResult(intentIG, ActID);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int activityID, int resultCode, Intent intent) {
        super.onActivityResult(activityID, resultCode, intent);

        if(activityID == ActID && resultCode == Activity.RESULT_OK){
            Uri uri = intent.getData();
            try{originalBm = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),uri);
            }catch(IOException e){}

            iv.setImageURI(uri);
        }
    }

    public native String stringFromJNI();
}

