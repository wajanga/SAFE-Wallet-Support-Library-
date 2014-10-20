package com.setecs.mobile.safe.apps.shared;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.setecs.mobile.safe.apps.R;


public class SecurityOptions extends Activity {

	private static final String TAG = "SecurityOptions";
	HashMap<String, String> hmap = new HashMap<String, String>();
	private String optionsvalue;
	private final DisplaySAFEResponse ds = new DisplaySAFEResponse();
	private Spinner spinner;
	private String appName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.security_options);
		spinner = (Spinner) findViewById(R.id.spinner1);

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add("No Security");
		adapter.add("Integrity Only");
		adapter.add("Encryption Only");
		adapter.add("Fully Protected");

		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				optionsvalue = Integer.toString(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		((Button) findViewById(R.id.btn_saveoptions)).setOnClickListener(saveOptionsButtonListener);

		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();// get the intent &
			// bundle passed by
			// X
			appName = bundle.getString("app_name");
		}
	}

	protected OnClickListener saveOptionsButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.btn_saveoptions) {
				saveSecurityOptions();
			}
		}

	};
	private String msg = "";

	protected void saveSecurityOptions() {
		// TODO Auto-generated method stub
		String value = "";
		String FILENAME = appName + "_config_file";
		try {
			FileInputStream f = openFileInput(FILENAME);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();
			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				value = readObject.get(key);
				hmap.put(key, value);

			}

			hmap.put(appName + "_Sec_Option", optionsvalue);

			FileOutputStream fStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oStream = new ObjectOutputStream(fStream);
			oStream.writeObject(hmap);

			oStream.flush();
			oStream.close();
			fStream.close();
			ds.displaySAFErespons(SecurityOptions.this, "Security options successfully saved", "Security Options");
			// displaySaveConfirmation("Security options successfully saved");

		}
		catch (Exception e) {
			msg = e.getMessage();
			Log.e(TAG, "saveSecurityOptions", e);
			throw new RuntimeException(msg, e);
		}

	}

	public void displaySaveConfirmation(String result) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Security Options");
		alert.setMessage(result);

		alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});

		alert.show();
	}

}
