package com.example;

import java.util.ArrayList;

import processing.core.PApplet;

public class GUI {
    // global variables
    PApplet l;
    int state = 1;
    String name1 = "a";
    String name2 = "b";
    Boolean name1Entered = false;
    Boolean name2Entered = false;
    String finalName = ""; 

    GUI(PApplet processing) {
        l = processing;
    }

    void display() {

        // if (state == 0) {
        //     l.background(255);
        //     l.text("click button to start", l.width / 2, l.height / 2);
        //     // nextButton();
        //     l.println(state);
        // }
        if (state == 1) {
            drawMainBackground();
            // nextButton();
            // l.println(state);
        }
        if (state == 3) {
            nameScreen1();

            // nextButton();
            // l.println(state);
        } else if (state > 2) {
            l.background(255);
            l.fill(0); 
            l.text("Game Over", l.width / 2, l.height / 2);
            l.text("Baby Name = " + finalName, l.width / 2, l.height / 2+100);
            // nextButton();
            // l.println(state);

        }
        // nextButton();

    }

    void drawMainBackground() {
        l.background(247, 121, 233);
        // nextButton();

        // displaying main title
        String s = "Compatibility Generator";
        l.fill(0);
        l.textSize(75);
        l.text(s, 300, l.width / 5);
        // nextButton();

    }

    void nameScreen1() {

        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);

        l.println(name1);
        l.println(name2);
        if (name1 != "a") {
            name1Entered = true;
        }
        if (name2 != "b") {
            name2Entered = true;
        }
        String namesTogether = name1 + name2;
        // l.println(namesTogether);
        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < namesTogether.length(); i++) {
            char a = namesTogether.charAt(i);
            String b = String.valueOf(a);

            names.add(b);
        }

        // for(int i =0; i < names.size();i++){
        // l.println(names.get(i));
        // }

        // public static void testAndTrainProbGen() {
        // declare and test prob gen
        if (name1Entered == true && name2Entered == true) {
            MarkovChainGenerator<String> nameGen = new MarkovChainGenerator<String>();
            // ProbabilityGenerator<Double> rythemGen = new ProbabilityGenerator<Double>();

            nameGen.trainM(names);
            ArrayList<String> babyName = nameGen.generateM(namesTogether.length()/2);

            for (int i = 0; i < babyName.size(); i++) {
                finalName += babyName.get(i); 
                // l.print(babyName.get(i));
            }
            l.println(finalName);
            changeState();
        }

        // rythemGen.train(midiNotes.getRhythmArray());
        // // System.out.println("Starting pre print");

        // nameGen.printProbabilityDistribution(false);

        // rythemGen.printProbabilityDistribution(false);

        // }

    }

    void changeState() {
        state++;
        // need if statement
        // if state == max screen rest to 0
    }

    void nextButton() {
        int squareX = l.width - 300;
        int squareY = 0 + 200;
        int squareAdd = 200;
        int squareYAdd = 100;
        l.stroke(0);
        l.fill(0);
        l.rect(squareX, squareY, squareAdd, squareYAdd);
        if ((l.mouseX > squareX && l.mouseX < l.mouseX + squareAdd && l.mouseY > squareY
                && l.mouseY < squareY + squareYAdd) && l.mousePressed) {
            state = 0;
            // l.println("screen reset");

        }
    }

    int getState() {
        return state;
    }

    void getName(String name) {
        name1 = name;
    }

    void getName2(String name) {
        name2 = name;
    }

}
