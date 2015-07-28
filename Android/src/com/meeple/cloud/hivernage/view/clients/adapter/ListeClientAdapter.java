package com.meeple.cloud.hivernage.view.clients.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.OrderClientBy;


public class ListeClientAdapter  extends BaseAdapter {	

	private Context ctx;
	private ArrayList<Client> liste;
	
	private OrderClientBy orderBy;
	
	public ListeClientAdapter(Context ctx, ArrayList<Client> list){
		this.ctx = ctx;
		this.liste = list;
	}	
	
	@Override
	public int getCount() {
		return liste.size();
	}

	@Override
	public Client getItem(int arg0) {
		return liste.get(arg0);
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
		case CAMPING : 
		case HANGAR : 
		case RESTEDU :
			holder.txt_infoSup.setText("Reste : " + (Math.random()*500) + "€" ) ;
		case ALPHABETIC:
		default :
			holder.txt_infoSup.setVisibility(View.GONE);
			break;

		}
		
//		holder.txt_infoSup.setText(""+getItem(position).getNb_partie_played());
		
		return convertView;
	}


	public void reorderClientsBy(OrderClientBy orderBy){
		this.orderBy = orderBy;
		notifyDataSetChanged();
	}
	
}
