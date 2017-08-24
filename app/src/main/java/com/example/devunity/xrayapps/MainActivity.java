package com.example.devunity.xrayapps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindorks.paracamera.Camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView picFrame;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picFrame = (ImageView) findViewById(R.id.picFrame);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);




        camera = new Camera.Builder()
                //.setDirectory(extStorageDirectory)   //add .nomedia file in this directory so that the pictures can't be seen on the gallery
                //.setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {

            Bitmap bitmap = camera.getCameraBitmap();

            if (bitmap != null) {
                Bitmap b = Bitmap.createScaledBitmap(bitmap, 450, 325, false);

                createDirectoryAndSaveFile(b, "my.jpg");

                picFrame.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this.getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Save Image in a Specific Directory

    private void createDirectoryAndSaveFile(Bitmap imgSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/xray");

        if (!direct.exists()) {
            File imageDirectory = new File("/sdcard/xray");
            imageDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/xray/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imgSave.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }

    public void scan(View view){
        Intent i = new Intent(MainActivity.this, LoadingPage.class);
        startActivity(i);
    }
}