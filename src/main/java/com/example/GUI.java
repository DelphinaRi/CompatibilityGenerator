package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

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
    // int timeRunning = 1;

    String question = "";
    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String correctAnswer = "";
    int score = 0;

    Random r = new Random();
    int low = 1;
    int high = 100;
    int result = r.nextInt(high - low) + low;
    // int wid = l.-50;
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
        if (state > 6) {
            // for the final version there will be more than 3 states
            // right now it displays the game over screen and the name generated from the
            // generation
            // l.background(255);
            // l.fill(0);
            // l.stroke(255);

            l.noStroke();
            l.fill(247, 121, 233);
            l.rect(0, 0, l.width, l.height);

            // draw inner square
            l.fill(252, 235, 250);
            l.rect(50, 50, l.width - 100, l.height - 100);
            l.stroke(0);

            String topPart = "Compatibility Results";
            String bottomPart = "Your Compatibility Score: ";

            l.stroke(0);
            l.fill(0);
            l.textSize(60);
            l.text(topPart, 330, 200);
            l.textSize(35);
            l.text(bottomPart + result + "%", l.width / 2 - 250, 250);
            l.textSize(25);

            String smart;
            if (score > 30) {
                smart = "Smart";
            } else {
                smart = "Not Smart";
            }
            // String dumb = "Not Smart";
            // l.text("Game Over", l.width / 2, l.height / 2);
            l.text("Baby Name = " + finalName, l.width / 2 - 140, 400);
            l.text("Bday = " + bday, l.width / 2 - 140, 450);
            l.text("Your child will be " + smart, l.width / 2 - 140, 500);
            resetButton("Click Here To Try Again", l.width / 2 - 100, l.height - 200, 200, 100);
            // for (int i = 0; i < answers.size(); i++) {
            // l.println(i + ": " + answers.get(i));
            // }
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
        // String a = "How compatibile are you with your partner?";
        String b = "Press Space to Continue";
        l.fill(255);
        l.textSize(75);
        l.text(s, 220, l.width / 5 - 75);

        l.fill(0);
        l.textSize(25);
        l.text(b, l.width / 2 - 150, l.height - 100);

    }

    void nameScreen1() {

        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);

        String topPart = "What will your child's birthday Be";
        String bottomPart = "Please input it in MM/DD/YYYY format";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(10);
        l.text(bottomPart, l.width / 2 - 75, 150);

        l.fill(0);
        l.textSize(20);
        l.text("Person 1 input birthday: ", l.width / 4, l.height / 3 + 100);

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
                String add = babyName.get(i);
                String space = " ";
                // converts the first letter in the name to uppercase
                if (i == 0) {
                    add = babyName.get(i).toUpperCase();
                }
                if (babyName.get(i) == space) {
                    add = "-";
                }
                finalName += add;
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

        String topPart = "What will your child's birthday Be";
        String bottomPart = "Please input it in MM/DD/YYYY format";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(10);
        l.text(bottomPart, l.width / 2 - 75, 150);

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

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 1 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        // if (i == 0) {
        question = "What is the capital of Lithuania?";
        answer1 = "Bucharest";
        answer2 = "Ankara";
        answer3 = "Tbilisi";
        correctAnswer = "Vilnius";

        l.text(question, l.width / 2 - 150, 120, 500, 100);
        button firstAnswer = new button(l, 225, 150, 125, 50, answer1, false);
        button secondButton = new button(l, 450, 150, 125, 50, answer2, false);
        button thirdButton = new button(l, 675, 150, 125, 50, correctAnswer, true);
        button fourButton = new button(l, 925, 150, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            question1Done = true;
            // }
            // if (timeRunning < 2) {
            // question5Done = true;
            // }
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

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 1 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "What is a group of rhinoceroses called?";
        answer1 = "Pow";
        answer2 = "Bang";
        answer3 = "Boom";
        correctAnswer = "Crash";

        l.text(question, l.width / 2 - 180, 120, 500, 100);
        button firstAnswer = new button(l, 225, 200, 125, 50, answer1, false);
        button secondButton = new button(l, 450, 200, 125, 50, correctAnswer, true);
        button thirdButton = new button(l, 675, 200, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 200, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            question2Done = true;
            // }
            // if (timeRunning < 2) {
            // question6Done = true;
            // }
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

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 1 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "What element's chemical symbol is Pb?";
        answer1 = "Plubonium";
        answer2 = "Steel";
        answer3 = "Iron";
        correctAnswer = "Lead";

        l.text(question, l.width / 2 - 200, 120, 500, 100);
        button firstAnswer = new button(l, 225, 250, 125, 50, correctAnswer, true);
        button secondButton = new button(l, 450, 250, 125, 50, answer1, false);
        button thirdButton = new button(l, 675, 250, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 250, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            question3Done = true;
            // }
            // if (timeRunning < 2) {
            // question7Done = true;
            // }
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

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 1 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "Who was the first woman ever inducted into the Rock and Roll Hall of Fame?";
        answer1 = "Madonna";
        answer2 = "Taylor Swift";
        answer3 = "Diana Ross";
        correctAnswer = "Aretha Franklin";

        l.text(question, l.width / 2 - 375, 120, 800, 100);
        button firstAnswer = new button(l, 225, 300, 125, 50, answer3, false);
        button secondButton = new button(l, 450, 300, 125, 50, answer1, false);
        button thirdButton = new button(l, 675, 300, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 300, 125, 50, correctAnswer, true);

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
            // if (timeRunning == 1) {
            question4Done = true;
            // }
            // if (timeRunning < 2) {
            // question8Done = true;
            // }
            // changeState();
            // i++;
            // continue;
        }
    }

    boolean questionScreen5() {

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

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 2 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        // if (i == 0) {
        question = "How many of Henry VIII's wives were executed?";
        answer1 = "1";
        answer2 = "3";
        answer3 = "None";
        correctAnswer = "2";

        l.text(question, l.width / 2 - 250, 120, 500, 100);
        button firstAnswer = new button(l, 225, 350, 125, 50, answer1, false);
        button secondButton = new button(l, 450, 350, 125, 50, answer2, false);
        button thirdButton = new button(l, 675, 350, 125, 50, correctAnswer, true);
        button fourButton = new button(l, 925, 350, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            // question1Done = true;
            // }
            // if (timeRunning < 2) {
            question5Done = true;
            // }
            return true;
            // changeState();
            // i++;
            // continue;
        }
        return false;
        // changeState();

    }

    void questionScreen6() {

        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 2 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "What is the smallest bone in the human body?";
        answer1 = " The phalanges, in your pinkies";
        answer2 = "The nasal shaft, in your nose";
        answer3 = "The cervical vertebrae, in your spine";
        correctAnswer = "The stapes bone, in your ear";

        l.text(question, l.width / 2 - 220, 120, 500, 100);
        button firstAnswer = new button(l, 225, 400, 125, 50, answer1, false);
        button secondButton = new button(l, 450, 400, 125, 50, correctAnswer, true);
        button thirdButton = new button(l, 675, 400, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 400, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            // question2Done = true;
            // }
            // if (timeRunning < 2) {
            question6Done = true;
            // }
            // changeState();
            // i++;
            // continue;
        }
    }

    void questionScreen7() {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 2 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "What country has competed the most times in the Summer Olympics yet has not won a gold medal?";
        answer1 = "Romania";
        answer2 = "Liechtenstein";
        answer3 = "Turkey";
        correctAnswer = "The Philippines";

        l.text(question, l.width / 2 - 250, 120, 500, 100);
        button firstAnswer = new button(l, 225, 450, 125, 50, correctAnswer, true);
        button secondButton = new button(l, 450, 450, 125, 50, answer1, false);
        button thirdButton = new button(l, 675, 450, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 450, 125, 50, answer3, false);

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
            // if (timeRunning == 1) {
            // question3Done = true;
            // }
            // if (timeRunning < 2) {
            question7Done = true;
            // }
            // changeState();
            // i++;
            // continue;
        }
    }

    void questionScreen8() {
        // draw outerSquare
        l.noStroke();
        l.fill(247, 121, 233);
        l.rect(0, 0, l.width, l.height);

        // draw inner square
        l.fill(252, 235, 250);
        l.rect(50, 50, l.width - 100, l.height - 100);
        l.stroke(0);

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 2 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(15);
        l.text(bottomPart, l.width / 2 - 75, 35);
        l.textSize(20);

        question = "In what country was Shakira born?";
        answer1 = "El Salvador";
        answer2 = "Mexico";
        answer3 = "Ecuador";
        correctAnswer = "Columbia";

        l.text(question, l.width / 2 - 200, 120, 500, 100);
        button firstAnswer = new button(l, 225, 500, 125, 50, answer3, false);
        button secondButton = new button(l, 450, 500, 125, 50, answer1, false);
        button thirdButton = new button(l, 675, 500, 125, 50, answer2, false);
        button fourButton = new button(l, 925, 500, 125, 50, correctAnswer, true);

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
            // if (timeRunning == 1) {
            // question4Done = true;
            // }
            // if (timeRunning < 2) {
            question8Done = true;
            // }
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
        l.stroke(255);
        l.fill(255);
        l.text(s, x+17, y+10, w, h);
        if ((l.mouseX > x && l.mouseX < l.mouseX + w && l.mouseY > y && l.mouseY < y + h) && l.mousePressed) {
            state = 1;
            name1 = "a";
            name2 = "b";
            name1Entered = false;
            name2Entered = false;
            finalName = "";
            reset = true;
            question1Done = false;
            question2Done = false;
            question3Done = false;
            question4Done = false;

            question5Done = false;
            question6Done = false;
            question7Done = false;
            question8Done = false;
            score = 0;

            result = r.nextInt(high - low) + low;
            // l.println("screen reset");

        }
        return false;
    }

    void getIntelligence() {
        MarkovChainGenerator<Boolean> smarts = new MarkovChainGenerator<Boolean>();
        smarts.trainM(answers);

        ArrayList<Boolean> newAnswers = smarts.generateM(10);

        ArrayList<Boolean> correctAnswers = new ArrayList<Boolean>();
        correctAnswers.add(true);
        correctAnswers.add(true);
        correctAnswers.add(false);
        correctAnswers.add(false);
        correctAnswers.add(true);
        correctAnswers.add(false);
        correctAnswers.add(false);
        correctAnswers.add(true);
        correctAnswers.add(false);
        correctAnswers.add(false);

        for (int i = 0; i < newAnswers.size(); i++) {
            l.println(i + " new state: " + newAnswers.get(i));
        }

        for (int i = 0; i < newAnswers.size(); i++) {
            if (correctAnswers.get(i) == newAnswers.get(i)) {
                score += 10;
            }
        }

        // changeState();

        // MarkovChainGenerator<String> dateGen = new MarkovChainGenerator<String>();
        // dateGen.trainM(dates);

        // ArrayList<String> birthday = dateGen.generateM(1);
        // bday = birthday.get(0);

        // changeState();

    }

    void askQuestionsPlayer1() {

        String topPart = "Will your child be smart or not";
        String bottomPart = "Player 1 Answer";

        l.stroke(0);
        l.fill(0);
        l.textSize(50);
        l.text(topPart, 250, 100);
        l.textSize(10);
        l.text(bottomPart, l.width / 2 - 75, 150);

        if (question1Done == false) {
            questionScreen1();
        } else if (question1Done == true && question2Done == false) {
            questionScreen2();
        } else if (question1Done == true && question2Done == true && question3Done == false) {
            questionScreen3();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == false) {
            // questionScreen3();
            questionScreen4();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == true
                && question5Done == false) {
            questionScreen5();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == true
                && question5Done == true && question6Done == false) {
            questionScreen6();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == true
                && question5Done == true && question6Done == true && question7Done == false) {
            questionScreen7();
        } else if (question1Done == true && question2Done == true && question3Done == true && question4Done == true
                && question5Done == true && question6Done == true && question7Done == true && question8Done == false) {
            // questionScreen3();
            questionScreen8();
        }
        if (question1Done == true && question2Done == true && question3Done == true && question4Done == true
                && question5Done == true && question6Done == true && question7Done == true && question8Done == true) {
            getIntelligence();
            changeState();
        }

        // if (question1Done == true && question2Done == true && question3Done == true
        // && question4Done == true) {
        // timeRunning++;
        // changeState();
        // }
        // changeState();

    }

    // void askQuestionsPlayer2() {
    // if (question5Done == false) {
    // questionScreen5();
    // } else if (question5Done == true && question6Done == false) {
    // questionScreen6();
    // } else if (question5Done == true && question6Done == true && question7Done ==
    // false) {
    // questionScreen7();
    // } else if (question5Done == true && question6Done == true && question7Done ==
    // true && question8Done == false) {
    // // questionScreen3();
    // questionScreen8();
    // }
    // if (question5Done == true && question6Done == true && question7Done == true
    // && question8Done == true) {
    // changeState();
    // }
    // // changeState();

    // }

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
