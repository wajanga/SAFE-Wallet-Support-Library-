package com.setecs.mobile.safe.apps.shared;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.R;


public class AccountBalance extends Activity {

	private static final int SAFEMSG = 0;
	private static final int FETCHLIST = 1;
	private static final String TAG = "AccountBalance";
	private static final int SERVERERROR = 2;
	protected static final int NOMOBNO = 3;
	private String accountno;
	private String MobileNo;
	private String result;
	private Spinner spinner;
	private ArrayAdapter<CharSequence> adapter;
	private boolean chechAccoutnStatus = false;
	private Message handlermsg;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private final SharedMethods cv = new SharedMethods();
	private String appName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(AccountBalance.this));

		setContentView(R.layout.account_balance);
		((Button) findViewById(R.id.btn_checkstatus)).setOnClickListener(checkStatusButtonListener);

		spinner = (Spinner) findViewById(R.id.accbalancespinner);
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();// get the intent &
			// bundle passed by
			// X
			appName = bundle.getString("app_name");
		}
		fetchAccountList();

	}

	protected OnClickListener checkStatusButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_checkstatus) {
				chechAccoutnStatus = true;
				waitDialog();

			}

		}
	};
	private String msg = "";
	private String[] value;

	private void waitDialog() {
		cv.progressStart(AccountBalance.this, "Please wait...");

		//		if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
		//			if (!cv.createSession()) {
		//				handlermsg = new Message();
		//				handlermsg.what = SERVERERROR;
		//				handler.sendMessage(handlermsg);
		//			}
		//			else {
		//				checkAccountBalance();
		//			}
		//		}
		//		else {
		//			checkAccountBalance();
		//		}
		checkAccountBalance();

	}

	protected void checkAccountBalance() {
		// TODO Auto-generated method stub
		if (chechAccoutnStatus) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					chechAccoutnStatus();
				}
			});
			t.start(); // Start it running
		}

	}

	protected void fetchAccountList() {
		// TODO Auto-generated method stub
		value = new String[10];

		try {

			FileInputStream f = openFileInput(appName + "_account_list");
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String[]> readObject = (HashMap<String, String[]>) s.readObject();
			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(appName + "_account_no")) {
					value = readObject.get(key);
				}
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			cv.closeSession();

			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			cv.closeSession();

			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			cv.closeSession();

			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		accountno = value[0];
		handlermsg = new Message();
		handlermsg.what = FETCHLIST;
		handler.sendMessage(handlermsg);
	}

	protected void chechAccoutnStatus() {
		//		if (SharedMethods.connected) {

		MobileNo = cv.readConfigurationFile(AccountBalance.this, appName + "_mob_no", appName + "_config_file");
		SharedMethods.serverIpAddress = cv.readConfigurationFile(AccountBalance.this, SAFEIPNO,
				appName + "_config_file");

		if (!SharedMethods.serverIpAddress.equals("")) {
			FutureTask<String> task = new FutureTask<String>(new CheckAccontStatusThread(MobileNo,
					SharedMethods.serverIpAddress));
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.submit(task);
			try {
				result = task.get();
				handlermsg = new Message();
				handlermsg.what = SAFEMSG;
				handler.sendMessage(handlermsg);
			}
			catch (Exception e) {
				cv.closeSession();

				msg = e.getMessage();
				Log.e(TAG, "chechAccoutnStatus", e);
				throw new RuntimeException(msg, e);
			}
			es.shutdown();

		}

		//		}
		//		else {
		//			handlermsg = new Message();
		//			handlermsg.what = SERVERERROR;
		//			handler.sendMessage(handlermsg);
		//		}
	}

	private void displaySAFErespons(String result) {
		// TODO Auto-generated method stub
		ds.displaySAFErespons(AccountBalance.this, result, "SAFE Account Balance");
	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case FETCHLIST:
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.add(accountno);

				spinner.setAdapter(adapter);

				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
				break;
			case SAFEMSG:
				cv.progressStop();

				/*if (result.contains("SAFE Account") && result.contains("Balance")) {
					//					result = "SAFE Account:\n" + result.substring(result.indexOf("SAFE Account:") + 16,
					//							result.indexOf("SAFE Account:") + 27)
					//							+ "\nBalance:\n"
					//							+ result.substring(result.indexOf("Balance:") + 11);
					displaySAFErespons(result);

				}*/

				displaySAFErespons(AccountBalanceParser.parse(result));

				break;
			case SERVERERROR:
				cv.progressStop();
				Toast.makeText(AccountBalance.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;
			case NOMOBNO:
				cv.progressStop();
				Toast.makeText(AccountBalance.this, "No mobile number", Toast.LENGTH_LONG).show();
				break;

			}

		}
	};

}
