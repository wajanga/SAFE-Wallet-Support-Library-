package com.setecs.mobile.safe.apps.shared;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.setecs.mobile.safe.apps.R;


public class SecuritySettings extends Activity {

	private String appName = "";
	private StringBuilder result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();
			appName = bundle.getString("app_name");
			result = new StringBuilder(appName.length());
			result.append(Character.toUpperCase(appName.charAt(0))).append(appName.substring(1));

		}
		setContentView(R.layout.security_settings);
		((Button) findViewById(R.id.change_app_PIN)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.change_app_PIN)).setText("Change " + result + " PIN");
		((Button) findViewById(R.id.change_SAFE_PIN)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.security_options)).setOnClickListener(mSecurityButtonListener);
		((Button) findViewById(R.id.req_certificate)).setOnClickListener(mSecurityButtonListener);

	}

	protected OnClickListener mSecurityButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer buttonId = ((Button) v).getId();

			if (buttonId == R.id.change_app_PIN) {
				change_app_PIN();
			}
			if (buttonId == R.id.change_SAFE_PIN) {
				change_SAFE_PIN();
			}
			if (buttonId == R.id.security_options) {
				security_options();
			}
			if (buttonId == R.id.req_certificate) {
				req_certificate();
			}

		}
	};

	protected void change_SAFE_PIN() {
		Intent i = new Intent().setClass(getApplicationContext(), ChangeSafePin.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", appName);
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);

	}

	protected void req_certificate() {
		Intent i = new Intent().setClass(getApplicationContext(), RequestCertificate.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", appName);
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	protected void security_options() {
		Intent i = new Intent().setClass(getApplicationContext(), SecurityOptions.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", appName);
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	protected void change_app_PIN() {
		Intent i = new Intent().setClass(getApplicationContext(), ChangeApplicationPin.class);
		Bundle bundle = new Bundle(); // bundle is like the letter
		bundle.putString("app_name", appName);
		i.putExtras(bundle);// actually it's bundle who carries the content u
		// wanna pass
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}
}
