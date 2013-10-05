package com.example.clocktime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ClockTime extends Activity {
	
	private static final String TAG = "ClockTime";
	
	int minFin;
	int hrFin;
	int minTil;
	boolean goodToLeave;
	boolean calc_log;
	public final static String MIN_FIN = "com.example.clocktime.minFin";
	public final static String HR_FIN = "com.example.clocktime.hrFin";
	public final static String MIN_TIL = "com.example.clocktime.minTil";
	public final static String GOOD_TO_LEAVE = "com.example.clocktime.goodToLeave";
	public final static String CALC_LOG = "com.example.clocktime.calcLog";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock_time);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clock_time, menu);
		return true;
	}
	
	public void calc_log_clock_time(View view)
	{
		Log.d(TAG, "calculating log time...??");
		Intent intent = new Intent(this, DisplayResults.class);
		calcTimes(true);
		intent.putExtra(MIN_FIN, minFin);
		intent.putExtra(HR_FIN, hrFin);
		intent.putExtra(MIN_TIL, minTil);
		intent.putExtra(GOOD_TO_LEAVE, goodToLeave);
		intent.putExtra(CALC_LOG, calc_log);
		startActivity(intent);
	}
	
	public void calc_act_clock_time(View view)
	{
		Log.d(TAG, "calculating actual time...?");
		Intent intent = new Intent(this, DisplayResults.class);
		calcTimes(false);
		intent.putExtra(MIN_FIN, minFin);
		intent.putExtra(HR_FIN, hrFin);
		intent.putExtra(CALC_LOG, calc_log);
		startActivity(intent);
	}
	
	private void calcTimes(boolean log)
	{
		Log.d(TAG, "inside of calcTimes");
		EditText hrInTemp = (EditText) findViewById(R.id.enter_clock_in_hr);
		EditText minInTemp = (EditText) findViewById(R.id.enter_clock_in_min);
		EditText hrOutTemp = (EditText) findViewById(R.id.enter_clock_out_hr);
		EditText minOutTemp = (EditText) findViewById(R.id.enter_clock_out_min);
		Log.d(TAG, "after extracting edittext info");
		int hrIn = Integer.parseInt(hrInTemp.getText().toString());
		int minIn = Integer.parseInt(minInTemp.getText().toString());
		int hrOut = Integer.parseInt(hrOutTemp.getText().toString());
		int minOut = Integer.parseInt(minOutTemp.getText().toString());
		Log.d(TAG, "conversion of editText to ints");
		
		// Adjustments made to hours to make calculations easier
		if(hrIn <= 6)
			hrIn += 12;
		if(hrOut <= 6)
			hrOut += 12;
		
		// Calculate the exact time in hours and minutes
		int hrTotal, minTemp, minExact;
		minTemp = minExact = minOut - minIn;
		hrTotal = hrOut - hrIn;	
		if(minTemp < 0)
		{
			minExact = 60 - Math.abs(minTemp);
			hrTotal--;
		}
		
		if(log)
		{
			// Calculate the actual time on log
			calc_log = true;
			int minTil15 = minExact % 15;
			if(minTil15 <= 7)
			{
				minExact -= minTil15;
				if(minTil15 == 0)
					goodToLeave = true;
				else
				{
					minTil = minTil15;
					goodToLeave = false;
				}
			}
			else
			{
				minExact += (15 - minTil15);
				if(minExact == 60)
				{
					minExact = 0;
					hrTotal++;
				}
				goodToLeave = true;
			}
		}
		else
			calc_log = false;
		
		// Set global variables
		minFin = minExact;
		hrFin = hrTotal;
	}

}
