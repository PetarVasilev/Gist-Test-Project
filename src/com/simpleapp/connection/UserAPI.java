package com.simpleapp.connection;

import java.util.Collections;
import java.util.List;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

import android.util.Log;

public class UserAPI
{
	public static List<Gist> getGistsFunc(String username, String password) {
		List<Gist> gistList = null;
		try {
			GistService service = new GistService();
			service.getClient().setCredentials(username, password);
			gistList = service.getGists(username);
		} catch (Exception e) {
			Log.e("error", e.toString());
		}
		return gistList;
	}
	public static boolean createGistFunc(String username, String password) {
		try {
			GistFile file = new GistFile();
			file.setContent("Simple app test");
			Gist gist = new Gist();
			gist.setPublic(true);
			gist.setDescription("Hello World");
			gist.setFiles(Collections.singletonMap("Simple app test", file));
			GistService service = new GistService();
			service.getClient().setCredentials(username, password);
			gist = service.createGist(gist);
		} catch (Exception e) {
			Log.e("error", e.toString());
			return false;
		}
		return true;
	}
}