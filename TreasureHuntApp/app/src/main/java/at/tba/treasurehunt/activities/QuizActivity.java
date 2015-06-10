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

import android.util.Log;
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
 * TODO: when in loading to check the data the "back" button is pressed, and then another treasure is opened instantly, the app crashes
 */
public class QuizActivity extends Activity {

    private Quiz quiz;
    private Treasure treasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActivityManager.setCurrentActivity(this);

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
     * The layout should be flexible. Quizzes can have a different number and types of answers.
     * For example, a picture can also be an answer.
     */
    private void generateQuizLayout(Quiz q) {

        int numAnswers = 4;

        TextView questionText = (TextView) findViewById(R.id.quizQuestionText);
        questionText.setText(q.getQuestion());

        Button[] answerButtons = new Button[numAnswers];

        for (int i = 0; i < numAnswers; i++) {
            answerButtons[i] = generateButton(this.quiz, i+1);
        }

        /*
        Shuffle the answer list
         */
        List<Button> btnList = Arrays.asList(answerButtons);
        Collections.shuffle(btnList);
        btnList.toArray(answerButtons);

        for (int i = 0; i < numAnswers; i++){
            bindButton(answerButtons[i], i+1);
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
            wrongAnswerSelected();
        }
    }

    private void wrongAnswerSelected(){
        TreasureChestHolder.getInstance().blockTreasureForUser(treasure);
        ShowMessageHelper.showNewSimpleMessagePopUp(this, "Wrong answer.", "Sorry, but this was not the right answer.", "Okay!",
                new Runnable(){
                    @Override
                    public void run() {
                        finish();
                    }
                });
    }


    private void treasureOpenedSuccessfully(){
        TreasureChestHolder.getInstance().treasureRightAnswer(treasure);
        this.finish();
        Intent actSwitch = new Intent(this, TreasureOpenActivity.class);
        startActivity(actSwitch);
    }

    private void openTreasure(){
        treasureOpenedSuccessfully();
    }


}
