package com.meeple.cloud.hivernage.view.agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
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
import com.meeple.cloud.hivernage.view.object.MyCalendar;

public class AgendaListFragment extends Fragment {

	public enum OrderCalendarsBy {
		TODAY(null), SEVEN_DAYS(null), MONTH(null), YEAR(null);
		
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
				list_adapter = new ListeAgendaAdapter(getActivity(), getCalendarEvent((OrderCalendarsBy) parent.getItemAtPosition(pos)));
				list_agenda.setAdapter(list_adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { 
				// Nothing Todo
			}
		});
		
		edt_agenda = (EditText) v.findViewById(R.id.agenda_liste_edittexte);
		
		list_agenda = (ListView) v.findViewById(R.id.agenda_liste_listview);
		list_adapter = new  ListeAgendaAdapter(getActivity(), getCalendarEvent(OrderCalendarsBy.TODAY));
		list_agenda.setAdapter(list_adapter);
	}
	
	
//	private String[] getCalendarEvent(OrderCalendarsBy order) {
//		// Crée une projection pour limiter le curseur résultat 
//		// aux colonnes désirées
//		String [] projection = {
//			CalendarContract.Events.TITLE,
//			CalendarContract.Events.DTSTART,
//			CalendarContract.Events.DTEND,
//			CalendarContract.Events.DESCRIPTION,
//		};
//		
//		Calendar range_start = Calendar.getInstance(); // Today 
//		Calendar range_end   = Calendar.getInstance();
//		String   selection   = null;
//		
//		
//		switch(order) {
//		case MONTH:
//	        range_end.add(Calendar.DAY_OF_YEAR, 31); //Note that months start from 0 (January)
//			break;
//		case SEVEN_DAYS:
//	        range_end.add(Calendar.DAY_OF_YEAR, 7); //Note that months start from 0 (January)
//			break;
//		case TODAY:
//			range_end.add(Calendar.DAY_OF_YEAR, 1); //Note that months start from 0 (January)
//			break;
//		default:
//			range_end.add(Calendar.DAY_OF_YEAR, 365);
//			break;
//		}
//		selection = "((dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
//		
//		// Récupére un curseur sur le fournisseur d'événements
//		//
//		Cursor cursor = this.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection , selection, null, null);
//		
//		// Récupére les indices des colonnes
//		//
//		int titleIdx   = cursor.getColumnIndexOrThrow(projection[0]);
//		int dtStartIdx = cursor.getColumnIndexOrThrow(projection[1]);
//		int dtEndIdx   = cursor.getColumnIndexOrThrow(projection[2]);
//		int dtDescIdx  = cursor.getColumnIndexOrThrow(projection[3]);
//		
//		
//		// Créer un tableau pour stocker le résultat
//		//
//		ArrayList<String> alresult = new ArrayList<>();
//		
//		// Parcours le cuseur résultat
//		while(cursor.moveToNext()) {
//			
//			String title = cursor.getString(titleIdx);
//			String dateS = convertIntStringToDate(cursor.getString(dtStartIdx));
//			String dateE = convertIntStringToDate(cursor.getString(dtEndIdx));
//			String desc  = cursor.getString(dtDescIdx);
//			
//			if (dateS != null && dateE != null) {
//				alresult.add(title + " \n(" + dateS+ " => " + dateE + ")\n" + desc);
//			}
//			else if(dateS != null){
//				alresult.add(title + " \n( Start: " + dateS + ")\n" + desc);
//			}
//			else if(dateE != null){
//				alresult.add(title + " \n( End: " +dateE + ")\n" + desc);
//			}
//			else {
//				alresult.add(title + "\n" + desc);
//			}
//		}
//		
//		//ferme le cursor
//		cursor.close();
//		
//		return alresult.toArray(new String[]{});
//	}
	
	private ArrayList<MyCalendar> getCalendarEvent(OrderCalendarsBy order) {
		// Crée une projection pour limiter le curseur résultat 
		// aux colonnes désirées
		String [] projection = {
			CalendarContract.Events._ID,
			CalendarContract.Events.TITLE,
			CalendarContract.Events.DTSTART,
			CalendarContract.Events.DTEND,
			CalendarContract.Events.DESCRIPTION,
		};
		
		Calendar range_start = Calendar.getInstance(); // Today 
		Calendar range_end   = Calendar.getInstance();
		
		String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?) AND (" + Calendars.OWNER_ACCOUNT + " = ?) ";
		String[] selectionArgs = new String[] {"auvray28@gmail.com", "com.google", "auvray28@gmail.com"}; 
		
		
		switch(order) {
		case MONTH:
	        range_end.add(Calendar.DAY_OF_YEAR, 31); //Note that months start from 0 (January)
			break;
		case SEVEN_DAYS:
	        range_end.add(Calendar.DAY_OF_YEAR, 7); //Note that months start from 0 (January)
			break;
		case TODAY:
			range_end.add(Calendar.DAY_OF_YEAR, 1); //Note that months start from 0 (January)
			break;
		default:
			range_end.add(Calendar.DAY_OF_YEAR, 365);
			break;
		}
		selection += " AND (dtstart >= "+range_start.getTimeInMillis()+") AND (dtend <= "+range_end.getTimeInMillis()+"))";
		// Récupére un curseur sur le fournisseur d'événements
		//
		Cursor cursor = this.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection , selection, selectionArgs, null);
		
		// Récupére les indices des colonnes
		//
		int idIdx      = cursor.getColumnIndexOrThrow(projection[0]);
		int titleIdx   = cursor.getColumnIndexOrThrow(projection[1]);
		int dtStartIdx = cursor.getColumnIndexOrThrow(projection[2]);
		int dtEndIdx   = cursor.getColumnIndexOrThrow(projection[3]);
		int dtDescIdx  = cursor.getColumnIndexOrThrow(projection[4]);
		
		
		// Créer un tableau pour stocker le résultat
		//
		ArrayList<MyCalendar> alresult = new ArrayList<>();
		
		// Parcours le cuseur résultat
		while(cursor.moveToNext()) {
			
			MyCalendar cal = new MyCalendar();
			
			String id    = cursor.getString(idIdx);
			String title = cursor.getString(titleIdx);
			String dateS = (cursor.getString(dtStartIdx));
			String dateE = (cursor.getString(dtEndIdx));
			String desc  = cursor.getString(dtDescIdx);
			
			cal.setCalendar_id(id);
			cal.setTitle(title);
			cal.setDescription(desc);
			
			if(dateS != null){
				long mlStart = Long.parseLong(dateS);
				cal.setStartDate(new Date(mlStart));
			}

			if(dateE != null){
				long mlEnd = Long.parseLong(dateS);
				cal.setEndDate(new Date(mlEnd));
			}
			
			alresult.add(cal);
		}
		
		//ferme le cursor
		cursor.close();
		
		return alresult;
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

	
}
