package com.dristy.talkingkids;

import java.util.ArrayList;

import com.dristy.talkingkids.beans.User;

public class StateVariable {
	public static String CURRENT_STATE = "";
	public static String CURRENT_USER;
	public static int CURRENT_USER_ID=0;
	
	public static final String STATE_CREATE_PROFILE="create_profile";
	public static final String STATE_VIEW_PROFILE ="view_profile";
	public static final String STATE_PLAY="play";
	public static final String STATE_UPDATE_PROFILE="update_profile";
	public static final String STATE_DLETE_PROFILE="delete_profile";
	public static final String STATE_EXIT="exit";
	
	public static ArrayList<String> allObjectList=new ArrayList<String>();
	
	
	public static String getCURRENT_STATE() {
		return CURRENT_STATE;
	}
	public static void setCURRENT_STATE(String cURRENT_STATE) {
		CURRENT_STATE = cURRENT_STATE;
	}
	public static String getCURRENT_USER() {
		return CURRENT_USER;
	}
	public static void setCURRENT_USER(String cURRENT_USER) {
		CURRENT_USER = cURRENT_USER;
	}
}
