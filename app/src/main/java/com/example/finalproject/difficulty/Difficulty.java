package com.example.finalproject.difficulty;

// 난이도 설정
public interface Difficulty {
    public int setQuestion(int operator); // operator에 따라 문제 만들기 문제에 대한 답을 return
    public String questionText(); // "문제 = " 형식으로 String을 return
}