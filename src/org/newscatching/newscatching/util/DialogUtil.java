package org.newscatching.newscatching.util;

import org.newscatching.newscatching.NewsPreference;
import org.newscatching.newscatching.activity.NewsActivity;
import org.newscatching.newscatching.observer.OnNicknameReady;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;

public class DialogUtil {

	public static LayoutInflater getInflater(Context context) {
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public static void askNicks(final Context context, final OnNicknameReady ready, final boolean ignoreExisting) {
		String nick = NewsPreference.getNickname(context);

		if (!ignoreExisting && nick != null) {
			ready.done(nick);
			return;
		}
		final EditText nickTextInput = new EditText(context);
		nickTextInput.setText(nick);
		new AlertDialog.Builder(context).setTitle("請設定暱稱").setIcon(android.R.drawable.ic_dialog_info)
				.setView(nickTextInput).setNegativeButton("確定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String nick = nickTextInput.getText().toString();

						if ("".equals(nick.trim())) {
							ViewUtils.alert(context, "請輸入訊息", "您送出的暱稱為空白，請重新輸入",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											askNicks(context, ready, ignoreExisting);
										}
									});
							return;
						}
						NewsPreference.saveNickname(context, nick);
						ready.done(nick);

					}
				}).show();
	}
}
