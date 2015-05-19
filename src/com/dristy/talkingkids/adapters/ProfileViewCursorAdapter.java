package com.dristy.talkingkids.adapters;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.database.DatabaseVariables;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileViewCursorAdapter extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public ProfileViewCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor c) {
		// TODO Auto-generated method stub
		TextView textViewUserName = (TextView) arg0
				.findViewById(R.id.singleProfile);
		int index1 = c.getColumnIndex(DatabaseVariables.USER_NAME);
		int index2 = c.getColumnIndex(DatabaseVariables.USER_SCORE);
		textViewUserName.setText(c.getString(index1)+" ( Score : "+c.getInt(index2)+" )");
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
		View retView = inflater.inflate(R.layout.profile_single_item, arg2,
				false);
		return retView;
	}
}
