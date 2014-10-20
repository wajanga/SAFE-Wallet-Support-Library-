package com.setecs.mobile.safe.apps.shared;

import static com.setecs.mobile.safe.apps.shared.Constants.SAFEIPNO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.R;


public class ChangeSafePin extends Activity {

	HashMap<String, byte[]> hma = new HashMap<String, byte[]>();
	private EditText oldpin;
	private EditText newpin;
	private EditText cnewpin;
	private String oldpins;
	private String newpins;
	private String cnewpins;
	HashMap<String, String> hmap = new HashMap<String, String>();
	private AlertDialog warningDialog;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private ProgressDialog progressDialog;
	private String userMobNo = "";
	private String result = "";
	private Message handlermsg;
	protected SharedMethods cv = new SharedMethods();
	private String appName = "";
	protected static final int SAFEMSG = 1;
	private static final String TAG = "ChangeSafePin";
	protected static final int SERVERERROR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(ChangeSafePin.this));

		setContentView(R.layout.change_safe_pin);

		((Button) findViewById(R.id.btn_safepinchange)).setOnClickListener(changesafepinButtonListener);

		oldpin = (EditText) findViewById(R.id.safeoldpin);
		newpin = (EditText) findViewById(R.id.safenewpin);
		cnewpin = (EditText) findViewById(R.id.csafenewpin);

		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();// get the intent &
			// bundle passed by
			// X
			appName = bundle.getString("app_name");
		}

	}

	protected OnClickListener changesafepinButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_safepinchange) {
				changeWalletSafePin();
			}
		}
	};
	private String msg = "";

	protected void changeWalletSafePin() {
		// TODO Auto-generated method stub
		oldpins = oldpin.getText().toString();
		newpins = newpin.getText().toString();
		cnewpins = cnewpin.getText().toString();
		/*byte[] inputPINBytes = oldpins.getBytes();
		byte[] encryptedPIN = readPINFile(appName + "_safe_pin");
		byte[] decryptedPIN = cv.decrypt(encryptedPIN);
		// String pin = readConfFile("SAFE_PIN");
		if (decryptedPIN.length > 0 && Arrays.equals(inputPINBytes, decryptedPIN))
			if (newpins.length() > 0)
				if (newpins.equals(cnewpins)) {
					try {
						waitDialog();

					}
					catch (Exception e) {
						cv.closeSession();

						msg = e.getMessage();
						Log.e(TAG, "changeWalletSafePin", e);
						throw new RuntimeException(msg, e);
					}
				}
				else {
					PINWarning("PINs entered are not the same!");
				}
			else
				PINWarning("Wrong Current PIN!");*/
		waitDialog();

	}

	private void waitDialog() {
		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(this, "", "Please wait...");

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				//				// TODO Auto-generated method stub
				//				if (SharedMethods.socket == null || !SharedMethods.socket.isConnected()) {
				//					if (!cv.createSession()) {
				//						progressDialog.dismiss();
				//						handlermsg = new Message();
				//						handlermsg.what = SERVERERROR;
				//						handler.sendMessage(handlermsg);
				//					}
				//					else {
				//						saveSAFEPIN();
				//					}
				//				}
				//				else {
				//					saveSAFEPIN();
				//
				//				}
				saveSAFEPIN();

			}
		});
		t.start(); // Start it running
	}

	protected void saveSAFEPIN() {
		// TODO Auto-generated method stub

		//		if (SharedMethods.connected) {

		userMobNo = cv.readConfigurationFile(ChangeSafePin.this, appName + "_mob_no", appName + "_config_file");
		SharedMethods.serverIpAddress = cv
				.readConfigurationFile(ChangeSafePin.this, SAFEIPNO, appName + "_config_file");
		if (!SharedMethods.serverIpAddress.equals("")) {
			FutureTask<String> task = new FutureTask<String>(new ChangeSafePinThread(userMobNo,
					SharedMethods.serverIpAddress, oldpins, newpins));
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
				Log.e(TAG, "saveSAFEPIN", e);
				throw new RuntimeException(msg, e);
			}
			es.shutdown();

		}

		//		}

	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msag) {

			switch (msag.what) {

			case SAFEMSG:
				progressDialog.dismiss();
				if (result.contains("Invalid")) {
					displaySaveConfirmation("Invalid PIN");

				}
				else if (result.contains("SUCCESS")) {
					byte[] safe_pin = cv.encrypt(newpins);
					hma.put(appName + "_safe_pin", safe_pin);
					try {
						FileOutputStream fStream = openFileOutput(appName + "_safepin_file", Context.MODE_PRIVATE);
						ObjectOutputStream oStream = new ObjectOutputStream(fStream);
						oStream.writeObject(hma);
						oStream.flush();
						oStream.close();
						fStream.close();
						displaySaveConfirmation("New PIN saved successfully");
					}
					catch (FileNotFoundException e) {
						cv.closeSession();
						msg = e.getMessage();
						Log.e(TAG, "handler", e);
						throw new RuntimeException(msg, e);
					}
					catch (StreamCorruptedException e) {
						cv.closeSession();
						msg = e.getMessage();
						Log.e(TAG, "handler", e);
						throw new RuntimeException(msg, e);
					}
					catch (IOException e) {
						cv.closeSession();
						msg = e.getMessage();
						Log.e(TAG, "handler", e);
						throw new RuntimeException(msg, e);
					}
				}
				else {
					displaySaveConfirmation(result);
				}

				break;
			case SERVERERROR:
				Toast.makeText(ChangeSafePin.this, "Server not responding", Toast.LENGTH_LONG).show();
				break;

			}

		}
	};

	public byte[] readPINFile(String string) {
		// TODO Auto-generated method stub
		byte[] readByte = null;

		try {
			FileInputStream f = openFileInput(appName + "_safepin_file");
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, byte[]> readObj = (HashMap<String, byte[]>) s.readObject();

			Iterator<String> myVeryOwnIterator = readObj.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(string))
					readByte = readObj.get(key);
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			cv.closeSession();
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			cv.closeSession();
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			cv.closeSession();
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}

		return readByte;
	}

	private void PINWarning(String string) {
		// TODO Auto-generated method stub
		warningDialog = new AlertDialog.Builder(this).create();

		warningDialog.setTitle("Alert");

		warningDialog.setMessage(string);

		warningDialog.setIcon(R.drawable.warningicon32x32);
		warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				warningDialog.dismiss();
				oldpin.setText("");
				newpin.setText("");
				cnewpin.setText("");
			}
		});
		warningDialog.show();

	}

	public void displaySaveConfirmation(String result) {
		// TODO Auto-generated method stub
		ds.displaySAFErespons(ChangeSafePin.this, result, "Change SAFE PIN");
	}

} // end class
