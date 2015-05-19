package com.dristy.talkingkids;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.adapters.ProfileViewCursorAdapter;
import com.dristy.talkingkids.database.DatabaseConnector;
import com.dristy.talkingkids.database.DatabaseVariables;

public class MainActivity extends DrawerActivity {
	DatabaseConnector databaseConnector;
	ProfileViewCursorAdapter cursorAdapter;
	Button button_nav_open;
	ListView profileList;
	TextView head;
	String name;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		databaseConnector = new DatabaseConnector(this);
		int flag = databaseConnector.getRowCount("SELECT * FROM "
				+ DatabaseVariables.TABLE_PROFILE);
		if (flag <= 0) {
			super.replaceContentLayout(R.layout.no_profile,
					super.CONTENT_LAYOUT_ID);
			button_nav_open = (Button) findViewById(R.id.button1);
			button_nav_open.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					openDrawer();
				}
			});
		} else {
			super.replaceContentLayout(R.layout.profiles,
					super.CONTENT_LAYOUT_ID);
			profileList = (ListView) findViewById(R.id.profileList);
			head = (TextView) findViewById(R.id.head);
			if (StateVariable.CURRENT_STATE
					.equals(StateVariable.STATE_VIEW_PROFILE)
					|| StateVariable.CURRENT_STATE.equals("")) {
				head.setText("View Profile");
				Toast.makeText(getApplicationContext(), name,
						Toast.LENGTH_SHORT).show();
			} else if (StateVariable.CURRENT_STATE
					.equals(StateVariable.STATE_PLAY)) {
				head.setText("Play");
			} else if (StateVariable.CURRENT_STATE
					.equals(StateVariable.STATE_UPDATE_PROFILE)) {
				head.setText("Update Profile");
			} else if (StateVariable.CURRENT_STATE
					.equals(StateVariable.STATE_DLETE_PROFILE)) {
				head.setText("Delete Profile");
			}
			new GetProfiles().execute((Object[]) null);
			profileList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					// TODO Auto-generated method stub
					name = ((TextView) view.findViewById(R.id.singleProfile))
							.getText().toString();
					int d=name.indexOf(" ");
					name=name.substring(0,d);
					StateVariable.CURRENT_USER=name;
					if (StateVariable.CURRENT_STATE
							.equals(StateVariable.STATE_VIEW_PROFILE)
							|| StateVariable.CURRENT_STATE.equals("")) {
						Intent intent=new Intent(MainActivity.this,ViewActivity.class);
						intent.putExtra("name",name);
						startActivity(intent);
					} else if (StateVariable.CURRENT_STATE
							.equals(StateVariable.STATE_PLAY)) {
						StateVariable.allObjectList.clear();
						Intent intent = new Intent(MainActivity.this,
								ViewPagerFragmentsActivity.class);
						String sql = "SELECT " + DatabaseVariables.USER_ID
								+ " FROM " + DatabaseVariables.TABLE_PROFILE
								+ " WHERE " + DatabaseVariables.USER_NAME
								+ " = '" + name + "'";
						Cursor cursor = databaseConnector.getCursor(sql);
						if (cursor.moveToFirst()) {
							StateVariable.CURRENT_USER_ID = cursor.getInt(cursor
									.getColumnIndex(DatabaseVariables.USER_ID));
						}
						startActivity(intent);
					} else if (StateVariable.CURRENT_STATE
							.equals(StateVariable.STATE_UPDATE_PROFILE)) {
						Intent intent = new Intent(MainActivity.this,
								CreateEditProfileActivity.class);
						intent.putExtra("update", "yes");
						intent.putExtra("name", name);
						startActivity(intent);
					} else if (StateVariable.CURRENT_STATE
							.equals(StateVariable.STATE_DLETE_PROFILE)) {
						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case DialogInterface.BUTTON_POSITIVE:
									// Yes button clicked
									new DeleteProfiles()
											.execute((Object[]) null);
									break;

								case DialogInterface.BUTTON_NEGATIVE:
									// No button clicked
									break;
								}
							}
						};

						AlertDialog.Builder builder = new AlertDialog.Builder(
								MainActivity.this);
						builder.setMessage(
								"Are you sure? You want to delete!!!")
								.setPositiveButton("Yes", dialogClickListener)
								.setNegativeButton("No", dialogClickListener)
								.show();
					}
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class GetProfiles extends AsyncTask<Object, Object, Cursor> {
		@Override
		protected Cursor doInBackground(Object... params) {
			return databaseConnector.getCursor("SELECT * FROM "
					+ DatabaseVariables.TABLE_PROFILE);
		}

		@Override
		protected void onPostExecute(Cursor result) {
			cursorAdapter = new ProfileViewCursorAdapter(MainActivity.this,
					result);
			profileList.setAdapter(cursorAdapter);

			databaseConnector.close();
		}
	}

	private class DeleteProfiles extends AsyncTask<Object, Object, Cursor> {
		@Override
		protected Cursor doInBackground(Object... params) {
			String sql = "DELETE FROM " + DatabaseVariables.TABLE_PROFILE
					+ " WHERE " + DatabaseVariables.USER_ID + " = "
					+ "(SELECT " + DatabaseVariables.USER_ID + " FROM "
					+ DatabaseVariables.TABLE_PROFILE + " WHERE "
					+ DatabaseVariables.USER_NAME + " = '" + name + "')";
			databaseConnector.executeSQL(sql);
			return databaseConnector.getCursor("SELECT * FROM "
					+ DatabaseVariables.TABLE_PROFILE);
		}

		@Override
		protected void onPostExecute(Cursor result) {
			cursorAdapter.changeCursor(null);
			cursorAdapter = new ProfileViewCursorAdapter(MainActivity.this,
					result);
			profileList.setAdapter(cursorAdapter);
			databaseConnector.close();
		}
	}
}
