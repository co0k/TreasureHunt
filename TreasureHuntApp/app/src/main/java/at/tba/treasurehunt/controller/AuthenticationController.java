package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import communication_controller.json.JsonConstructor;

/**
 * Singleton
 * Handles login actions
 * Maybe GoogleServices will be used to log in
 */
public class AuthenticationController implements IResponseCallback {

	private static AuthenticationController instance = null;

	public static AuthenticationController getInstance() {
		if (instance == null) instance = new AuthenticationController();
		return instance;
	}

	private AuthenticationController() {
	}

	private IAuthenticationCallback callback;
	private Integer userID = 0;

	public void authenticateUser(String uName, String password, IAuthenticationCallback callback) {
		this.callback = callback;
		password = generateHash(uName + password);
		ServerCommunication.getInstance().logInToServer(uName, password, this);
	}

	public void authenticateUserHashed(String uName, String passwordHashed, IAuthenticationCallback callback) {
		this.callback = callback;
		ServerCommunication.getInstance().logInToServer(uName, passwordHashed, this);
	}

	public void registerNewUser(String uName, String email, String password, String passwordRetype, IAuthenticationCallback callback) {
		this.callback = callback;
		if (!password.equals(passwordRetype)) {
			callback.onRegistrationError(AuthenticationError.REGISTRATION_WRONG_PASSWORD_RETYPE);
			return;
		}
		password = generateHash(uName + password);
		ServerCommunication.getInstance().registerUserOnServer(uName, email, password, this);
	}

	@Override
	public void onResponseReceived(JSONRPC2Response response) {
		JsonConstructor constr = new JsonConstructor();

		if (response.toJSONObject().get("id").equals("0")) {
			if (response.toJSONObject().get("result").equals("null")) {
				callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
				return;
			}
			Integer result = constr.fromJson((String) (response.getResult())
					, Integer.class);
			if (result == null) {
				callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
				return;
			}
			callback.onAuthenticationSuccess();
			userID = result;
		} else if (response.toJSONObject().get("id").equals("-1")) {
			if (response.toJSONObject().get("result").equals("null")) {
				callback.onRegistrationError(AuthenticationError.UNKNOWN_ERROR);
				return;
			}
			Boolean result = constr.fromJson((String) response.getResult(), Boolean.class);
			if (result == false) {
				callback.onRegistrationError(AuthenticationError.UNKNOWN_ERROR);
				return;
			}

			callback.onRegistrationSuccess();
		} else {
			callback.onRegistrationError(AuthenticationError.UNKNOWN_ERROR);
			callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
		}
	}

	public static String generateHash(String toHash) {
		MessageDigest md = null;
		byte[] hash = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			hash = md.digest(toHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return convertToHex(hash);
	}

	/**
	 * Converts the given byte[] to a hex string.
	 *
	 * @param raw the byte[] to convert
	 * @return the string the given byte[] represents
	 */
	private static String convertToHex(byte[] raw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < raw.length; i++) {
			sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	@Override
	public void onResponseReceiveError() {
		callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
	}

	public Integer getLoggedInUserID() {
		return userID;
	}
}
