package com.meeple.cloud.hivernage;


import android.app.Application;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.db.DbHelper;

public class HivernageApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Initialise les singletons
		initMock();
	}
	
	protected void initMock() {
		DBMock.DB.fill();
		DbHelper.instance = new DbHelper(getApplicationContext());
	}
	
}
