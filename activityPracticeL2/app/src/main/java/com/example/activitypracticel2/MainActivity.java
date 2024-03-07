package com.example.activitypracticel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button _button;
    Button _btn2;
    Button _btn3;
    Button _btn4;
    Button _btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _button = findViewById(R.id.button);
        _btn2 = findViewById(R.id.button2);
        _btn3 = findViewById(R.id.button3);
        _btn4 = findViewById(R.id.button4);
        _btnEdit = findViewById(R.id.button5);

        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Activity2.class);
                intent.putExtra("Data", "Hello world");
                startActivity(intent);
            }
        });

        _btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), A2.class);
                intent.putExtra("tekstas", "Antras");
                startActivity(intent);
            }
        });
        _btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), A3.class);
                intent.putExtra("tekstas", "Trecias");
                startActivity(intent);
            }
        });
        _btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), A4.class);
                intent.putExtra("tekstas", "Ketvirtas");
                startActivity(intent);
            }
        });

        _btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), editText.class);
                startActivity(intent);
            }
        });


        TextView textView = findViewById(R.id.textView5);
        String data = getIntent().getStringExtra("wdText");
        textView.setText(data);



    }
}