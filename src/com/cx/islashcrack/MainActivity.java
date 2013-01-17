package com.cx.islashcrack;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String targetFile = "/data/data/com.duellogames.islash/shared_prefs/com.duellogames.islash_preferences.xml";

	ArrayList<String> list = new ArrayList<String>();
	private final String seed = "03D031FE96060D842C7759E235F4CB47883C71EDD74A4C596298B2B07DC5C6A36E5D2D151BDCFAB1588F3843C134330AD8CEF49C0FEA2D38F84C500143837EB0D378D654999ABFC9FFD0D3FDA91EF4FE";
	File file;
	private String INIT_ITEM = "999";
	private String INIT_MONEY = "99999";
	private String EQUIPMENT_TYPE_TIME, EQUIPMENT_TYPE_BOMB, EQUIPMENT_TYPE_SKIP, SOMEWHERE_ONLY_WE_KNOW,
			EQUIPMENT_TYPE_POWERBLADE;

	private boolean isSuccess = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (runRootCommand("chmod 777 " + targetFile)) {
			try {
				EQUIPMENT_TYPE_BOMB = EQUIPMENT_TYPE_POWERBLADE = EQUIPMENT_TYPE_SKIP = EQUIPMENT_TYPE_TIME = PasswordCode
						.encrypt(seed, INIT_ITEM);
				SOMEWHERE_ONLY_WE_KNOW = PasswordCode.encrypt(seed, INIT_MONEY);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			showToast(R.string.error_no_root);
			finish();
		}
	}

	public void crack(View view) {
		file = new File(targetFile);
		if (file.exists()) {
			new CrackTask().execute();
		} else {
			showToast(R.string.error_no_file);
			// finish();
		}

	}

	class CrackTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			readFile(file);
			writeFile(file);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			AppProgressDialog.show(MainActivity.this, getString(android.R.string.dialog_alert_title),
					getString(R.string.crack_processing));
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list.clear();
			AppProgressDialog.cancel();
			if (isSuccess) {
				AlertDialogUtil.openDialog(MainActivity.this, R.string.crack_success);
			} else {
				AlertDialogUtil.openDialog(MainActivity.this, R.string.crack_fail);
			}

		}
	}

	private void readFile(File fileSource) {
		BufferedReader brSource;
		try {
			brSource = new BufferedReader(new FileReader(fileSource));
			String string = "";
			while (brSource.ready()) {
				string = brSource.readLine();
				if (string.contains("EQUIPMENT_TYPE_TIME")) {
					string = "<string name=\"EQUIPMENT_TYPE_TIME\">" + EQUIPMENT_TYPE_TIME + "</string>";
				} else if (string.contains("EQUIPMENT_TYPE_BOMB")) {
					string = "<string name=\"EQUIPMENT_TYPE_BOMB\">" + EQUIPMENT_TYPE_BOMB + "</string>";
				} else if (string.contains("EQUIPMENT_TYPE_SKIP")) {
					string = "<string name=\"EQUIPMENT_TYPE_SKIP\">" + EQUIPMENT_TYPE_SKIP + "</string>";
				} else if (string.contains("SOMEWHERE_ONLY_WE_KNOW")) {
					string = "<string name=\"SOMEWHERE_ONLY_WE_KNOW\">" + SOMEWHERE_ONLY_WE_KNOW + "</string>";
				} else if (string.contains("EQUIPMENT_TYPE_POWERBLADE")) {
					string = "<string name=\"EQUIPMENT_TYPE_POWERBLADE\">" + EQUIPMENT_TYPE_POWERBLADE + "</string>";
				}
				list.add(string);
			}
			brSource.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			isSuccess = false;
			e.printStackTrace();
		}
	}

	private void writeFile(File fileTarget) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(fileTarget, "UTF-8");
			// 建立一个输出流，把东西写入文件
			for (int i = 0; i < list.size(); i++) {
				String str = (String) list.get(i);
				// 从集合当中取出字符串
				pw.println(str);
				// 把该字符串写入文件当中
			}
			pw.close();
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}

	}

	private boolean runRootCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: " + e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				// nothing
			}
		}
		return true;
	}

	private void showToast(String message) {
		Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	private void showToast(int messageRes) {
		Toast.makeText(MainActivity.this, messageRes, Toast.LENGTH_SHORT).show();
	}

}
