package com.dristy.talkingkids.beans;

public class NavDrawerItems {
		private String title;
		private int icon;

		public NavDrawerItems() {
		}

		public NavDrawerItems(String title, int icon) {
			this.title = title;
			this.icon = icon;
		}

		public NavDrawerItems(String title) {
			this.title = title;
		}

		public String getTitle() {
			return this.title;
		}

		public int getIcon() {
			return this.icon;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.getTitle();
		}
}
