package com.dristy.talkingkids.database;

import com.dristy.talkingkids.R;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String MY_PREFERENCES = "my_pref";
	public static final String DB_CREATED = "db_created";
	String[] objectClass;
	Context context;
	SharedPreferences preferences;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DatabaseVariables.DB_NAME, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		preferences = context.getSharedPreferences(MY_PREFERENCES,
				Context.MODE_PRIVATE);
		String check = preferences.getString(DB_CREATED, "yes");
		if (check.equals("yes")) {
			db.execSQL(DatabaseVariables.CREATE_TABLE_PROFILE);
			db.execSQL(DatabaseVariables.CREATE_TABLE_GIFTS);
			db.execSQL(DatabaseVariables.CREATE_TABLE_OBJECT_CLASS);
			db.execSQL(DatabaseVariables.CREATE_TABLE_COMPLETED_OBJECTS);
			objectClass = context.getResources().getStringArray(
					R.array.object_class);
			for (int i = 0; i < objectClass.length; i++) {
				ContentValues conValue = new ContentValues();
				conValue.put(DatabaseVariables.OBJECT_CLASS_NAME,
						objectClass[i]);
				db.insert(DatabaseVariables.TABLE_OBJECT_CLASS, null,
						conValue);
			}
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(DB_CREATED, "no");
			editor.commit();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE " +DatabaseVariables.TABLE_OBJECT_CLASS);
		db.execSQL("DROP TABLE " +DatabaseVariables.TABLE_PROFILE);
		db.execSQL("DROP TABLE " +DatabaseVariables.TABLE_COMPLETED_OBJECT);
		db.execSQL("DROP TABLE " +DatabaseVariables.TABLE_GIFTS);
		
		onCreate(db);
	}
}
