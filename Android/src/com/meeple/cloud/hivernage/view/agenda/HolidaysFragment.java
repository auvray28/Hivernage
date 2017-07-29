package com.meeple.cloud.hivernage.view.agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.service.Services;

import android.app.Activity;
import android.app.DatePickerDialog;
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
		this.holyCamping.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, getAllCampingName()));
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
	
		DatePickerDialog datePIckerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
				paramTextView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
				
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		
		datePIckerDialog.show();
		/*
		DatePickerDialog.Builder ad = new DatePickerDialog.Builder(this.getActivity());
		ad.setTitle("");
		ad.setMessage("");
		
		ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		ad.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ne rien faire
			}
		});
		// POur le bouton retour
		ad.setCancelable(true);
		ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// ne rien faire
			}
		});
		*/
		/*
		final Dialog localDialog = new Dialog(getActivity());
		localDialog.setContentView(2130903053);
		localDialog.setTitle(2131034158);
		final TimePicker localTimePicker = (TimePicker)localDialog.findViewById(2131230838);
		localTimePicker.setIs24HourView(Boolean.valueOf(true));
		final DatePicker localDatePicker = (DatePicker)localDialog.findViewById(2131230837);
		((Button)localDialog.findViewById(2131230841)).setOnClickListener(new View.OnClickListener()
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
				}
				//TODO met a jour la text view dans la vue parent
				//
				for (paramAnonymousView = HolidaysFragment.this.beginDate;; paramAnonymousView = HolidaysFragment.this.endDate)
				{
					paramTextView.setText(MyCalendar.formatter.format(paramAnonymousView.getTime()));
					paramTextView.setError(null);
					localDialog.hide();
					return;
					HolidaysFragment.this.endDate = new GregorianCalendar(k, m, n, j, i);
				}
				//
			}
		});
		((Button)localDialog.findViewById(2131230840)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				localDialog.hide();
			}
		});
		localDialog.show();*/
	}

	public void planifierVacances()
	{
		//TODO
		/*
		Camping localCamping;
		int i = 1;
		if (this.txt_beginDate.getText().toString().equals("X"))
		{
			i = 0;
			this.txt_beginDate.setError("Choisir une date de d�but");
			if (!this.txt_endDate.getText().toString().equals("X")) {
				break label164;
			}
			i = 0;
			this.txt_endDate.setError("Choisir une date de fin");
			label66:
				if (!this.holyCamping.getSelectedItem().toString().equals("Aucun")) {
					break label175;
				}
			i = 0;
			((TextView)this.holyCamping.getSelectedView()).setError("Choisir un camping");
			label102:
				if (!this.edt_emplacement.getText().toString().equals("")) {
					break label192;
				}
			i = 0;
			this.edt_emplacement.setError("Mettre un emplacement du camping");
		}
		for (;;)
		{
			if (i != 0) {
				break label203;
			}
			Toast.makeText(getActivity(), 2131034148, 0).show();
			return;
			this.txt_beginDate.setError(null);
			break;
			label164:
				this.txt_endDate.setError(null);
			break label66;
			label175:
				((TextView)this.holyCamping.getSelectedView()).setError(null);
			break label102;
			label192:
				this.edt_emplacement.setError(null);
		}
		label203:
			localCamping = Services.campingService.findCampingByName(this.holyCamping.getSelectedItem().toString());
		EmplacementCamping localEmplacementCamping = new EmplacementCamping(this.edt_emplacement.getText().toString(), this.client.getCaravane());
		localEmplacementCamping.setCamping(localCamping);
		localEmplacementCamping.setEntree(this.beginDate.getTime());
		localEmplacementCamping.setSortie(this.endDate.getTime());
		Services.caravaneService.addEmplacementCamping(this.client.getCaravane(), localEmplacementCamping);
		pushAppointmentsToCalender(getActivity(), "Entr�e : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement " + this.edt_emplacement.getText().toString(), "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.beginDate.getTimeInMillis());
		pushAppointmentsToCalender(getActivity(), "Sortie : " + this.client.getFullName().toString(), "Caravane : " + this.client.getCaravane().getPlaque() + " \nEmplacement " + this.edt_emplacement.getText().toString(), "Camping : " + this.holyCamping.getSelectedItem().toString(), 1, this.endDate.getTimeInMillis());
		Toast.makeText(getActivity(), "Date enregistr� pour le client", 0).show();
		cleanAllFields();
		*/
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