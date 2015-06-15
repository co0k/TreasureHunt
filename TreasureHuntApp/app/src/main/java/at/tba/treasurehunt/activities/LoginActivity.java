package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.AuthenticationError;
import at.tba.treasurehunt.controller.IAuthenticationCallback;
import at.tba.treasurehunt.controller.UserLoginDataController;
import at.tba.treasurehunt.servercomm.IServerConnectionCallback;
import at.tba.treasurehunt.servercomm.ServerConnection;
import at.tba.treasurehunt.tasks.ITaskCallback;
import at.tba.treasurehunt.tasks.UserAuthTask;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>, IServerConnectionCallback, IAuthenticationCallback, ITaskCallback {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /*private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };*/
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserAuthTask mAuthTask = null;
    private StayLoggedInHandler mStayLoggedInHandler;
    private UserLoginDataController mUserLoginDataController;

    // UI references.
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ( id == EditorInfo.IME_NULL) {
                    attemptLogin(mUserNameView.getText().toString(), mPasswordView.getText().toString(), false);
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(mUserNameView.getText().toString(), mPasswordView.getText().toString(), false);
            }
        });

        // setup the stay logged in button
        final CheckedTextView mStayLogged = (CheckedTextView) findViewById(R.id.checkedTextView1);
        mStayLoggedInHandler = new StayLoggedInHandler(mStayLogged);
        mStayLogged.setOnClickListener(mStayLoggedInHandler);

        mUserLoginDataController = new UserLoginDataController(this.getBaseContext());

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        ActivityManager.setCurrentActivity(this);

        if (mStayLoggedInHandler.getStayLoggedIn()) {
            String defaultUsername = mUserLoginDataController.getDefaultUserName();
            String defaultPasswordHash = mUserLoginDataController.getDefaultUserPasswordHash();
            if (defaultPasswordHash != null && defaultUsername != null && defaultUsername != "" && defaultPasswordHash != "")
                attemptLogin(defaultUsername, defaultPasswordHash, true);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(String name, String password, boolean hashedPassword) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user name.
        if (TextUtils.isEmpty(name)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUsernameValid(name)) {
            mUserNameView.setError(getString(R.string.error_invalid_user_name));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserAuthTask(name, password, this, this, hashedPassword);
            connectServer();
            //mAuthTask.execute((Void) null);
        }


    }

    private boolean isUsernameValid(String email) {
        return true;
        //TODO: Replace this with your own logic
       // return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return true;
        //TODO: Replace this with your own logic
       // return password.length() > 4;
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onTaskCancelled() {
        mAuthTask = null;
        showProgress(false);
    }

    @Override
    public void onTaskPostExecute() {
        mAuthTask = null;
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mUserNameView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    public void connectServer(){
        //ActivityManager.showLoadingSpinner("Connecting to server..");
        ServerConnection.getInstance().connectServer(this);
    }

    private void loginSuccess() {
        finish();
        Intent actSwitch = new Intent(this, HomeActivity.class);
        // save the user default login data
        String defaultUsername = mUserNameView.getText().toString();
        String defaultPassword = mPasswordView.getText().toString();
        if (mUserLoginDataController.getDefaultUserName() == "" && defaultUsername != "" && defaultPassword != "")
            mUserLoginDataController.saveDefaultLoginData(defaultUsername, defaultPassword);
        startActivity(actSwitch);
    }

    public void onServerConnected(){
        mAuthTask.execute((Void) null);
    }

    public void onServerNotConnected(){
        mAuthTask = null;
        showProgress(false);
        mUserNameView.requestFocus();

    }

    public void onButtonRegister(View v){
        Intent actSwitch = new Intent(this, RegisterActivity.class);
        startActivity(actSwitch);
    }

    @Override
    public void onAuthenticationSuccess() {
        ActivityManager.dismissLoadingSpinner();
        showProgress(false);
        loginSuccess();
    }

    @Override
    public void onAuthenticationFailure(AuthenticationError err) {
        mAuthTask = null;
        showProgress(false);
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void onRegistrationSuccess() {

    }

    @Override
    public void onRegistrationError(AuthenticationError err) {

    }

    /**
     * handles and saves the state if the user wants to stay logged in
     */
    public static class StayLoggedInHandler implements OnClickListener {
        final CheckedTextView mStayLogged;

        public StayLoggedInHandler(final CheckedTextView mStayLogged) {
            this.mStayLogged = mStayLogged;
            // setup the currentState
            mStayLogged.setChecked(PreferenceManager.getDefaultSharedPreferences(mStayLogged.getContext()).getBoolean(
                    mStayLogged.getContext().getString(R.string.prefStayLoggedIn), false));
        }

        public boolean getStayLoggedIn() {
            return PreferenceManager.getDefaultSharedPreferences(mStayLogged.getContext()).getBoolean(
                    mStayLogged.getContext().getString(R.string.prefStayLoggedIn), false);
        }

        public void setStayLoggedin() {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mStayLogged.getContext()).edit();
            editor.putBoolean(mStayLogged.getContext().getString(R.string.prefStayLoggedIn), mStayLogged.isChecked());
            editor.commit();
        }

        public void resetStayLoggedin() {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mStayLogged.getContext()).edit();
            editor.putBoolean(mStayLogged.getContext().getString(R.string.prefStayLoggedIn), false);
            editor.commit();
        }

        @Override
        public void onClick(View v) {
            if (mStayLogged.isChecked())
                mStayLogged.setChecked(false);
            else
                mStayLogged.setChecked(true);
            setStayLoggedin();
        }
    }

}



