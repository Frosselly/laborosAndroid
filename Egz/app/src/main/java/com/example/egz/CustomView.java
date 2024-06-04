package com.example.egz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//https://developer.android.com/develop/ui/views/layout/custom-views/create-view
//https://developer.android.com/develop/ui/views/layout/custom-views/custom-drawing
public class CustomView extends View {
    private Paint fill;
    private Paint outline;
    private int numCircles;
    private Random random;

    List<CircleData> circleDataList;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedPreferences = context.getSharedPreferences("circleData", Context.MODE_PRIVATE);
        init();
    }

    private void init() {
        random = new Random();

        fill = new Paint();
       // fill.setColor(0xff101010);
        fill.setARGB(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        fill.setStyle(Paint.Style.FILL);

        outline = new Paint();
//        outline.setColor(0xffFF0000);
        outline.setStyle(Paint.Style.STROKE);
        outline.setARGB(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        outline.setStrokeWidth(5);

        String json = sharedPreferences.getString("circleDataList", null);
        if (json != null) {
            Type type = new TypeToken<List<CircleData>>(){}.getType();
            circleDataList = gson.fromJson(json, type);
        } else {
            circleDataList = new ArrayList<>();
        }
    }
    public void drawMemory() {

        invalidate(); // redraw view
    }

    public void setNumCircles(int numCircles) {
        this.numCircles = numCircles;
        circleDataList.clear(); // clear the list before drawing new circles
        invalidate(); // redraw view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!circleDataList.isEmpty()) {
            for (CircleData circleData : circleDataList) {
                //fill.setColor(circleData.color);
                outline.setStrokeWidth(circleData.borderSize);
                canvas.drawCircle(circleData.x, circleData.y, circleData.radius, fill);
                canvas.drawCircle(circleData.x, circleData.y, circleData.radius, outline);
            }
        } else {
            for (int i = 0; i < numCircles; i++) {
                float cx = random.nextFloat() * getWidth();
                float cy = random.nextFloat() * getHeight();
                float radius = random.nextFloat() * (Math.min(getWidth(), getHeight()) / 10);
                canvas.drawCircle(cx, cy, radius, fill);
                canvas.drawCircle(cx, cy, radius, outline); // draw the outline

                CircleData circleData = new CircleData(fill.getColor(), (int) radius, (int) cx, (int) cy, (int) outline.getStrokeWidth());
                circleDataList.add(circleData);
            }
//            if(numCircles > 0)
//                saveData();
        }


    }
    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(circleDataList);
        editor.putString("circleDataList", json);
        editor.apply();
    }


}

