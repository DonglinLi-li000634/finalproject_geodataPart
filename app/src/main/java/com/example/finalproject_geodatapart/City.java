package com.example.finalproject_geodatapart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class City extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        TextView showInfo=(TextView) findViewById(R.id.CityDetails);
        Button goGG=(Button)findViewById(R.id.SearchOnGG);
        Intent fromLast=getIntent();
        String infoF=fromLast.getStringExtra("info");
        double lat=fromLast.getDoubleExtra("lat",0.0);
        double lon=fromLast.getDoubleExtra("lon",0.0);
        if(infoF!=null){
            showInfo.setText(infoF);
        }
        goGG.setOnClickListener(click->{
            if(lat!=0.0&&lon!=0.0){
                String urlSEC="https://www.google.com/maps/search/?api=1&query="+lat+","+lon;//47.5951518,-122.3316393"
                Uri uri= Uri.parse(urlSEC);
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
}