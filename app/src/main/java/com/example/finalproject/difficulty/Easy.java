package com.example.finalproject.difficulty;

public class Easy implements Difficulty {
    private int num1;
    private int num2;

    private int result;
    private String questionText;

    //operator의 형식에 따라 num의 범위를 변경
    public int setQuestion(int operator) {
        switch (operator) {
            case 0:
                num1 = (int)(Math.random()*89+10);
                num2 = (int)(Math.random()*89+10);
                result = num1 + num2;
                questionText = num1 + " + " + num2 + " = ";
                break;
            case 1:
                result = (int)(Math.random()*50+10);
                num1 = (int)(Math.random()*50+10);
                num2 = num1 + result;
                questionText = num2 + " - " + num1 + " = ";
                break;
            case 2:
                num1 = (int)(Math.random()*89+10);
                num2 = (int)(Math.random()*7+2);
                result = num1 * num2;
                questionText = num1 + " x " + num2 + " = ";
                break;
            case 3:
                // 나눗셈은 결과 * 피연산자1 = 피연산자2 구해서 피연산자2 / 피연산자1 = 결과로 변경
                result = (int)(Math.random()*7+3);
                num1 = (int)(Math.random()*20+10);
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