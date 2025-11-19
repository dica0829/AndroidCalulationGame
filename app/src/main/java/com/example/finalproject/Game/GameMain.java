package com.example.finalproject.Game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class GameMain extends AppCompatActivity {
    int setDiff = 0; // 난이도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_view);
        EditText edtQuestionCount = findViewById(R.id.edtQuestionCount);
        Button btnEasy = findViewById(R.id.btnEasy);
        Button btnNormal = findViewById(R.id.btnNormal);
        Button btnHard = findViewById(R.id.btnHard);
        Button btnStart = findViewById(R.id.btnStart);

        //버튼은 선택되면 검정색, 선택되지 않으면 초록색
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setBackgroundColor(Color.rgb(0,0,0));
                btnNormal.setBackgroundColor(Color.rgb(76, 175, 80));
                btnHard.setBackgroundColor(Color.rgb(76, 175, 80));
                setDiff = 1; // easy
            }
        });
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNormal.setBackgroundColor(Color.rgb(0,0,0));
                btnEasy.setBackgroundColor(Color.rgb(76, 175, 80));
                btnHard.setBackgroundColor(Color.rgb(76, 175, 80));
                setDiff = 2; // normal
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHard.setBackgroundColor(Color.rgb(0,0,0));
                btnNormal.setBackgroundColor(Color.rgb(76, 175, 80));
                btnEasy.setBackgroundColor(Color.rgb(76, 175, 80));
                setDiff = 3; // hard
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionCountString = edtQuestionCount.getText().toString();
                if (questionCountString.equals("") || setDiff == 0) { // 오류 방지
                    Toast.makeText(getApplicationContext(),"선택하지 않은 것이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else { // 난이도와 문제수를 GamePlay로 넘김
                    int questionCount = Integer.parseInt(questionCountString);
                    Intent intent = new Intent(GameMain.this, GamePlay.class);
                    intent.putExtra("setDiff", setDiff);
                    intent.putExtra("questionCount", questionCount);
                    startActivity(intent);
                }
            }
        });
    }

}
