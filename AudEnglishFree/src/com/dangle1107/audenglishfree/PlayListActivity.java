package com.dangle1107.audenglishfree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dangle1107.audenglishlibrary.Lesson;
import com.dangle1107.audenglishlibrary.LessonDataSource;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class PlayListActivity extends Activity {
	public final static String PLAYLIST_EXTRA_MESSAGE_LESSON_INDEX = "com.dangle.audplayer.PLAYLIST_EXTRA_MESSAGE_LESSON_INDEX";	
	private LessonDataSource _lessonDataSource;
	private List<Lesson> lessonList;
	private final static String LESSON_TITLE = "LESSON_TITLE";
	
	public final static long MAX_LESSON_ID = 11;
	
	private InterstitialAd interstitial;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		
		// Create the interstitial.
	    interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId("ca-app-pub-7967292479037053/9176042722");

	    // Create ad request.
	    AdRequest adRequest = new AdRequest.Builder().build();

	    // Begin loading your interstitial.
	    interstitial.loadAd(adRequest);
	    
		_lessonDataSource = new LessonDataSource(this);
		_lessonDataSource.open();
		lessonList = _lessonDataSource.getLessons(MAX_LESSON_ID);
		_lessonDataSource.close();
		
		ListView lvLesson = (ListView) findViewById(R.id.lvSong);			    

		// Adding menuItems to ListView
		ArrayList<HashMap<String, String>> lessonListData = new ArrayList<HashMap<String, String>>();
		for(int i=0;i<lessonList.size();i++)
		{
			HashMap<String, String> lesson = new HashMap<String, String>();
			String songName = lessonList.get(i).toString();
			lesson.put(LESSON_TITLE, songName);
			lessonListData.add(lesson);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, lessonListData,
				R.layout.playlist_item, new String[] { LESSON_TITLE }, new int[] {
						R.id.songTitle });

		lvLesson.setAdapter(adapter);
											    
	    lvLesson.setOnItemClickListener(new OnItemClickListener()
	    {
	        @Override
			public void onItemClick(AdapterView<?> parent, final View view,
			          int position, long id) {
	        	int lessonIndex = position;				
				Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
				intent.putExtra(PLAYLIST_EXTRA_MESSAGE_LESSON_INDEX, lessonIndex);
				setResult(100, intent);
				// Closing PlayListView
				finish();;
			}
	    });	    	
	}
	
	 // Invoke displayInterstitial() when you are ready to display an interstitial.
	  public void displayInterstitial() {
	    if (interstitial.isLoaded()) {
	      interstitial.show();
	    }
	  }
	  
	  @Override
	  public void onResume() {
	    super.onResume();
	    displayInterstitial();
	  }

	  @Override
	  public void onPause() {
		displayInterstitial();
	    super.onPause();
	  }
	  	 
}
