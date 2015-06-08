package at.tba.treasurehunt.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import at.tba.treasurehunt.activities.ActivityManager;
import at.tba.treasurehunt.activities.HomeActivity;
import at.tba.treasurehunt.servercomm.ServerConnection;

/**
 * Created by dAmihl on 17.05.15.
 */
public class AlertHelper {

    public static void showConnectionErrorAlert(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Connection error!")
                .setMessage("An error occured connecting to the server. Please try again.")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showUnknownErrorAlert(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Error!")
                .setMessage("An unknown error occured. Please try again.")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showNewAlertSingleButton(Context context, String title, String message, final Runnable btnAction){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btnAction.run();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

}
