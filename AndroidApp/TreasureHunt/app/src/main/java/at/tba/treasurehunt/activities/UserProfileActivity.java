package at.tba.treasurehunt.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.UserDataController;
import at.tba.treasurehunt.datastructures.user.User;


public class UserProfileActivity extends ActionBarActivity {


    TextView f_userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initLayoutFields();
        fillUserData();
        ActivityManager.setCurrentActivity(this);
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
    private void fillUserData(){
        User userData = UserDataController.getInstance().getCurrentUserData();
        f_userName.setText(userData.getName());
    }


    private void initLayoutFields(){
        this.f_userName = (TextView) this.findViewById(R.id.editText);
    }
}
