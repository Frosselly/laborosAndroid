package com.example.egzaminobuvusiuzduotis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;
    Button triangleBtn;
    Button circleBtn;
    Button rectangleBtn;
    Button clearBtn;
    CheckBox fillFlagCB;

    Boolean fillFlag = true;

    Button spinBtn;
    Button thicknessBtn;
    Button colorBtn;
    Button saveBtn;
    Button openBtn;


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

        drawView = findViewById(R.id.view);

        triangleBtn = findViewById(R.id.TRIANGLE);
        circleBtn = findViewById(R.id.CIRCLE);
        rectangleBtn = findViewById(R.id.RECTANGLE);
        clearBtn = findViewById(R.id.CLEAR);
        fillFlagCB = findViewById(R.id.checkBox);
        spinBtn = findViewById(R.id.spin);
        thicknessBtn = findViewById(R.id.thickness);
        colorBtn= findViewById(R.id.color);
        openBtn = findViewById(R.id.open);

        int figure = 1;

        //region Listeners

        CompoundButton.OnCheckedChangeListener fillFlagCBChange = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fillFlag = isChecked;
            }
        };

        View.OnClickListener triangleBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFigure(1, fillFlag);
            }
        };

        View.OnClickListener circleBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFigure(2, fillFlag);
            }
        };

        View.OnClickListener rectangleBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFigure(3, fillFlag);
            }
        };

        View.OnClickListener clearBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFigure(0, fillFlag);
            }
        };
        View.OnClickListener saveBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(drawView);
            }
        };
        View.OnClickListener openBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(drawView);
            }
        };


        triangleBtn.setOnClickListener(triangleBtnClick);
        circleBtn.setOnClickListener(circleBtnClick);
        rectangleBtn.setOnClickListener(rectangleBtnClick);
        clearBtn .setOnClickListener(clearBtnClick);
        fillFlagCB.setOnCheckedChangeListener(fillFlagCBChange);
        //endregion

        View.OnClickListener spinBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(drawView, "rotation",0f, 360f);
                animation.setDuration(1000);
                animation.start();

            }
        };
        spinBtn.setOnClickListener(spinBtnClick);

        View.OnClickListener thicknessBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomizeThickness();
            }
        };
        thicknessBtn.setOnClickListener(thicknessBtnClick);

        View.OnClickListener colorBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomizeColor();
            }
        };
        colorBtn.setOnClickListener(colorBtnClick);

        //drawView.invalidate();

    }

    private void RandomizeColor() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                drawView.setFigure(2);
//                drawView.setFillFlag(true);
                drawView.changeColor();
                drawView.figureSave = null;
                drawView.invalidate();
            }
        });
    }

    private void RandomizeThickness() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                drawView.setFigure(2);
//                drawView.setFillFlag(true);
                drawView.changeThickness();
                drawView.figureSave = null;
                drawView.invalidate();
            }
        });
    }

    public void setFigure(final int figure, final boolean fillFlag)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawView.setFigure(figure);
                drawView.setFillFlag(fillFlag);
                drawView.figureSave = null;
                drawView.invalidate();
            }
        });
    }

    public void save(View v){
        Bitmap b = Bitmap.createBitmap(drawView.getWidth(), drawView.getHeight(), Bitmap.Config.ARGB_8888);

        FileOutputStream outStream = null;
        File file = new File(getExternalFilesDir(null), "shapeImage.jpg");
        try {
            outStream = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(View v){
        File imgFile = new  File(getExternalFilesDir(null), "shapeImage.jpg");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            drawView.setBitmap(myBitmap);
        }
    }
}