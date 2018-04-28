package com.meeple.cloud.hivernage.view.agenda;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.object.MyCalendar;
import com.meeple.cloud.hivernage.view.object.MyEditView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class HolidaysFragment extends Fragment
{
	private GregorianCalendar beginDate;
	private ImageButton btn_beginDate;
	private ImageButton btn_endDate;
	private Button btn_planifier;
	private Client client;
	private EditText edt_emplacement;
	private GregorianCalendar endDate;
	private Spinner holyCamping;
	private TextView txt_beginDate;
	private TextView txt_client_nom;
	private TextView txt_client_prenom;
	private TextView txt_endDate;
	
	private EditText edt_note_begin, edt_note_end;


	public static HolidaysFragment newInstance(int paramInt)
	{
		HolidaysFragment localHolidaysFragment = new HolidaysFragment();
		Bundle localBundle = new Bundle();
		localBundle.putInt("clientId", paramInt);
		localHolidaysFragment.setArguments(localBundle);
		return localHolidaysFragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		this.client = Services.clientService.findById(getArguments().getInt("clientId", 0));
		
		View v = inflater.inflate(R.layout.client_holidays_layout, container, false); 

		initView(v);

		return v;
	}
	
	
	private void initView(View paramView)
	{
		this.txt_client_nom = ((TextView)paramView.findViewById(R.id.client_info_nom));
		this.txt_client_nom.setText(this.client.getNom());
		this.txt_client_prenom = ((TextView)paramView.findViewById(R.id.client_info_prenom));
		this.txt_client_prenom.setText(this.client.getPrenom());
		this.txt_beginDate = ((TextView)paramView.findViewById(R.id.begin_holidays_datepicker));
		this.txt_endDate = ((TextView)paramView.findViewById(R.id.end_holidays_datepicker));
		
		this.edt_note_begin = ((EditText)paramView.findViewById(R.id.holidays_note_begin));
		this.edt_note_end   = ((EditText)paramView.findViewById(R.id.holidays_note_end));
		
		this.edt_emplacement = ((EditText)paramView.findViewById(R.id.holidays_empl));
		this.btn_planifier = ((Button)paramView.findViewById(R.id.btn_confirm_holidays));
		this.btn_planifier.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				HolidaysFragment.this.planifierVacances();
			}
		});
		this.btn_beginDate = ((ImageButton)paramView.findViewById(R.id.btn_begindp));
		this.btn_beginDate.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				HolidaysFragment.this.pickDate(HolidaysFragment.this.txt_beginDate, 0);
			}
		});
		this.btn_endDate = ((ImageButton)paramView.findViewById(R.id.btn_enddp));
		this.btn_endDate.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				HolidaysFragment.this.pickDate(HolidaysFragment.this.txt_endDate, 1);
			}
		});
		this.holyCamping = ((Spinner)paramView.findViewById(R.id.holidays_select_camping));
		this.holyCamping.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_layout, getAllCampingName()));
	}
	
	
	private void cleanAllFields()
	{
		this.edt_emplacement.setText("X");
		this.holyCamping.setSelection(0);
		this.txt_beginDate.setText("X");
		this.txt_endDate.setText("X");
		this.edt_note_begin.setText("X");
		this.edt_note_end.setText("X");
		getView().invalidate();
	}

	private ArrayList<String> getAllCampingName()
	{
		ArrayList<String> localArrayList = new ArrayList<String>();
		localArrayList.add("Aucun");
		Iterator<Camping> localIterator = Services.campingService.getAllCampings().iterator();
		for (;;)
		{
			if (!localIterator.hasNext()) {
				return localArrayList;
			}
			localArrayList.add(((Camping)localIterator.next()).getNom());
		}
	}

	
	public void pickDate(final TextView paramTextView, final int paramInt)
	{
		final Dialog localDialog = new Dialog(getActivity());
		localDialog.setContentView(R.layout.dt_picker);
		localDialog.setTitle(R.string.choose_date_and_time);

		final TimePicker localTimePicker = (TimePicker)localDialog.findViewById(R.id.timePicker1);
		localTimePicker.setIs24HourView(Boolean.valueOf(true));
		final DatePicker localDatePicker = (DatePicker)localDialog.findViewById(R.id.datePicker1);
		
		// Bouton Valider
		((Button)localDialog.findViewById(R.id.bt_valider)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				int i = localTimePicker.getCurrentMinute().intValue();
				int j = localTimePicker.getCurrentHour().intValue();
				int k = localDatePicker.getYear();
				int m = localDatePicker.getMonth();
				int n = localDatePicker.getDayOfMonth();
				if (paramInt == 0) {
					HolidaysFragment.this.beginDate = new GregorianCalendar(k, m, n, j, i);
					paramTextView.setText(MyCalendar.formatter.format(HolidaysFragment.this.beginDate.getTime()));
				}
				else {
					HolidaysFragment.this.endDate = new GregorianCalendar(k, m, n, j, i);
					paramTextView.setText(MyCalendar.formatter.format(HolidaysFragment.this.endDate.getTime()));
				}
				localDialog.hide();
			}
		});
		
		// Bouton Annuler
		((Button)localDialog.findViewById(R.id.bt_annuler)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				localDialog.hide();
			}
		});
		
		
		localDialog.show();
	}

	public void planifierVacances()
	{
		Boolean error      = false;
		Boolean isBeginSet = false;
		Boolean isEndSet   = false;
		Camping localCamping;
		
		if (this.holyCamping.getSelectedItem().toString().equals("Aucun")) {
			((TextView)this.holyCamping.getSelectedView()).setError("Choisir un camping");
			error = true;
		}
		else {
			((TextView)this.holyCamping.getSelectedView()).setError(null);
		}
		if (this.edt_emplacement.getText().toString().equals("X")) {
			this.edt_emplacement.setError("Choisir un emplacement");
			error = true;
		}
		else {
			this.edt_emplacement.setError(null);
		}
		
		if (this.txt_beginDate.getText().toString().equals("X")) {
			this.txt_beginDate.setError("Choisir une date de début");
		}
		else {
			this.txt_beginDate.setError(null);
			isBeginSet = true;
		}
		
		if (this.txt_endDate.getText().toString().equals("X")) {
			this.txt_endDate.setError("Choisir une date de fin");
		}
		else {
			this.txt_endDate.setError(null);
			isEndSet = true;
		}
		
		if (!isBeginSet && !isEndSet) {
			error = true;
		}
		
		
		// Notes 
		String note_begin = "";
		String note_end   = "";
		
		if (!this.edt_note_begin.getText().toString().equals("X")) { note_begin = this.edt_note_begin.getText().toString();}
		if (!this.edt_note_end.getText().toString().equals("X"))   { note_end   = this.edt_note_end.getText().toString();}
		
		
		
		if (error) {
			Toast.makeText(getActivity(), R.string.fillAll, Toast.LENGTH_SHORT).show();
		}
		else {
			localCamping = Services.campingService.findCampingByName(this.holyCamping.getSelectedItem().toString());
			
			EmplacementCamping localEmplacementCamping = new EmplacementCamping(localCamping, this.edt_emplacement.getText().toString(), this.client.getCaravane());
			//
			if(isBeginSet) localEmplacementCamping.setEntree(this.beginDate.getTime());
			if(isEndSet)   localEmplacementCamping.setSortie(this.endDate.getTime());
			//
			Services.caravaneService.addEmplacementCamping(this.client.getCaravane(), localEmplacementCamping);
			//
			if(isBeginSet) pushAppointmentsToCalender(getActivity(), "Entrée : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement : " + this.edt_emplacement.getText().toString() + "\n"+note_begin, "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.beginDate.getTimeInMillis());
			if(isEndSet)   pushAppointmentsToCalender(getActivity(), "Sortie : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement : " + this.edt_emplacement.getText().toString() + "\n"+note_end, "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.endDate.getTimeInMillis());
			//
			Toast.makeText(getActivity(), "Date enregistrée pour le client", Toast.LENGTH_SHORT).show();
			cleanAllFields();
		}
	}

	public long pushAppointmentsToCalender(Activity paramActivity, String title, String description, String location, int status, long durée)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("calendar_id", 2);
		localContentValues.put("title", title);
		localContentValues.put("description", description);
		localContentValues.put("eventLocation", location);
		localContentValues.put("dtstart", durée);
		localContentValues.put("dtend", Long.valueOf(durée + 3600000L));
		localContentValues.put("eventStatus", status);
		localContentValues.put("eventTimezone", "UTC/GMT +2:00");
		localContentValues.put("hasAlarm", 1);
		return Long.parseLong(paramActivity.getApplicationContext().getContentResolver().insert(Uri.parse("content://com.android.calendar/events"), localContentValues).getLastPathSegment());
	}
}
