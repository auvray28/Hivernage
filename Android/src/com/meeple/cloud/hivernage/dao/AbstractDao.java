package com.meeple.cloud.hivernage.dao;

import java.lang.reflect.Field;

import android.content.ContentValues;
import android.database.Cursor;

import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.model.Entity;

@SuppressWarnings("unchecked")
public class AbstractDao<T extends Entity<T>> {

	private String tableName;
	private String entityType;
	
	public AbstractDao() {
		String g = getClass().getGenericSuperclass().toString();
		this.entityType = g.substring(g.indexOf('<') + 1, g.indexOf('>'));
		try {
			this.tableName = DbHelper.entityToTableName(Class.forName(entityType).getSimpleName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public T find(int id) {

		T res = null;
		try {
			res = (T) Class.forName(entityType).newInstance();
			String serial = "";
			String cols = res.getIdColumn() + " = ?";
			String[] values = { "" + id };

			/*
			Cursor c = DbHelper.getInstance().getReadableDatabase()
					.query(tableName, // The table to query
							null, // The columns to return
							cols, // The columns for the WHERE clause
							values, // The values for the WHERE clause
							null, // don't group the rows
							null, // don't filter by row groups
							null // The sort order
					);
			c.moveToFirst();

			for (int i = 0; i < c.getColumnCount(); i++) {
				serial += c.getColumnName(i) + ":" + c.getString(i);
			}

			res.parseFromString(serial);
			*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public void create(T entity) {
		try {
			ContentValues values = new ContentValues();
			
			for (Field f : entity.getClass().getDeclaredFields()) {
				String val;
				val = ""+entity.getClass().getMethod(DbHelper.fieldToGetter(f.getName())).invoke(entity);
				values.put(DbHelper.fieldToColName(f.getName()), val);
				System.out.println("key:"+DbHelper.fieldToColName(f.getName())+" val:"+val);
			}
//			DbHelper.getInstance().getWritableDatabase().insert(tableName, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void delete(T entity) {

		String cols = entity.getIdColumn()+" = ?";
		String[] values = {""+entity.getIdValue()};

//		DbHelper.getInstance().getWritableDatabase().delete(tableName, cols, values);
	}
	
	public void update(T entity) {
		try {
			ContentValues values = new ContentValues();

			for (Field f : entity.getClass().getDeclaredFields()) {
				String val;
				val = ""+entity.getClass().getMethod(DbHelper.fieldToGetter(f.getName())).invoke(entity);
				values.put(DbHelper.fieldToColName(f.getName()), val);
				System.out.println("key:"+DbHelper.fieldToColName(f.getName())+" val:"+val);
			}
			
			String cols = entity.getIdColumn()+" = ?";
			String[] id = {""+entity.getIdValue()};

//			DbHelper.getInstance().getWritableDatabase().update(tableName, values, cols, id);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
