package com.dristy.talkingkids;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.database.DatabaseConnector;
import com.dristy.talkingkids.database.DatabaseVariables;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEditProfileActivity extends DrawerActivity {
	EditText name, age;
	Button save;
	DatabaseConnector databaseConnector;
	OnClickListener onClickListener;
	Cursor cursor;
	Cursor cursorUpdate;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.replaceContentLayout(R.layout.create_profile,
				super.CONTENT_LAYOUT_ID);
		getActionBar().setTitle("Create Profile");
		// initializing
		name = (EditText) findViewById(R.id.name);
		age = (EditText) findViewById(R.id.age);
		save = (Button) findViewById(R.id.saveButton);
		databaseConnector = new DatabaseConnector(this);
		Bundle bundle = getIntent().getExtras();
		String string = "no";
		if (bundle != null) {
			string = bundle.getString("update");
		}
		if (string.equals("yes")) {
			save.setText("Update");
			String userName = getIntent().getExtras().getString("name");
			String sql = "SELECT * FROM " + DatabaseVariables.TABLE_PROFILE
					+ " WHERE " + DatabaseVariables.USER_NAME + " = '"
					+ userName + "'";
			cursorUpdate = databaseConnector.getCursor(sql);
			if (cursorUpdate.moveToFirst()) {
				name.setText(cursorUpdate.getString(cursorUpdate
						.getColumnIndex(DatabaseVariables.USER_NAME)));
				age.setText(String.valueOf(cursorUpdate.getInt(cursorUpdate
						.getColumnIndex(DatabaseVariables.USER_AGE))));
			}
			save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (name.getText().length() != 0
							&& age.getText().length() != 0
							&& isNumeric(age.getText().toString())) {
						String sql = "SELECT * FROM "
								+ DatabaseVariables.TABLE_PROFILE + " WHERE "
								+ DatabaseVariables.USER_NAME + " = " + "'"
								+ name.getText().toString().replace(" ", "") + "'";
						int rowNumber = databaseConnector.getRowCount(sql);
						if (rowNumber <= 0) {
							AsyncTask<Object, Object, Object> saveNoteAsyncTask = new AsyncTask<Object, Object, Object>() {
								@Override
								protected Object doInBackground(
										Object... params) {
									insertOrUpdate("update");
									return null;
								}

								@Override
								protected void onPostExecute(Object result) {
									// Start activity
									StateVariable.setCURRENT_STATE(StateVariable.STATE_VIEW_PROFILE);
									Intent intent = new Intent(
											CreateEditProfileActivity.this,
											MainActivity.class);
									startActivity(intent);
								}
							};
							saveNoteAsyncTask.execute((Object[]) null);
						} else {
							AlertDialog.Builder alert = new AlertDialog.Builder(
									CreateEditProfileActivity.this);
							alert.setTitle("Name Exits OR Age not valid");
							alert.setMessage("Please enter another name and correct age");
							alert.setPositiveButton("Okay", null);
							alert.show();
						}
					}
				}
			});
		} else {
			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (name.getText().length() != 0
							&& age.getText().length() != 0
							&& isNumeric(age.getText().toString())) {
						String sql = "SELECT * FROM "
								+ DatabaseVariables.TABLE_PROFILE + " WHERE "
								+ DatabaseVariables.USER_NAME + " = " + "'"
								+ name.getText().toString() + "'";
						int rowNumber = databaseConnector.getRowCount(sql);
						Toast.makeText(getApplicationContext(), rowNumber+"", Toast.LENGTH_SHORT).show();
						if (rowNumber <= 0) {
							AsyncTask<Object, Object, Object> saveNoteAsyncTask = new AsyncTask<Object, Object, Object>() {
								@Override
								protected Object doInBackground(
										Object... params) {
									insertOrUpdate("new");
									return null;
								}

								@Override
								protected void onPostExecute(Object result) {
									// Start activity
									StateVariable.setCURRENT_STATE(StateVariable.STATE_VIEW_PROFILE);
									Intent intent = new Intent(
											CreateEditProfileActivity.this,
											MainActivity.class);
									startActivity(intent);
								}
							};
							saveNoteAsyncTask.execute((Object[]) null);
						} else {
							AlertDialog.Builder alert = new AlertDialog.Builder(
									CreateEditProfileActivity.this);
							alert.setTitle("Name Exits OR Age not valid");
							alert.setMessage("Please enter another name and correct age");
							alert.setPositiveButton("Okay", null);
							alert.show();
						}
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(
								CreateEditProfileActivity.this);
						alert.setTitle("Name and Age is required");
						alert.setMessage("Please Enter Name and Age");
						alert.setPositiveButton("Okay", null);
						alert.show();
					}
				}
			});
		}
	}

	private void insertOrUpdate(String status) {
		// TODO Auto-generated method stub
		if (status.equals("new")) {
			databaseConnector.insertTableProfile(name.getText().toString(),
					Integer.valueOf(age.getText().toString()));
			String sql = "SELECT * FROM " + DatabaseVariables.TABLE_PROFILE
					+ " WHERE " + DatabaseVariables.USER_NAME + " = " + "'"
					+ name.getText().toString() + "'";
			cursor = databaseConnector.getCursor(sql);
			if (cursor.moveToFirst()) {
				int id = cursor.getInt(0);
				databaseConnector.insertTableGifts(id);
			}
			cursor.close();
		} else {
			databaseConnector.updateProfile(cursorUpdate.getInt(0), name
					.getText().toString(), Integer.valueOf(age.getText()
					.toString()));
			cursorUpdate.close();
		}

	}

	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
			System.out.print(d);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
