package org.newscatching.newscatching.activity;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.util.AsyncImageDownloader;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		Bundle extras = getIntent().getExtras();
		String newsID = extras.getString("NewsID");

		findViewById(R.id.head_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		new AsyncTask<String, Void, ReturnMessage<News>>() {

			@Override
			protected ReturnMessage<News> doInBackground(String... params) {

				INewsDao dao = BaseNewsDao.newInstance(NewsActivity.this);

				return dao.getNews(params[0]);
			}

			protected void onPostExecute(ReturnMessage<News> result) {

				if (!result.isSuccess()) {
					Toast.makeText(NewsActivity.this, R.string.ERROR_DATA_FAIL, Toast.LENGTH_SHORT).show();
					return;
				}

				if (result.getStatusCode() == ReturnMessage.SUCCESS_IN_CACHE) {
					Toast.makeText(NewsActivity.this, R.string.SUCCESS_IN_CACHE_WARNING, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				News news = result.getData();
				render(news);
			};

		}.execute(newsID);

	}

	public void render(final News news) {
		// inflater = (LayoutInflater)
		// activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView tx = _findViewById(R.id.news_title);
		tx.setText(news.getTitle());

		tx = _findViewById(R.id.news_source);
		tx.setText(news.getSource());

		ImageView iv = _findViewById(R.id.news_share);
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, news.getShareUrl() + " " + news.getTitle());
				sendIntent.setType("text/plain");
				startActivity(sendIntent);
			}
		});

		iv = _findViewById(R.id.news_image);

		if (news.hasImageURL()) {
			new AsyncImageDownloader(NewsActivity.this, "news_" + news.getNewsID(), iv, news.getImageURL())
					.setThumbsize(800).execute();
		} else {
			iv.setVisibility(View.GONE);
		}

		WebView wv = _findViewById(R.id.news_content);
		wv.loadData(news.getContent(), "text/html; charset=UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
