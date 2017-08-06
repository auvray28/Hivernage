package com.meeple.cloud.hivernage.model.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper
{
  public static final String CAMPING_PREF_NAME = "campings";
  public static final String CLIENT_PREF_NAME = "clients";
  public static final String GABARIS_PREF_NAME = "gabaris";
  public static final String HANGAR_PREF_NAME = "hangars";
  public static final String IDS_PREF_NAME = "ids";
  public static final String INIT_PREF_NAME = "init";
  public static final String SHARED_PREF_NAME = "hivernage";
  private SharedPreferences prefs;
  
  public SharedPreferenceHelper(Context paramContext)
  {
    this.prefs = paramContext.getSharedPreferences("hivernage", 0);
  }
  
  public String getCampings()
  {
    return this.prefs.getString("campings", "");
  }
  
  public String getClients()
  {
    return this.prefs.getString("clients", "");
  }
  
  public String getGabaris()
  {
    return this.prefs.getString("gabaris", "");
  }
  
  public String getHangars()
  {
    return this.prefs.getString("hangars", "");
  }
  
  public String getIds()
  {
    return this.prefs.getString("ids", "");
  }
  
  public boolean isInitialized()
  {
    return this.prefs.getBoolean("init", false);
  }
  
  public void saveCampings(String paramString)
  {
    this.prefs.edit().putString("campings", paramString).commit();
  }
  
  public void saveClients(String paramString)
  {
    this.prefs.edit().putString("clients", paramString).commit();
  }
  
  public void saveGabaris(String paramString)
  {
    this.prefs.edit().putString("gabaris", paramString).commit();
  }
  
  public void saveHangars(String paramString)
  {
    this.prefs.edit().putString("hangars", paramString).commit();
  }
  
  public void saveIds(String paramString)
  {
    this.prefs.edit().putString("ids", paramString).commit();
  }
  
  public void setInitialized()
  {
    this.prefs.edit().putBoolean("init", true).commit();
  }
  
  public void update() {}
}
