package org.newscatching.newscatching.adapter;

import java.util.List;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.util.AsyncImageDownloader;
import org.newscatching.newscatching.viewmodel.HotNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyHotListAdapter extends BaseAdapter {

	private BaseActivity activity;
	private List<HotNews> data;
	private static LayoutInflater inflater = null;

	public LazyHotListAdapter(BaseActivity a, List<HotNews> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout._hot_list_row, null);

		HotNews news = data.get(position);

		TextView title = (TextView) vi.findViewById(R.id.hot_listview_title); // title
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.hot_listview_image);

		title.setText(news.getTitle());

		// ActivityDestroyObserver context, String name, ImageView iv, String
		// url
		// (ActivityDestroyObserver context, String name, ImageView iv, String
		// url,boolean save)
		new AsyncImageDownloader(this.activity, news.getNewsID() + "_thumb", thumb_image, news.getImageUrl(), true)
				.setThumbsize(200).execute();

		return vi;
	}
}