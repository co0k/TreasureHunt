package at.tba.treasurehunt.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.datastructures.treasure.Quiz;
import at.tba.treasurehunt.treasures.TreasureChestHolder;


/**
 * This activity will be called when a Treasure chest is opened,
 * which has a Treasure.Type of Quiz
 * The user has to answer the quiz in order to open the TreasureChest.
 *
 * The layout gets programmatically generated, dependent on the Quiz' structure.
 */
public class QuizActivity extends ActionBarActivity {

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
        Quiz q = (Quiz) TreasureChestHolder.getInstance().getNearestTreasure().getType();
        generateQuizLayout(q);
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

    }
}
