package com.simpleapp.main;

import java.util.List;

import org.eclipse.egit.github.core.Gist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleapp.adapter.GistListAdapter;
import com.simpleapp.connection.UserAPI;
import com.simpleapp.constants.Constants;
import com.simpleapp.constants.UserInfo;

public class MainActivity extends Activity implements OnClickListener {

	private ListView 		gistListView_ = null;
	private Button 			createButton_ = null;
	private UserInfo		userInfo_ = null;
	private List<Gist>      gistList_ = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		setListener();
		initData();
	}
	public void initView() {
		gistListView_ = (ListView) findViewById(R.id.gistListView);
		createButton_ = (Button) findViewById(R.id.createButton);
	}
	
	public void setListener() {
		createButton_.setOnClickListener(this);
	}
	
	public void initData() {
		userInfo_ = Constants.getUserInfo(this);
		GetGistTask getGistTask = new GetGistTask();
		getGistTask.execute();
	}
	
	
	public void onClick(View v) {
		int viewID = v.getId();
		switch (viewID) {
		case R.id.createButton: {
			CreateGistTask createGistTask = new CreateGistTask();
			createGistTask.execute();
		}
		    break;
	    default:
	    	break;
		}
	}
	
	private class CreateGistTask extends AsyncTask<String, Void, String>
	{
		ProgressDialog mProgressDialog ;
		boolean isSuccess = false;
		@Override
		protected void onPostExecute( String result )
		{
			mProgressDialog.cancel();
			if (isSuccess) {
				Toast.makeText(MainActivity.this, "Success to create new Gist!", Toast.LENGTH_LONG).show();
				initData();
			}
			else
				Toast.makeText(MainActivity.this, "Failed to create new Gist!", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( MainActivity.this ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String doInBackground( String... params )
		{
			isSuccess = UserAPI.createGistFunc(userInfo_.getUsername(), userInfo_.getPassword());
			return null;
		}
	}
	private class GetGistTask extends AsyncTask<String, Void, String>
	{
		ProgressDialog mProgressDialog ;
		@Override
		protected void onPostExecute( String result )
		{
			mProgressDialog.cancel();
			if (gistList_ == null || gistList_.size() == 0) {
				Toast.makeText(MainActivity.this, "Gist is empty.", Toast.LENGTH_LONG).show();
				return;
			}
			GistListAdapter gistListAdapter = new GistListAdapter(MainActivity.this, gistList_, gistListView_);
			gistListView_.setAdapter(gistListAdapter);
			gistListAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( MainActivity.this ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String doInBackground( String... params )
		{
			gistList_ = UserAPI.getGistsFunc(userInfo_.getUsername(), userInfo_.getPassword());
			return null;
		}
	}
}
