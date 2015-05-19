package com.dristy.talkingkids.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dristy.talkingkids.fragments.AnimalFragment;
import com.dristy.talkingkids.fragments.BirdFragment;
import com.dristy.talkingkids.fragments.FlowerFragment;
import com.dristy.talkingkids.fragments.FoodFragment;
import com.dristy.talkingkids.fragments.FruitFragment;
import com.dristy.talkingkids.fragments.HumanBodyFragment;
import com.dristy.talkingkids.fragments.NatureFragment;
import com.dristy.talkingkids.fragments.StudyFragment;
import com.dristy.talkingkids.fragments.ToyFragment;
import com.dristy.talkingkids.fragments.VehicleFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 10;
	// Tab Titles
	private String tabtitles[] = new String[] { "Animal", "Bird", "Flower",
			"Food", "Fruit","Human Body","Nature","Study","Toy","Vehicle"};
	Context context;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			AnimalFragment animalFragment = new AnimalFragment();
			return animalFragment;
		case 1:
			BirdFragment birdFragment = new BirdFragment();
			return birdFragment;
		case 2:
			FlowerFragment flowerFragment =new FlowerFragment();
			return flowerFragment;
		case 3:
			FoodFragment foodFragment =new FoodFragment();
			return foodFragment;
		case 4:
			FruitFragment fruitFragment=new FruitFragment();
			return fruitFragment;
		case 5:
			HumanBodyFragment humanBodyFragment =new HumanBodyFragment();
			return humanBodyFragment;
		case 6:
			NatureFragment natureFragment =new NatureFragment();
			return natureFragment;
		case 7:
			StudyFragment studyFragment=new StudyFragment();
			return studyFragment;
		case 8:
			ToyFragment toyFragment=new ToyFragment();
			return toyFragment;
		case 9:
			VehicleFragment vehicleFragment=new VehicleFragment();
			return vehicleFragment;
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabtitles[position];
	}
}
