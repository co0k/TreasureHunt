package at.tba.treasurehunt.activities;

import android.app.Activity;
import android.app.FragmentManager;

/**
 * Created by dAmihl on 17.05.15.
 */
public class ActivityManager {

    private static Activity currentActivity;

    private static LoadingDialog serverConnLoadingDialog;
    private static FragmentManager fragmentManager;

    public static Activity getCurrentActivity(){
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity){
        currentActivity = activity;
    }

    public static void showLoadingSpinner(String text){
        serverConnLoadingDialog = new LoadingDialog();
        fragmentManager = currentActivity.getFragmentManager();
        serverConnLoadingDialog.show(fragmentManager, text);
    }

    public static void dismissLoadingSpinner(){
        if (serverConnLoadingDialog == null) return;
        serverConnLoadingDialog.dismiss();
    }

    public static void closeApplication(){
        getCurrentActivity().finish();
        System.exit(0);
    }



}
