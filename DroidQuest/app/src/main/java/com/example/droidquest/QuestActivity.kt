package com.example.droidquest

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class QuestActivity : AppCompatActivity() {

    companion object
    {
        private const val TAG = "QuestActivity"
        private const val KEY_INDEX = "index"
        private const val IS_DECEIT = "deceit"
        private val REQUEST_CODE_DECEIT = 0
    }


    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mPreviousButton: ImageButton
    private lateinit var mQuestionTextView : TextView
    private lateinit var mDeceitButton: Button
    private val mQuestionBank = listOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true)
    )
    private var mCurrentIndex = 0
    private val mDeceiterIndex = mutableListOf<Int>()
    private var mIsDeceiter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiter = savedInstanceState.getBoolean(IS_DECEIT, true)
        }

        mQuestionTextView = findViewById(R.id.textView)
        var question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
        mQuestionTextView.setOnClickListener{
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            question = mQuestionBank[mCurrentIndex].textResId
            mQuestionTextView.setText(question)
        }

        mTrueButton = findViewById(R.id.trueButton)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.falseButton)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        mPreviousButton = findViewById(R.id.previousButton)
        mPreviousButton.setOnClickListener{
            mCurrentIndex = (mCurrentIndex + mQuestionBank.size - 1) % mQuestionBank.size
            question = mQuestionBank[mCurrentIndex].textResId
            mQuestionTextView.setText(question)
            mIsDeceiter = mCurrentIndex in mDeceiterIndex
        }

        mNextButton = findViewById(R.id.nextButton)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            question = mQuestionBank[mCurrentIndex].textResId
            mQuestionTextView.setText(question)
            mIsDeceiter = mCurrentIndex in mDeceiterIndex
        }

        mDeceitButton = findViewById(R.id.deceit_button)
        mDeceitButton.setOnClickListener{
            val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
            val intent = DeceitActivity.newIntent(this, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_DECEIT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK)
            return
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null)
                return
            mIsDeceiter = DeceitActivity.wasAnswerShown(data)
            mDeceiterIndex.add(mCurrentIndex)
        }
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (mIsDeceiter) R.string.judgment_toast
        else if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

        override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState!!.putInt(KEY_INDEX, mCurrentIndex)
            outState!!.putBoolean(IS_DECEIT, mIsDeceiter)
    }
}