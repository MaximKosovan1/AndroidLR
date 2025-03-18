package com.example.lr2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityGame extends AppCompatActivity {

    private TextView tvParty, tvFact, tvQuestion, tvResult, tvTimer;
    private Button btnYes, btnNo;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvParty = findViewById(R.id.tvParty);
        tvFact = findViewById(R.id.tvFact);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvResult = findViewById(R.id.tvQuestion);
        tvTimer = findViewById(R.id.tvTimer);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        loadQuestions();
        Collections.shuffle(questionList);
        showNextQuestion();

        btnYes.setOnClickListener(v -> checkAnswer(true));
        btnNo.setOnClickListener(v -> checkAnswer(false));

        startTimer();
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Слуга Народу", "Обіцяли повністю скасувати недоторканність народних депутатів.", true));
        questionList.add(new Question("Європейська Солідарність", "Їхній лідер — Володимир Зеленський.", false));
        questionList.add(new Question("Опозиційна платформа – За життя", "Головним ідеологом партії був Віктор Медведчук.", true));
        questionList.add(new Question("Батьківщина", "Юлія Тимошенко — засновниця цієї партії.", true));
        questionList.add(new Question("Голос", "Партія була створена у 2018 році Святославом Вакарчуком.", true));
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            tvParty.setText(currentQuestion.getParty());
            tvFact.setText(currentQuestion.getFact());
        } else {
            endGame();
        }
    }

    private void checkAnswer(boolean userAnswer) {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);

            if (currentQuestion.isTrue() == userAnswer) {
                score++;
            }

            currentQuestionIndex++;
            showNextQuestion();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Час: " + millisUntilFinished / 1000 + " сек");

                if (currentQuestionIndex >= questionList.size()) {
                    cancel();
                    endGame();
                }
            }

            public void onFinish() {
                endGame();
            }
        }.start();
    }

    private void endGame() {
        if (timer != null) {
            timer.cancel();
        }

        tvParty.setVisibility(View.GONE);
        tvFact.setVisibility(View.GONE);
        btnYes.setVisibility(View.GONE);
        btnNo.setVisibility(View.GONE);

        tvResult.setVisibility(View.VISIBLE);
        tvResult.setText("Гра завершена!\nТвій результат: " + score + " / " + questionList.size());
    }

    public class Question {
        private String party;
        private String fact;
        private boolean isTrue;

        public Question(String party, String fact, boolean isTrue) {
            this.party = party;
            this.fact = fact;
            this.isTrue = isTrue;
        }

        public String getParty() {
            return party;
        }

        public String getFact() {
            return fact;
        }

        public boolean isTrue() {
            return isTrue;
        }
    }
}
