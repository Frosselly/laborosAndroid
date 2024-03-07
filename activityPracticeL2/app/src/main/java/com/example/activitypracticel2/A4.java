package com.example.activitypracticel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class A4 extends AppCompatActivity {

    Button _btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a4);

        TextView textView = findViewById(R.id.textView4);
        String data = getIntent().getStringExtra("tekstas");
        textView.setText(data);

        _btn = findViewById(R.id.backButton4);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}