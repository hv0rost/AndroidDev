package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.firstapplication.R.id.before_button;
import static com.example.firstapplication.R.id.deceit_button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX_ONE = "index";
    private static final String KEY_INDEX_TWO = "index";
    private static final String KEY_INDEX_QUESTION = "index";

    private static final int REQUEST_CODE_DECEIT = 0;

    private Button mNoButton;
    private Button mYesButton;
    private ImageButton mNextButton;
    private  ImageButton mBeforeButton;
    private TextView mQuestionTextView;
    public TextView mTextViewNext;
    private Button mDeceitButton;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_android,true),
            new Question(R.string.question_res,true),
            new Question(R.string.question_linear,false),
            new Question(R.string.question_manifest,true),
            new Question(R.string.question_service,false),
            new Question(R.string.question_log,true),
            new Question(R.string.question_LogCat,true),
            new Question(R.string.question_xml,true),
            new Question(R.string.question_turn,false),
            new Question(R.string.question_string,false),
    };
    private int mCurrentIndex = 0;
    private boolean mIsDeceiter;
    private boolean mUsedHint[] = new boolean[mQuestionBank.length];
    private  void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private  void checkAnswer(boolean userProccedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(mIsDeceiter || mUsedHint[mCurrentIndex])
            messageResId = R.string.judgment_toast;
        else {
            if (userProccedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        Log.d(TAG, "onStart() called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX_ONE,0);
            mIsDeceiter = savedInstanceState.getBoolean(KEY_INDEX_TWO, false);
            mUsedHint = savedInstanceState.getBooleanArray(KEY_INDEX_QUESTION);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question);

        mYesButton = (Button) findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mNoButton = (Button) findViewById(R.id.no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsDeceiter = false;
                updateQuestion();
            }
        });

        mBeforeButton = (ImageButton) findViewById(before_button);
        mBeforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTextViewNext = (TextView) findViewById(R.id.question);
        mTextViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mDeceitButton = (Button) findViewById(deceit_button);
        mDeceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue  = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_DECEIT);
            }
        });
        updateQuestion();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode == REQUEST_CODE_DECEIT){
            if(data == null)
                return;
            mIsDeceiter = DeceitActivity.wasAnswerShown(data);
            mUsedHint[mCurrentIndex] = true;
            super.onActivityResult(requestCode,resultCode, data);
        }
    }

    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_INDEX_TWO, mIsDeceiter);
        savedInstanceState.putBooleanArray(KEY_INDEX_QUESTION, mUsedHint);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
