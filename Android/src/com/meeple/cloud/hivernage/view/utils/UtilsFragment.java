package com.meeple.cloud.hivernage.view.utils;


import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.db.DbHelper;
import com.meeple.cloud.hivernage.db.file.FileManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class UtilsFragment extends Fragment {

	
	private Button btn_importer, btn_exporter;
	
	public static UtilsFragment newInstance() {
		
		UtilsFragment myFragment = new UtilsFragment();
		
//		Bundle args = new Bundle();
//	    myFragment.setArguments(args);

		return myFragment;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        

        View v = inflater.inflate(R.layout.utils_layout, container, false); 
        
        initView(v);
        // Inflate the layout for this fragment
        return v;
    }
	
	
	private void initView(View v) {
		btn_importer = (Button) v.findViewById(R.id.btn_importer);
		btn_exporter = (Button) v.findViewById(R.id.btn_exporter);
		
		
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
		
	}
	
	
	
	
	
}
