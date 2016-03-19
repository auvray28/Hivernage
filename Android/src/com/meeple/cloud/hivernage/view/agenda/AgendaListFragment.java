package com.meeple.cloud.hivernage.view.agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.adapters.ListeAgendaAdapter;

public class AgendaListFragment extends Fragment {

	public enum OrderCalendarsBy {
		TODAY(null), SEVEN_DAYS(null), MONTH(null), ALL(null);
		
		private Comparator<Calendar> comparator;
		
		private OrderCalendarsBy(Comparator<Calendar> comparator){
			this.comparator = comparator;
		}
		
		public Comparator<Calendar> getComparator() {
			return this.comparator;
		}
		
	}
	
	private Spinner spinner_agenda;
	private EditText edt_agenda;
	private ListView list_agenda;
	
	private ListeAgendaAdapter list_adapter;
 	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//		if (savedInstanceState != null) {}

		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.agenda_list_layout, container, false); 

		initView(v);

		return v;
	}

	private void initView(View v) {

		spinner_agenda = (Spinner) v.findViewById(R.id.agenda_liste_spinner);
		spinner_agenda.setAdapter(new ArrayAdapter<OrderCalendarsBy>(getActivity().getApplicationContext(), R.layout.spinner_layout, OrderCalendarsBy.values()));
		spinner_agenda.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//				listeCampingsAdapter.reorderCampingsBy((OrderCampingsBy) parent.getItemAtPosition(pos));
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_list_item_1, getCalendarEvent((OrderCalendarsBy) parent.getItemAtPosition(pos)));
				list_agenda.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { 
				// Nothing Todo
			}
		});
		
		
		edt_agenda = (EditText) v.findViewById(R.id.agenda_liste_edittexte);
		
		list_agenda = (ListView) v.findViewById(R.id.agenda_liste_listview);
//		list_adapter = new  ListeAgendaAdapter(getActivity(), getAllCalendarEvent());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getCalendarEvent(OrderCalendarsBy.TODAY));
		list_agenda.setAdapter(adapter);
		
	}
	
	
	private String[] getCalendarEvent(OrderCalendarsBy order) {
		// Crée une projection pour limiter le curseur résultat 
		// aux colonnes désirées
		String [] projection = {
			CalendarContract.Events.TITLE,
			CalendarContract.Events.DTSTART,
			CalendarContract.Events.DTEND,
			CalendarContract.Events.DESCRIPTION,
		};
		
		Calendar range_start = Calendar.getInstance(); // Today 
		Calendar range_end   = Calendar.getInstance();
		String   selection   = null;
		
		
		switch(order) {
		case MONTH:
	        range_end.add(Calendar.DAY_OF_YEAR, 31); //Note that months start from 0 (January)
	        selection = "((dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
			break;
		case SEVEN_DAYS:
	        range_end.add(Calendar.DAY_OF_YEAR, 7); //Note that months start from 0 (January)
	        selection = "((dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
			break;
		case TODAY:
			range_end.add(Calendar.DAY_OF_YEAR, 1); //Note that months start from 0 (January)
	        selection = "((dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
			break;
		default:
			selection = "(dtstart >= "+range_start.getTimeInMillis()+")";
			break;
		}
		
		// Récupére un curseur sur le fournisseur d'événements
		//
		Cursor cursor = this.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection , selection, null, null);
		
		// Récupére les indices des colonnes
		//
		int titleIdx   = cursor.getColumnIndexOrThrow(projection[0]);
		int dtStartIdx = cursor.getColumnIndexOrThrow(projection[1]);
		int dtEndIdx   = cursor.getColumnIndexOrThrow(projection[2]);
		int dtDescIdx  = cursor.getColumnIndexOrThrow(projection[3]);
		
		
		// Créer un tableau pour stocker le résultat
		//
		ArrayList<String> alresult = new ArrayList<>();
//		String [] result = new String[cursor.getCount()];
		
		// Parcours le cuseur résultat
		while(cursor.moveToNext()) {
			
			String title = cursor.getString(titleIdx);
			String dateS = convertIntStringToDate(cursor.getString(dtStartIdx));
			String dateE = convertIntStringToDate(cursor.getString(dtEndIdx));
			String desc  = cursor.getString(dtDescIdx);
			
			if (dateS != null && dateE != null) {
//				result[cursor.getPosition()] = title + " \n(" + dateS+ " => " + dateE + ")\n" + desc ;
				alresult.add(title + " \n(" + dateS+ " => " + dateE + ")\n" + desc);
			}
			else if(dateS != null){
//				result[cursor.getPosition()] = title + " \n( Start: " + dateS + ")\n" + desc ;
				alresult.add(title + " \n( Start: " + dateS + ")\n" + desc);
			}
			else if(dateE != null){
//				result[cursor.getPosition()] = title + " \n( End: " + dateE + ")\n" + desc ;
				alresult.add(title + " \n( End: " +dateE + ")\n" + desc);
			}
			else {
//				result[cursor.getPosition()] = title + "\n" + desc ;
				alresult.add(title + "\n" + desc);
			}
		}
		
		//ferme le cursor
		cursor.close();
		
		return alresult.toArray(new String[]{});
	}
	
	private String convertIntStringToDate(String str_value){
		
		if(str_value != null) {
			long value = Long.parseLong(str_value);
		
			return getDateStr(value, "dd/MM/yyyy");
		}
		else {
			return null;
		}
	}
	
	/**
	 * Return date in specified format.
	 * @param milliSeconds Date in milliseconds
	 * @param dateFormat Date format 
	 * @return String representing date in specified format
	 */
	public String getDateStr(long milliSeconds, String dateFormat)
	{
	    // Create a DateFormatter object for displaying date in specified format.
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

	    // Create a calendar object that will convert the date and time value in milliseconds to date. 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return formatter.format(calendar.getTime());
	}
	
	public Calendar getCalendar(String str_value)
	{
		if(str_value != null) {
			long value = Long.parseLong(str_value);
		
			return getCalendar(value);
		}
		else {
			return null;
		}
	}
	
	public Calendar getCalendar(long milliSeconds)
	{
	    // Create a calendar object that will convert the date and time value in milliseconds to date. 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return calendar;
	}

	public boolean compareCalendar(Calendar cal, OrderCalendarsBy order) {
		
		if (cal != null) {
			switch (order) {
			case ALL:
	//			break;
			case MONTH:
	//			break;
			case SEVEN_DAYS:
//				Calendar range_start = Calendar.getInstance(); // Today   
//		        Calendar range_end = Calendar.getInstance();   range_end.add(Calendar.DAY_OF_YEAR, 7); //Note that months start from 0 (January)
//
//		        String selection = "((dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
//
//		        String[] selectionArgs = new String[] {startString, endString};
//		        cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"), 
//		                (new String[] { "calendar_id", "title", "description", "dtstart", "dtend", "eventLocation"})
//		                ,null,selectionArgs,selection);
//				break;
			case TODAY:
			default:
				Calendar today = Calendar.getInstance();
				if (today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
					return true;
				}
				break;
			}
			
		
		}
		return false;
	}
	
}
