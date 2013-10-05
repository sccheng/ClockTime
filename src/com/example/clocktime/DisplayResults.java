package com.example.clocktime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayResults extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Get the message from the intent
        Intent intent = getIntent();
        int hrs = intent.getIntExtra(ClockTime.HR_FIN, -1);
        int min = intent.getIntExtra(ClockTime.MIN_FIN, -1);
        int minTil15;
        boolean goodToLeave = intent.getBooleanExtra(ClockTime.GOOD_TO_LEAVE, true);
        boolean log = intent.getBooleanExtra(ClockTime.CALC_LOG, false);
        
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        if(!log)
        {
        	textView.setText(hrs + " hrs and " + min + " min");
        }
        else if(goodToLeave)
        {
        	textView.setText(hrs + " hrs and " + min + " min" + "\nIt's a good time to leave~");
        }
        else
        {
        	minTil15 = intent.getIntExtra(ClockTime.MIN_TIL, -1);
        	textView.setText(hrs + " hrs and " + min + " min" + "\nYou should wait " + (8 - minTil15) + " more minute(s)!");
        }
        setContentView(textView);        	
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
