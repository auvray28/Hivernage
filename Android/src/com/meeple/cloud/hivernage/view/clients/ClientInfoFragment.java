package com.meeple.cloud.hivernage.view.clients;


import java.util.ArrayList;
import java.util.Arrays;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.object.MyEditView;

import android.app.Activity;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClientInfoFragment extends Fragment implements TextWatcher{

	public interface ClientInfoInterface {
		public void refreshList();
		
		public void displayNewHolidaysView(int clientID);
	}

	
	private Client client;  
	
	private ClientInfoInterface mCallback;
	
	// Pour le client
	private MyEditView txt_client_nom, txt_client_prenom, txt_client_adresse, txt_client_tel,
					   txt_client_mail, txt_client_obs, txt_caravane_obs, txt_showHolidaysDate,
					   txt_caravane_immatriculation;
	private TextView txt_client_acompte;
	
	// Pour sa caravane
	private TextView txt_caravane_label_position, txt_caravane_position;
	private Spinner  spinner_hangar, spinner_gabarit;
	//
	private Button btn_updateClient, btn_orga_vac, btn_delivery;
	
	// Hangar
	private Hangar WAITING;
	
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
        this.WAITING = Services.hangarService.findHangarByName("Waiting");

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
    	txt_caravane_immatriculation = (MyEditView) v.findViewById(R.id.caravane_info_immat);
    	txt_caravane_label_position  = (TextView) v.findViewById(R.id.caravane_info_label_position);
    	txt_caravane_position        = (TextView) v.findViewById(R.id.caravane_info_position);
    	spinner_hangar               = (Spinner)  v.findViewById(R.id.show_hangar);
    	spinner_gabarit              = (Spinner)  v.findViewById(R.id.caravane_info_gabarit);
    	txt_caravane_obs             = (MyEditView) v.findViewById(R.id.caravane_info_obs);
    	txt_showHolidaysDate         = (MyEditView) v.findViewById(R.id.show_holidaysdate);
    	//
    	spinner_hangar.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_layout, NewClientFragment.getAllHangarName()));
    	spinner_hangar.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				changeHangar(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { 
				// Nothing Todo
			}
		});
    	//
    	spinner_gabarit.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_layout, NewClientFragment.getAllGabaritName()));
    	spinner_gabarit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				changeGabarit(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { 
				// Nothing Todo
			}
		});
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
					mCallback.refreshList();
				}
			}
		});
    	
    	btn_orga_vac                = (Button) v.findViewById(R.id.btn_orga_vac);
    	btn_orga_vac.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				mCallback.displayNewHolidaysView(client.getClientId());
			}
		});
    	
    	this.btn_delivery = (Button) v.findViewById(R.id.btn_carav_delivery);
        this.btn_delivery.setOnClickListener(new OnClickListener() {
			@Override
		      public void onClick(View v) {
	            ClientInfoFragment.this.moveCaravane();
	        }
        });
        
        switch (this.client.getCaravane().getStatus()) {
            case CAMPING:
                this.btn_delivery.setText(R.string.take_caravane);
                this.btn_delivery.setBackgroundColor(Color.RED);
                return;
            default:
                this.btn_delivery.setText(R.string.deliver_caravane);
                this.btn_delivery.setBackgroundColor(Color.BLUE);
                return;
        }
    }
	
    protected void changeHangar(int hangarPos) {

    	String newhangarName = NewClientFragment.getAllHangarName()[hangarPos];
    	
		Services.caravaneService.removeFromHangar(this.client.getCaravane());
		
		Hangar newHangar = Services.hangarService.findHangarByName(newhangarName);
		
		if (newHangar != null) {
			Services.caravaneService.putInHangar(this.client.getCaravane(), new EmplacementHangar(0, 0, 0.0d, newHangar));
		}
	}
    
    protected void changeGabarit(int gabaritPos) {

    	String newgabaritName = NewClientFragment.getAllGabaritName()[gabaritPos];
    	
		Gabarit newGabarit = Services.gabaritService.findGabaritByName(newgabaritName);
		
		if (newGabarit != null) {
			client.getCaravane().setGabari(newGabarit);
			Services.caravaneService.update(client.getCaravane());
		}
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
    	txt_caravane_immatriculation.setOriginalText(displayClient.getCaravane().getPlaque());
    	txt_caravane_obs.setOriginalText( displayClient.getCaravane().getObservation());
    	
    	spinner_gabarit.setSelection(Arrays.asList(NewClientFragment.getAllGabaritName()).indexOf(displayClient.getCaravane().getGabarit().getNom()));
    	
    	majCaravaneLocation(displayClient);
    	
    	addTextWatcher(this);
	}

	private void majCaravaneLocation(Client displayClient) {

    	switch(displayClient.getCaravane().getStatus()) {
    	case CAMPING : 
			txt_caravane_position.setVisibility(View.VISIBLE);
			txt_caravane_label_position.setVisibility(View.VISIBLE);
			
    		txt_caravane_position.setText(displayClient.getCaravane().getCurrentCamping().getNom());
    		txt_caravane_label_position.setText(R.string.camping);
    		
    		spinner_hangar.setVisibility(View.GONE);
    		break;
    	case HANGAR : 
			txt_caravane_position.setVisibility(View.GONE);
			txt_caravane_label_position.setVisibility(View.VISIBLE);
    		
	    	spinner_hangar.setVisibility(View.VISIBLE);
	    	
	    	spinner_hangar.setSelection(Arrays.asList(NewClientFragment.getAllHangarName()).indexOf(displayClient.getCaravane().getEmplacementHangar().getHangar().getNom()));
	    	
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
			spinner_hangar.setVisibility(View.GONE);
			break;
    	}
    	
    	txt_showHolidaysDate.setOriginalText(client.getCampingStringDate());
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
		
		// Sa caravane
		client.getCaravane().setPlaque(txt_caravane_immatriculation.getText().toString());
		client.getCaravane().setObservation(txt_caravane_obs.getText().toString());
	}
	
	
	private void moveCaravane() {
        ArrayList<EmplacementCamping> alEmc = this.client.getCaravane().getEmplacementCamping();
        if (alEmc.size() > 0) {
            switch (this.client.getCaravane().getStatus()) {
                case CAMPING:
                    this.btn_delivery.setText(R.string.deliver_caravane);
                    this.btn_delivery.setBackgroundColor(Color.BLUE);
                    Services.caravaneService.removeFromCamping(this.client.getCaravane());
                    Services.caravaneService.putInHangar(this.client.getCaravane(), new EmplacementHangar(0, 0, 0.0d, this.WAITING));
                    Toast.makeText(getActivity(), "Caravane placée dans le hangar WAITING", 0).show();
                    this.btn_delivery.invalidate();
                    majCaravaneLocation(client);
                    return;
                default:
                    this.btn_delivery.setText(R.string.take_caravane);
                    this.btn_delivery.setBackgroundColor(Color.RED);
                    Services.caravaneService.removeFromHangar(this.client.getCaravane());
                    EmplacementCamping emc = (EmplacementCamping) alEmc.get(alEmc.size() - 1);
                    Services.caravaneService.putToCamping(this.client.getCaravane(), emc);
                    Toast.makeText(getActivity(), "Caravane amenée dans le camping : " + emc.getCamping().getNom(), 0).show();
                    this.btn_delivery.invalidate();
                    majCaravaneLocation(client);
                    return;
            }
        }
        Toast.makeText(getActivity(), "Pas de vacances organisé pour ce client", 0).show();
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
