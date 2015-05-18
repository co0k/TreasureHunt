package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.controller.AuthenticationError;
import at.tba.treasurehunt.controller.IAuthenticationCallback;
import at.tba.treasurehunt.utils.AlertHelper;

public class RegisterActivity extends Activity implements IAuthenticationCallback {

    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPasswordRetype;

    private View mProgressView;
    private View registerFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.registration_progress);
        inputUsername = (EditText) findViewById(R.id.edit_text_username);
        inputEmail = (EditText) findViewById(R.id.edit_text_email);
        inputPassword = (EditText) findViewById(R.id.edit_text_password);
        inputPasswordRetype = (EditText) findViewById(R.id.edit_text_password_retype);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void onButtonRegister(View v){

        showProgress(true);

        String uName = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String pwd = inputPassword.getText().toString();
        String pwdRetype = inputPasswordRetype.getText().toString();

        AuthenticationController.getInstance().registerNewUser(uName, email, pwd, pwdRetype, this);

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

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onAuthenticationSuccess() {
        // not used here, TODO maybe split in two different interfaces?
    }

    @Override
    public void onAuthenticationFailure(AuthenticationError err) {
        // not used here, TODO maybe split in two different interfaces?
    }

    @Override
    public void onRegistrationSuccess() {
        finish();
        Intent actSwitch = new Intent(this, LoginActivity.class);
        startActivity(actSwitch);
    }

    @Override
    public void onRegistrationError(AuthenticationError err) {
        showProgress(false);
        if (err == AuthenticationError.REGISTRATION_EMAIL_NOT_AVAILABLE){
            inputEmail.setError("Email not available.");
        }
        if (err == AuthenticationError.REGISTRATION_USERNAME_NOT_AVAILABLE){
            inputUsername.setError("Username not available.");
        }
        if (err == AuthenticationError.REGISTRATION_WRONG_PASSWORD_RETYPE){
            inputPasswordRetype.setError("Password don't match.");
        }
        if (err == AuthenticationError.UNKNOWN_ERROR){
            AlertHelper.showUnknownErrorAlert(this);
        }
    }
}
