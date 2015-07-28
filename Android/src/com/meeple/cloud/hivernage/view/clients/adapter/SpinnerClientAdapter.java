package com.meeple.cloud.hivernage.view.clients.adapter;

import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.OrderClientBy;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

public class SpinnerClientAdapter implements SpinnerAdapter {

	public SpinnerClientAdapter(){
		
	}
	
	@Override
	public int getCount() {
		return OrderClientBy.values().length;
	}

	@Override
	public Object getItem(int position) {
		return OrderClientBy.values()[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		
		
		return null;
	}

}
