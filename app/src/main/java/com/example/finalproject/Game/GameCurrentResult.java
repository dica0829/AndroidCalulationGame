package com.example.finalproject.Game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameCurrentResult extends AppCompatActivity {
    Boolean answerBool, last;


    ArrayList<String> questionList;
    String currentQuestion;
    int currentAnswerInt;

    TextView tvResultText, tvAnswer;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_current_result_view);

        //view
        tvResultText = findViewById(R.id.tvResultText);
        tvAnswer = findViewById(R.id.tvAnswer);
        btnNext = findViewById(R.id.btnNext);

        Intent intent = getIntent();

        //currentResult에서 사용하는 것들
        answerBool = intent.getBooleanExtra("answerBool", false);
        last = intent.getBooleanExtra("last", false);
        currentQuestion = intent.getStringExtra("currentQuestion");
        currentAnswerInt = intent.getIntExtra("currentAnswerInt", currentAnswerInt);
        questionList = intent.getStringArrayListExtra("questionList");

        tvResultText.setText(currentQuestion);
        // -1(시간초과)면 시간 초과로 표시 아니면 그대로 숫자
        tvAnswer.setText(currentAnswerInt == -1 ? "시간 초과" : currentAnswerInt + "");
        tvAnswer.setTextColor(answerBool ? Color.rgb(0x4C,0xAF,0x50) : Color.rgb(0xF4,0x43,0x36));

        //정답 여부에 따른 색 변환 : 임시
        //LinearLayout linearLayout = findViewById(R.id.linearLayout);
        //linearLayout.setBackgroundColor(answerBool ? Color.rgb(0x4C,0xAF,0x50) : Color.rgb(0xF4,0x43,0x36));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!last)
                    finish();
                else {
                    // 마지막 문항이 끝났을때 result로 넘겨주는 값들 : currentResult에서는 사용 안됨
                    int answerCount = intent.getIntExtra("answerCount", 0);
                    int questionCount = intent.getIntExtra("questionCount", 0);
                    boolean[] boolArray = intent.getBooleanArrayExtra("boolArray");
                    ArrayList<Integer> answerList = intent.getIntegerArrayListExtra("answerList");

                    Intent intent = new Intent(GameCurrentResult.this, GameResult.class);
                    intent.putExtra("answerCount", answerCount); // 정답 개수
                    intent.putExtra("questionCount", questionCount); // 문제 개수
                    intent.putExtra("questionList", questionList); // 문항
                    intent.putExtra("boolArray", boolArray); // 문항이 틀렸는지
                    intent.putExtra("answerList", answerList); // 자기가 적은 답
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
