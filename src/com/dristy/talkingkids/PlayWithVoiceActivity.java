package com.dristy.talkingkids;

import java.util.ArrayList;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.database.DatabaseConnector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayWithVoiceActivity extends DrawerActivity implements OnClickListener {
	private static final int VR_REQUEST = 999;
	TextView curretObject;
	TextView counter;
	ImageView image;
	Button speakButton;
	ArrayList<String> objectName;
	int controler = 0;
	int objectSize = 1;
	int count = 0;
	int objectClassId;
	DatabaseConnector databaseConnector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.replaceContentLayout(R.layout.play_voice,CONTENT_LAYOUT_ID);
		databaseConnector = new DatabaseConnector(this);
		curretObject = (TextView) findViewById(R.id.currentObject);
		counter = (TextView) findViewById(R.id.counter);
		image = (ImageView) findViewById(R.id.currentObjectPhoto);
		speakButton = (Button) findViewById(R.id.speakButton);
		objectName = new ArrayList<String>();
		objectName = getIntent().getExtras().getStringArrayList("objectName");
		objectClassId = getIntent().getExtras().getInt("objectClassId");
		Toast.makeText(getApplicationContext(), objectName.toString(),
				Toast.LENGTH_SHORT).show();
		objectSize = objectName.size();
		int id = getResources().getIdentifier(
				getPackageName() + ":drawable/" + objectName.get(controler),
				null, null);
		image.setImageResource(id);
		curretObject.setText(objectName.get(controler));
		counter.setText(String.valueOf(count));
		speakButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.speakButton) {
			listenToSpeech();
		}
	}

	private void listenToSpeech() {

		// start the speech recognition intent passing required data
		Intent listenIntent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// indicate package
		listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				getClass().getPackage().getName());
		// message to display while listening
		listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");
		// set speech model
		listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// specify number of results to retrieve
		listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
		// start listening
		startActivityForResult(listenIntent, VR_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VR_REQUEST && resultCode == RESULT_OK) {
			ArrayList<String> suggestedWords = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			Toast.makeText(getApplicationContext(), suggestedWords.toString(),
					Toast.LENGTH_SHORT).show();
			if (suggestedWords.size() >= 1) {
				if (suggestedWords.contains(objectName.get(controler))) {
					if (controler < objectSize) {
						Toast.makeText(getApplicationContext(), "while",
								Toast.LENGTH_SHORT).show();
						if (count <= 1) {
							databaseConnector
									.updateGiftBest(StateVariable.CURRENT_USER_ID);
						} else if (count == 2 || count == 3) {
							databaseConnector
									.updateGiftBetter(StateVariable.CURRENT_USER_ID);
						} else if (count == 4 || count == 5) {
							databaseConnector
									.updateGiftGood(StateVariable.CURRENT_USER_ID);
						}
						databaseConnector.updateScore(
								StateVariable.CURRENT_USER_ID, count,objectName.get(controler));
						databaseConnector
								.insertTableCompletedObject(
										StateVariable.CURRENT_USER_ID,
										objectName.get(controler),
										objectClassId, count);
						count = 0;
						controler++;
						if (controler < objectSize) {

							curretObject.setText(objectName.get(controler));
							counter.setText(String.valueOf(count));
							int id = getResources().getIdentifier(
									getPackageName() + ":drawable/"
											+ objectName.get(controler), null,
									null);
							image.setImageResource(id);
						}
					}
					if (controler == objectSize) {
						StateVariable.allObjectList.clear();
						Intent intent = new Intent(PlayWithVoiceActivity.this,
								ViewPagerFragmentsActivity.class);
						startActivity(intent);
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Not Correct,try Again..", Toast.LENGTH_SHORT)
							.show();
					count++;
					curretObject.setText(objectName.get(controler));
					counter.setText(String.valueOf(count));
				}
			}
		} else {
			Toast.makeText(getApplicationContext(), "Not Working :(",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(PlayWithVoiceActivity.this,
					ViewPagerFragmentsActivity.class);
			startActivity(intent);
		}
	}
}