package com.dristy.talkingkids.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector {
	private SQLiteDatabase database;
	private DatabaseHelper dbOpenHelper;

	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseHelper(context, DatabaseVariables.DB_NAME,
				null, DatabaseVariables.DATABASE_VERSION);
	}

	// Open Database function
	public void open() throws SQLException {
		// Allow database to be in writable mode
		database = dbOpenHelper.getWritableDatabase();
	}

	// Close Database function
	public void close() {
		if (database != null)
			database.close();
	}

	public void executeSQL(String sql) {
		open();
		database.execSQL(sql);
		close();
	}

	// Create Database function
	public void insertTableProfile(String name, int age) {
		ContentValues newCon = new ContentValues();
		newCon.put(DatabaseVariables.USER_NAME, name);
		newCon.put(DatabaseVariables.USER_AGE, age);
		open();
		database.insert(DatabaseVariables.TABLE_PROFILE, null, newCon);
		close();
	}

	public void insertTableGifts(int id) {
		ContentValues newCon = new ContentValues();
		newCon.put(DatabaseVariables.USER_ID, id);
		open();
		database.insert(DatabaseVariables.TABLE_GIFTS, null, newCon);
		close();
	}

	public void insertTableCompletedObject(int id, String objectName,
			int objectClassId, int repetationTime) {
		open();
		String sql = "SELECT * FROM "
				+ DatabaseVariables.TABLE_COMPLETED_OBJECT + " WHERE "
				+ DatabaseVariables.USER_ID + " = " + id + " AND "
				+ DatabaseVariables.OBJECT_NAME + " = '" + objectName + "'";
		int rowCount = getRowCount(sql);
		if (rowCount <= 0) {
			ContentValues newCon = new ContentValues();
			newCon.put(DatabaseVariables.USER_ID, id);
			newCon.put(DatabaseVariables.OBJECT_NAME, objectName);
			newCon.put(DatabaseVariables.OBJECT_CLASS_ID, objectClassId);
			newCon.put(DatabaseVariables.REPEATATION_TIME, repetationTime);

			database.insert(DatabaseVariables.TABLE_COMPLETED_OBJECT, null,
					newCon);
			close();
		} else {
			int updateRepTime;
			Cursor cursor = getCursor(sql);
			if (cursor.moveToFirst()) {
				updateRepTime = cursor.getInt(cursor
						.getColumnIndex(DatabaseVariables.REPEATATION_TIME));
				if (updateRepTime < repetationTime) {
					sql = "UPDATE " + DatabaseVariables.TABLE_COMPLETED_OBJECT
							+ " SET " + DatabaseVariables.REPEATATION_TIME
							+ " = " + updateRepTime + " WHERE "
							+ DatabaseVariables.USER_ID + " = " + id + " AND "
							+ DatabaseVariables.OBJECT_NAME + " = '"
							+ objectName + "'";
					database.execSQL(sql);
					close();
				}
			}
		}
	}

	// Update Database function
	public void updateProfile(int id, String name, int age) {
		ContentValues editCon = new ContentValues();
		editCon.put(DatabaseVariables.USER_NAME, name);
		editCon.put(DatabaseVariables.USER_AGE, age);
		open();
		database.update(DatabaseVariables.TABLE_PROFILE, editCon,
				DatabaseVariables.USER_ID + "=" + id, null);
		close();
	}

	public void updateGiftGood(int id) {
		String sql = "UPDATE " + DatabaseVariables.TABLE_GIFTS + " SET "
				+ DatabaseVariables.GIFT_GOOD + " = "
				+ DatabaseVariables.GIFT_GOOD + "+1" + " WHERE "
				+ DatabaseVariables.USER_ID + " = " + id;
		open();
		database.execSQL(sql);
		close();
	}

	public void updateGiftBetter(int id) {
		String sql = "UPDATE " + DatabaseVariables.TABLE_GIFTS + " SET "
				+ DatabaseVariables.GIFT_BETTER + " = "
				+ DatabaseVariables.GIFT_BETTER + "+1" + " WHERE "
				+ DatabaseVariables.USER_ID + " = " + id;
		open();
		database.execSQL(sql);
		close();
	}

	public void updateGiftBest(int id) {
		String sql = "UPDATE " + DatabaseVariables.TABLE_GIFTS + " SET "
				+ DatabaseVariables.GIFT_BEST + " = "
				+ DatabaseVariables.GIFT_BEST + " + 1" + " WHERE "
				+ DatabaseVariables.USER_ID + " = " + id;
		open();
		database.execSQL(sql);
		close();
	}

	public void updateScore(int id, int score, String objectName) {
		String sql = "SELECT * FROM "
				+ DatabaseVariables.TABLE_COMPLETED_OBJECT + " WHERE "
				+ DatabaseVariables.OBJECT_NAME + " = '" + objectName + "'";
		int checkPresence = getRowCount(sql);
		if (checkPresence <= 0) {
			int updateScore = 0;
			if (score <= 5) {
				updateScore = 5 - score;
				sql = "UPDATE " + DatabaseVariables.TABLE_PROFILE + " SET "
						+ DatabaseVariables.USER_SCORE + " = "
						+ DatabaseVariables.USER_SCORE + " + " + updateScore
						+ " WHERE " + DatabaseVariables.USER_ID + " = " + id;
				open();
				database.execSQL(sql);
				close();
			} else {
				updateScore = score - 5;
				sql = "UPDATE " + DatabaseVariables.TABLE_PROFILE
						+ " SET " + DatabaseVariables.USER_SCORE + " = "
						+ DatabaseVariables.USER_SCORE + " - " + updateScore
						+ " WHERE " + DatabaseVariables.USER_ID + " = " + id;
				open();
				database.execSQL(sql);
				close();
			}
		}
	}

	// Delete Database function
	public void deleteProfile(int id) {
		open();
		database.delete(DatabaseVariables.TABLE_PROFILE,
				DatabaseVariables.USER_ID + "=" + id, null);
		close();
	}

	public Cursor getCursor(String sql) {
		open();
		Cursor cursor = database.rawQuery(sql, null);
		return cursor;
	}

	public int getRowCount(String sql) {
		open();
		Cursor cursor = database.rawQuery(sql, null);
		int val = cursor.getCount();
		cursor.close();
		return val;
	}
}
