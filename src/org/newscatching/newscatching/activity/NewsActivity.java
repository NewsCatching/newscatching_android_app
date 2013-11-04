package org.newscatching.newscatching.activity;

import org.newscatching.newscatching.BaseActivity;
import org.newscatching.newscatching.R;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.observer.OnNicknameReady;
import org.newscatching.newscatching.util.AsyncImageDownloader;
import org.newscatching.newscatching.util.DialogUtil;
import org.newscatching.newscatching.util.ViewUtils;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.NewsDetails;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsActivity extends BaseActivity {
	private News current_news;

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

		new AsyncTask<String, Void, ReturnMessage<NewsDetails>>() {

			@Override
			protected ReturnMessage<NewsDetails> doInBackground(String... params) {

				INewsDao dao = BaseNewsDao.newInstance(NewsActivity.this);
				return dao.getNews(params[0]);
			}

			protected void onPostExecute(ReturnMessage<NewsDetails> result) {

				if (!result.isSuccess()) {
					Toast.makeText(NewsActivity.this, R.string.ERROR_DATA_FAIL, Toast.LENGTH_SHORT).show();
					return;
				}

				if (result.getStatusCode() == ReturnMessage.SUCCESS_IN_CACHE) {
					Toast.makeText(NewsActivity.this, R.string.SUCCESS_IN_CACHE_WARNING, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				News news = result.getData().getNews();
				render(news);
			};

		}.execute(newsID);

	}

	public void render(final News news) {
		current_news = news;
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

		iv = _findViewById(R.id.news_award);
		if (!news.isHot()) {
			iv.setVisibility(View.GONE);
		} else {
			iv.setVisibility(View.VISIBLE);
		}

		WebView wv = _findViewById(R.id.news_content);
		wv.loadData(news.getContent(), "text/html; charset=UTF-8", null);

		RelativeLayout rl = _findViewById(R.id.news_talk_submit);
		ViewUtils.deepOnClickBinder(rl, new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final EditText edit = _findViewById(R.id.news_talk_input);
				final String talk = edit.getText().toString().trim();
				if ("".equals(talk)) {
					ViewUtils.alert(NewsActivity.this, "請輸入訊息", "您送出的訊息為空白，請輸入資料後再試", null);
					return;
				}

				DialogUtil.askNicks(NewsActivity.this, new OnNicknameReady() {
					@Override
					public void done(String name) {
						sendTalks(name, talk);
					}
				}, false);

			}
		});

		ViewUtils.deepOnClickBinder(_findViewById(R.id.news_post_container), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openReportDialog();
			}
		});

		ViewUtils.deepOnClickBinder(_findViewById(R.id.news_browses_container), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsActivity.this, NewsListActivity.class);
				intent.putExtra("type", NewsListActivity.TYPE_NEWS);
				startActivity(intent);
			}
		});
	}

	private void sendTalks(final String nick, final String talk) {
		if (current_news != null) {
			View view = DialogUtil.getInflater(this).inflate(R.layout._talk_list_row, null);

			final ImageView iv = (ImageView) view.findViewById(R.id.talk_star);
			((TextView) view.findViewById(R.id.talk_name)).setText(nick);
			((TextView) view.findViewById(R.id.talk_content)).setText(talk);

			ViewGroup vg = _findViewById(R.id.news_talks);
			vg.addView(view);

			new AsyncTask<Void, Void, ReturnMessage<Object>>() {
				public ReturnMessage<Object> doInBackground(Void[] params) {
					INewsDao dao = BaseNewsDao.newInstance(NewsActivity.this);
					return dao.addNewTalk(current_news.getNewsID(), talk, nick);
				};

				protected void onPostExecute(org.newscatching.newscatching.viewmodel.ReturnMessage<Object> result) {
					iv.setVisibility(View.VISIBLE);
					if (!result.isSuccess()) {
						// TODO:fail
						// iv.setImageDrawable(getResources().getDrawable(R.drawable.talk_fail));
						return;
					}
				};
			}.execute();
		}
		final EditText edit = _findViewById(R.id.news_talk_input);
		edit.setText("");
	}

	private void openReportDialog() {

		final View view = DialogUtil.getInflater(this).inflate(R.layout._dialog_news_report, null);

		final AlertDialog dialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info)
				.setView(view).show();

		TextView tv = (TextView) view.findViewById(R.id.news_report_title);
		tv.setText(current_news.getTitle());

		tv = (TextView) view.findViewById(R.id.news_report_source);
		tv.setText(current_news.getSource());

		view.findViewById(R.id.news_report_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		view.findViewById(R.id.news_report_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText url = (EditText) view.findViewById(R.id.news_report_url);
				EditText comment = (EditText) view.findViewById(R.id.news_report_comment);

				sendNewReport(url.getText().toString(), comment.getText().toString());

				dialog.dismiss();
			}
		});

	}

	public void sendNewReport(final String url, final String comment) {
		DialogUtil.askNicks(NewsActivity.this, new OnNicknameReady() {
			@Override
			public void done(final String name) {
				if (current_news != null) {
					View view = DialogUtil.getInflater(NewsActivity.this).inflate(R.layout._report_list_row, null);

					final ImageView iv = (ImageView) view.findViewById(R.id.report_star);
					((TextView) view.findViewById(R.id.report_content)).setText(url + " " + comment);

					ViewGroup vg = _findViewById(R.id.news_reports);
					vg.addView(view);

					ViewUtils.deepOnClickBinder(view, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_VIEW);
							intent.addCategory(Intent.CATEGORY_BROWSABLE);
							intent.setData(Uri.parse(url));
							startActivity(intent);
						}
					});

					new AsyncTask<Void, Void, ReturnMessage<Object>>() {
						public ReturnMessage<Object> doInBackground(Void[] params) {
							INewsDao dao = BaseNewsDao.newInstance(NewsActivity.this);
							return dao.addNewReport(current_news.getNewsID(), name, url, comment);
						};

						protected void onPostExecute(
								org.newscatching.newscatching.viewmodel.ReturnMessage<Object> result) {
							iv.setVisibility(View.VISIBLE);
							if (!result.isSuccess()) {
								// TODO:review
								// iv.setImageDrawable(getResources().getDrawable(R.drawable.talk_fail));
								return;
							}
						};
					}.execute();
				}
			}
		}, false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
