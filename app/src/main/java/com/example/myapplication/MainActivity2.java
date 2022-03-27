package com.example.myapplication;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    Button Info;
    String time2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Info = findViewById(R.id.Info);
        Info.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent time = getIntent();
                if(time.getExtras() != null){
                    time2 = getIntent().getStringExtra("time");
                }
                String token ;
                Intent tokenIntent =getIntent();
                token = tokenIntent.getStringExtra("token");
                Intent intent2 = new Intent(MainActivity2.this,MainActivity3.class);
                intent2.putExtra("time",time);
                intent2.putExtra("token2",token);
                System.out.println(token);
                startActivity(intent2);
            }
        });
    }
}