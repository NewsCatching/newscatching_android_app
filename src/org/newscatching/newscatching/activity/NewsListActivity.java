package org.newscatching.newscatching.activity;

import java.util.List;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.adapter.LazyHotListAdapter;
import org.newscatching.newscatching.adapter.LazyListAdapter;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class NewsListActivity extends BaseActivity {

	public final static int TYPE_NEWS = 0;
	public final static int TYPE_HOT = 1;
	public final static int TYPE_INTERNEW = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);

		final int type = getIntent().getExtras().getInt("type");

		new AsyncTask<Void, Void, ReturnMessage<List<News>>>() {

			@Override
			protected ReturnMessage<List<News>> doInBackground(Void... params) {

				INewsDao dao = BaseNewsDao.newInstance(NewsListActivity.this);
				// TODO
				return dao.getNewsList(type, null);
			}

			protected void onPostExecute(ReturnMessage<java.util.List<News>> result) {

				if (!result.isSuccess()) {
					Toast.makeText(NewsListActivity.this, R.string.ERROR_DATA_FAIL, Toast.LENGTH_SHORT).show();
					return;
				}

				if (result.getStatusCode() == ReturnMessage.SUCCESS_IN_CACHE) {
					Toast.makeText(NewsListActivity.this, R.string.SUCCESS_IN_CACHE_WARNING, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				List<News> news = result.getData();
				LazyListAdapter adapter = new LazyListAdapter(NewsListActivity.this, news,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								News news = (News) v.getTag();
								if (news != null) {
									Intent intent = new Intent(NewsListActivity.this, NewsActivity.class);
									intent.putExtra("NewsID", "" + news.getNewsID());
									startActivity(intent);
								}
							}
						});
				ListView lv = (ListView) findViewById(R.id.hot_listview);
				lv.setAdapter(adapter);

			};

		}.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
