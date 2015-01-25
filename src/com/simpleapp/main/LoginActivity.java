package com.simpleapp.main;

import org.eclipse.egit.github.core.client.GitHubClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.simpleapp.constants.Constants;
import com.simpleapp.constants.UserInfo;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText 		usernameEditText_ = null;  // github username
	private EditText 		passwordEditText_ = null;  // github password
	private Button 			loginButton_ = null;
	
	private GitHubClient	gitHubClient_ = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		initView();
		setListener();
		initData();
	}
	
	public void initView() {
		usernameEditText_ = (EditText) findViewById(R.id.usernameEditText);
		passwordEditText_ = (EditText) findViewById(R.id.passwordEditText);
		loginButton_ = (Button) findViewById(R.id.loginButton);
	}
	
	public void setListener() {
		loginButton_.setOnClickListener(this);
	}
	
	public void initData() {

	}
	
	public void loginFunc() {
		String username = usernameEditText_.getText().toString();
		String password = passwordEditText_.getText().toString();
		if (username.length() == 0) {
			usernameEditText_.setError(getResources().getString(R.string.username_empty));
			return;
		}
		if (password.length() == 0) {
			passwordEditText_.setError(getResources().getString(R.string.password_empty));
			return;
		}
		try {
			gitHubClient_ = new GitHubClient();
			gitHubClient_.setCredentials(username, password);
		} catch (Exception e) {
			Log.e("error", e.toString());
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		Constants.setUserInfo(this, userInfo);
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onClick(View v) {
		int viewID = v.getId();
		switch (viewID) {
		case R.id.loginButton: {
			loginFunc();
		}
		    break;
	    default:
	    	break;
		}
	}
}
