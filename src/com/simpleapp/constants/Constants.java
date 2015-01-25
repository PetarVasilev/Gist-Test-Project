package com.simpleapp.constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.ImageLoader;

public class Constants {
	
	public static ImageLoader imageLoader = null;
	
	public static void setUserInfo(Context context, UserInfo info) {
		SharedPreferences.Editor editor = context.getSharedPreferences("com.simpleapp.main", 0).edit();
		editor.putString("username", info.getUsername());
		editor.putString("password", info.getPassword());
		editor.commit();
	}

	public static UserInfo getUserInfo(Context context) {
		SharedPreferences pref = context.getSharedPreferences("com.simpleapp.main", 0);
		UserInfo info = new UserInfo();
		info.setUsername(pref.getString("username", ""));
		info.setPassword(pref.getString("password", ""));
		return info;
	}
	
}
