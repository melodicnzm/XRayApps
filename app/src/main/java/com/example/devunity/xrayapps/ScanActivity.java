package com.example.devunity.xrayapps;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by devUnity on 30/06/2017.
 */

public class ScanActivity extends AppCompatActivity {

    ImageView imageView;
    Button shareButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);


        imageView = (ImageView) findViewById(R.id.imageView);

//        //textView = (TextView) findViewById(R.id.textView);
//
//        if (randomBox() == 0 ){
//            textView.setText("You're Virgin!!");
//        } else {
//            textView.setText("Sorry, You have lost your virginity!!");
//        }

        // Getting Images from the sdcard folder

        File imgFile = new  File("/sdcard/xray/my.jpg");
        if(imgFile.exists())
        {


            Bitmap mBitmap = BitmapFactory.decodeFile("/sdcard/xray/my.jpg");
            Bitmap b = Bitmap.createScaledBitmap(mBitmap, 450, 325, false);
            Bitmap nBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tool);
            Bitmap c = Bitmap.createScaledBitmap(nBitmap, 450, 325, false);

            final Bitmap merged = combineImages(b, c);

            imageView.setImageBitmap(merged);

            shareButton =(Button) findViewById(R.id.shareButton);
            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    shareIt(merged);
                }
            });




        }
            ///Now set this bitmap on imageview

//        File imgFile = new  File("/sdcard/xray/my.jpg");
//        if(imgFile.exists())
//        {
//            //ImageView myImage = new ImageView(this);
//            imageView.setImageURI(Uri.fromFile(imgFile));
//
//        }



    }

    private void shareIt(Bitmap bmp) {
//sharing implementation here

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "virgin");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);


        OutputStream outstream;
        try {
            outstream = getContentResolver().openOutputStream(uri);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

//    public void share(View view, Bitmap bmp){
//
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//    }

    public static int randomBox() {

        Random rand = new Random();
        int pickedNumber = rand.nextInt(2);
        return pickedNumber;

    }

    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }
}
