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
    private boolean mHashedPassword;

    public UserAuthTask(String email, String password, IAuthenticationCallback callback, ITaskCallback taskCallback, boolean hashedPassword) {
        mEmail = email;
        mPassword = password;
        this.callback = callback;
        this.taskCallback = taskCallback;
        this.mHashedPassword = hashedPassword;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!ServerConnection.getInstance().isConnected()){
            callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
            return false;
        }

        if (mHashedPassword)
            AuthenticationController.getInstance().authenticateUserHashed(mEmail, mPassword, callback);
        else
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
