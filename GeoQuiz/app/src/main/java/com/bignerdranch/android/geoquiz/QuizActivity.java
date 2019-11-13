package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
    };

    private int mCurrentIndex = 0;
    private int correct = 0;
    private int incorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                if(mQuestionBank[mCurrentIndex].ismDone()) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mBackButton = (Button) findViewById(R.id.back_button);
        if(mCurrentIndex == 0) {
            mBackButton.setEnabled(false);
        } else {
            mBackButton.setEnabled(true);
        }
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                if(mQuestionBank[mCurrentIndex].ismDone()) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
                if(mCurrentIndex == 0) {
                    mBackButton.setEnabled(false);
                } else {
                    mBackButton.setEnabled(true);
                }
            }
        });


        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        if(correct + incorrect == mQuestionBank.length){
            if(correct > incorrect){
                mQuestionTextView.setText("You Win\nSCORE:\n" + correct + " Answered Correctly\n" + incorrect + " Answered Incorrectly");
            }else{
                mQuestionTextView.setText("You Lose\nSCORE:\n" + correct +" Answered Correctly\n" + incorrect + " Answered Incorrectly");
            }
            mFalseButton.setVisibility(View.GONE);
            mTrueButton.setVisibility(View.GONE);
            mNextButton.setVisibility(View.GONE);
        }else{
            while(mQuestionBank[mCurrentIndex].ismDone()){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            }
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }
        if(mCurrentIndex == 0) {
            mBackButton.setEnabled(false);
        } else {
            mBackButton.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        mQuestionBank[mCurrentIndex].setmDone(true);
        if (userPressedTrue == answerIsTrue) {
//            messageResId = R.string.correct_toast;
            correct++;
        } else {
//            messageResId = R.string.incorrect_toast;
            incorrect++;

        }
        updateQuestion();
        Toast.makeText(getApplicationContext(),"Poin Sekarang\nBenar:" + correct + "\nSalah:" + incorrect, Toast.LENGTH_SHORT).show();

    }
}
