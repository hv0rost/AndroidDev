package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeceitActivity extends AppCompatActivity {

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private  boolean mIsLayoutIsChanged = false;

    public static final String EXTRA_ANSWER_IS_TRUE = "main.answer_is_true";
    public static final String EXTRA_ANSWER_IS_SHOWN = "main.answer_is_shown";
    public  static final String KEY_INDEX = "index";


    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, DeceitActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static  boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deceit);

        if(savedInstanceState != null)mIsLayoutIsChanged = savedInstanceState.getBoolean(KEY_INDEX);
        if(mIsLayoutIsChanged){
            if(mAnswerIsTrue)mAnswerTextView.setText(R.string.yes);
            else  mAnswerTextView.setText(R.string.no);
            setAnswerShownResult(true);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.yes);
                }
                else{
                    mAnswerTextView.setText(R.string.no);
                }
                setAnswerShownResult(true);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanseState) {
        super.onSaveInstanceState(savedInstanseState);
        savedInstanseState.putBoolean(KEY_INDEX, mIsLayoutIsChanged);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
