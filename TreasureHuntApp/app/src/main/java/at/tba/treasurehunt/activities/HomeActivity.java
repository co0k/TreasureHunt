package at.tba.treasurehunt.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.UserLoginDataController;
import at.tba.treasurehunt.servercomm.ServerConnection;
import at.tba.treasurehunt.utils.AlertHelper;


public class HomeActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityManager.setCurrentActivity(this);
        //connectServer();
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void onButtonUserProfileClick(View v){
        Intent actSwitch = new Intent(this, UserProfileActivity.class);
        startActivity(actSwitch);
    }

    public void onButtonSettingsClick(View v){
        Intent actSwitch = new Intent(this, SettingsActivity.class);
        startActivity(actSwitch);
    }

    public void onButtonHighscoresClick(View v){
        Intent actSwitch = new Intent(this, HighscoresActivity.class);
        startActivity(actSwitch);
    }

    public void onButtonExitClick(View v){
        finish();
        System.exit(0);
    }

    public void onButtonLogoutClick(View v){
        new UserLoginDataController(this).deleteDefaultLoginData();
        finish();
        Intent actSwitch = new Intent(this, LoginActivity.class);
        startActivity(actSwitch);
    }

    public void onButtonPlayClick(View v){
        Intent actSwitch = new Intent(this, MapsActivity.class);
        startActivity(actSwitch);
    }


}
