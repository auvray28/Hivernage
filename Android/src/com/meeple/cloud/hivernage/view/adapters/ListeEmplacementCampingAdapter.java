package com.meeple.cloud.hivernage.view.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.EmplacementCamping;


public class ListeEmplacementCampingAdapter  extends BaseAdapter {	

	private Context ctx;
	private List<EmplacementCamping> viewedListe = null;
	private ArrayList<EmplacementCamping> dataListe;
	
//	private OrderEmplacementCampingsBy orderBy;
	
	public ListeEmplacementCampingAdapter(Context ctx, ArrayList<EmplacementCamping> list){
		this.ctx = ctx;
		this.viewedListe = list;
		
		dataListe = new ArrayList<EmplacementCamping>();
		dataListe.addAll(viewedListe);
		
//		reorderEmplacementCampingsBy(OrderEmplacementCampingsBy.ALPHABETIC);
	}	
	
//	public ListeEmplacementCampingsAdapter(Context ctx, ArrayList<EmplacementCamping> list, OrderEmplacementCampingsBy newOrder){
//		this.ctx = ctx;
//		this.viewedListe = list;
//		
//		dataListe = new ArrayList<EmplacementCamping>();
//		dataListe.addAll(viewedListe);
//		
//		reorderEmplacementCampingsBy(newOrder);
//	}	
	
	@Override
	public int getCount() {
		return viewedListe.size();
	}

	@Override
	public EmplacementCamping getItem(int arg0) {
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
		
		holder.txt_name.setText(getItem(position).getCaravane().getClient().getFullName());
		
//		switch (orderBy) {
//		case PRIX_CROISSANT :
//		case PRIX_DECROISSANT : 
//		case ALPHABETIC:
//		default :
//			holder.txt_infoSup.setVisibility(View.VISIBLE);
//			break;
//
//		}
		
		holder.txt_infoSup.setText(""+getItem(position).getCaravane().getPlaque());
		
		return convertView;
	}


//	public void reorderEmplacementCampingsBy(OrderEmplacementCampingsBy new_orderBy){
//		this.orderBy = new_orderBy;
//
//		Collections.sort(viewedListe, orderBy.getComparator());
//		
//		notifyDataSetChanged();
//	}
	
}
