package com.setecs.mobile.safe.apps.shared;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.setecs.mobile.safe.apps.R;


public class ShowSafeSystemList extends ListActivity {

	public static final String SAFE_CHOICE = null;
	/** Called when the activity is first created. */
	ListView listView;
	protected String choice = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safe_system_list);

		String[] safeSystems = getResources().getStringArray(R.array.safe_systems2);

		setListAdapter(new SafeSystemAdapter(this, safeSystems));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String choice = (String) getListAdapter().getItem(position);

		Intent intent = new Intent();
		intent.putExtra(SAFE_CHOICE, choice);
		// Set result and finish this Activity
		this.setResult(Activity.RESULT_OK, intent);
		finish();
	}

}
