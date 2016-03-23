package com.meeple.cloud.hivernage.view.camping;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.meeple.cloud.hivernage.R;

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
