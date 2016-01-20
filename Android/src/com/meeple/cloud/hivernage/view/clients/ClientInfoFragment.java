package com.meeple.cloud.hivernage.view.clients;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.service.Services;

public class ClientInfoFragment extends Fragment {

	private Client client; 
	
	// Pour le client
	private TextView txt_client_name, txt_client_adresse, txt_client_tel,
					 txt_client_acompte, txt_client_mail, txt_client_obs;
	
	// Pour sa caravane
	private TextView txt_caravane_immatriculation, txt_caravane_gabarit, txt_caravane_label_position,
					  txt_caravane_position, txt_caravane_obs;
	
	
	public static ClientInfoFragment newInstance(int clientId) {
		
		ClientInfoFragment myFragment = new ClientInfoFragment();
		
		Bundle args = new Bundle();
	    args.putInt("clientId", clientId);
	    myFragment.setArguments(args);

		return myFragment;
	}
	
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        	
        }

        client = Services.clientService.findById(getArguments().getInt("clientId", 0));

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.client_info_layout, container, false); 
        
        initView(v);

        fillClientInformation(client);
        
        
        
        return v;
    }

	private void initView(View v) {
    	txt_client_name     = (TextView) v.findViewById(R.id.client_info_name);
    	txt_client_adresse  = (TextView) v.findViewById(R.id.client_info_adresse);
    	txt_client_tel      = (TextView) v.findViewById(R.id.client_info_tel);
    	txt_client_mail     = (TextView) v.findViewById(R.id.client_info_mail);
    	txt_client_acompte  = (TextView) v.findViewById(R.id.client_info_acompte);
    	txt_client_obs      = (TextView) v.findViewById(R.id.client_info_obs);
    	
    	txt_caravane_immatriculation = (TextView) v.findViewById(R.id.caravane_info_immat);
    	txt_caravane_gabarit         = (TextView) v.findViewById(R.id.caravane_info_gabarit);
    	txt_caravane_label_position  = (TextView) v.findViewById(R.id.caravane_info_label_position);
    	txt_caravane_position        = (TextView) v.findViewById(R.id.caravane_info_position);
    	txt_caravane_obs             = (TextView) v.findViewById(R.id.caravane_info_obs);
    }

    public void fillClientInformation(Client displayClient) {
    	// Client
    	txt_client_name.setText(displayClient.getFullName());
    	txt_client_adresse.setText(displayClient.getAdresse());
    	txt_client_tel.setText(displayClient.getTelephone());
    	txt_client_mail.setText(displayClient.getMail());
    	
    	txt_client_acompte.setText(" "+displayClient.getCurrentAcompte()+ "€");
    	
    	int colorAcompte = Color.BLACK; // == 0
    	if ( displayClient.getCurrentAcompte() > 0) colorAcompte = Color.GREEN;
    	else if (displayClient.getCurrentAcompte() < 0) colorAcompte = Color.RED;
    	txt_client_acompte.setTextColor(colorAcompte);
    	
    	txt_client_obs.setText(displayClient.getObservation());
    	
    	// Caravane
    	txt_caravane_immatriculation.setText(displayClient.getCaravane().getPlaque());
    	txt_caravane_gabarit.setText(displayClient.getCaravane().getGabarit().getNom());
    	txt_caravane_obs.setText(displayClient.getCaravane().getObservation());
    	
    	switch(displayClient.getCaravane().getStatus()) {
    	case CAMPING : 
			txt_caravane_position.setVisibility(View.VISIBLE);
			txt_caravane_label_position.setVisibility(View.VISIBLE);
			
    		txt_caravane_position.setText(displayClient.getCaravane().getCurrentCamping().getNom());
    		txt_caravane_label_position.setText(R.string.camping);
    		break;
    	case HANGAR : 
			txt_caravane_position.setVisibility(View.VISIBLE);
			txt_caravane_label_position.setVisibility(View.VISIBLE);
    		
    		txt_caravane_position.setText(displayClient.getCaravane().getEmplacementHangar().getHangar().getNom());
    		txt_caravane_label_position.setText(R.string.hangar);
    		break;
//    	case LAVEE : 
//    	case NEW : 
//    	case SORTIE :
//    	case ATTENTE : 
		default : 
			txt_caravane_position.setVisibility(View.GONE);
			txt_caravane_label_position.setVisibility(View.GONE);
			break;
    	}
    	
	}
    
    
	
}
