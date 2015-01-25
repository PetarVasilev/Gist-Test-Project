package com.simpleapp.adapter;
import java.util.List;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.User;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.simpleapp.constants.Constants;
import com.simpleapp.main.R;

public class GistListAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private Context context = null;
	private List<Gist> gistlist = null;
	private DisplayImageOptions options;
	
	public GistListAdapter(Activity context, List<Gist> itemList, ListView listView) {
		this.gistlist = itemList;
		this.context = context;
		options = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY)
				   .cacheOnDisc(true)
				   .cacheInMemory(true)
				   .bitmapConfig(Bitmap.Config.RGB_565)
				   .considerExifParams(true)
				   .displayer(new FadeInBitmapDisplayer(300))
				   .build();
	}

	@Override
	public int getCount() {
		return gistlist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final CellViewCache cache;
		if (view == null) {
			inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.gist_cell, parent, false);
			cache = new CellViewCache(view);
			view.setTag(cache);
		} else {
			cache = (CellViewCache) view.getTag();
		}
	
		Gist gistInfo = gistlist.get(position);
		if (gistInfo == null)
			return view;
		TextView gistTitleTextView = cache.getGistTitleTextView();
		gistTitleTextView.setText(gistInfo.getDescription());
		
		ImageView avatarImageView = cache.getGistImageView();
		User user = gistInfo.getUser();
		Constants.imageLoader.displayImage(user.getAvatarUrl(), avatarImageView, options);
		return view;
	}
	class CellViewCache {
		private View view;
		private TextView gistTitleTextView = null;
		private ImageView gistImageView = null;
		public CellViewCache(View view) {
			this.view = view;
		}
		
		public TextView getGistTitleTextView() {
			if (gistTitleTextView == null) {
				gistTitleTextView = (TextView) view.findViewById(R.id.gistTitleTextView);
			}
			return gistTitleTextView;
		}
		public ImageView getGistImageView() {
			if (gistImageView == null) {
				gistImageView = (ImageView) view.findViewById(R.id.avatarImageView);
			}
			return gistImageView;
		}
		
	}
}

