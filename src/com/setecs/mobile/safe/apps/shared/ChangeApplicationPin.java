package com.setecs.mobile.safe.apps.shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.R;
import com.setecs.mobile.safe.apps.util.security.CryptoProviderClient;
import com.setecs.mobile.safe.apps.util.security.DigestProvider;


public class ChangeApplicationPin extends Activity {

	private static final String TAG = "ChangeApplicationPin";
	private EditText oldpin;
	private EditText newpin;
	private EditText cnewpin;
	private String oldpins;
	private String newpins;
	private String cnewpins;
	HashMap<String, byte[]> hma = new HashMap<String, byte[]>();
	HashMap<String, String> hmap = new HashMap<String, String>();
	private AlertDialog warningDialog;
	private DigestProvider digest;
	private byte[] keyBytes;
	private CryptoProviderClient crypto;
	private byte[] pinBytes;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private String appName = "";
	private StringBuilder app_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();
			appName = bundle.getString("app_name");
			app_name = new StringBuilder(appName.length());
			app_name.append(Character.toUpperCase(appName.charAt(0))).append(appName.substring(1));

		}
		setContentView(R.layout.change_wallet_pin);
		((TextView) findViewById(R.id.title)).setText("Change " + app_name + " PIN");

		((Button) findViewById(R.id.btn_changepin)).setOnClickListener(changepinButtonListener);
		((Button) findViewById(R.id.btn_changepin)).setText("Change " + app_name + " PIN");

		oldpin = (EditText) findViewById(R.id.oldpin);
		newpin = (EditText) findViewById(R.id.newpin);
		cnewpin = (EditText) findViewById(R.id.cnewpin);

		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();// get the intent &
			// bundle passed by
			// X
			appName = bundle.getString("app_name");
		}

	}

	protected OnClickListener changepinButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_changepin) {
				changeWalletPin();
			}

		}
	};
	private String msg = "";

	protected void changeWalletPin() {
		// TODO Auto-generated method stub
		oldpins = oldpin.getText().toString();
		newpins = newpin.getText().toString();
		cnewpins = cnewpin.getText().toString();

		byte[] inputPINBytes = oldpins.getBytes();
		byte[] encryptedPIN = readPINFile(appName + "_pin");
		byte[] decryptedPIN = decryptPIN(encryptedPIN, inputPINBytes);
		if (decryptedPIN.length > 0 && Arrays.equals(inputPINBytes, decryptedPIN))
			if (newpins.length() > 0)
				if (newpins.equals(cnewpins)) {
					try {
						byte[] pin = encryptPin(newpins);
						hma.put(appName + "_pin", pin);

						FileOutputStream fStream = openFileOutput(appName + "_pin_file", Context.MODE_PRIVATE);
						ObjectOutputStream oStream = new ObjectOutputStream(fStream);
						oStream.writeObject(hma);

						oStream.flush();
						oStream.close();
						fStream.close();
						displaySaveConfirmation("New PIN saved successfully");

					}
					catch (Exception e) {
						msg = e.getMessage();
						Log.e(TAG, "changeWalletPin", e);
						throw new RuntimeException(msg, e);
					}
				}
				else {
					PINWarning("PINs entered are not the same!");
				}
			else {
				PINWarning("Please enter your new PIN!");
			}
		else
			PINWarning("Wrong current PIN!");

	}

	protected byte[] encryptPin(String newPINcnfrmtxt) {
		// TODO Auto-generated method stub
		pinBytes = newPINcnfrmtxt.getBytes();
		digest = new DigestProvider();
		keyBytes = digest.pinDigest(pinBytes);
		SecretKeySpec theKey = new SecretKeySpec(keyBytes, "AES");
		crypto = new CryptoProviderClient();
		return crypto.AESEncrypt(pinBytes, theKey);
	}

	public byte[] readPINFile(String string) {
		// TODO Auto-generated method stub
		byte[] readByte = null;

		try {

			FileInputStream f = openFileInput(appName + "_pin_file");
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
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readPINFile", e);
			throw new RuntimeException(msg, e);
		}

		return readByte;
	}

	protected byte[] decryptPIN(byte[] pin, byte[] inputPINBytes) {
		// TODO Auto-generated method stub
		digest = new DigestProvider();
		keyBytes = digest.pinDigest(inputPINBytes);
		SecretKeySpec theKey = new SecretKeySpec(keyBytes, "AES");
		crypto = new CryptoProviderClient();
		pinBytes = crypto.AESDecrypt(pin, theKey);

		return pinBytes;
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

		ds.displaySAFErespons(ChangeApplicationPin.this, result, "Change " + app_name + " PIN");

	}

} // end class