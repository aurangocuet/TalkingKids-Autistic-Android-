package com.dristy.talkingkids.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.StateVariable;
import com.dristy.talkingkids.beans.CheckList;
import com.dristy.talkingkids.database.DatabaseConnector;
import com.dristy.talkingkids.database.DatabaseVariables;

@SuppressLint("DefaultLocale")
public class CheckListAdapter extends BaseAdapter {
	ArrayList<CheckList> list;
	Context context;
	DatabaseConnector databaseConnector;

	public CheckListAdapter(Context context, ArrayList<CheckList> list) {
		this.context = context;
		this.list = list;
		databaseConnector = new DatabaseConnector(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.check_box_single_item,
					null);
		}

		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.textView1);

		checkBox.setText(list.get(position).getCheckItem());
		int check = databaseConnector.getRowCount("SELECT * FROM "
				+ DatabaseVariables.TABLE_COMPLETED_OBJECT + " WHERE "
				+ DatabaseVariables.OBJECT_NAME + " = '"
				+ list.get(position).getCheckItem().toLowerCase() + "'"
				+ " AND " + DatabaseVariables.USER_ID + " = " + "( SELECT "
				+ DatabaseVariables.USER_ID + " FROM "
				+ DatabaseVariables.TABLE_PROFILE + " WHERE "
				+ DatabaseVariables.USER_NAME + " = '"
				+ StateVariable.CURRENT_USER + "')");
		if (check == 1) {
			txtTitle.setText("Completed :)");
		}else{
			txtTitle.setText("");
		}
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					list.get(position).setChecked(true);
					StateVariable.allObjectList.add(list.get(position)
							.getCheckItem().toLowerCase());
				} else {
					list.get(position).setChecked(false);
					StateVariable.allObjectList.remove(list.get(position)
							.getCheckItem().toLowerCase());
				}
			}
		});
		return convertView;
	}
}
