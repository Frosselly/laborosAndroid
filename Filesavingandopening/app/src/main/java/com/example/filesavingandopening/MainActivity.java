package com.example.filesavingandopening;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Xml;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;



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





        Button spalvaBtn = findViewById(R.id.Spalva);
        Button storisBtn = findViewById(R.id.Storis);
        Button localSave = findViewById(R.id.LocalSave);
        Button localOpen = findViewById(R.id.LocalOpen);
        Button Xmlsave = findViewById(R.id.SaveXML);
        Button Xmlopen = findViewById(R.id.OpenXML);
        Button spinBtn = findViewById(R.id.Spin);

        drawView = findViewById(R.id.view);


        spalvaBtn.setOnClickListener(v -> {
           drawView.color = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            draw();
        });
        storisBtn.setOnClickListener(v -> {
            Random random = new Random();
            drawView.width = 200 + random.nextInt(drawView.getHeight() - 200);
            draw();
        });

        localSave.setOnClickListener(v -> {
            drawView.saveData(drawView.width, 200, 200);
        });
        localOpen.setOnClickListener(v -> {
            drawView.openLocal();
        });
        Xmlsave.setOnClickListener(v -> {
            saveToXML();
        });
        Xmlopen.setOnClickListener(v -> {
            loadFromXML();
        });

        spinBtn.setOnClickListener(v -> {
            ObjectAnimator animation = ObjectAnimator.ofFloat(drawView, "rotation",0f, 360f);
            animation.setDuration(1000);
            animation.start();

//            ObjectAnimator animation = ObjectAnimator.ofFloat(drawView, "translationX",100f);
//            animation.setDuration(1000);
//
//
//            ObjectAnimator animation2 = ObjectAnimator.ofFloat(drawView, "translationX",-100f);
//            animation2.setDuration(1000);
//
//            ObjectAnimator animation3 = ObjectAnimator.ofFloat(drawView, "translationX",0);
//            animation3.setDuration(1000);
//
//
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playSequentially(animation, animation2, animation3);
//            animatorSet.start();
        });

        draw();
    }

    private void draw() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawView.invalidate();
            }
        });
    }


    public void saveToXML() {
        try {
            // Create an XmlSerializer and use it to write the state of the DrawView to a StringWriter
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag(null, "DrawView");
            serializer.startTag(null, "Width");
            serializer.text(String.valueOf(drawView.width));
            serializer.endTag(null, "Width");
            serializer.startTag(null, "Color");
            serializer.text(String.valueOf(drawView.color));
            serializer.endTag(null, "Color");
            serializer.endTag(null, "DrawView");
            serializer.endDocument();

            // Create a ContentValues object and set the necessary values for the file you want to create
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "drawView.xml");
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/xml");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
            }

            // Use the ContentResolver to create a new file in the MediaStore
            ContentResolver resolver = getContentResolver();
            Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

            // Open an OutputStream to the URI of the new file and write the XML data to it
            if (uri != null) {
                OutputStream os = resolver.openOutputStream(uri);
                if (os != null) {
                    os.write(writer.toString().getBytes());
                    os.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void loadFromXML() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                // Use the Uri object to open the XML file and load data into your DrawView
                loadFromXML2(uri);
            }
        }
    }
    public void loadFromXML2(Uri uri) {
        try {
            // Use the ContentResolver to open an InputStream to the XML file
            ContentResolver resolver = getContentResolver();
            //Uri uri = MediaStore.Files.getContentUri("external");
            InputStream is = resolver.openInputStream(uri);

            // Create an XmlPullParser and set the InputStream as its input
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);

            // Use the nextTag() method to read the XML file tag by tag
            while (parser.nextTag() == XmlPullParser.START_TAG) {
                String tagName = parser.getName();

                // When you encounter the "Width" and "Color" tags, use the nextText() method to read their values
                if (tagName.equals("Width")) {
                    drawView.width = Integer.parseInt(parser.nextText());
                } else if (tagName.equals("Color")) {
                    drawView.color = Integer.parseInt(parser.nextText());
                }
            }

            // Don't forget to close the InputStream when you're done
            is.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}