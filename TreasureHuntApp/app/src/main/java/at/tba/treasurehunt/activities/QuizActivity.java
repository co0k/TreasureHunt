package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.logging.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.dataprovider.IOpenTreasureCallback;
import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.AlertHelper;
import at.tba.treasurehunt.utils.ShowMessageHelper;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;


/**
 * This activity will be called when a Treasure chest is opened,
 * which has a Treasure.Type of Quiz
 * The user has to answer the quiz in order to open the TreasureChest.
 *
 * The layout gets programmatically generated, dependent on the Quiz' structure.
 */
public class QuizActivity extends Activity implements IOpenTreasureCallback {

    private Quiz quiz;
    private Treasure treasure;

    private View mLayoutView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActivityManager.setCurrentActivity(this);
        mProgressView = findViewById(R.id.open_treasure_progress);
        mLayoutView = findViewById(R.id.quizLayout);

        /**
         * Get the quiz of the opened Treasure.
         * Only the nearest TreasureChest is possible to be opened. (at the moment)
         * This won't work, if a Treasure gets inactive in that time, and the new nearest
         * Treasure is somewhere else.
         * TODO: The Treasure should be given by MapActivity later on.
         */
        this.treasure = TreasureChestHolder.getInstance().getNearestTreasure();
        this.quiz = (Quiz) this.treasure.getType();
        generateQuizLayout(this.quiz);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLayoutView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLayoutView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLayoutView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLayoutView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * The layout should be flexible. Quizzes can have a different number and types of answers.
     * For example, a picture can also be an answer.
     */
    private void generateQuizLayout(Quiz q) {

        int numAnswers = 4;

        TextView questionText = (TextView) findViewById(R.id.quizQuestionText);
        questionText.setText(q.getQuestion());

        Button[] answerButtons = new Button[numAnswers + 1];

        for (int i = 1; i <= numAnswers; i++) {
            answerButtons[i] = generateButton(this.quiz, i);
        }

        /*
        Shuffle the answer list
         */
        List<Button> btnList = Arrays.asList(answerButtons);
        Collections.shuffle(btnList);
        btnList.toArray(answerButtons);

        for (int i = 1; i <= numAnswers; i++){
            bindButton(answerButtons[i], i);
        }
    }

    private void bindButton(final Button b, int index){

        Button tmpButton = new Button(this);

        switch(index){
            case 1:
                tmpButton = (Button) findViewById(R.id.quiz_answer1);
                break;
            case 2: tmpButton = (Button) findViewById(R.id.quiz_answer2);
                break;
            case 3: tmpButton = (Button) findViewById(R.id.quiz_answer3);
                break;
            case 4: tmpButton = (Button) findViewById(R.id.quiz_answer4);
                break;
        }
        tmpButton.setText(b.getText());
        tmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.performClick();
            }
        });

    }

    private Button generateButton(Quiz q, final int answerId) {
        Button answerButton = new Button(this);

        String answerText = "";


        switch (answerId) {
            case 1: {
                answerText = q.getAnswer1();
                break;
            }
            case 2: {
                answerText = q.getAnswer2();
                break;
            }
            case 3: {
                answerText = q.getAnswer3();
                break;
            }
            case 4: {
                answerText = q.getAnswer4();
                break;
            }
        }

        answerButton.setText(answerText);



        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerClick(answerId);
            }
        });


        return answerButton;
    }

    public void onAnswerClick(int answerId) {
        if (answerId == 1) { // correct answer is always id == 1
            openTreasure();
        } else {
            ShowMessageHelper.showSimpleInfoMessagePopUp("Wrong answer bro. sry.", this);
        }
    }


    private void treasureOpenedSuccessfully(){
        this.finish();
        Intent actSwitch = new Intent(this, TreasureOpenActivity.class);
        startActivity(actSwitch);
    }

    private void openTreasure(){
        showProgress(true);
        TreasureChestHolder.getInstance().openTreasure(this.treasure, this);
    }

    @Override
    public void onOpenTreasureSuccess() {
        showProgress(false);
        treasureOpenedSuccessfully();
    }

    @Override
    public void onOpenTreasureFailure() {
        AlertHelper.showNewAlertSingleButton(this, "Something went wrong.."
                , "You are not allowed to open this treasure! Sorry.",
                new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
        showProgress(false);
    }

}
