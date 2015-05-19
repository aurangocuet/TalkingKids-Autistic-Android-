package com.dristy.talkingkids.fragments;

import java.util.ArrayList;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.PlayWithVoiceActivity;
import com.dristy.talkingkids.StateVariable;
import com.dristy.talkingkids.adapters.CheckListAdapter;
import com.dristy.talkingkids.beans.CheckList;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BirdFragment extends Fragment {
	ListView checkList;
	Button playButton;
	View view;
	CheckListAdapter adapter;
	ArrayList<CheckList> list;
	String[] objectName = { "Parrot", "Moyna", "Pegion", "Hen", "Duck" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater
				.inflate(R.layout.single_fragment_play, container, false);
		checkList = (ListView) view.findViewById(R.id.listcheck);
		playButton = (Button) view.findViewById(R.id.play);
		list = new ArrayList<CheckList>();
		for (int i = 0; i < objectName.length; i++) {
			list.add(new CheckList(objectName[i]));
		}
		adapter = new CheckListAdapter(getActivity(), list);
		checkList.setAdapter(adapter);
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(),
						StateVariable.allObjectList.toString(),
						Toast.LENGTH_LONG).show();
				if (StateVariable.allObjectList.size() <= 0) {
					Toast.makeText(getActivity(), "No Object Selected",
							Toast.LENGTH_SHORT).show();

				} else {
					Intent intent = new Intent(getActivity(),
							PlayWithVoiceActivity.class);
					intent.putStringArrayListExtra("objectName",StateVariable.allObjectList);
					intent.putExtra("objectClassId", 1);
					startActivity(intent);
				}
			}
		});
		return view;
	}

}
