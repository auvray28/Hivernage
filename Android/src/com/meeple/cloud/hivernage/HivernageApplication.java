package com.meeple.cloud.hivernage;


import android.app.Application;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.model.tools.SharedPreferenceHelper;

public class HivernageApplication extends Application {

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
