package com.example.egzaminobuvusiuzduotis;

import static androidx.core.math.MathUtils.clamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class DrawView extends View {

    private static final int NONE = 0;
    private static final int TRIANGLE = 1;
    private static final int CIRCLE = 2;
    private static final int RECTANGLE = 3;


    boolean fillFlag = false;
    int colorH = Color.GREEN;
    int width = getWidth();
    int height =300;


    int figure;
    private SharedPreferences sharedPreferences;
    public FigureSave figureSave;
    private Gson gson;
    private Bitmap bitmap;


    public DrawView(Context context) {
        super(context);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    void init(Context context)
    {
        Gson gson = new Gson();
        sharedPreferences = context.getSharedPreferences("circleData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("circleDataList", null);
        if (json != null) {
            figureSave = gson.fromJson(json, FigureSave.class);
        } else {
            figureSave = null;
        }
    }


    public int getFigure(){
        return figure;
    }
    public void setFigure(int figure) {
        this.figure = figure;
    }
    public boolean getFillFlag() {
        return fillFlag;
    }

    public void setFillFlag(boolean fillFlag) {
        this.fillFlag = fillFlag;
    }

    public void changeThickness(){
        Random random = new Random();
        height = random.nextInt(getHeight()/3);
    }
boolean isRandom;
    public void changeColor(){
        Random random = new Random();
       colorH =  Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
       isRandom = true;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        colorH = Color.GREEN;
        width = getWidth();

        Paint paint;
        paint = new Paint();

        if(figureSave != null)
        {
            colorH = figureSave.color;
            figure = figureSave.figure;
            fillFlag = true;
            switch (figure){
                case TRIANGLE:{
                    triangle(paint, width, figureSave.thickness, canvas);
                    break;
                }
                case CIRCLE:{
                    circle(paint, width, figureSave.thickness, canvas);
                    break;}
                case RECTANGLE:{
                    rectangle(paint, width, height, canvas,figureSave.rectColor1, new Rect(0, 0, width, figureSave.rectHeight1));
                    rectangle(paint, width, height, canvas, figureSave.rectColor2, new Rect(0, figureSave.rectHeight1, width, figureSave.rectHeight2));
                    rectangle(paint, width, height, canvas,  figureSave.rectColor3, new Rect(0, figureSave.rectHeight2, width, figureSave.rectHeight3));
                    break;}
                case NONE:{ break;}
                default:break;
            }

        }
        else{
            switch (figure){
                case TRIANGLE:{
                    triangle(paint, width, height, canvas);
                    figureSave = new FigureSave(figure, height, colorH);
                    break;
                }
                case CIRCLE:{
                    circle(paint, width, height, canvas);
                    figureSave = new FigureSave(figure, height, colorH);
                    break;}
                case RECTANGLE:{
                    int color1 =  Color.YELLOW;
                    int color2 = Color.RED;
                    int color3 =  Color.GREEN;
                    Random random = new Random();
                    if(isRandom)
                    {
                        color1 = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        color2 = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        color3 = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        isRandom = false;
                    }
                    int h1 = clamp( random.nextInt(height), 1, height);
                    int h2 = clamp(height + random.nextInt(2*height), height, 2*height);
                    int h3 = clamp(2*height + random.nextInt(3*height), 2*height, 3*height);

                    rectangle(paint, width, height, canvas,color1, new Rect(0, 0, width, h1));
                    rectangle(paint, width, height, canvas, color2, new Rect(0, h1, width, h2));
                    rectangle(paint, width, height, canvas,  color3, new Rect(0, h2, width, h3));
                    figureSave = new FigureSave(RECTANGLE, color1, color2, color3, h1, h2, h3);
                    break;}
                case NONE:{ break;}
                default:break;
            }
        }
        Log.d("Test", "Figure: " + figure + " ColorH: " + colorH);
        if(figureSave != null)  saveData();
    }

    private void rectangle(Paint paint, int width, int height, Canvas canvas, int color, Rect rect) {
        paint.setColor(color);
        if(fillFlag){paint.setStyle(Paint.Style.FILL_AND_STROKE);}
        else{
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setStrokeWidth(10f);

        canvas.drawRect(rect, paint);


    }

    private void circle(Paint paint, int width, int height, Canvas canvas) {
        height = getHeight();
        paint.setColor(colorH);
       if(fillFlag){paint.setStyle(Paint.Style.FILL_AND_STROKE);}
       else {
           paint.setStyle(Paint.Style.STROKE);
       }
       paint.setStrokeWidth(10f);

       canvas.drawCircle(width/2, height/2, width/2, paint);
    }

    private void triangle(Paint paint, int width,int height,Canvas canvas) {
        height = getHeight();
        paint.setColor(colorH);
        if(fillFlag){paint.setStyle(Paint.Style.FILL_AND_STROKE);}
        else paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(10f);

        Point point1_draw = new Point(width/2, 0);
        Point point2_draw = new Point(0, height);
        Point point3_draw = new Point(width, height);

        Path path = new Path();
        path.moveTo(point1_draw.x, point1_draw.y);
        path.lineTo(point2_draw.x, point2_draw.y);
        path.lineTo(point3_draw.x, point3_draw.y);
        path.lineTo(point1_draw.x, point1_draw.y);
        path.close();

        canvas.drawPath(path, paint);

    }
    public void saveData() {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(figureSave);
        editor.putString("circleDataList", json);
        editor.apply();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }


}
