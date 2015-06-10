package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.dataprovider.HighscoreListProvider;
import at.tba.treasurehunt.dataprovider.IHighscoresLoadedCallback;
import at.tba.treasurehunt.utils.AlertHelper;
import at.tba.treasurehunt.utils.DummyDataProvider;
import data_structures.user.HighscoreList;


public class HighscoresActivity extends Activity implements IHighscoresLoadedCallback {

    private ListView listView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;

    private View mLayoutView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        ActivityManager.setCurrentActivity(this);
        listView = (ListView) findViewById(R.id.highscoreListView);
        listItems = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(arrayAdapter);
        this.mLayoutView = (LinearLayout) findViewById(R.id.highscoreLayout);
        this.mProgressView = findViewById(R.id.load_highscore_progress);
        startLoadingHighscores();
        //setList(DummyDataProvider.getDummyHighscoreList());
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
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



    private void startLoadingHighscores(){
        showProgress(true);
        Integer low = 10;
        Integer high = 10;
        HighscoreListProvider.getInstance().getHighscoreListFromServer(this, low, high);
    }


    private void setList(HighscoreList list){
        //listItems.clear();
        int i = 0;
        for (HighscoreList.Entry e: list.getList()) {
            arrayAdapter.insert(e.getName()+" with "+e.getXP()+" XP", i);
            if (e.getId() == AuthenticationController.getInstance().getLoggedInUserID()){
                highlightEntryAtPosition(i);
            }
            i++;
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void highlightEntryAtPosition(int pos){
        View entry = arrayAdapter.getView(pos, null, listView);
        entry.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void onHighscoresLoadedSuccess(HighscoreList list) {
        showProgress(false);
        setList(list);
    }

    @Override
    public void onHighscoresLoadedFailure() {
        showProgress(false);
        AlertHelper.showNewAlertSingleButton(this, "Highscores could not be load..", "There was an error retreiving the Highscores from our Server. Please try again",
                new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
    }
}
