package com.example.finalproject.difficulty;

public class Hard implements Difficulty {
    private int num1;
    private int num2;

    private int result;
    private String questionText;

    public int setQuestion(int operator) {
        switch (operator) {
            case 0:
                num1 = (int)(Math.random()*9000+1000);
                num2 = (int)(Math.random()*9000+1000);
                result = num1 + num2;
                questionText = num1 + " + " + num2 + " = ";
                break;
            case 1:
                num1 = (int)(Math.random()*9000+1000);
                num2 = (int)(Math.random()*9000+1000);
                result = num1 - num2;
                questionText = num1 + " - " + num2 + " = ";
                break;
            case 2:
                num1 = (int)(Math.random()*40+10);
                num2 = (int)(Math.random()*50+30);
                result = num1 * num2;
                questionText = num1 + " x " + num2 + " = ";
                break;
            case 3:
                result = (int)(Math.random()*9+11);
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
