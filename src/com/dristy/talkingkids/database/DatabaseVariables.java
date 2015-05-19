package com.dristy.talkingkids.database;

public class DatabaseVariables {
	public static final int DATABASE_VERSION = 2;
	public static final String DB_NAME = "talkingkids";
	public static final String TABLE_PROFILE = "profile";
	public static final String TABLE_GIFTS = "gifts";
	public static final String TABLE_COMPLETED_OBJECT ="completed_gift";
	public static final String TABLE_OBJECT_CLASS = "object_class";
	
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "user_name";
	public static final String USER_AGE = "user_age";
	public static final String USER_SCORE = "user_score";
	
	public static final String GIFT_GOOD = "gift_good";
	public static final String GIFT_BETTER = "gift_better";
	public static final String GIFT_BEST = "gift_best";
	
	public static final String OBJECT_NAME = "object_name";
	public static final String REPEATATION_TIME = "repeatation_time";
	
	public static final String OBJECT_CLASS_ID = "class_id";
	public static final String OBJECT_CLASS_ID_STRING = "object_class_id";
	public static final String OBJECT_CLASS_NAME = "object_class_name";
	
	public static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "(" 
			+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
            + USER_NAME + " TEXT," 
			+ USER_AGE + " INTEGER," 
            + USER_SCORE + " INTEGER DEFAULT 100" 
			+ ")";
	
	public static final String CREATE_TABLE_GIFTS = "CREATE TABLE "
            + TABLE_GIFTS + "(" 
			+ USER_ID + " INTEGER," 
            + GIFT_GOOD + " INTEGER DEFAULT 0," 
			+ GIFT_BETTER + " INTEGER DEFAULT 0," 
            + GIFT_BEST + " INTEGER DEFAULT 0," 
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_PROFILE+" ("+USER_ID+") ON DELETE CASCADE)";
	
	public static final String CREATE_TABLE_OBJECT_CLASS = "CREATE TABLE "
            + TABLE_OBJECT_CLASS + "(" 
			+ OBJECT_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
            + OBJECT_CLASS_NAME + " TEXT" 
			+ ")";
	
	public static final String CREATE_TABLE_COMPLETED_OBJECTS = "CREATE TABLE "
            + TABLE_COMPLETED_OBJECT + "(" 
			+ USER_ID + " INTEGER," 
            + OBJECT_NAME + " TEXT," 
			+ OBJECT_CLASS_ID + " INTEGER," 
            + REPEATATION_TIME + " INTEGER DEFAULT 0," 
			+ " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_PROFILE+" ("+USER_ID+") ON DELETE CASCADE ,"
			+ " FOREIGN KEY ("+OBJECT_CLASS_ID+") REFERENCES "+TABLE_OBJECT_CLASS+" ("+OBJECT_CLASS_ID+"))";
}