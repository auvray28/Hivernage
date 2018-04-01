package com.meeple.cloud.hivernage.view.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.meeple.cloud.hivernage.HivernageApplication;
import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.OrderClientBy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListeClientAdapter  extends BaseAdapter {	

	private Context ctx;
	private List<Client> viewedListe = null;
	private ArrayList<Client> dataListe;
	
	private OrderClientBy orderBy;
	private boolean showUEClient;
	
	private String lastFilter ="";;
	
	public ListeClientAdapter(Context ctx, ArrayList<Client> list){
		this.ctx = ctx;
		this.viewedListe = new ArrayList<Client>();
		
		dataListe = list;
		showUEClient = ctx.getSharedPreferences(HivernageApplication.TAG_PREFS, Activity.MODE_PRIVATE).getBoolean("showUEClient", false);
		orderBy = OrderClientBy.ALPHABETIC;
		
		filter("");
	}	
	
	public ListeClientAdapter(Context ctx, ArrayList<Client> list, OrderClientBy newOrder){
		this.ctx = ctx;
		this.viewedListe = new ArrayList<Client>();
		
		dataListe = list;
		showUEClient = ctx.getSharedPreferences(HivernageApplication.TAG_PREFS, Activity.MODE_PRIVATE).getBoolean("showUEClient", false);
		orderBy = newOrder;
		
		filter("");
	}	
	
	public boolean isShowOldClient() {
		return showUEClient;
	}

	public void setShowUEClient(boolean showUEClient) {
		this.showUEClient = showUEClient;
	}

	@Override
	public int getCount() {
		return viewedListe.size();
	}

	@Override
	public Client getItem(int arg0) {
		return viewedListe.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


	public List<Client> getViewedList() {
		return viewedListe;
	}
	

	public ArrayList<Client> getDataList() {
		return dataListe;
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
		
		holder.txt_name.setText(getItem(position).getFullName());
		
		switch (orderBy) {
		case CAMPING : 
			if (getItem(position).getCaravane().getCurrentCamping() != null) {
				holder.txt_infoSup.setText(getItem(position).getCaravane().getCurrentCamping().getNom());
			}
			else {
				holder.txt_infoSup.setText("No Camping");
			}
			holder.txt_infoSup.setVisibility(View.VISIBLE);
			break;
		case HANGAR : 
			if (getItem(position).getCaravane().getEmplacementHangar() != null) {
				holder.txt_infoSup.setText(getItem(position).getCaravane().getEmplacementHangar().getHangar().getNom());
			}
			else {
				holder.txt_infoSup.setText("No Hangar");
			}
			holder.txt_infoSup.setVisibility(View.VISIBLE);
			break;
		case RESTEDU :
			holder.txt_infoSup.setText(getItem(position).getCurrentAcompte() + "€" ); 
			holder.txt_infoSup.setVisibility(View.VISIBLE);
			break;
		case ALPHABETIC:
		default :
			holder.txt_infoSup.setVisibility(View.GONE);
			break;

		}
		
//		holder.txt_infoSup.setText(""+getItem(position).getNb_partie_played());
		
		return convertView;
	}


	public void reorderClientsBy(OrderClientBy new_orderBy){
		this.orderBy = new_orderBy;

		Collections.sort(viewedListe, orderBy.getComparator());
		
		notifyDataSetChanged();
	}
	
	
	// Filter Class
	public void filter(String charText) {
		lastFilter = charText.toLowerCase(Locale.getDefault());
		viewedListe.clear();
		if (lastFilter.length() == 0) {
			
			for (Client c : dataListe) {
				if ( !(!showUEClient && c.isEuropeenClient()) ) {
					viewedListe.add(c);
				}
			}
		} else {
			for (Client c : dataListe) {
				if( (c.getNom().toLowerCase(Locale.getDefault()).contains(lastFilter)
				  || c.getPrenom().toLowerCase(Locale.getDefault()).contains(lastFilter)
				  || c.getCaravane().getPlaque().toLowerCase(Locale.getDefault()).contains(lastFilter))
				  && !(!showUEClient && c.isEuropeenClient())) {
					viewedListe.add(c);
				}
			}
		}
		
		Collections.sort(viewedListe, orderBy.getComparator());
		
		notifyDataSetChanged();
	}
	
	
	public void doLastFilter() {
		filter(lastFilter);
	}
	
	
}
