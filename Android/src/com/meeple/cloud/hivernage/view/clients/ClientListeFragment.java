package com.meeple.cloud.hivernage.view.clients;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.db.DBMock;
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

	public enum OrderClientBy {
		ALPHABETIC, RESTEDU, CAMPING, HANGAR; 
	}
	
	
	private EditText searchClients;
	private ListView listeClients;
	private ListeClientAdapter listeClientAdapter;
	
	private Spinner orderBy;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        View v = inflater.inflate(R.layout.client_liste_layout, container, false); 
        
        initView(v);
        
        
        
        // Inflate the layout for this fragment
        return v;
    }
    
    private void initView(View v){
    	searchClients = (EditText) v.findViewById(R.id.client_liste_edittexte);
    	searchClients.addTextChangedListener(this);
    	
    	
    	listeClients  = (ListView) v.findViewById(R.id.client_liste_listview);
    	
//    	orderBy       = (Spinner) v.findViewById(R.id.client_liste_spinner);
//    	orderBy.setAdapter(new SpinnerClientAdapter());
    	
    	//MOCK
//    	listeClientAdapter = new ListeClientAdapter(getActivity().getApplicationContext(), new ArrayList<Client>());
    	listeClientAdapter = new ListeClientAdapter(getActivity().getApplicationContext(), DBMock.DB.getClients());
    	//
    	listeClients.setAdapter(listeClientAdapter);

    }

	@Override
	public void afterTextChanged(Editable arg0) {}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// il faudra faire le tri dans la liste, je ne sais pas comment on va faire
		// ce n'est pas comme cela que je faisais mais sa ne devrait pas etre tres compliqué
	}
    
    
	
}
