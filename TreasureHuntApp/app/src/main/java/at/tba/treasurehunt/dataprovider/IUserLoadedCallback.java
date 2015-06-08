package at.tba.treasurehunt.dataprovider;

import data_structures.user.User;

/**
 * Created by dAmihl on 08.06.15.
 */
public interface IUserLoadedCallback {
    public void onUserLoadedSuccess(User u);

    public void onUserLoadedFailure();
}
