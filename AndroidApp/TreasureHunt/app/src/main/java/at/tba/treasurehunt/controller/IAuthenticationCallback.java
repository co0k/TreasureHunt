package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 18.05.15.
 */
public interface IAuthenticationCallback {

    public void onAuthenticationSuccess();

    public void onAuthenticationFailure(AuthenticationError err);

    public void onRegistrationSuccess();

    public void onRegistrationError(AuthenticationError err);

}
