package com.meeple.cloud.hivernage.view.clients;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.model.enums.HivernageStatus;
import com.meeple.cloud.hivernage.service.Services;

public class NewClientFragment extends Fragment {
	
	public interface NewClientInterface {
		public void refreshList();
	}
	
	private NewClientInterface mCallback;
	
	// Components for New Client
	private EditText edt_nom, edt_prenom, edt_adresse, edt_mail, edt_tel, edt_acompte, edt_observation;
	
	// Components for New Caravane
	private EditText edt_immat, edt_carvaneObs;
	private Spinner spinner_hangar, spinner_gabarit;
	
	// 
	private Button btn_createClient;
	
	public static NewClientFragment newInstance() {
		
		NewClientFragment myFragment = new NewClientFragment();
		
//		Bundle args = new Bundle();
//	    myFragment.setArguments(args);

		return myFragment;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        

        View v = inflater.inflate(R.layout.new_client_layout, container, false); 
        
        initView(v);
        // Inflate the layout for this fragment
        return v;
    }
	
	private void initView(View v) {
		edt_nom         = (EditText) v.findViewById(R.id.edt_nom);
		edt_prenom      = (EditText) v.findViewById(R.id.edt_prenom);
		edt_adresse     = (EditText) v.findViewById(R.id.edt_adresse);
		edt_mail        = (EditText) v.findViewById(R.id.edt_mail);
		edt_tel         = (EditText) v.findViewById(R.id.edt_tel);
		edt_acompte     = (EditText) v.findViewById(R.id.edt_acompte);
		edt_observation = (EditText) v.findViewById(R.id.edt_observation);
		edt_immat       = (EditText) v.findViewById(R.id.edt_newCaravaneImmat);
		edt_carvaneObs  = (EditText) v.findViewById(R.id.edt_newCaravaneObs);
		
		spinner_hangar = (Spinner) v.findViewById(R.id.spinner_hangar);
		spinner_hangar.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_layout, getAllHangarName()));
		
		spinner_gabarit = (Spinner) v.findViewById(R.id.spinner_gabarit);
		spinner_gabarit.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_layout, getAllGabaritName()));
		
		btn_createClient = (Button) v.findViewById(R.id.btn_createClient);
		
		btn_createClient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAllEditFill()) {
					createClient();
					Toast.makeText(getActivity(), R.string.confirmNewClient, Toast.LENGTH_SHORT).show();
					if(mCallback != null) mCallback.refreshList();
					emptyAllEdt();
				}
				else {
					Toast.makeText(getActivity(), R.string.fillAll, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		
	}
	
	public static String[] getAllHangarName(){
		ArrayList<String> als = new ArrayList<String>();
		
		for(Hangar hang : Services.hangarService.getAllHangars()) {
			// Ouais je sais le test est un poil moche mais je sais que �a marchera
			// sans pb, tant que Waiting restera Waiting
			if(hang.getNom().toLowerCase().equals("Waiting".toLowerCase())) {
				als.add(0, hang.getNom());
			}
			else {
				als.add(hang.getNom());
			}
		}
		
		
		return als.toArray(new String[]{});
	}
	
	public static String[] getAllGabaritName(){
		ArrayList<String> als = new ArrayList<String>();
		
		for(Gabarit gab : Services.gabaritService.getAllGabarits()) {
			als.add(gab.getNom());
		}
		return als.toArray(new String[]{});
	}
	
	
	public void createClient() {
		String nom     = edt_nom.getText().toString();
		String prenom  = edt_prenom.getText().toString();
		String adresse = edt_adresse.getText().toString();
		String mail    = edt_mail.getText().toString();
		String tel     = edt_tel.getText().toString();
		String obs     = edt_observation.getText().toString();
		
		int acompte   = Integer.parseInt(edt_acompte.getText().toString());
		HivernageStatus hStatus = HivernageStatus.PAYE;
		if( acompte < 0) hStatus = HivernageStatus.IMPAYE;
		else if( acompte > 0) hStatus = HivernageStatus.ACCOMPTE;
		
		// Caravane
		String immat = edt_immat.getText().toString();
		String obsC  = edt_carvaneObs.getText().toString();
		
		Hangar hang  = Services.hangarService.findHangarByName(spinner_hangar.getSelectedItem().toString());
		Gabarit gab  = Services.gabaritService.findGabaritByName(spinner_gabarit.getSelectedItem().toString());
		
		Caravane caravane = new Caravane(immat, obsC, gab, null);
		Client client = new Client(nom, prenom, adresse, tel, mail, obs, caravane);
		client.addHivernage(new Hivernage(hStatus, acompte));
		caravane.setClient(client);
		Services.clientService.create(client);
		
		EmplacementHangar empl = new EmplacementHangar(100, 100, 0,  hang);
		Services.caravaneService.putInHangar(caravane, empl);
	}
	
	public boolean isAllEditFill() {
		if( (edt_nom.getText().toString().length() == 0)  
			|| (edt_prenom.getText().toString().length() == 0)
			|| (edt_adresse.getText().toString().length() == 0)
			|| (edt_mail.getText().toString().length() == 0)
			|| (edt_tel.getText().toString().length() == 0)
			|| (edt_acompte.getText().toString().length() == 0)
			|| (edt_immat.getText().toString().length() == 0)) {
			return false;
		}
		
		return true;
	}
	
	private void emptyAllEdt(){
		edt_nom.getText().clear();
		edt_prenom.getText().clear();
		edt_adresse.getText().clear();
		edt_mail.getText().clear();
		edt_tel.getText().clear();
		edt_acompte.getText().clear();
		edt_observation.getText().clear();
		edt_immat.getText().clear();
		edt_carvaneObs.getText().clear();
	}
	
	 @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (NewClientInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NewClientInterface");
        }
    }

	 
	 
}
