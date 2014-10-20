package com.setecs.mobile.safe.apps.shared;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.setecs.mobile.safe.apps.R;


public class SafeSystemAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] names;

	static class ViewHolder {
		public TextView text;
	}

	public SafeSystemAdapter(Activity context, String[] names) {
		super(context, R.layout.safe_list_row, names);
		this.context = context;
		this.names = names;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.safe_list_row, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.safe_name);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		String s = names[position];
		holder.text.setText(s);
		Drawable img;
		if (s.equals(context.getString(R.string.test))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_sweden);
		}
		else if (s.equals(context.getString(R.string.setecs_demo_system))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_sweden);
		}
		else if (s.equals(context.getString(R.string.demo))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_sweden);
		}
		else if (s.equals(context.getString(R.string.usa))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_usa);
		}
		else if (s.equals(context.getString(R.string.setecs_usa_1))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_usa);
		}
		else if (s.equals(context.getString(R.string.setecs_usa_2))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_usa);
		}
		else if (s.equals(context.getString(R.string.setecs_usa_3))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_usa);
		}
		else if (s.equals(context.getString(R.string.peru))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_peru);
		}
		else if (s.equals(context.getString(R.string.mozambique))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_mozambique);
		}
		else if (s.equals(context.getString(R.string.uganda))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_uganda);
		}
		else if (s.equals(context.getString(R.string.setecs_canada))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_canada);
		}
		else if (s.equals(context.getString(R.string.setecs_italy))) {
			img = getContext().getResources().getDrawable(R.drawable.flag_italy);
		}
		else {
			img = getContext().getResources().getDrawable(R.drawable.flag_sweden);
		}
		img.setBounds(0, 0, 60, 60);
		holder.text.setCompoundDrawables(img, null, null, null);

		return rowView;
	}

}
