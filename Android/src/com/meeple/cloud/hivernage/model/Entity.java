package com.meeple.cloud.hivernage.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.db.annotation.Id;

public abstract class Entity<T> {

	public static final String VALUE_SEPARATOR = ":";
	public static final String FIELD_SEPARATOR = ";";

	public String createTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE table " + DbHelper.entityToTableName(getClass().getSimpleName())).append(" (");
		int nbFieldsAdded = 0;
		for(Field f : getClass().getDeclaredFields()) {
			if(DbHelper.getType(f) != null) {
				if(DbHelper.getColName(f) != null)
				{
					if(nbFieldsAdded > 0) {
						sb.append(", ");
					}
					sb.append(DbHelper.getColName(f)).append(" ");
					sb.append(DbHelper.getType(f));
					nbFieldsAdded++;
				}
			}
		}
		sb.append(")");
		
//		System.out.println(sb);
		String sql = sb.toString();
		return sql;
	}
	
	public Integer getIdValue() {
		try {
			for(Field f : getClass().getDeclaredFields()) {
				if (f.getAnnotation(Id.class) != null) {
					return (Integer) getClass().getMethod(DbHelper.fieldToGetter(f.getName())).invoke(this);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getIdColumn() {
		for(Field f : getClass().getDeclaredFields()) {
			if (f.getAnnotation(Id.class) != null) {
				return DbHelper.fieldToColName((f.getName()));
			}
		}
		return null;
	}
	
	public void parseFromString(String content) {
//		for(String field : content.split(FIELD_SEPARATOR)) {
//			String fieldName = DbHelper.colNameToField(field.split(VALUE_SEPARATOR)[0]);
//			try {
//				Field f = getClass().getField(fieldName);
//				String type = DbHelper.getType(f);
//				if(type.equals(anObject))
//				String methodName = DbHelper.fieldToSetter(fieldName);
//				getClass().getMethod(methodName).invoke(this, field.split(VALUE_SEPARATOR)[1]);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			}
		}
	
}
