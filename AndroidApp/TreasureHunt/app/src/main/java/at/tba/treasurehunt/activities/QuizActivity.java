package at.tba.treasurehunt.activities;

import android.app.ActionBar;
import android.app.Activity;
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

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.datastructures.treasure.Quiz;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.ShowMessageHelper;


/**
 * This activity will be called when a Treasure chest is opened,
 * which has a Treasure.Type of Quiz
 * The user has to answer the quiz in order to open the TreasureChest.
 *
 * The layout gets programmatically generated, dependent on the Quiz' structure.
 */
public class QuizActivity extends Activity {

    private Quiz quiz;
    private Treasure treasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
    private void generateQuizLayout(Quiz q){

        GridLayout btnLayout = (GridLayout) findViewById(R.id.quizLayout);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(3, GridLayout.CENTER);
        params.rowSpec = GridLayout.spec(1, GridLayout.CENTER);
        params.bottomMargin = 10;
        params.topMargin = 10;
        TextView question = new TextView(this);
        question.setText(q.getQuestion());
        question.setLayoutParams(params);
        btnLayout.addView(question);



        int numQuestions = 0;
        if (q.getAnswer1() != null) numQuestions++;
        if (q.getAnswer2() != null) numQuestions++;
        if (q.getAnswer3() != null) numQuestions++;
        if (q.getAnswer4() != null) numQuestions++;
        if (q.getAnswer5() != null) numQuestions++;
        if (q.getAnswer6() != null) numQuestions++;

        Button[] answerButtons = new Button[numQuestions+1];

        for (int i = 1; i <= numQuestions; i++){
            answerButtons[i] = generateButton(this.quiz, i);
            GridLayout.LayoutParams p = new GridLayout.LayoutParams(
                    GridLayout.spec(2+((i-1)/2), GridLayout.CENTER), //row
                    GridLayout.spec(2 + ((i - 1) % 2)*2, GridLayout.CENTER)); //col
            p.width = GridLayout.LayoutParams.WRAP_CONTENT;
            p.height = GridLayout.LayoutParams.WRAP_CONTENT;
            p.topMargin = 3;

            answerButtons[i].setLayoutParams(p);
            btnLayout.addView(answerButtons[i]);
        }
        setContentView(btnLayout);
    }

    private Button generateButton(Quiz q, final int answerId){
        Button answerButton = new Button(this);

        String answerText = "";

        switch(answerId){
            case 1: answerText = q.getAnswer1(); break;
            case 2: answerText = q.getAnswer2(); break;
            case 3: answerText = q.getAnswer3(); break;
            case 4: answerText = q.getAnswer4(); break;
            case 5: answerText = q.getAnswer5(); break;
            case 6: answerText = q.getAnswer6(); break;
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

    public void onAnswerClick(int answerId){
        if (answerId == this.quiz.getCorrectAnswer()){
            //ShowMessageHelper.showSimpleInfoMessagePopUp("Right Answer! Nice bro.", this);
            TreasureChestHolder.getInstance().openTreasure(this.treasure);
            this.finish();
        }else{
            ShowMessageHelper.showSimpleInfoMessagePopUp("Wrong answer bro. sry.", this);
        }
    }
}
