package com.meeple.cloud.hivernage.view.utils;


import java.util.ArrayList;

import com.meeple.cloud.hivernage.HivernageApplication;
import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.db.file.FileManager;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.service.Services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UtilsFragment extends Fragment implements TextWatcher {

	private SharedPreferences sharedpref;
	
	private Button btn_importer, btn_exporter;
	
	private TextView txt_nb_total;
	private TextView txt_nb_bygab_1, txt_nb_bygab_2, txt_nb_bygab_3 , txt_nb_bygab_4, txt_nb_bygab_5;
	private EditText txt_price_bygab_1, txt_price_bygab_2, txt_price_bygab_3 , txt_price_bygab_4, txt_price_bygab_5;
	private TextView txt_result_bygab_1, txt_result_bygab_2, txt_result_bygab_3 , txt_result_bygab_4, txt_result_bygab_5;
	
	private CheckBox showUEClient;
	
	public static UtilsFragment newInstance() {
		
		UtilsFragment myFragment = new UtilsFragment();
		
//		Bundle args = new Bundle();
//	    myFragment.setArguments(args);

		return myFragment;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        

        View v = inflater.inflate(R.layout.utils_layout, container, false); 
        
        sharedpref = getActivity().getSharedPreferences(HivernageApplication.TAG_PREFS, Activity.MODE_PRIVATE);
        
        initView(v);
        // Inflate the layout for this fragment
        
        return v;
    }
	
	
	private void initView(View v) {
		
		txt_nb_total   = (TextView) v.findViewById(R.id.txt_nbClientTotal);
		
		txt_nb_bygab_1 = (TextView) v.findViewById(R.id.txt_client_bygab_1);
		txt_nb_bygab_2 = (TextView) v.findViewById(R.id.txt_client_bygab_2);
		txt_nb_bygab_3 = (TextView) v.findViewById(R.id.txt_client_bygab_3);
		txt_nb_bygab_4 = (TextView) v.findViewById(R.id.txt_client_bygab_4);
		txt_nb_bygab_5 = (TextView) v.findViewById(R.id.txt_client_bygab_5);
		//
		resetView();
		//
		txt_price_bygab_1 = (EditText) v.findViewById(R.id.txt_price_bygab_1);
		txt_price_bygab_2 = (EditText) v.findViewById(R.id.txt_price_bygab_2);
		txt_price_bygab_3 = (EditText) v.findViewById(R.id.txt_price_bygab_3);
		txt_price_bygab_4 = (EditText) v.findViewById(R.id.txt_price_bygab_4);
		txt_price_bygab_5 = (EditText) v.findViewById(R.id.txt_price_bygab_5);
		//
		txt_result_bygab_1 = (TextView) v.findViewById(R.id.txt_result_bygab_1);
		txt_result_bygab_2 = (TextView) v.findViewById(R.id.txt_result_bygab_2);
		txt_result_bygab_3 = (TextView) v.findViewById(R.id.txt_result_bygab_3);
		txt_result_bygab_4 = (TextView) v.findViewById(R.id.txt_result_bygab_4);
		txt_result_bygab_5 = (TextView) v.findViewById(R.id.txt_result_bygab_5);
		//
		// On ajoute le text listener apres avoir mis les prefs sinon override
		// vu ue maj des prefs dans after text changed
		//
		txt_price_bygab_1.setText(""+sharedpref.getInt("price_g1", 0));		
		txt_price_bygab_2.setText(""+sharedpref.getInt("price_g2", 0));
		txt_price_bygab_3.setText(""+sharedpref.getInt("price_g3", 0));
		txt_price_bygab_4.setText(""+sharedpref.getInt("price_g4", 0));
		//
		txt_price_bygab_1.addTextChangedListener(this);
		txt_price_bygab_2.addTextChangedListener(this);
		txt_price_bygab_3.addTextChangedListener(this);
		txt_price_bygab_4.addTextChangedListener(this);
		txt_price_bygab_5.addTextChangedListener(this);
		//
		// juste le dernier pour trigger le on change et maj des resultats
		txt_price_bygab_5.setText(""+sharedpref.getInt("price_g5", 0));
		
		
		btn_importer = (Button) v.findViewById(R.id.btn_importer);
		btn_exporter = (Button) v.findViewById(R.id.btn_exporter);
		//		
		btn_importer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean result = true;
				Toast.makeText(getActivity(), "Importation en cours... !", Toast.LENGTH_SHORT).show();
				DbHelper.instance.isImportingModel = true;
				result = FileManager.createAllCampings(getActivity()) != null;
				if(!result) Toast.makeText(getActivity(), "Echec de l'exportation des Campings!", Toast.LENGTH_LONG).show();
				result = FileManager.createAllClients(getActivity()) != null;
				if(!result) Toast.makeText(getActivity(), "Echec de l'exportation des Campings!", Toast.LENGTH_LONG).show();
				else             Toast.makeText(getActivity(), "Importation reussi !", Toast.LENGTH_SHORT).show();
				DbHelper.instance.isImportingModel = false;
				DbHelper.instance.saveModel();
				resetView();
			}
		});

		btn_exporter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int result = 0;
				Toast.makeText(getActivity(), "Exportation en cours... !", Toast.LENGTH_SHORT).show();
				result = FileManager.writeAllCampings(getActivity());
				if(result == -1) Toast.makeText(getActivity(), "Echec de l'exportation des Campings!", Toast.LENGTH_LONG).show();
				result = FileManager.writeAllClients(getActivity());
				if(result == -1) Toast.makeText(getActivity(), "Echec de l'exportation des Clients!", Toast.LENGTH_LONG).show();
				else             Toast.makeText(getActivity(), "Exportation réussi !", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		showUEClient = (CheckBox) v.findViewById(R.id.cb_showUE);
		showUEClient.setChecked(sharedpref.getBoolean("showUEClient", false));
		showUEClient.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = sharedpref.edit();
				editor.putBoolean("showUEClient", isChecked);
				editor.commit();
			}
		});
	}
	
	public void resetView() {
	
		// Ils snt tous crée ensemble donc pas de soucis a ne faire qu'un seul if
		if(txt_nb_total != null) {
			txt_nb_total.setText(""+Services.clientService.getAllClient().size());
			txt_nb_total.invalidate();
			
			txt_nb_bygab_1.setText(""+getNbClientByGabarit(Services.gabaritService.findGabaritByName("g1")));  txt_nb_bygab_1.invalidate(); 
			txt_nb_bygab_2.setText(""+getNbClientByGabarit(Services.gabaritService.findGabaritByName("g2")));  txt_nb_bygab_2.invalidate();
			txt_nb_bygab_3.setText(""+getNbClientByGabarit(Services.gabaritService.findGabaritByName("g3")));  txt_nb_bygab_3.invalidate();
			txt_nb_bygab_4.setText(""+getNbClientByGabarit(Services.gabaritService.findGabaritByName("g4")));  txt_nb_bygab_4.invalidate();
			txt_nb_bygab_5.setText(""+getNbClientByGabarit(Services.gabaritService.findGabaritByName("g5")));  txt_nb_bygab_5.invalidate();
			
		}
	}
	
	
	public int getNbClientByGabarit(Gabarit gab) {
		ArrayList<Client> al_clients =  Services.clientService.getAllClient();
		int compteur = 0;
		
		for(Client client : al_clients) {
			if (client.getCaravane().getGabarit().getNom().equals(gab.getNom())) {
				compteur++;
			}
		}
		
		return compteur;
	}
	
	
    //////////////// TextWatcher ////////////////

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int nbg1 = 0, priceg1 = 0;
		int nbg2 = 0, priceg2 = 0;
		int nbg3 = 0, priceg3 = 0;
		int nbg4 = 0, priceg4 = 0;
		int nbg5 = 0, priceg5 = 0;

		
		// je fais plusieurs try comme ça, s'il n'y en a qu'un seul qui fail, les autres seront good
		// C'est plus pour etre tranquil, vu que c'est moi qui remplie les cases...
		try {
			nbg1 = Integer.parseInt(txt_nb_bygab_1.getText().toString());
			priceg1 = Integer.parseInt(txt_price_bygab_1.getText().toString());
		}
		catch (Exception e) {}

		try {
			nbg2 = Integer.parseInt(txt_nb_bygab_2.getText().toString());
			priceg2 = Integer.parseInt(txt_price_bygab_2.getText().toString());
		}
		catch (Exception e) {}
		
		try {
			nbg3 = Integer.parseInt(txt_nb_bygab_3.getText().toString());
			priceg3 = Integer.parseInt(txt_price_bygab_3.getText().toString());
		}
		catch (Exception e) {}
		
		try {
			nbg4 = Integer.parseInt(txt_nb_bygab_4.getText().toString());
			priceg4 = Integer.parseInt(txt_price_bygab_4.getText().toString());
		}
		catch (Exception e) {}
		
		try {
			nbg5 = Integer.parseInt(txt_nb_bygab_5.getText().toString());
			priceg5 = Integer.parseInt(txt_price_bygab_5.getText().toString());
		}
		catch (Exception e) {}
		
		SharedPreferences.Editor editor = sharedpref.edit();
		editor.putInt("price_g1", priceg1);
		editor.putInt("price_g2", priceg2);
		editor.putInt("price_g3", priceg3);
		editor.putInt("price_g4", priceg4);
		editor.putInt("price_g5", priceg5);
		editor.commit();
		
		txt_result_bygab_1.setText(""+(nbg1 * priceg1));
		txt_result_bygab_2.setText(""+(nbg2 * priceg2));
		txt_result_bygab_3.setText(""+(nbg3 * priceg3));
		txt_result_bygab_4.setText(""+(nbg4 * priceg4));
		txt_result_bygab_5.setText(""+(nbg5 * priceg5));
		
		
		
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
}
