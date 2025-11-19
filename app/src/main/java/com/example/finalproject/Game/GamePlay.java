package com.example.finalproject.Game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.difficulty.Difficulty;
import com.example.finalproject.difficulty.Easy;
import com.example.finalproject.difficulty.Normal;
import com.example.finalproject.difficulty.Hard;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {
    int difficulty; // main에서 넘겨받은 난이도 index
    Difficulty diff; // 난이도 선택

    int questionCount; // 문제 전체 수
    int questionIndex = 0; // 현재 문제 번호

    int answerCount = 0; // 내가 맞춘 문제수
    int currentAnswerInt; // 내가 방금 적은 답
    int result; // 실제 답

    ArrayList<Integer> answerList = new ArrayList<>(); // 내가 쓴 답 모음
    ArrayList<String> questionList = new ArrayList<String>(); // 실제 수식 + 답 모음
    boolean[] boolArray; // final result를 위한 정답 확인용
    boolean last; // 문제가 다 끝났는지 확인

    // view
    TextView tvQuestion;
    Button btnSubmit;
    EditText edtAnswer;

    int operator = 0;

    int num1, num2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play_view);
        // view 받아오기
        tvQuestion = findViewById(R.id.tvQuestion);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtAnswer = findViewById(R.id.edtAnswer);

        // intent 받아오기
        Intent intent = getIntent();
        difficulty = intent.getIntExtra("setDiff", 2);
        questionCount = intent.getIntExtra("questionCount", 10);

        boolArray = new boolean[questionCount];

        // 받아온 난이도에 따라 초기 설정
        switch (difficulty) {
            case 1:
                diff = new Easy();
                break;
            case 2:
                diff = new Normal();
                break;
            case 3:
                diff = new Hard();
                break;

        }

        loadQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            // 문제가 정답이면 정답을 넘겨주고
            // 오답이면 오답이라고 gameCurrentResult에게 넘겨주기
            @Override
            public void onClick(View view) {
                String currentAnswer = edtAnswer.getText().toString(); // 내가 적은 답
                Boolean answerBool = false;
                if (currentAnswer.equals("")) { // 답을 입력 안하면 못 넘어감
                    Toast.makeText(getApplicationContext(),"답을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentAnswerInt = Integer.parseInt(currentAnswer);
                    answerList.add(currentAnswerInt);
                    boolArray[questionIndex-1] = result == currentAnswerInt; // 내가 쓴 답이 정답인지 확인
                    answerCount = boolArray[questionIndex-1] ? answerCount+1 : answerCount; // 정답이면 answerCount 올림

                    Intent intent;
                    intent = new Intent(GamePlay.this, GameCurrentResult.class);
                    last = (questionIndex == questionCount);

                    intent.putExtra("answerBool", boolArray[questionIndex-1]); // 답 맞췄는지
                    intent.putExtra("currentQuestion", questionList.get(questionIndex-1)); // 실제 수식 + 답 문제 번호가 1부터 시작하므로 1 빼줘야함
                    intent.putExtra("currentAnswerInt", currentAnswerInt); // 내가 방금 적은 답
                    intent.putExtra("last", last); // 마지막 문제인지 확인
                    intent.putExtra("boolArray", boolArray); // 문항이 틀렸는지
                    intent.putExtra("answerCount", answerCount); // 정답 개수
                    intent.putExtra("questionCount", questionCount); // 문제 개수
                    intent.putExtra("questionList", questionList); // 수식 + 답 리스트
                    intent.putExtra("answerList", answerList);
                    startActivity(intent);
                    if (!last)
                        loadQuestion();
                    else
                        finish();
                }
            }
        });
    }

    private void loadQuestion() { // 문제 불러오는 함수
        questionIndex++; // 문제번호 1 증가
        edtAnswer.setText("");
        operator = (int)(Math.random() * 4); // operator 0,1,2,3은 덧셈,뺄셈,곱셈,나눗셈 대응
        result = diff.setQuestion(operator);
        tvQuestion.setText(diff.questionText() + "?"); // "문제 = ?" 실제 보여지는 문항
        questionList.add(diff.questionText() + result); // "문제 = 정답"을 저장함
    }
}