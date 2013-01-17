package com.cx.islashcrack;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Progress Dialog tools
 * 
 * @author CX
 */
public class AppProgressDialog {

	public static ProgressDialog pro;

	/**
	 * show ProgressBar Dialog of custiom title and message
	 * 
	 * @param con
	 * @param title
	 * @param messge
	 */
	public static void show(Context con, String title, String messge) {
		pro = new ProgressDialog(con);
		pro.setTitle(title);
		pro.setMessage(messge);
		pro.show();
	}

	/**
	 * cancel ProgressBar Dialog
	 */
	public static void cancel() {
		if (pro != null && pro.isShowing()) {
			pro.cancel();
		}
	}
}
