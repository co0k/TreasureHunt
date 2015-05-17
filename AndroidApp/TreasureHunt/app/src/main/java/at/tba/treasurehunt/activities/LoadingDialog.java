package at.tba.treasurehunt.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by dAmihl on 17.05.15.
 */
public class LoadingDialog extends DialogFragment {

    private ProgressDialog _dialog;

    public LoadingDialog(){

    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        _dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme()); // You can use styles or inflate a view
        _dialog.setMessage("Connecting.."); // set your messages if not inflated from XML

        //dlgAlert.setCancelable(false);
        _dialog.setCancelable(false);
        return _dialog;
    }

}
