package com.dristy.talkingkids.beans;

public class CheckList {
	String checkItem;
	String comUncom;
	boolean isChecked;
	public CheckList(String checkItem) {
		super();
		this.checkItem = checkItem;
		this.comUncom = "";
		//this.isChecked= false;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public String getComUncom() {
		return comUncom;
	}
	public void setComUncom(String comUncom) {
		this.comUncom = comUncom;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
