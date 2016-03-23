package com.meeple.cloud.hivernage.view.camping;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.service.Services;

public class NewCampingFragment extends Fragment {

	public interface NewCampingInterface {
		public void refreshList();
	}
	
	private NewCampingInterface mCallback;
	
	public static NewCampingFragment newInstance() {
		
		NewCampingFragment myFragment = new NewCampingFragment();
		
//		Bundle args = new Bundle();
//	    args.putInt("campingId", campingId);
//	    myFragment.setArguments(args);

		return myFragment;
	}
	
	private EditText edt_nom, edt_adresse, edt_tel, edt_mail, edt_prix, edt_obs;
	
	//
	private Button btn_createCamping;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        

        View v = inflater.inflate(R.layout.new_camping_layout, container, false); 
        
        initView(v);
        // Inflate the layout for this fragment
        return v;
    }
	
	
	
	private void initView(View v) {
		
		edt_nom     = (EditText) v.findViewById(R.id.edt_nom);
		edt_adresse = (EditText) v.findViewById(R.id.edt_adresse);
		edt_tel     = (EditText) v.findViewById(R.id.edt_tel);
		edt_mail    = (EditText) v.findViewById(R.id.edt_mail);
		edt_prix    = (EditText) v.findViewById(R.id.edt_prix);
		edt_obs     = (EditText) v.findViewById(R.id.edt_observation);
		
		btn_createCamping = (Button) v.findViewById(R.id.btn_createCamping);
		
		btn_createCamping.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isAllEditFill()) {
					createCamping();
					Toast.makeText(getActivity(), R.string.confirmNewCamping, Toast.LENGTH_SHORT).show();
					if(mCallback != null) mCallback.refreshList();
					emptyAllEdt();
				}
				else {
					Toast.makeText(getActivity(), R.string.fillAll, Toast.LENGTH_SHORT).show();
				}				
			}
		});
	}
	
	
	
	 protected void createCamping() {
		String nom     = edt_nom.getText().toString();
		String adresse = edt_adresse.getText().toString();
		String mail    = edt_mail.getText().toString();
		String tel     = edt_tel.getText().toString();
		Double prix    = Double.parseDouble(edt_prix.getText().toString());
//		String obs     = edt_obs.getText().toString();
		 
		 Camping camp = new Camping(nom, mail, tel, prix);
		 
		 Services.campingService.createCamping(camp);
	}

	protected void emptyAllEdt() {
		edt_nom.getText().clear();
		edt_adresse.getText().clear();
		edt_tel.getText().clear();
		edt_mail.getText().clear();
		edt_prix.getText().clear();
		edt_obs.getText().clear();
	}

	protected boolean isAllEditFill() {

		if( (edt_nom.getText().toString().length() == 0)  
				|| (edt_adresse.getText().toString().length() == 0)
				|| (edt_mail.getText().toString().length() == 0)
				|| (edt_tel.getText().toString().length() == 0)
				|| (edt_prix.getText().toString().length() == 0)) {
			return false;
		}


		return true;
	}

	@Override
   public void onAttach(Activity activity) {
       super.onAttach(activity);

       // This makes sure that the container activity has implemented
       // the callback interface. If not, it throws an exception
       try {
           mCallback = (NewCampingInterface) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                   + " must implement NewClientInterface");
       }
   }
	
}
