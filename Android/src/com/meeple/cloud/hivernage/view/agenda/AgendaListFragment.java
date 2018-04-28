package com.meeple.cloud.hivernage.view.agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.adapters.ListeAgendaAdapter;
import com.meeple.cloud.hivernage.view.object.MyCalendar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AgendaListFragment extends Fragment implements TextWatcher {
    private static /* synthetic */ int[] f5xa1c01707;
    private static String MAIL = "pechmajou24@gmail.com";
    //private static String MAIL = "vallejo.thibaut@gmail.com";
    private Drawable IMG_CLEAR;
    private EditText edt_agenda;
    private ListeAgendaAdapter list_adapter;
    private ListView list_agenda;
    private Spinner spinner_agenda;

    public enum OrderCalendarsBy {
        SEPT_JOUR,
        AUJOURDHUI,
        CE_MOIS,
        CETTE_ANNEE
    }

    // Bon c'est le OrderBy que j'ai recup de la decompilation du jar...
    //
    static /* synthetic */ int[] m4xa1c01707() {
        int[] iArr = f5xa1c01707;
        if (iArr == null) {
            iArr = new int[OrderCalendarsBy.values().length];
            try {
                iArr[OrderCalendarsBy.AUJOURDHUI.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[OrderCalendarsBy.CETTE_ANNEE.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[OrderCalendarsBy.CE_MOIS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[OrderCalendarsBy.SEPT_JOUR.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            f5xa1c01707 = iArr;
        }
        return iArr;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.agenda_list_layout, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        this.IMG_CLEAR = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
        this.spinner_agenda = (Spinner) v.findViewById(R.id.agenda_liste_spinner);
        this.spinner_agenda.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_layout, OrderCalendarsBy.values()));
        this.spinner_agenda.setOnItemSelectedListener(new OnItemSelectedListener() {
			
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                AgendaListFragment.this.list_adapter = new ListeAgendaAdapter(AgendaListFragment.this.getActivity(), AgendaListFragment.this.getCalendarEvent((OrderCalendarsBy) parent.getItemAtPosition(pos)));
                AgendaListFragment.this.list_agenda.setAdapter(AgendaListFragment.this.list_adapter);
            }

        	@Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
		});
        this.edt_agenda = (EditText) v.findViewById(R.id.agenda_liste_edittexte);
        this.edt_agenda.addTextChangedListener(this);
        this.IMG_CLEAR.setBounds(0, 0, this.IMG_CLEAR.getIntrinsicWidth() - 10, this.IMG_CLEAR.getIntrinsicHeight() - 10);
        this.edt_agenda.setOnTouchListener(new OnTouchListener() {
			
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (AgendaListFragment.this.edt_agenda.getCompoundDrawables()[2] != null && event.getAction() == 1 && event.getX() > ((float) ((AgendaListFragment.this.edt_agenda.getWidth() - AgendaListFragment.this.edt_agenda.getPaddingRight()) - AgendaListFragment.this.IMG_CLEAR.getIntrinsicWidth()))) {
	                AgendaListFragment.this.edt_agenda.setText("");
	                AgendaListFragment.this.edt_agenda.setCompoundDrawables(null, null, null, null);
	            }
	            return false;
	        }
		});
        this.list_agenda = (ListView) v.findViewById(R.id.agenda_liste_listview);
        this.list_adapter = new ListeAgendaAdapter(getActivity(), getCalendarEvent(OrderCalendarsBy.SEPT_JOUR));
        this.list_agenda.setAdapter(this.list_adapter);
        
        this.list_agenda.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
	            String title = AgendaListFragment.this.list_adapter.getItem(position).getTitle();
	            String[] titleSplit = title.split(":");
	            if (titleSplit.length > 1) {
	            	Client client = null;
	                String[] clientName = titleSplit[1].trim().split(" ");
	                client = Services.clientService.findByName(clientName[1], clientName[0]);
	                if (client != null) {
	                    Intent i = new Intent("android.intent.action.SEND");
	                    i.setType("message/rfc822");
	                    i.putExtra("android.intent.extra.EMAIL", new String[]{client.getMail()});
	                    i.putExtra("android.intent.extra.SUBJECT", title);
	                    AgendaListFragment.this.startActivity(Intent.createChooser(i, title));
	                }
	            }
	        }
		});
    }

    private ArrayList<MyCalendar> getCalendarEvent(OrderCalendarsBy order) {
        String[] projection = new String[]{"_id", "title", "dtstart", "dtend", "eventLocation", "description"};
        Calendar range_start = Calendar.getInstance();
        Calendar range_end = Calendar.getInstance();
        String selection = "((account_name = ?) AND (account_type = ?) AND (ownerAccount = ?) ";
        String[] selectionArgs = new String[]{MAIL, "com.google", MAIL};
        switch (m4xa1c01707()[order.ordinal()]) {
            case 1:
                range_end.add(6, 7);
                break;
            case 2:
                range_end.add(6, 1);
                break;
            case 3:
                range_end.add(6, 31);
                break;
            default:
                range_end.add(6, 365);
                break;
        }
        Cursor cursor = getActivity().getContentResolver().query(Events.CONTENT_URI, projection, new StringBuilder(String.valueOf(selection)).append(" AND (dtstart >= ").append(range_start.getTimeInMillis()).append(") AND (dtend <= ").append(range_end.getTimeInMillis()).append("))").toString(), selectionArgs, null);
        int idIdx = cursor.getColumnIndexOrThrow(projection[0]);
        int titleIdx = cursor.getColumnIndexOrThrow(projection[1]);
        int dtStartIdx = cursor.getColumnIndexOrThrow(projection[2]);
        int dtEndIdx = cursor.getColumnIndexOrThrow(projection[3]);
        int dtCampIdx = cursor.getColumnIndexOrThrow(projection[4]);
        int dtDescIdx = cursor.getColumnIndexOrThrow(projection[5]);
        ArrayList<MyCalendar> alresult = new ArrayList();
        while (cursor.moveToNext()) {
            MyCalendar cal = new MyCalendar();
            String id = cursor.getString(idIdx);
            String title = cursor.getString(titleIdx);
            String dateS = cursor.getString(dtStartIdx);
            String dateE = cursor.getString(dtEndIdx);
            String camping = cursor.getString(dtCampIdx);
            String desc = cursor.getString(dtDescIdx);
            cal.setCalendar_id(id);
            cal.setTitle(title);
            cal.setCamping(camping);
            cal.setDescription(desc);
            if (dateS != null) {
                cal.setStartDate(new Date(Long.parseLong(dateS)));
            }
            if (dateE != null) {
                cal.setEndDate(new Date(Long.parseLong(dateS)));
            }
            alresult.add(cal);
        }
        cursor.close();
        return alresult;
    }

    public void afterTextChanged(Editable arg0) {
        this.edt_agenda.setCompoundDrawables(null, null, this.edt_agenda.getText().toString().equals("") ? null : this.IMG_CLEAR, null);
        this.list_adapter.filter(this.edt_agenda.getText().toString().toLowerCase(Locale.getDefault()));
    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }
}
