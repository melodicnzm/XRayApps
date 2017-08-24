package com.example.devunity.xrayapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by devUnity on 30/06/2017.
 */

public class MyApplication extends AppCompatActivity {
    private AdView mAdView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_app);

    }

    public void openCamera(View view){
        Intent i = new Intent(MyApplication.this, MainActivity.class);
        startActivity(i);
    }

    public void instruction(View view){
        Intent i = new Intent(MyApplication.this, InstructionActivity.class);
        startActivity(i);
    }

    public void exitFromApp(View view){
        //Exiting from app Code
    }


}
