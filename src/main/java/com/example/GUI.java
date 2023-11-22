package com.example;

import java.lang.reflect.Array;
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
    Boolean reset = false;
    Boolean question1Done = false;
    Boolean question2Done = false;
    Boolean question3Done = false;
    Boolean question4Done = false;

    Boolean question5Done = false;
    Boolean question6Done = false;
    Boolean question7Done = false;
    Boolean question8Done = false;
    int timeRunning = 1;

    String question = "";
    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String correctAnswer = "";
    ArrayList<Boolean> answers = new ArrayList<Boolean>();

    ArrayList<String> dates = new ArrayList<String>();

    GUI(PApplet processing) {
        l = processing;
    }

    void display() {

        // sets whats displayed on the screen
        if (state == 1) {
            // main game start background
            drawMainBackground();
            reset = false;
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
        if (state == 6) {
            askQuestionsPlayer1();
        }
        // if (state == 7) {
        //     askQuestionsPlayer2();

        // }
        // // state++;
        // }
        // if (state == 7) {
        // questionScreen2();
        // }
        // if (state == 8) {
        // questionScreen3();
        // }
        // // if (state == 9) {
        // // questionScreen4();
        // // }
        // // if (state == 10) {
        // // questionScreen1();
        // // }
        // // if (state == 11) {
        // // questionScreen2();
        // // }
        // // if (state == 12) {
        // // questionScreen3();
        // // }
        // // if (state == 13) {
        // // questionScreen4();
        // // }
        if (state > 6) {
            // for the final version there will be more than 3 states
            // right now it displays the game over screen and the name generated from the
            // generation
            l.background(255);
            l.fill(0);
            l.stroke(255);
            l.text("Game Over", l.width / 2, l.height / 2);
            l.text("Baby Name = " + finalName, l.width / 2, l.height / 2 + 100);
            l.text("Bday = " + bday, l.width / 2, l.height / 2 + 150);
            resetButton("Click Here To Try Again", l.width / 2, l.height - 100, 200, 100);
        }
        // // nextButton();
        // // l.println(state);

        // }

    }

    void endScreen() {
        l.background(255);
        l.fill(0);
        l.stroke(255);
        l.text("Game Over", l.width / 2, l.height / 2);
        l.text("Baby Name = " + finalName, l.width / 2, l.height / 2 + 100);
        l.text("Bday = " + bday, l.width / 2, l.height / 2 + 150);
        resetButton("Click Here To Try Again", l.width / 2, l.height - 100, 200, 100);
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

    boolean questionScreen1() {

        // draw outerSquare
        // l.noStroke();
        // l.fill(247, 121, 233);
        // l.rect(0, 0, l.width, l.height);

        // // draw inner square
        // l.fill(252, 235, 250);
        // l.rect(50, 50, l.width - 100, l.height - 100);

        // while (i <4) {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);
        // if (i == 0) {
        question = "What is the capital of Lithuania?";
        answer1 = "Bucharest";
        answer2 = "Ankara";
        answer3 = "Tbilisi";
        correctAnswer = "Vilnius";

        l.text(question, 0, 0, 500, 100);
        button firstAnswer = new button(l, 100, 200, 100, 100, answer1, false);
        button secondButton = new button(l, 100, 400, 100, 100, answer2, false);
        button thirdButton = new button(l, 100, 600, 100, 100, correctAnswer, true);
        button fourButton = new button(l, 100, 800, 100, 100, answer3, false);

        firstAnswer.display();
        secondButton.display();
        thirdButton.display();
        fourButton.display();
        if (firstAnswer.display() == true || secondButton.display() == true || thirdButton.display() == true
                || fourButton.display() == true) {
            if (thirdButton.display() == true && thirdButton.getCorrect() == true) {
                answers.add(true);
            } else {
                answers.add(false);
            }
            question1Done = true;
            if (timeRunning == 2) {
                question5Done = true;
            }
            return true;
            // changeState();
            // i++;
            // continue;
        }
        return false;
        // changeState();

    }

    void questionScreen2() {

        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);
        question = "What is the smallest bone in the human body?";
        answer1 = " The phalanges, in your pinkies";
        answer2 = "The nasal shaft, in your nose";
        answer3 = "The cervical vertebrae, in your spine";
        correctAnswer = "The stapes bone, in your ear";

        l.text(question, 0, 0, 500, 100);
        button firstAnswer = new button(l, 200, 200, 100, 100, answer1, false);
        button secondButton = new button(l, 200, 400, 100, 100, correctAnswer, true);
        button thirdButton = new button(l, 200, 600, 100, 100, answer2, false);
        button fourButton = new button(l, 200, 800, 100, 100, answer3, false);

        firstAnswer.display();
        secondButton.display();
        thirdButton.display();
        fourButton.display();
        if (firstAnswer.display() == true || secondButton.display() == true || thirdButton.display() == true
                || fourButton.display() == true) {
            if (secondButton.display() == true && secondButton.getCorrect() == true) {
                answers.add(true);

            } else {
                answers.add(false);

            }
            question2Done = true;
            if (timeRunning == 2) {
                question6Done = true;
            }
            // changeState();
            // i++;
            // continue;
        }
    }

    void questionScreen3() {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);
        question = "What country has competed the most times in the Summer Olympics yet has not won a gold medal?";
        answer1 = "Romania";
        answer2 = "Liechtenstein";
        answer3 = "Turkey";
        correctAnswer = "The Philippines";

        l.text(question, 0, 0, 500, 100);
        button firstAnswer = new button(l, 300, 200, 100, 100, correctAnswer, true);
        button secondButton = new button(l, 300, 400, 100, 100, answer1, false);
        button thirdButton = new button(l, 300, 600, 100, 100, answer2, false);
        button fourButton = new button(l, 300, 800, 100, 100, answer3, false);

        firstAnswer.display();
        secondButton.display();
        thirdButton.display();
        fourButton.display();
        if (firstAnswer.display() == true || secondButton.display() == true || thirdButton.display() == true
                || fourButton.display() == true) {
            if (firstAnswer.display() == true && firstAnswer.getCorrect() == true) {
                answers.add(true);
                // state++;
            } else {
                answers.add(false);
                // state++;
            }
            question3Done = true;
            if (timeRunning == 2) {
                question7Done = true;
            }
            // changeState();
            // i++;
            // continue;
        }
    }

    void questionScreen4() {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);
        question = "Who was the first woman ever inducted into the Rock and Roll Hall of Fame?";
        answer1 = "Madonna";
        answer2 = "Taylor Swift";
        answer3 = "Diana Ross";
        correctAnswer = "Aretha Franklin";

        l.text(question, 0, 0, 500, 100);
        button firstAnswer = new button(l, 400, 200, 100, 100, answer3, false);
        button secondButton = new button(l, 400, 400, 100, 100, answer1, false);
        button thirdButton = new button(l, 400, 600, 100, 100, answer2, false);
        button fourButton = new button(l, 400, 800, 100, 100, correctAnswer, true);

        firstAnswer.display();
        secondButton.display();
        thirdButton.display();
        fourButton.display();
        if (firstAnswer.display() == true || secondButton.display() == true || thirdButton.display() == true
                || fourButton.display() == true) {
            if (fourButton.display() == true && fourButton.getCorrect() == true) {
                answers.add(true);
                // state++;
            } else {
                answers.add(false);
                // state++;
            }
            question4Done = true;
            if (timeRunning == 2) {
                question8Done = true;
            }
            // changeState();
            // i++;
            // continue;
        }
    }

    // if (question1 == true) {
    // l.fill(0);
    // l.textSize(75);
    // l.text("Question 1: ", l.width/2, 200);
    // }

    void changeState() {
        state++;
    }

    boolean nextButton(String s, int x, int y, int w, int h) {
        // ignore this, might use this is next part of prokect idk yet
        // int squareX = l.width - 300;
        // int squareY = 0 + 200;
        // int squareAdd = 200;
        // int squareYAdd = 100;
        l.stroke(0);
        l.fill(0);
        l.rect(x, y, w, h);
        l.text(s, x, y, w, h);
        if ((l.mouseX > x && l.mouseX < l.mouseX + w && l.mouseY > y && l.mouseY < y + h) && l.mousePressed) {
            return true;
            // l.println("screen reset");

        }
        return false;
    }

    boolean resetButton(String s, int x, int y, int w, int h) {
        // ignore this, might use this is next part of prokect idk yet
        // int squareX = l.width - 300;
        // int squareY = 0 + 200;
        // int squareAdd = 200;
        // int squareYAdd = 100;
        l.stroke(0);
        l.fill(0);
        l.rect(x, y, w, h);
        l.text(s, x, y, w, h);
        if ((l.mouseX > x && l.mouseX < l.mouseX + w && l.mouseY > y && l.mouseY < y + h) && l.mousePressed) {
            state = 1;
            name1 = "a";
            name2 = "b";
            name1Entered = false;
            name2Entered = false;
            finalName = "";
            reset = true;
            // l.println("screen reset");

        }
        return false;
    }

    void askQuestionsPlayer1() {
        if (question1Done == false) {
            questionScreen1();
        } else if (question1Done == true && question2Done == false) {
            questionScreen2();
        } else if (question1Done == true && question2Done == true && question3Done == false) {
            questionScreen3();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == false) {
            // questionScreen3();
            questionScreen4();
        }
        if (question1Done == true && question2Done == true && question3Done == true && question4Done == true) {
            timeRunning++;
            changeState();
        }
        // changeState();

    }

    void askQuestionsPlayer2() {
        if (question5Done == false) {
            questionScreen1();
        } else if (question5Done == true && question6Done == false) {
            questionScreen2();
        } else if (question5Done == true && question6Done == true && question7Done == false) {
            questionScreen3();
        } else if (question5Done == true && question6Done == true && question7Done == true && question8Done == false) {
            // questionScreen3();
            questionScreen4();
        }
        if (question5Done == true && question6Done == true && question7Done == true && question8Done == true) {
            changeState();
        }
        // changeState();

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

    Boolean getReset() {
        return reset;
    }

}
