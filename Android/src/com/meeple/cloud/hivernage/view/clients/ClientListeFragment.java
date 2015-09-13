package com.meeple.cloud.hivernage.view.clients;

import java.util.Comparator;
import java.util.Locale;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.clients.adapter.ListeClientAdapter;


/**
 * Fragment affichant la liste des clients que l'on peut ensuite trier, 
 * On peut également effectuer une recherche
 * 
 * 
 * @author Francois
 *
 */
public class ClientListeFragment extends Fragment implements TextWatcher{
	
	// L'image du clear	pour l'EditText
	//
	private  Drawable IMG_CLEAR;
	
	public enum OrderClientBy {
		ALPHABETIC(Client.Comparators.ALPHABETIC), RESTEDU(Client.Comparators.RESTEDU),
		CAMPING(Client.Comparators.CAMPING), HANGAR(Client.Comparators.HANGAR);
		
		private Comparator<Client> comparator;
		
		private OrderClientBy(Comparator<Client> comparator){
			this.comparator = comparator;
		}
		
		public Comparator<Client> getComparator() {
			return this.comparator;
		}
		
	}
	
	public interface ClientListInterface {
		public void displayClientInfo(int clientId);
	}
	
	
	private EditText searchClients;
	private ListView listeClients;
	private ListeClientAdapter listeClientAdapter;
	
	private Spinner orderBy;
	
	private ClientListInterface mCallback;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        
        IMG_CLEAR = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
        

        View v = inflater.inflate(R.layout.client_liste_layout, container, false); 
        
        initView(v);
        // Inflate the layout for this fragment
        return v;
    }
    
    private void initView(View v){
    	searchClients = (EditText) v.findViewById(R.id.client_liste_edittexte);
    	searchClients.addTextChangedListener(this);
    	
    	IMG_CLEAR.setBounds(0, 0, IMG_CLEAR.getIntrinsicWidth()-10, IMG_CLEAR.getIntrinsicHeight()-10);
    	searchClients.setOnTouchListener(new OnTouchListener() {
    	    @Override
    	    public boolean onTouch(View v, MotionEvent event) {
    	        if (searchClients.getCompoundDrawables()[2] == null) {
    	            return false;
    	        }
    	        if (event.getAction() != MotionEvent.ACTION_UP) {
    	            return false;
    	        }
    	        if (event.getX() > searchClients.getWidth() - searchClients.getPaddingRight() - IMG_CLEAR.getIntrinsicWidth()) {
    	        	searchClients.setText("");
    	        	searchClients.setCompoundDrawables(null, null, null, null);
    	        }
    	        return false;
    	    }
    	});
    	
    	listeClients  = (ListView) v.findViewById(R.id.client_liste_listview);
    	
    	orderBy       = (Spinner) v.findViewById(R.id.client_liste_spinner);
    	
    	orderBy.setAdapter(new ArrayAdapter<OrderClientBy>(getActivity().getApplicationContext(), R.layout.spinner_layout, OrderClientBy.values()));
    	orderBy.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				listeClientAdapter.reorderClientsBy((OrderClientBy) parent.getItemAtPosition(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { 
				// Nothing Todo
			}
		});
    	
    	listeClientAdapter = new ListeClientAdapter(getActivity().getApplicationContext(), Services.clientService.getAllClient());
    	listeClients.setAdapter(listeClientAdapter);

    	listeClients.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// On veux afficher les infos du client
				//
				mCallback.displayClientInfo( ((Client)parent.getItemAtPosition(pos)).getClientId());
			}
		});
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ClientListInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ClientListInterface");
        }
    }
    
    /***** TextWatcher ****/
    
	@Override
	public void afterTextChanged(Editable arg0) {
		searchClients.setCompoundDrawables(null, null, searchClients.getText().toString().equals("") ? null : IMG_CLEAR, null);
		
		String text = searchClients.getText().toString().toLowerCase(Locale.getDefault());
		listeClientAdapter.filter(text);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
    
    
	
}
