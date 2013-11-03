package org.newscatching.newscatching.activity;

import java.util.List;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.adapter.LazyListAdapter;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.util.LoadingAsyncTask;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsListActivity extends BaseActivity {

	public final static int TYPE_NEWS = 0;
	public final static int TYPE_HOT = 1;
	public final static int TYPE_INTERNET = 2;

	private int count = 0;
	private int current_type = TYPE_NEWS;
	private int ver = 0 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_list);

		findViewById(R.id.head_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});

		final int type = getIntent().getExtras().getInt("type");
		current_type = type;
		TextView tv = null;
		switch (type) {
		case TYPE_NEWS:
			tv = (TextView) findViewById(R.id.news_list_news);
			tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.news_footer_button_selected));
			break;
		case TYPE_HOT:
			tv = (TextView) findViewById(R.id.news_list_hot);
			tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.news_footer_button_selected));
			break;
		case TYPE_INTERNET:
			tv = (TextView) findViewById(R.id.news_list_lazys);
			tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.news_footer_button_selected));
			break;
		}

		loadList(type, null);

		findViewById(R.id.news_list_news).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsListActivity.this, NewsListActivity.class);
				intent.putExtra("type", NewsListActivity.TYPE_NEWS);
				startActivity(intent);
				finish();
			}
		});
		findViewById(R.id.news_list_hot).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsListActivity.this, NewsListActivity.class);
				intent.putExtra("type", NewsListActivity.TYPE_HOT);
				startActivity(intent);
				finish();
			}
		});
		findViewById(R.id.news_list_lazys).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsListActivity.this, NewsListActivity.class);
				intent.putExtra("type", NewsListActivity.TYPE_INTERNET);
				startActivity(intent);
				finish();
			}
		});

		final EditText edittext = (EditText) findViewById(R.id.newslist_search);

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					loadList(current_type, edittext.getText().toString());
					return true;
				}
				count++;
				if (count % 3 == 0) {
					loadList(current_type, edittext.getText().toString());
				}
				return false;
			}
		});
	}

	private void loadList(final int type, final String query) {
		ver ++; 
		final int now_ver = ver;
		new LoadingAsyncTask<Void, Void, ReturnMessage<List<News>>>(this, "loading", this) {

			@Override
			protected ReturnMessage<List<News>> doInBackground(Void... params) {
				INewsDao dao = BaseNewsDao.newInstance(NewsListActivity.this);
				return dao.getNewsList(type, query);
			}

			protected void onPostExecute(ReturnMessage<java.util.List<News>> result) {
				if(now_ver != ver){
					return ;
				}
				super.onPostExecute(result);
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
