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

    import java.util.ArrayList;

    public class GameResult extends AppCompatActivity {
        int answerCount, questionCount;
        ArrayList<String> questionList;
        ArrayList<Integer> answerList;
        boolean[] boolArray;
        Button btnBack;
        TextView tvSummary;
        LinearLayout resultContainer;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game_result_view);

            //view
            btnBack = findViewById(R.id.btnBack);
            btnBack = findViewById(R.id.btnBack);
            tvSummary = findViewById(R.id.tvSummary);
            resultContainer = findViewById(R.id.resultContainer);

            // intent
            Intent intent = getIntent();
            boolArray = intent.getBooleanArrayExtra("boolArray");
            questionList = intent.getStringArrayListExtra("questionList");
            answerList = intent.getIntegerArrayListExtra("answerList");
            answerCount = intent.getIntExtra("answerCount", 0);
            questionCount = intent.getIntExtra("questionCount", 0);
            System.out.println(answerList);
            displayResults();

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        private void displayResults() {
            // 요약
            String summaryText = "총 정답: " + answerCount + " / " + questionCount;
            tvSummary.setText(summaryText);

            // 데이터 유효 검사
            if (questionList == null || boolArray == null || questionList.size() != boolArray.length) {
                TextView error = new TextView(this);
                error.setText("오류: 문제 데이터가 없거나 유효하지 않습니다.");
                resultContainer.addView(error);
                return;
            }

            // 문제 목록 만들기
            for (int i = 0; i < questionList.size(); i++) {
                TextView tv = new TextView(this);
                String questionInfo = questionList.get(i);
                int questionAnswer = answerList.get(i);
                boolean isCorrect = boolArray[i];


                String resultPrefix = isCorrect ? "✅ [정답] " : "❌ [오답] ";
                tv.setText(String.format("문제 %d. %s \n\t %s: %d\n", i + 1, questionInfo, resultPrefix, questionAnswer));

                tv.setTextColor(isCorrect ? Color.BLUE : Color.RED);


                tv.setPadding(0, 10, 0, 10);
                tv.setTextSize(16f);

                // resultContainer에 동적으로 추가
                resultContainer.addView(tv);
            }
        }
    }
