package org.newscatching.newscatching.activity;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class NewsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
        
		Bundle extras = getIntent().getExtras();
        String newsID = extras.getString("NewsID");
        
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
					Toast.makeText(NewsActivity.this, R.string.SUCCESS_IN_CACHE_WARNING, Toast.LENGTH_SHORT).show();
					return;
				}
				
				News news = result.getData();
				
				//TODO rendering
			};

		}.execute(newsID);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
