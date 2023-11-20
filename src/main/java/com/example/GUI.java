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
    int dateCount = 0;
    String bday; 

    ArrayList<String> dates = new ArrayList<String>();

    GUI(PApplet processing) {
        l = processing;
    }

    void display() {

        // sets whats displayed on the screen
        if (state == 1) {
            // main game start background
            drawMainBackground();
            // nextButton();
        }
        if (state == 3) {
            // state 2 is entirely done by the keyPressed function because processing
            // keywords for enter and backspace do not work outside the main file
            // aftere the names are taken in by keyPressed this does the training and
            // generation on them
            nameScreen1();
            // nextButton();
        }
        if (state == 5) {
            dateScreen();
        }
        if (state > 5) {
            // for the final version there will be more than 3 states
            // right now it displays the game over screen and the name generated from the
            // generation
            l.background(255);
            l.fill(0);
            l.text("Game Over", l.width / 2, l.height / 2);
            l.text("Baby Name = " + finalName, l.width / 2, l.height / 2 + 100);
            l.text("Bday = " + bday, l.width / 2, l.height / 2 + 200);
            // nextButton();
            // l.println(state);

        }

    }

    void drawMainBackground() {
        l.background(247, 121, 233);

        // displaying main title
        String s = "Compatibility Generator";
        l.fill(0);
        l.textSize(75);
        l.text(s, 300, l.width / 5);

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
        // tells if the name has been input or not yet
        if (name1 != "a") {
            name1Entered = true;
        }
        if (name2 != "b") {
            name2Entered = true;
        }

        // adds the names together
        String namesTogether = name1 + name2;
        // l.println(namesTogether);
        ArrayList<String> names = new ArrayList<String>();

        // puts the letters of the names in a string arrayList
        for (int i = 0; i < namesTogether.length(); i++) {
            char a = namesTogether.charAt(i);
            String b = String.valueOf(a);

            names.add(b);
        }

        if (name1Entered == true && name2Entered == true) {
            // if both names are submitted so it doesnt run continuously
            MarkovChainGenerator<String> nameGen = new MarkovChainGenerator<String>();
            // trains and generates
            nameGen.trainM(names);
            ArrayList<String> babyName = nameGen.generateM(namesTogether.length() / 2);

            for (int i = 0; i < babyName.size(); i++) {
                finalName += babyName.get(i);
                // l.print(babyName.get(i));
            }
            l.println(finalName);
            // once the final name is gernated it goes to the next sceren
            changeState();
        }

    }

    void dateScreen() {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);

        // int pastdate = dateCount;
        // if()
        l.text("this is running", l.width / 2, l.height / 2);
        for (int i = 0; i < dates.size(); i++) {
            l.println(dates.get(i));
        }
        MarkovChainGenerator<String> dateGen = new MarkovChainGenerator<String>();
        dateGen.trainM(dates);

        ArrayList<String> birthday = dateGen.generateM(1); 
        bday = birthday.get(0);  


        changeState();

    }

    void questionScreen(){
        //this asks questions and
    }

    void changeState() {
        state++;
    }

    void nextButton() {
        // ignore this, might use this is next part of prokect idk yet
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

    // these 2 fucntions get the name from the keyPressed fucntion
    void getName(String name) {
        name1 = name;
    }

    void getName2(String name) {
        name2 = name;
    }

    void getDate(String date) {
        dates.add(date);
        dateCount++;
    }

}
