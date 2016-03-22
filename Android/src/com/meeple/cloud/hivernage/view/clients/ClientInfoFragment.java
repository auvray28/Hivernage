package com.meeple.cloud.hivernage.view.clients;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.ClientListInterface;
import com.meeple.cloud.hivernage.view.object.MyEditView;

public class ClientInfoFragment extends Fragment implements TextWatcher{

	public interface ClientInfoInterface {
		public void refreshClientList();
	}
	
	
	private Client client;  
	
	private ClientInfoInterface mCallback;
	
	// Pour le client
	private MyEditView txt_client_nom, txt_client_prenom, txt_client_adresse, txt_client_tel,
					   txt_client_mail, txt_client_obs;
	private TextView txt_client_acompte;
	
	// Pour sa caravane
	private TextView txt_caravane_immatriculation, txt_caravane_obs;
	private TextView txt_caravane_gabarit,txt_caravane_label_position, txt_caravane_position;
	
	//
	private Button btn_updateClient;
	
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
    	txt_client_nom      = (MyEditView) v.findViewById(R.id.client_info_nom);
    	txt_client_prenom   = (MyEditView) v.findViewById(R.id.client_info_prenom);
    	txt_client_adresse  = (MyEditView) v.findViewById(R.id.client_info_adresse);
    	txt_client_tel      = (MyEditView) v.findViewById(R.id.client_info_tel);
    	txt_client_mail     = (MyEditView) v.findViewById(R.id.client_info_mail);
    	txt_client_acompte  = (TextView) v.findViewById(R.id.client_info_acompte);
    	txt_client_obs      = (MyEditView) v.findViewById(R.id.client_info_obs);
    	//
    	txt_caravane_immatriculation = (TextView) v.findViewById(R.id.caravane_info_immat);
    	txt_caravane_gabarit         = (TextView) v.findViewById(R.id.caravane_info_gabarit);
    	txt_caravane_label_position  = (TextView) v.findViewById(R.id.caravane_info_label_position);
    	txt_caravane_position        = (TextView) v.findViewById(R.id.caravane_info_position);
    	txt_caravane_obs             = (TextView) v.findViewById(R.id.caravane_info_obs);
    	//
    	btn_updateClient             = (Button) v.findViewById(R.id.btn_updateClient);
    	btn_updateClient.setVisibility(View.INVISIBLE);
    	btn_updateClient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setClientWithNewValue();
				
				Services.clientService.update(client);
				
				v.setVisibility(View.INVISIBLE);
				
				if (mCallback != null) {
					mCallback.refreshClientList();
				}
			}
		});
    }
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ClientInfoInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ClientInfoInterface");
        }
    }
	
	private void addTextWatcher(TextWatcher tw) {
    	txt_client_nom.addTextChangedListener(tw);
    	txt_client_prenom.addTextChangedListener(tw);
    	txt_client_adresse.addTextChangedListener(tw);
    	txt_client_tel.addTextChangedListener(tw);
    	txt_client_mail.addTextChangedListener(tw);
    	txt_client_acompte.addTextChangedListener(tw);
    	txt_client_obs.addTextChangedListener(tw);
    	txt_caravane_immatriculation.addTextChangedListener(tw);
    	txt_caravane_obs.addTextChangedListener(tw);
	}

    public void fillClientInformation(Client displayClient) {
    	
    	// Client
    	txt_client_nom.setOriginalText(displayClient.getNom());
    	txt_client_prenom.setOriginalText(displayClient.getPrenom());
    	txt_client_adresse.setOriginalText(displayClient.getAdresse());
    	txt_client_tel.setOriginalText(displayClient.getTelephone());
    	txt_client_mail.setOriginalText(displayClient.getMail());
    	
    	txt_client_acompte.setText(" "+displayClient.getCurrentAcompte()+ "€");
    	
    	int colorAcompte = Color.BLACK; // == 0
    	if ( displayClient.getCurrentAcompte() > 0) colorAcompte = Color.GREEN;
    	else if (displayClient.getCurrentAcompte() < 0) colorAcompte = Color.RED;
    	txt_client_acompte.setTextColor(colorAcompte);
    	
    	txt_client_obs.setOriginalText(displayClient.getObservation());
    	
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
    	
    	addTextWatcher(this);
	}

	public void setClientWithNewValue() {
		
		// Client
		//
		client.setNom(txt_client_nom.getText().toString());
		client.setPrenom(txt_client_prenom.getText().toString());
		client.setAdresse(txt_client_adresse.getText().toString());
		client.setTelephone(txt_client_tel.getText().toString());
		client.setMail(txt_client_mail.getText().toString());
		client.setObservation(txt_client_obs.getText().toString());
		
	}
    
    //////////////// TextWatcher ////////////////

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		btn_updateClient.setVisibility(View.VISIBLE);
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

    ///////////////////////////////////////////////
	
	public ClientInfoInterface getCallback() {
		return mCallback;
	}

	public void setCallback(ClientInfoInterface callback) {
		this.mCallback = callback;
	}
}
