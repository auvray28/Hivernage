package com.meeple.cloud.hivernage.view.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.object.MyCalendar;


public class ListeAgendaAdapter  extends BaseAdapter {	

	private Context ctx;
	private List<MyCalendar> viewedListe = null;
	private ArrayList<MyCalendar> dataListe;
	
	public ListeAgendaAdapter(Context ctx, ArrayList<MyCalendar> list){
		this.ctx = ctx;
		this.viewedListe = list;
		
		dataListe = new ArrayList<MyCalendar>();
		dataListe.addAll(viewedListe);
		
		Collections.sort(dataListe);
	}	
	
	@Override
	public int getCount() {
		return viewedListe.size();
	}

	@Override
	public MyCalendar getItem(int arg0) {
		return viewedListe.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder{
		public LinearLayout ll_StarDate;
		public LinearLayout ll_EndDate;
		
		public TextView txt_title;
		public TextView txt_camping;
		public TextView txt_startDate;
		public TextView txt_endDate;
		public TextView txt_startStr;
		public TextView txt_description;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.agenda_list_items_layout, null);
			
			holder.ll_StarDate = (LinearLayout) convertView.findViewById(R.id.ll_StartDate);
			holder.ll_EndDate  = (LinearLayout) convertView.findViewById(R.id.ll_endDate);
			
			holder.txt_title       = (TextView) convertView.findViewById(R.id.tx_tittle);
			holder.txt_camping     = (TextView) convertView.findViewById(R.id.tx_camping);
			holder.txt_startDate   = (TextView) convertView.findViewById(R.id.tx_startDate);
			holder.txt_endDate     = (TextView) convertView.findViewById(R.id.tx_endDate);
			holder.txt_startStr    = (TextView) convertView.findViewById(R.id.tx_debStr);
			holder.txt_description = (TextView) convertView.findViewById(R.id.tx_description);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MyCalendar myCal = getItem(position);
		
		holder.txt_title.setText(myCal.getTitle());
		holder.txt_camping.setText(myCal.getCamping());
		holder.txt_startDate.setText(myCal.getStrStartDate());
		holder.txt_description.setText(myCal.getDescription());
		
		holder.txt_startStr.setText(R.string.debut);
		
		if(myCal.isSameDay()) {
			holder.ll_EndDate.setVisibility(View.GONE);
			holder.txt_startStr.setText(R.string.date);
		}
		else if(myCal.getEndDate() != null)  {
			holder.ll_EndDate.setVisibility(View.VISIBLE);
			holder.txt_endDate.setText(myCal.getStrEndDate());
		}
		else {
			holder.ll_EndDate.setVisibility(View.GONE);
		}
		
		return convertView;
	}


	
	// Filter Class
   public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        this.viewedListe.clear();
        if (charText.length() == 0) {
            this.viewedListe.addAll(this.dataListe);
        } else {
            Iterator it = this.dataListe.iterator();
            while (it.hasNext()) {
                MyCalendar c = (MyCalendar) it.next();
                if (c.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    this.viewedListe.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
	
}
