//MyList .java

package com.setecs.mobile.safe.apps.shared;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.R;


public class ShowCountryCodesList extends Activity {
	protected static final int SAFEMSG = 0;
	public static final String CONTRY_CODE = null;
	/** Called when the activity is first created. */
	ListView listView;
	protected Message handlermsg;
	protected String code = "";
	protected String[] continents;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country_code_list);
		listView = (ListView) findViewById(R.id.lv_country);
		listView.setAdapter(new EfficientAdapter(this));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				code = CountriesList.abbreviations[Integer.parseInt(listView.getItemAtPosition(position).toString())];
				Intent intent = new Intent();
				intent.putExtra(CONTRY_CODE, code);
				// Set result and finish this Activity
				ShowCountryCodesList.this.setResult(Activity.RESULT_OK, intent);
				finish();

			}
		});
	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return CountriesList.abbreviations.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.two_col_row, null);
				holder = new ViewHolder();
				holder.text1 = (TextView) convertView.findViewById(R.id.TextView01);
				holder.text2 = (TextView) convertView.findViewById(R.id.TextView02);

				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text1.setText(CountriesList.abbreviations[position]);
			holder.text2.setText(CountriesList.countries[position]);

			return convertView;
		}

		static class ViewHolder {
			TextView text1;
			TextView text2;
		}
	}

	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(CONTRY_CODE, code);
		// Set result and finish this Activity
		ShowCountryCodesList.this.setResult(Activity.RESULT_OK, intent);
		finish();
	}

}
