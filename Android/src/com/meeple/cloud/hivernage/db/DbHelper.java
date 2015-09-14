package com.meeple.cloud.hivernage.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Entity;
import com.meeple.cloud.hivernage.model.Facture;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.model.Paiement;
import com.meeple.cloud.hivernage.model.Tranche;

@SuppressLint("DefaultLocale")
@SuppressWarnings("rawtypes")
public class DbHelper extends SQLiteOpenHelper {

	public final static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
	public final static ArrayList<Class<? extends Entity>> entityList = new ArrayList<Class<? extends Entity>>();
	public static String TAG = "POUET";
	static {
		map.put(boolean.class, Boolean.class);
		map.put(byte.class, Byte.class);
		map.put(short.class, Short.class);
		map.put(char.class, Character.class);
		map.put(int.class, Integer.class);
		map.put(long.class, Long.class);
		map.put(float.class, Float.class);
		map.put(double.class, Double.class);
		
		entityList.add(Camping.class);
		entityList.add(Caravane.class);
		entityList.add(Client.class);
		entityList.add(EmplacementCamping.class);
		entityList.add(EmplacementHangar.class);
		entityList.add(Facture.class);
		entityList.add(Gabarit.class);
		entityList.add(Hangar.class);
		entityList.add(Hivernage.class);
		entityList.add(Paiement.class);
		entityList.add(Tranche.class);
		
	}
	public static DbHelper instance;
	
	public DbHelper(Context context) {
		super(context, "hivernage.db", null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "call onCreate");
		
		for(Class<? extends Entity> c : entityList) {
			try {
				Entity<?> e = (Entity) c.newInstance();
				String createTable = e.createTable();
				Log.i(TAG, createTable);
				db.execSQL(createTable);
			} catch (Exception e) {
				Log.e(TAG,"create table fail : "+entityToTableName(c.getSimpleName()),e);
			} 
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG,"delete db first");
		for(Class<? extends Entity> c : entityList) {
			db.execSQL("drop table if exists " + entityToTableName(c.getSimpleName()));
		}
		onCreate(db);
	}
	
	public static String fieldToSetter(String f) {
		return "set" + f.substring(0, 1).toUpperCase() + f.substring(1);
	}

	public static String fieldToGetter(String f) {
		return "get" + f.substring(0, 1).toUpperCase() + f.substring(1);
	}

	public static String fieldToColName(String f) {
		String res = "";
		for (char c : f.toCharArray()) {
			if (Character.isLowerCase(c)) {
				res += Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				res += "_" + c;
			}
		}
		return res;
	}

	public static String colNameToField(String s) {
		String res = "";
		boolean needUpperCase = false;
		for (char c : s.toCharArray()) {
			if (c != '_') {
				if (!needUpperCase) {
					res += Character.toLowerCase(c);
				} else {
					res += c;
					needUpperCase = false;
				}
			} else {
				needUpperCase = true;
			}
		}
		return res;
	}
	
	public static String entityToTableName(String entityName) {
		return fieldToColName(entityName.substring(0,1).toLowerCase()+entityName.substring(1));
	}
	

	public void executeStatement(String sql) {
		getWritableDatabase().execSQL(sql);
	}
	
	public static String getColName(Field f) {
		String colName = null;
		if(f.getAnnotation(Column.class) != null) {
			colName = f.getAnnotation(Column.class).colName();
			if(colName.isEmpty()) {
				colName = fieldToColName(f.getName());
			}
		}
		
		return colName;
	}
	

	public static String getType(Field f) {
		Class<? extends Object> type = f.getType();
		String typeStr = null;
		if (type.isPrimitive()) {
			typeStr = map.get(type).getSimpleName();
		} else if (type.getSuperclass().getSimpleName().equals("Entity")) {
			typeStr = "Integer";
		}
		else if (type.getSimpleName().equals("ArrayList")) {
			
		}else if(type.getSimpleName().equals("String")) {
			typeStr = "TEXT";
		}
		
		else {
			typeStr = type.getSimpleName();
		}
		if(typeStr != null && f.getAnnotation(Id.class) != null) {
			typeStr+= " PRIMARY KEY";
		}
		return typeStr;
	}

	public static DbHelper getInstance() {
		return instance;
	}
	
	public static void sql(String sql) {
		instance.executeStatement(sql);
	}

	
	
}
