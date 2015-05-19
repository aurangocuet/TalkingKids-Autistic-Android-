package com.dristy.talkingkids.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.UserDictionary.Words;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.database.DatabaseVariables;

public class SingleProfileViewCursorAdapter extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public SingleProfileViewCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor c) {
		// TODO Auto-generated method stub
		TextView textViewObjectName = (TextView) arg0
				.findViewById(R.id.singleTextViewObjectName);
		TextView textViewObjectScore = (TextView) arg0
				.findViewById(R.id.singleTextViewObjectScore);
		int index1 = c.getColumnIndex(DatabaseVariables.OBJECT_NAME);
		String input=c.getString(index1);
		textViewObjectName.setText(input.substring(0, 1).toUpperCase() + input.substring(1));
		int index2 = c.getColumnIndex(DatabaseVariables.REPEATATION_TIME);
		textViewObjectScore.setText("Best Time : "+c.getString(index2));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
		View retView = inflater.inflate(R.layout.view_layout_list_view_singleitem, arg2,
				false);
		return retView;
	}
}
