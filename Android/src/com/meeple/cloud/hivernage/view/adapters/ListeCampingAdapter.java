package com.meeple.cloud.hivernage.view.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment.OrderCampingsBy;


public class ListeCampingAdapter  extends BaseAdapter {	

	private Context ctx;
	private List<Camping> viewedListe = null;
	private ArrayList<Camping> dataListe;
	
	private OrderCampingsBy orderBy;
	
	public ListeCampingAdapter(Context ctx, ArrayList<Camping> list){
		this.ctx = ctx;
		this.viewedListe = list;
		
		dataListe = new ArrayList<Camping>();
		dataListe.addAll(viewedListe);
		
		reorderCampingsBy(OrderCampingsBy.ALPHABETIC);
	}	
	
	public ListeCampingAdapter(Context ctx, ArrayList<Camping> list, OrderCampingsBy newOrder){
		this.ctx = ctx;
		this.viewedListe = list;
		
		dataListe = new ArrayList<Camping>();
		dataListe.addAll(viewedListe);
		
		reorderCampingsBy(newOrder);
	}	
	
	@Override
	public int getCount() {
		return viewedListe.size();
	}

	@Override
	public Camping getItem(int arg0) {
		return viewedListe.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder{
		public TextView txt_name;
		public TextView txt_infoSup;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.liste_client_items_layout, null);
			
			holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			holder.txt_infoSup = (TextView) convertView.findViewById(R.id.txt_info);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_name.setText(getItem(position).getNom());
		
		switch (orderBy) {
		case PRIX_CROISSANT :
		case PRIX_DECROISSANT : 
		case ALPHABETIC:
		default :
			holder.txt_infoSup.setVisibility(View.VISIBLE);
			break;

		}
		
		holder.txt_infoSup.setText(""+getItem(position).getPrix());
		
		return convertView;
	}


	public void reorderCampingsBy(OrderCampingsBy new_orderBy){
		this.orderBy = new_orderBy;

		Collections.sort(viewedListe, orderBy.getComparator());
		
		notifyDataSetChanged();
	}
	
	
	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		viewedListe.clear();
		if (charText.length() == 0) {
			viewedListe.addAll(dataListe);
		} else {
			for (Camping c : dataListe) {
				if (c.getNom().toLowerCase(Locale.getDefault()).contains(charText) ) {
					viewedListe.add(c);
				}
			}
		}
		
		Collections.sort(viewedListe, orderBy.getComparator());
		
		notifyDataSetChanged();
	}
	
}
