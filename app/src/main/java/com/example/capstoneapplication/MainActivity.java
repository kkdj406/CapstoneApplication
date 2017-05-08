package com.example.capstoneapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.FrameLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {
    static final int ActID = 1;

    public native String stringFromJNI();

    Toolbar tb;
    FrameLayout previewFrame;
    FloatingActionButton fab;
    Bitmap originalBm;
    Paper paper;


    //이미지 회전관련 멤버변수
    Button btnRot90, btnRot270;
    int rotIndex;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = (Toolbar) findViewById(R.id.toolbar);
        previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btnRot90 = (Button) findViewById(R.id.btnRot90);
        btnRot270 = (Button) findViewById(R.id.btnRot270);
        paper = (Paper) findViewById(R.id.paper);

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

///////////////////////회전 버튼////////////////////////////////////////////////////////////////////////////
        //90도 회전
        btnRot90.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                paper.setRotation(90*(1+(rotIndex%4)));
                rotIndex++;
            }
        });
        //270도 회전
        btnRot270.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                paper.setRotation(270*(1+(rotIndex%4)));
                rotIndex++;
            }
        });
///////////////////////회전 버튼////////////////////////////////////////////////////////////////////////////
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_load){
            Intent intentIG = new Intent(Intent.ACTION_GET_CONTENT);
            intentIG.setType("image/*");
            startActivityForResult(intentIG, ActID);
            rotIndex = 0;

            return true;
        }
        else if (id == R.id.action_save) {

            paper.setDrawingCacheEnabled(true);

            paper.buildDrawingCache();

            SaveLoad.saveBitmaptoJpeg(paper.getDrawingCache());
            Toast.makeText(MainActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();


            /* Log.v("DJ","action_save");
            if(originalBm!=null){
                SaveLoad.saveBitmaptoJpeg(((BitmapDrawable)iv.getDrawable()).getBitmap());

*/
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
                    MainActivity.this.getContentResolver(),uri);
            }catch(IOException e){}
            paper.drawBm(originalBm);
        }
    }

}




