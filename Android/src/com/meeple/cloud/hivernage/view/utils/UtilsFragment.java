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
				Toast.makeText(getActivity(), "Importation en cours... !", 1).show();
				DbHelper.instance.isImportingModel = true;
				FileManager.createAllCampings(getActivity());
				FileManager.createAllClients(getActivity());
				DbHelper.instance.isImportingModel = false;
				DbHelper.instance.saveModel();
				Toast.makeText(getActivity(), "Importation fini !", 0).show();
			}
		});

		btn_exporter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "Exportation en cours... !", 1).show();
				FileManager.writeAllCampings(getActivity());
				FileManager.writeAllClients(getActivity());
				Toast.makeText(getActivity(), "Exportation fini !", 0).show();
			}
		});
		
	}
	
	
	
	
	
}
