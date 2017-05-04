package com.example.capstoneapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

    ImageView iv;
    Toolbar tb;
    FrameLayout previewFrame;
    FloatingActionButton fab;
    Bitmap originalBm;

    //이미지 회전관련 멤버변수
    Button btnRot90, btnRot270;
    Bitmap rotBm;

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
        btnRot90 = (Button) findViewById(R.id.btnRot90);
        btnRot270 = (Button) findViewById(R.id.btnRot270);


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


        //90도 회전
        btnRot90.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (rotBm != null) {
                    MyAsyncTask rot = new MyAsyncTask();
                    rot.execute(1,90);
                } else {
                    Toast.makeText(MainActivity.this, "아무이미지가없어영", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //270도 회전
        btnRot270.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(rotBm != null){
                    MyAsyncTask rot = new MyAsyncTask();
                    rot.execute(1,270);
                }
                else{
                    Toast.makeText(MainActivity.this, "아무이미지가없어영", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        if (id == R.id.action_load){
            Intent intentIG = new Intent(Intent.ACTION_GET_CONTENT);
            intentIG.setType("image/*");
            startActivityForResult(intentIG, ActID);

            return true;
        }
        else if (id == R.id.action_save) {
            Log.v("DJ","action_save");
            if(originalBm!=null){
                SaveLoad.saveBitmaptoJpeg(((BitmapDrawable)iv.getDrawable()).getBitmap());
                Toast.makeText(MainActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();}

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int activityID, int resultCode, Intent intent) {
        super.onActivityResult(activityID, resultCode, intent);
        Log.v("DJ","시작");
        if(activityID == ActID && resultCode == Activity.RESULT_OK){
            Uri uri = intent.getData();
            try{originalBm = MediaStore.Images.Media.getBitmap(
                    MainActivity.this.getContentResolver(),uri);
            }catch(IOException e){}
            rotBm = originalBm;
            MyAsyncTask initIV = new MyAsyncTask();
            initIV.execute(2);
            Log.v("DJ","끝남");
        }
    }

    public native String stringFromJNI();

    class MyAsyncTask extends AsyncTask<Integer, Integer, Bitmap> {
        @Override
        public Bitmap doInBackground(Integer... params){
            int task = params[0].intValue();
            switch(task){
                case 1 :
                    int angle = params[1].intValue();
                    rotBm = ImageProc.rotImage(rotBm,angle);
                    break;
                case 2 :
                    break;
                default :
                    break;
            }
            return rotBm;
        }
        @Override
        public void onPreExecute(){
            //pb.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPostExecute(Bitmap bm){
            iv.setImageBitmap(rotBm);
            //pb.setVisibility(View.GONE);
        }

    }
}




