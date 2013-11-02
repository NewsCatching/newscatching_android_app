package org.newscatching.newscatching.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import org.newscatching.newscatching.observer.ActivityDestroyObserver;
import org.newscatching.newscatching.observer.ActivityEndAction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncImageDownloader extends AsyncTask<Void, Void, Drawable> {

	private WeakReference<ImageView> _reference;
	private String _name;
	private String _url;
	private ActivityDestroyObserver _context;
	private boolean _save;
	private Bitmap bm;
	private boolean destroy = false;
	private boolean fullsize = false;

	private int thumbsize = 100;

	public AsyncImageDownloader(ActivityDestroyObserver context, String name, ImageView iv, String url) {
		this(context, name, iv, url, false);
	}

	public AsyncImageDownloader setThumbsize(int size) {
		thumbsize = size;
		return this;
	}

	public AsyncImageDownloader setFullSize(boolean size) {
		fullsize = size;
		return this;
	}

	public AsyncImageDownloader(ActivityDestroyObserver context, String name, ImageView iv, String url,
			boolean save) {
		_name = name;
		_url = url;
		_reference = new WeakReference<ImageView>(iv);
		_save = save;
		_context = context;

		context.registerOnDestroy(new ActivityEndAction() {
			public void execute(Context context) {
				destroy = true;
				if (bm != null) {
					bm.recycle();
				}
				_reference.clear();
				_reference = null;
				bm = null;
				_context = null;
				_name = null;

			}
		});
	}

	protected Drawable doInBackground(Void... params) {
		if (_reference != null && _reference.get() != null && !destroy) {

			String file = FileUtil.getFilePath("cache/image_download/" + _name);
			if (new File(file).exists()) {
				return decodeFileToDrawable(file);
			}

			URL url;
			try {
				url = new URL(_url);
				InputStream content = (InputStream) url.getContent();

				FileUtil.createFolders("cache/image_download/");
				FileUtil.saveTo(file, content);
				Drawable dw = decodeFileToDrawable(file);
				if (!_save) {
					new File(file).delete();
				}
				return dw;

			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}
		return null;
	}

	private Drawable decodeFileToDrawable(String f) {
		if (_context == null) {
			return null;
		}
		if (_context.getActivity() == null) {
			return null;
		}
		return new BitmapDrawable(_context.getActivity().getResources(), decodeFile(new File(f)));
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File is) {
		// Decode image size
		try {

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(is), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = this.fullsize ? 1024 : thumbsize;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			this.bm = BitmapFactory.decodeStream(new FileInputStream(is), null, o2);
			return this.bm;
		} catch (FileNotFoundException e) {
			LogUtil.e("File not found:" + is.getAbsolutePath(), e);
			return null;
		}
	}

	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		if (_reference != null && _reference.get() != null) {
			_reference.get().setImageDrawable(result);
		}
	}

}
