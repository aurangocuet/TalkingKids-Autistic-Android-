package com.dristy.talkingkids.beans;

import java.util.HashMap;

public class User {
	public int userId;
	public String userName;
	public int userAge;

	public int giftGood;
	public int giftBetter;
	public int giftBest;

	public HashMap<String, Integer> objectRepetationTime;

	public User(int userId, String userName, int userAge, int giftGood,
			int giftBetter, int giftBest,
			HashMap<String, Integer> objectRepetationTime) {
		this.userId = userId;
		this.userName = userName;
		this.userAge = userAge;
		this.giftGood = giftGood;
		this.giftBetter = giftBetter;
		this.giftBest = giftBest;
		this.objectRepetationTime = objectRepetationTime;
	}

	public User(int userId, String userName, int userAge) {
		this.userId = userId;
		this.userName = userName;
		this.userAge = userAge;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public int getGiftGood() {
		return giftGood;
	}

	public void setGiftGood(int giftGood) {
		this.giftGood = giftGood;
	}

	public int getGiftBetter() {
		return giftBetter;
	}

	public void setGiftBetter(int giftBetter) {
		this.giftBetter = giftBetter;
	}

	public int getGiftBest() {
		return giftBest;
	}

	public void setGiftBest(int giftBest) {
		this.giftBest = giftBest;
	}

	public HashMap<String, Integer> getObjectRepetationTime() {
		return objectRepetationTime;
	}

	public void setObjectRepetationTime(
			HashMap<String, Integer> objectRepetationTime) {
		this.objectRepetationTime = objectRepetationTime;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getUserName();
	}
}
