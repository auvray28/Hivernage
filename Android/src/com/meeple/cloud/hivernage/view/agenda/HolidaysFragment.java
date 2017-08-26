package com.meeple.cloud.hivernage.view.agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.object.MyCalendar;

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
	private static final String AUCUN = "Aucun";
	private static final int BEGIN = 0;
	private static final int END = 1;
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

	private Calendar calendar = Calendar.getInstance();        


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
		
		// TODO faire le holidays layout
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
		this.edt_emplacement.setText("");
		this.holyCamping.setSelection(0);
		this.txt_beginDate.setText("X");
		this.txt_endDate.setText("X");
	}

	private ArrayList<String> getAllCampingName()
	{
		ArrayList localArrayList = new ArrayList();
		localArrayList.add("Aucun");
		Iterator localIterator = Services.campingService.getAllCampings().iterator();
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
		Boolean error = false;
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
			error = true;
		}
		else {
			this.txt_beginDate.setError(null);
		}
		if (this.txt_endDate.getText().toString().equals("X")) {
			this.txt_endDate.setError("Choisir une date de fin");
			error = true;
		}
		else {
			this.txt_endDate.setError(null);
		}
		
		if (error) {
			Toast.makeText(getActivity(), R.string.fillAll, Toast.LENGTH_SHORT).show();
		}
		else {
			localCamping = Services.campingService.findCampingByName(this.holyCamping.getSelectedItem().toString());
			
			EmplacementCamping localEmplacementCamping = new EmplacementCamping(localCamping, this.edt_emplacement.getText().toString(), this.client.getCaravane());
			localEmplacementCamping.setEntree(this.beginDate.getTime());
			localEmplacementCamping.setSortie(this.endDate.getTime());
			Services.caravaneService.addEmplacementCamping(this.client.getCaravane(), localEmplacementCamping);
			pushAppointmentsToCalender(getActivity(), "Entrée : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement " + this.edt_emplacement.getText().toString(), "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.beginDate.getTimeInMillis());
			pushAppointmentsToCalender(getActivity(), "Sortie : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement " + this.edt_emplacement.getText().toString(), "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.endDate.getTimeInMillis());
			Toast.makeText(getActivity(), "Date enregistrée pour le client", 0).show();
			cleanAllFields();
		}
	}

	public long pushAppointmentsToCalender(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, long paramLong)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("calendar_id", Integer.valueOf(2));
		localContentValues.put("title", paramString1);
		localContentValues.put("description", paramString2);
		localContentValues.put("eventLocation", paramString3);
		localContentValues.put("dtstart", Long.valueOf(paramLong));
		localContentValues.put("dtend", Long.valueOf(paramLong + 3600000L));
		localContentValues.put("eventStatus", Integer.valueOf(paramInt));
		localContentValues.put("eventTimezone", "UTC/GMT +2:00");
		localContentValues.put("hasAlarm", Integer.valueOf(1));
		return Long.parseLong(paramActivity.getApplicationContext().getContentResolver().insert(Uri.parse("content://com.android.calendar/events"), localContentValues).getLastPathSegment());
	}
}
