package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;


public class button{
    PApplet l;
    int x;
    int y;
    int w;
    int h;
    String s;
    Boolean correctAnswer; 

    
    button(PApplet processing, int xVal, int yVal, int wVal, int hVal, String sVal, Boolean correct){
        l = processing;
        x = xVal;
        y= yVal;
        w = wVal;
        h = hVal;
        s = sVal;
        correctAnswer = correct; 
    }

    boolean display(){
        l.textSize(10);
        l.stroke(0);
        l.fill(255); 
        l.rect(x,y,w,h);
        l.text(s, x,y,w,h);

        if ((l.mouseX > x && l.mouseX < l.mouseX + w && l.mouseY > y && l.mouseY < y + h) && l.mousePressed) {
            l.println("button pushed"); 
            return true;
        }
        return false;

    }

    boolean getCorrect(){
        return correctAnswer; 
    }

}