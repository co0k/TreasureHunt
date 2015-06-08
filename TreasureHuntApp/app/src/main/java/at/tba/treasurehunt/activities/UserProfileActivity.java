package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.controller.UserDataController;
import at.tba.treasurehunt.dataprovider.IUserLoadedCallback;
import at.tba.treasurehunt.dataprovider.UserDataProvider;
import at.tba.treasurehunt.utils.AlertHelper;
import data_structures.user.User;


public class UserProfileActivity extends Activity implements IUserLoadedCallback {


    private TextView f_userName;
    private TextView f_email;
    private TextView f_level;
    private TextView f_experience;

    private LinearLayout mUserLayout;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initLayoutFields();
        ActivityManager.setCurrentActivity(this);
        loadMyUserData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
     * Uses the UserDataController to get the current Users data
     */
    private void fillUserData(User u){
        User userData = u;
        f_userName.setText("Username: "+userData.getName());
        f_email.setText("Email: "+userData.getEmail());
        f_level.setText("Level: "+userData.getRank());
        f_experience.setText("Experience: "+userData.getXP());
    }


    private void initLayoutFields(){
        this.mUserLayout = (LinearLayout) findViewById(R.id.userProfileLayout);
        this.mProgressView = findViewById(R.id.load_user_progress);
        this.f_userName = (TextView) this.findViewById(R.id.profile_username);
        this.f_email = (TextView) this.findViewById(R.id.profile_email);
        this.f_level = (TextView) this.findViewById(R.id.profile_level);
        this.f_experience = (TextView) this.findViewById(R.id.profile_experience);

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

            mUserLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mUserLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUserLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mUserLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void onButtonInventoryClick(View v){
        Intent actSwitch = new Intent(this, InventoryActivity.class);
        startActivity(actSwitch);
    }


    private void loadMyUserData(){
        showProgress(true);
        UserDataProvider.getInstance().getUserFromServer(AuthenticationController.getInstance().getLoggedInUserID(), this);
    }


    @Override
    public void onUserLoadedSuccess(User u) {
        showProgress(false);
        fillUserData(u);
        UserDataController.getInstance().setLoggedInUser(u);
    }

    @Override
    public void onUserLoadedFailure() {

        AlertHelper.showNewAlertSingleButton(this, "Something went wrong..",
                "There was an error loading the user profile. Please try again.",
                new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
        showProgress(false);
    }
}
