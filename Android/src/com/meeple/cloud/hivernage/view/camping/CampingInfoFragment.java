package com.meeple.cloud.hivernage.view.camping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.clients.ClientInfoFragment;

public class CampingInfoFragment extends Fragment {

	public static ClientInfoFragment newInstance(int campingId) {
		
		ClientInfoFragment myFragment = new ClientInfoFragment();
		
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.camping_info_layout, container, false);
    } 

}
