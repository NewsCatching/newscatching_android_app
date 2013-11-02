package org.newscatching.newscatching;

import java.util.List;

import org.newscatching.newscatching.activity.NewsActivity;
import org.newscatching.newscatching.adapter.LazyHotListAdapter;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.util.ViewUtils;
import org.newscatching.newscatching.viewmodel.HotNews;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class HotActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);

		new AsyncTask<Void, Void, ReturnMessage<List<HotNews>>>() {

			@Override
			protected ReturnMessage<List<HotNews>> doInBackground(Void... params) {

				INewsDao dao = BaseNewsDao.newInstance(HotActivity.this);

				return dao.getHotNews();
			}

			protected void onPostExecute(
					org.newscatching.newscatching.viewmodel.ReturnMessage<java.util.List<HotNews>> result) {

				if (!result.isSuccess()) {
					Toast.makeText(HotActivity.this, R.string.ERROR_DATA_FAIL, Toast.LENGTH_SHORT).show();
					return;
				}

				if (result.getStatusCode() == ReturnMessage.SUCCESS_IN_CACHE) {
					Toast.makeText(HotActivity.this, R.string.SUCCESS_IN_CACHE_WARNING, Toast.LENGTH_SHORT).show();
					return;
				}
				List<HotNews> news = result.getData();
				LazyHotListAdapter adapter = new LazyHotListAdapter(HotActivity.this, news,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								HotNews news = (HotNews) v.getTag();
								if (news != null) {
									Intent intent = new Intent(HotActivity.this, NewsActivity.class);
									intent.putExtra("NewsID", ""+news.getNewsID());
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
