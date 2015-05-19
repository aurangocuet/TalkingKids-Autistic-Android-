package com.dristy.talkingkids;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dristy.talkingkids.adapters.SingleProfileViewCursorAdapter;
import com.dristy.talkingkids.database.DatabaseConnector;
import com.dristy.talkingkids.database.DatabaseVariables;

public class ViewActivity extends DrawerActivity {
	DatabaseConnector databaseConnector;
	SingleProfileViewCursorAdapter cursorAdapter;
	ListView completedObjectList;
	TextView name, score, giftGood, giftBetter, giftbest;
	String currentUserName;
	
	Button play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.replaceContentLayout(R.layout.view_layout, CONTENT_LAYOUT_ID);
		// initializing
		name = (TextView) findViewById(R.id.viewName);
		score = (TextView) findViewById(R.id.textScore);
		giftGood = (TextView) findViewById(R.id.textScoreGood);
		giftBetter = (TextView) findViewById(R.id.textScoreBetter);
		giftbest = (TextView) findViewById(R.id.textScoreBest);
		completedObjectList = (ListView) findViewById(R.id.listViewCompletedObjectList);
		play=(Button) findViewById(R.id.buttonFromViewPlay);
		databaseConnector = new DatabaseConnector(this);
		currentUserName = getIntent().getExtras().getString("name");

		// set Data
		String sql1 = "SELECT * FROM " + DatabaseVariables.TABLE_PROFILE
				+ " WHERE " + DatabaseVariables.USER_NAME + " = '" + currentUserName + "'";
		String sql2="SELECT * FROM "
				+ DatabaseVariables.TABLE_GIFTS + " WHERE "
				+ DatabaseVariables.USER_ID + " = " + "( SELECT "
				+ DatabaseVariables.USER_ID + " FROM "
				+ DatabaseVariables.TABLE_PROFILE + " WHERE "
				+ DatabaseVariables.USER_NAME + " = '" + currentUserName + "')";
		Cursor cursor = databaseConnector.getCursor(sql1);
		if(cursor.moveToFirst()){
		name.setText(cursor.getString(cursor.getColumnIndex(DatabaseVariables.USER_NAME)));
		score.setText(cursor.getInt(cursor.getColumnIndex(DatabaseVariables.USER_SCORE))+"");
		}
		cursor =databaseConnector.getCursor(sql2);
		if(cursor.moveToFirst()){
		giftbest.setText(cursor.getInt(cursor.getColumnIndex(DatabaseVariables.GIFT_BEST))+"");
		giftBetter.setText(cursor.getInt(cursor.getColumnIndex(DatabaseVariables.GIFT_BETTER))+"");
		giftGood.setText(cursor.getInt(cursor.getColumnIndex(DatabaseVariables.GIFT_GOOD))+"");
		}
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				StateVariable.allObjectList.clear();
				Intent intent = new Intent(ViewActivity.this,
						ViewPagerFragmentsActivity.class);
				String sql = "SELECT " + DatabaseVariables.USER_ID
						+ " FROM " + DatabaseVariables.TABLE_PROFILE
						+ " WHERE " + DatabaseVariables.USER_NAME
						+ " = '" + currentUserName + "'";
				Cursor cursor = databaseConnector.getCursor(sql);
				if (cursor.moveToFirst()) {
					StateVariable.CURRENT_USER_ID = cursor.getInt(cursor
							.getColumnIndex(DatabaseVariables.USER_ID));
				}
				startActivity(intent);
			}
		});
		new GetListData().execute((Object[]) null);
	}

	private class GetListData extends AsyncTask<Object, Object, Cursor> {
		@Override
		protected Cursor doInBackground(Object... params) {
			String sql = "SELECT * FROM "
			+ DatabaseVariables.TABLE_COMPLETED_OBJECT + " WHERE "
			+ DatabaseVariables.USER_ID + " = " + "( SELECT "
			+ DatabaseVariables.USER_ID + " FROM "
			+ DatabaseVariables.TABLE_PROFILE + " WHERE "
			+ DatabaseVariables.USER_NAME + " = '" + currentUserName + "')";
			return databaseConnector.getCursor(sql);
		}

		@Override
		protected void onPostExecute(Cursor result) {
			cursorAdapter = new SingleProfileViewCursorAdapter(
					ViewActivity.this, result);
			completedObjectList.setAdapter(cursorAdapter);
			databaseConnector.close();
		}
	}
}
