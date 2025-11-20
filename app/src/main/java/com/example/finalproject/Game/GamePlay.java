package com.example.finalproject.Game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    //프로그레스바, 타이머용
    TextView tvTimer;
    ProgressBar timerProgressBar;
    CountDownTimer countDownTimer;
    final long TOTAL_TIME_MILLIS = 31000;
    final long TICK_INTERVAL = 1000;
    private boolean isReturningFromSubmit = false;

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
        tvTimer = findViewById(R.id.tvTimer);
        timerProgressBar = findViewById(R.id.timerProgressBar);

        timerProgressBar.setMax((int)(TOTAL_TIME_MILLIS / TICK_INTERVAL));
        timerProgressBar.setProgress(timerProgressBar.getMax());

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
                    if (countDownTimer != null) countDownTimer.cancel(); // 시간 멈추기
                    isReturningFromSubmit = true; //
                    goCurrentResult();
                    if (last) finish();
                }
            }
        });

        //메인
        loadQuestion();
    }
    private void loadQuestion() { // 문제 불러오는 함수
        questionIndex++; // 문제번호 1 증가
        edtAnswer.setText("");
        operator = (int)(Math.random() * 4); // operator 0,1,2,3은 덧셈,뺄셈,곱셈,나눗셈 대응
        result = diff.setQuestion(operator);
        tvQuestion.setText(diff.questionText() + "?"); // "문제 = ?" 실제 보여지는 문항
        questionList.add(diff.questionText() + result); // "문제 = 정답"을 저장함
        startTimer();
    }

    private void goCurrentResult() {
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
        intent.putExtra("answerList", answerList); // 내가 적은 답 리스트
        startActivity(intent);
    }


    // ------------------ 시간 함수들 --------------------------------
    @Override
    protected void onResume() {
        super.onResume();

        // 제출 후 돌아왔고, 마지막 문제가 아닐 때만 다음 문제 로드 및 타이머 시작
        if (isReturningFromSubmit) {
            loadQuestion(); // loadQuestion 실행하기
            isReturningFromSubmit = false; // 플래그 초기화
        }
    }
    private void startTimer() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }


        timerProgressBar.setProgress(timerProgressBar.getMax());
        btnSubmit.setEnabled(true);
        edtAnswer.setEnabled(true);
        long initialSeconds = TOTAL_TIME_MILLIS / 1000;
        tvTimer.setText(String.format("%02d:%02d", 0, initialSeconds));

        countDownTimer = new CountDownTimer(TOTAL_TIME_MILLIS, TICK_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;

                // TextView 업데이트 (분:초 형식)
                long minutes = secondsRemaining / 60;
                long seconds = secondsRemaining % 60;
                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));

                // ProgressBar 업데이트
                timerProgressBar.setProgress((int) secondsRemaining);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("시간 초과!");
                timerProgressBar.setProgress(0);

                // ⭐ 시간 초과 시 처리 로직 호출
                handleTimeout();
            }
        }.start();
    }

    private void handleTimeout() {
        // 제출 버튼과 입력창 비활성화
        btnSubmit.setEnabled(false);
        edtAnswer.setEnabled(false);

        // 현재 문제 오답 처리
        boolArray[questionIndex - 1] = false;
        currentAnswerInt = -1;
        answerList.add(currentAnswerInt);// 타임아웃 답을 -1으로 저장 -> 추후에 시간 초과로 변경함
        isReturningFromSubmit = true;
        goCurrentResult();
        if (last) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 메모리 누수 방지를 위해 Activity 종료 시 타이머를 반드시 취소합니다.
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}