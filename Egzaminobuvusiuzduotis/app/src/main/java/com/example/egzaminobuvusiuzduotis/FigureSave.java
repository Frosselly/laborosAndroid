package com.example.egzaminobuvusiuzduotis;

public class FigureSave {

    public boolean isFilled;

    public int figure;
    public int thickness;
    public int color;


    //For rectangle
    public int rectColor1;
    public int rectColor2;
    public int rectColor3;
    public int rectHeight1;
    public int rectHeight2;
    public int rectHeight3;


    public FigureSave(int figure, int thickness, int color) {
        this.figure = figure;
        this.thickness = thickness;
        this.color = color;
    }


    public FigureSave(int figure, int rectColor1, int rectColor2, int rectColor3, int rectHeight1, int rectHeight2, int rectHeight3) {
        this.figure = figure;
        this.rectColor1 = rectColor1;
        this.rectColor2 = rectColor2;
        this.rectColor3 = rectColor3;
        this.rectHeight1 = rectHeight1;
        this.rectHeight2 = rectHeight2;
        this.rectHeight3 = rectHeight3;
    }



}
