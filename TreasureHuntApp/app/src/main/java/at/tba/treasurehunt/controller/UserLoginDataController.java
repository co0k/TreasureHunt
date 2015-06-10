package at.tba.treasurehunt.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import at.tba.treasurehunt.R;

/**
 * saves the default login in data if wished
 */
public class UserLoginDataController {
	private Context context;

	public UserLoginDataController(Context context) {
		this.context = context;
	}

	public void deleteDefaultLoginData(String name, String password) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(context.getString(R.string.prefDefaultLoginUserName), "");
		editor.putString(context.getString(R.string.prefDefaultLoginUserPassword), "");
		editor.commit();
	}

	public String getDefaultUserName() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.prefDefaultLoginUserName), "");
	}

	public String getDefaultUserPasswordHash() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.prefDefaultLoginUserPassword), "");
	}

	public void saveDefaultLoginData(String name, String password) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(context.getString(R.string.prefDefaultLoginUserName), name);
		editor.putString(context.getString(R.string.prefDefaultLoginUserPassword), AuthenticationController.generateHash(name + password));
		editor.commit();
	}

//	public void deleteCurrentLoginData(String name, String password) {
//		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//		editor.putString(context.getString(R.string.prefCurrentLoginUserName), "");
//		editor.putString(context.getString(R.string.prefCurrentLoginUserPassword), "");
//		editor.commit();
//	}
//
//	public String getCurrentUserName() {
//		return PreferenceManager.getDefaultSharedPreferences(context).getString(
//				context.getString(R.string.prefCurrentLoginUserName), "");
//	}
//
//	public String getCurrentUserPasswordHash() {
//		return PreferenceManager.getDefaultSharedPreferences(context).getString(
//				context.getString(R.string.prefDefaultLoginUserPassword), "");
//	}
//
//	public void saveDefaultLoginData(String name, String password) {
//		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//		editor.putString(context.getString(R.string.prefDefaultLoginUserName), name);
//		editor.putString(context.getString(R.string.prefDefaultLoginUserPassword), AuthenticationController.generateHash(name + password));
//		editor.commit();
//	}
}

