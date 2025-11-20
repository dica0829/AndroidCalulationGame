package com.example.finalproject.difficulty;

public class Normal implements Difficulty {
    private int num1;
    private int num2;

    private int result;
    private String questionText;

    public int setQuestion(int operator) {
        switch (operator) {
            case 0:
                num1 = (int)(Math.random()*900+100);
                num2 = (int)(Math.random()*400+100);
                result = num1 + num2;
                questionText = num1 + " + " + num2 + " = ";
                break;
            case 1:
                result = (int)(Math.random()*400+100);
                num1 = (int)(Math.random()*900+100);
                num2 = num1 + result;
                questionText = num2 + " - " + num1 + " = ";
                break;
            case 2:
                num1 = (int)(Math.random()*40+10);
                num2 = (int)(Math.random()*15+1);
                result = num1 * num2;
                questionText = num1 + " x " + num2 + " = ";
                break;
            case 3:
                result = (int)(Math.random()*7+3);
                num1 = (int)(Math.random()*90+10);
                num2 = result * num1;
                questionText = num2 + " ÷ " + num1 + " = ";
                break;

        }
        return result;
    }
    public String questionText() {
        return questionText;
    }
}
