package com.example.finalproject_geodatapart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getin=(Button)findViewById(R.id.GetIn);
        getin.setOnClickListener(click->{
            Intent intent=new Intent(MainActivity.this,GeoMain.class);
            startActivityForResult(intent,30);
        });
    }
}