package com.example.filesavingandopening;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawView extends View {

    public int width = 200;
    public int color = Color.RED;

    private SharedPreferences sharedPreferences;

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

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        sharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE);


    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //make sure its within boundaries
        canvas.drawRect(0, 0, getRight(),width, paint);
    }

    public void saveData(int h1, int h2, int h3) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("h1", h1);
        editor.putInt("h2", h2);
        editor.putInt("h3", h3);
        editor.apply();
    }

    public void openLocal() {
        width = sharedPreferences.getInt("h1", 200);
        invalidate();
    }
}
























