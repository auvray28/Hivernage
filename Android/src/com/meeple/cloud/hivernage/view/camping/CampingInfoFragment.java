package com.meeple.cloud.hivernage.view.camping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.service.Services;

public class CampingInfoFragment extends Fragment {

	private Camping camping;
	
	private TextView camping_name, camping_phone, camping_mail, camping_info;
	private ListView caravanes_liste;
	
	public static CampingInfoFragment newInstance(int campingId) {
		
		CampingInfoFragment myFragment = new CampingInfoFragment();
		
		Bundle args = new Bundle();
	    args.putInt("campingId", campingId);
	    myFragment.setArguments(args);

		return myFragment;
	}
	
	
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        
        camping = Services.campingService.findCampingById(getArguments().getInt("campingId", 0));

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.camping_info_layout, container, false); 
        
        initView(v);

        fillCampingInformation(camping);

        // Inflate the layout for this fragment
        return v;
    }

	private void initView(View v) {
		camping_name = (TextView) v.findViewById(R.id.camping_info_name);
		camping_phone = (TextView) v.findViewById(R.id.camping_phone);
		camping_mail = (TextView) v.findViewById(R.id.camping_mail);
		camping_info = (TextView) v.findViewById(R.id.camping_details);
		
		caravanes_liste = (ListView) v.findViewById(R.id.camping_liste_caravanes_inside);
		
	} 
	

	private void fillCampingInformation(Camping camping2) {

		camping_name.setText(camping2.getNom());
		camping_phone.setText(camping2.getTel());
		camping_mail.setText(camping2.getMail());
		camping_info.setText("Prix : " + camping2.getPrix());
		
		
	}


}
