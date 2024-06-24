package com.example.egzaminobuvusiuzduotis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import android.os.Environment;
import android.Manifest;

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
        saveBtn = findViewById(R.id.save);
        int figure = 1;

//        if (ContextCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_MEDIA_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // You can use the API that requires the permission.
//            Log.i("PERMISSION", "PERMISSION GRANTED");
//
//        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
//                this, Manifest.permission.ACCESS_MEDIA_LOCATION)) {
//            Log.e("PERMISSION", "PERMISSION Requested");
//            //showInContextUI(...);
//        } else {
////            requestPermissionLauncher.launch(
////                    Manifest.permission.ACCESS_MEDIA_LOCATION);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION}, 1);
//            Log.e("PERMISSION", "PERMISSION DENIED");
//        }

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
        saveBtn.setOnClickListener(saveBtnClick);

        View.OnClickListener openBtnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(drawView);
            }
        };
        openBtn.setOnClickListener(openBtnClick);


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

//    public void save(View v){
//        Bitmap b = Bitmap.createBitmap(drawView.getWidth(), drawView.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(b);
//        drawView.draw(canvas);
//
//        FileOutputStream outStream = null;
//        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File file = new File(picturesDir, "shapeImage.jpg");
//        if (file.exists())
//            file.delete();
//        try {
//            outStream = new FileOutputStream(file);
//            b.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//
//            outStream.flush();
//            outStream.close();
//
//            //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
//        } catch (FileNotFoundException e) {
//            Log.w("TAG", "Error saving image file: " + e.getMessage());
//
//        } catch (IOException e) {
//            Log.w("TAG", "Error saving image file: " + e.getMessage());
//
//        }
//        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
//    }

    public void save(View v){
        Bitmap b = Bitmap.createBitmap(drawView.getWidth(), drawView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        drawView.draw(canvas);

        String displayName = "shapeImage.jpg";

        // Delete
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[] {displayName};
        getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream outStream = new FileOutputStream(pfd.getFileDescriptor());
            b.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        }
    }



//    public void open(View v){
//
//        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File imgFile = new File(picturesDir, "shapeImage.jpg");
//
//        if(imgFile.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            drawView.setBitmap(myBitmap);
//        }
//        else {
//            Log.e("FNF", "File not found " + imgFile.getAbsolutePath());
//        }
//    }

    public void open(View v){
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[] {"shapeImage.jpg"};
        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(queryUri, null, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            long imageId = cursor.getLong(columnIndex);
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);

            try {
                ParcelFileDescriptor pfd = resolver.openFileDescriptor(imageUri, "r");
                if (pfd != null) {
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                    drawView.setBitmap(bitmap);
                    pfd.close();
                }
            } catch (FileNotFoundException e) {
                Log.w("TAG", "Error opening image file: " + e.getMessage());
            } catch (IOException e) {
                Log.w("TAG", "Error opening image file: " + e.getMessage());
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}