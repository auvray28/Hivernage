package com.meeple.cloud.hivernage;


import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.model.tools.SharedPreferenceHelper;

import android.app.Application;

public class HivernageApplication extends Application {

	public static final String TAG_PREFS = "hivernage_prefs";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Initialise les singletons
		initMock();
	}
	
	protected void initMock() {
		DbHelper.instance = new DbHelper(getBaseContext(), new SharedPreferenceHelper(getBaseContext()));
		//DBMock.DB.fill();
		DbHelper.instance.load();
	}
	
}
