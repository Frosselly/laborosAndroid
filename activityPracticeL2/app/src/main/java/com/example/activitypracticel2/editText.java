package com.example.activitypracticel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editText extends AppCompatActivity {

    Button _backBtn;
    Button _sendBtn;

    EditText _eText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        _backBtn = findViewById(R.id.backButton5);
        _sendBtn = findViewById(R.id.sendButton);
        _eText = findViewById(R.id.editText);

        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        _sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("wdText", _eText.getText().toString());
                startActivity(intent);
            }
        });
    }
}