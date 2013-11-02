package org.newscatching.newscatching;

import java.util.ArrayList;
import java.util.List;

import org.newscatching.newscatching.adapter.LazyHotListAdapter;
import org.newscatching.newscatching.viewmodel.HotNews;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class HotActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);

		List<HotNews> news = new ArrayList<HotNews>();

		for (int i = 0; i < 5; ++i) {
			news.add(new HotNews(i, "test11" + i,
					"https://www.gravatar.com/avatar/0198a85bfde16c6a9db8503043bc68f4?s=32&d=identicon&r=PG"));
		}
		LazyHotListAdapter adapter = new LazyHotListAdapter(this, news);
		ListView lv = (ListView) findViewById(R.id.hot_listview);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
