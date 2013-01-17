package com.cx.islashcrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

/**
 * alert dialog tools
 * 
 * @author CX
 * 
 */
public class AlertDialogUtil {

	public interface EditDialogListener {
		public void onEditDown(String content);
	}

	public interface IAlertOnClickButtonListener {

		/**
		 * 点击确定
		 */
		void onPositiveButton();

		/**
		 * 点击取消
		 */
		void onNegativeButton();
	}

	public static void showEditDialog(Context con, String title, String message, final EditDialogListener m) {
		final EditText edit = new EditText(con);
		edit.setText(message);
		AlertDialog.Builder dialog = new AlertDialog.Builder(con);
		dialog.setTitle(title);
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setView(edit);
		dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				m.onEditDown(edit.getText().toString());
			}
		});
		dialog.show();
	}

	public static void openDialog(Context con, int message) {
		AlertDialog.Builder ad = new AlertDialog.Builder(con);
		ad.setTitle(android.R.string.dialog_alert_title);
		ad.setMessage(message);
		ad.setPositiveButton(android.R.string.ok, null);
		ad.show();
	}

	public static void openDialog(Context con, String dialogTitle, String dialogMessage) {
		AlertDialog.Builder ad = new AlertDialog.Builder(con);
		ad.setTitle(dialogTitle);
		ad.setMessage(dialogMessage);
		ad.setPositiveButton(android.R.string.ok, null);
		ad.show();
	}

	public static void openDialog(Context con, String dialogTitle, String dialogMessage,
			final IAlertOnClickButtonListener mIAlertOnClickButtonListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(con);
		ad.setTitle(dialogTitle);
		ad.setMessage(dialogMessage);
		ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mIAlertOnClickButtonListener!=null){
					mIAlertOnClickButtonListener.onPositiveButton();
				}
			}
		});

		ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mIAlertOnClickButtonListener!=null){
					mIAlertOnClickButtonListener.onNegativeButton();
				}
			}
		});
		ad.show();
	}
}
