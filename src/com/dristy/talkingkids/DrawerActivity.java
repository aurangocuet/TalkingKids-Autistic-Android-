package com.dristy.talkingkids;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.adapters.NavDrawerListAdapter;
import com.dristy.talkingkids.beans.NavDrawerItems;

public class DrawerActivity extends FragmentActivity implements ArrayListMaker {
	public static int  CONTENT_LAYOUT_ID;
	
	private ListView mDrawerList;
	private DrawerLayout mDrawer;
	private CustomActionBarDrawerToggle mDrawerToggle;
	
	ArrayList<NavDrawerItems> arrayList;
	NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.drawer_layout);
		
		CONTENT_LAYOUT_ID= R.id.content_frame;
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList =(ListView) findViewById(R.id.left_drawer);

		mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
		
		mDrawer.setDrawerListener(mDrawerToggle);
		
		arrayList = getArrayList();
		adapter =new NavDrawerListAdapter(this, arrayList);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> ArrayList<E> getArrayList() {
		// TODO Auto-generated method stub
		ArrayList<E> list=new ArrayList<E>();
		list.add((E) new NavDrawerItems("Create Profile",R.drawable.add));
		list.add((E) new NavDrawerItems("View Profile",R.drawable.show));
		list.add((E) new NavDrawerItems("Play",R.drawable.play));
		list.add((E) new NavDrawerItems("Update Profile",R.drawable.update));
		list.add((E) new NavDrawerItems("Delete Profile",R.drawable.delete));
		list.add((E) new NavDrawerItems("Exit",R.drawable.exit));
		return list;
	}
	
	protected void replaceContentLayout(int sourceId, int destinationId) {
	    View contentLayout = findViewById(destinationId);

	    ViewGroup parent = (ViewGroup) contentLayout.getParent();
	    int index = parent.indexOfChild(contentLayout);

	    parent.removeView(contentLayout);
	    contentLayout = getLayoutInflater().inflate(sourceId, parent, false);
	    parent.addView(contentLayout, index);
	}
	
	public void openDrawer(){
	    mDrawer.openDrawer(Gravity.START);
	}
	
	public void closeDrawer(){
	    mDrawer.closeDrawer(Gravity.START);
	}
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * The action bar home/up should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

		public CustomActionBarDrawerToggle(Activity mActivity,DrawerLayout mDrawerLayout) {
			super(mActivity, mDrawerLayout, R.drawable.talkingkids,
					R.string.hello_world, R.string.app_name);
		}

		public void onDrawerClosed(View view) {
			getActionBar().setTitle("Close");
			invalidateOptionsMenu();
		}

		public void onDrawerOpened(View drawerView) {
			getActionBar().setTitle("Open");
			invalidateOptionsMenu();
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			closeDrawer();
	        String title = arrayList.get(position).getTitle();
	        Toast.makeText(getApplicationContext(), position +" " +title, Toast.LENGTH_SHORT).show();
	        Intent intent;
	        switch (position) {
			case 0:
				intent=new Intent(DrawerActivity.this, CreateEditProfileActivity.class);
				StateVariable.setCURRENT_STATE(StateVariable.STATE_CREATE_PROFILE);
				startActivity(intent);
				break;
			case 1:
				intent=new Intent(DrawerActivity.this, MainActivity.class);
				StateVariable.setCURRENT_STATE(StateVariable.STATE_VIEW_PROFILE);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 2:
				intent=new Intent(DrawerActivity.this, MainActivity.class);
				StateVariable.setCURRENT_STATE(StateVariable.STATE_PLAY);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 3:
				intent=new Intent(DrawerActivity.this, MainActivity.class);
				StateVariable.setCURRENT_STATE(StateVariable.STATE_UPDATE_PROFILE);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case 4:
				intent=new Intent(DrawerActivity.this, MainActivity.class);
				StateVariable.setCURRENT_STATE(StateVariable.STATE_DLETE_PROFILE);
				startActivity(intent);
				break;
			case 5:
				break;
			default:
				break;
			}
		}
	}
}

interface ArrayListMaker{
	public <E> ArrayList<E> getArrayList();
}