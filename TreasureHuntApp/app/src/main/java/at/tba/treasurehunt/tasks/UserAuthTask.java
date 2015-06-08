package at.tba.treasurehunt.tasks;

import android.os.AsyncTask;

import at.tba.treasurehunt.activities.LoginActivity;
import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.controller.AuthenticationError;
import at.tba.treasurehunt.controller.IAuthenticationCallback;
import at.tba.treasurehunt.servercomm.ServerConnection;

/**
 * Created by dAmihl on 29.05.15.
 */
public class UserAuthTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final IAuthenticationCallback callback;
    private final ITaskCallback taskCallback;

    public UserAuthTask(String email, String password, IAuthenticationCallback callback, ITaskCallback taskCallback) {
        mEmail = email;
        mPassword = password;
        this.callback = callback;
        this.taskCallback = taskCallback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!ServerConnection.getInstance().isConnected()){
            callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
            return false;
        }

        AuthenticationController.getInstance().authenticateUser(mEmail, mPassword, callback);
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        this.taskCallback.onTaskPostExecute();
   }

    @Override
    protected void onCancelled() {
        this.taskCallback.onTaskCancelled();
    }

}
