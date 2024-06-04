package com.example.egz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    private CustomView customView;

    RadioButton circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextText);
        customView = findViewById(R.id.customView);
        customView.drawMemory();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawCircles();
            }
        });
//        Paint painter = new Paint(0);
//        painter.setColor(0xff101010);


    }



    private void DrawCircles() {
        String text = editText.getText().toString();

        int numCircles = editText.getText().length();
        try {
            numCircles = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        customView.setNumCircles(numCircles);
    }
    @Override
    protected void onPause() {
        super.onPause();
        customView.saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customView.saveData();
    }

}