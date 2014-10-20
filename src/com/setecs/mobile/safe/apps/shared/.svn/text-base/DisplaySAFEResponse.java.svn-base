package com.setecs.mobile.safe.apps.shared;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.R;


@SuppressLint("Registered")
public class DisplaySAFEResponse extends Activity {

	private TextView msg_title;
	private TextView alert_message;
	private RelativeLayout Rlayout;
	private Button pos_button;
	HashMap<String, String> hmap = new HashMap<String, String>();
	HashMap<String, String> hm = new HashMap<String, String>();
	HashMap<String, String> hmh = new HashMap<String, String>();
	HashMap<String, byte[]> hmapp = new HashMap<String, byte[]>();
	private AlertDialog warningDialog;

	public void displaySAFErespons(final Activity activity, String result, String msgtitle) {
		//		hideKeyboard(activity);
		if (result.length() > 0) {

			if (result.contains("SocketException")) {
				activity.setContentView(R.layout.system_msg);
				msg_title = (TextView) activity.findViewById(R.id.title);
				msg_title.setText(msgtitle);
				alert_message = (TextView) activity.findViewById(R.id.message);
				alert_message.setText("Connection to SAFE Server could not be established!" + "\n"
						+ "Please check your network or contact Server Administrator.");
				alert_message.setVisibility(View.VISIBLE);
				Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
				Rlayout.setVisibility(View.VISIBLE);
				pos_button = (Button) activity.findViewById(R.id.btnSingle);
				pos_button.setText("	OK	");
				pos_button.setVisibility(View.VISIBLE);
				pos_button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedMethods.login = false;
						activity.finish();
					}
				});

			}
			else {
				activity.setContentView(R.layout.system_msg);
				msg_title = (TextView) activity.findViewById(R.id.title);
				msg_title.setText(msgtitle);
				alert_message = (TextView) activity.findViewById(R.id.message);
				alert_message.setText(result);
				alert_message.setVisibility(View.VISIBLE);
				Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
				Rlayout.setVisibility(View.VISIBLE);
				pos_button = (Button) activity.findViewById(R.id.btnSingle);
				pos_button.setText("	OK	");
				pos_button.setVisibility(View.VISIBLE);
				pos_button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedMethods.login = false;
						activity.finish();
					}
				});
			}
		}

		else {
			activity.setContentView(R.layout.system_msg);
			msg_title = (TextView) activity.findViewById(R.id.title);
			msg_title.setText(msgtitle);
			alert_message = (TextView) activity.findViewById(R.id.message);
			alert_message.setText("No answer from SAFE server");
			alert_message.setVisibility(View.VISIBLE);
			Rlayout = (RelativeLayout) activity.findViewById(R.id.SingleLayout);
			Rlayout.setVisibility(View.VISIBLE);
			pos_button = (Button) activity.findViewById(R.id.btnSingle);
			pos_button.setText("	OK	");
			pos_button.setVisibility(View.VISIBLE);
			pos_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SharedMethods.login = false;

					activity.finish();
				}
			});
		}
	}

	public void displaySAFEresponse(final Activity src_activity, String result, String msgtitle) {
		src_activity.setContentView(R.layout.system_msg);
		msg_title = (TextView) src_activity.findViewById(R.id.title);
		msg_title.setText(msgtitle);
		alert_message = (TextView) src_activity.findViewById(R.id.message);
		if (result.length() > 0) {
			if (result.contains("SocketException")) {
				alert_message.setText("Connection to SAFE Server could not be established!" + "\n"
						+ "Please check your network or contact Server Administrator.");
			}
			else {
				alert_message.setText(result);

			}

		}
		else
			alert_message.setText("No answer from SAFE server");
		alert_message.setVisibility(View.VISIBLE);
		Rlayout = (RelativeLayout) src_activity.findViewById(R.id.SingleLayout);
		Rlayout.setVisibility(View.VISIBLE);
		pos_button = (Button) src_activity.findViewById(R.id.btnSingle);
		pos_button.setText("	OK	");
		pos_button.setVisibility(View.VISIBLE);
		pos_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				src_activity.finish();
			}
		});
	}

	public void noMobileNoWarning(final Activity activity) {
		// TODO Auto-generated method stub
		warningDialog = new AlertDialog.Builder(activity).create();

		warningDialog.setTitle("Alert");

		warningDialog.setMessage("No mobile number found!");

		warningDialog.setIcon(R.drawable.warningicon32x32);
		warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		warningDialog.show();
	}

	public void blankFieldsWarning(final Activity activity) {
		// TODO Auto-generated method stub

		warningDialog = new AlertDialog.Builder(activity).create();

		warningDialog.setTitle("Alert");

		warningDialog.setMessage("Please fill out empty fields!");

		warningDialog.setIcon(R.drawable.warningicon32x32);
		warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		warningDialog.show();
	}

	protected void hideKeyboard(final Activity activity) {
		// TODO Auto-generated method stub
		InputMethodManager inputManager = (InputMethodManager) activity.getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
