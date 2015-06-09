package at.tba.treasurehunt.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by dAmihl on 28.04.15.
 */
public class ShowMessageHelper {


    public static void showSimpleInfoMessagePopUp(String msg, Context context){
        new AlertDialog.Builder(context)
                .setTitle("Info for you")
                .setMessage(msg)
                .setPositiveButton("nice bro", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public static void showNewSimpleMessagePopUp(Context context, String title, String msg, String buttonText, final Runnable btnAction){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       btnAction.run();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }

}
