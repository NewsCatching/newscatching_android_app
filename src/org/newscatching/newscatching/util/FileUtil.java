package org.newscatching.newscatching.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.newscatching.newscatching.NewsConstant;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class FileUtil {

	public static void createFolders(String relatedPath) {
		File datafolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
				+ NewsConstant.PACKAGE_NAME + "/files/" + relatedPath);
		if (!datafolder.exists()) {
			datafolder.mkdirs();
		}
	}

	public static boolean deleteFolder(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteFolder(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	public static boolean saveTo(String path, InputStream input) {

		FileOutputStream result;
		try {
			result = new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			LogUtil.e("saveTo not found", e1);
			return false;
		}
		try {
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;

			while ((len = input.read(buf)) > 0) {
				result.write(buf, 0, len);
			}

			input.close();
			result.close();
			return true;
		} catch (IOException e) {
			LogUtil.e("saveTo", e);
		}
		return false;
	}

	public static String readFileToBase64(String filePath) {
		InputStream input;
		try {
			input = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			return null;
		}

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			// Copy the bits from instream to outstream
			byte[] buf = new byte[3096];
			int len;

			while ((len = input.read(buf)) > 0) {
				result.write(buf, 0, len);
			}

			input.close();
			result.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b = result.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static String getFilePath(String relatedPath) {
		File datafolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
				+ NewsConstant.PACKAGE_NAME + "/files/");
		if (!datafolder.exists()) {
			datafolder.mkdirs();
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
				+ NewsConstant.PACKAGE_NAME + "/files/" + relatedPath;
	}
}
