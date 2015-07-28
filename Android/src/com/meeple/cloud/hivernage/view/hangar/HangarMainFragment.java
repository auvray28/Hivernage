package com.meeple.cloud.hivernage.view.hangar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meeple.cloud.hivernage.R;

public class HangarMainFragment extends Fragment {

	private View listeHangar, currentHangarView, hangarToWash, hangarToWait;
	
	
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
        View v = inflater.inflate(R.layout.hangar_main_layout, container, false); 
        
        initView(v);
        
        // Inflate the layout for this fragment
        return v;
    }
	
    
    private void initView(View v){
    	listeHangar = v.findViewById(R.id.hangar_list_hangar);
    	currentHangarView = v.findViewById(R.id.hangar_current_hangar_view);
    	hangarToWash = v.findViewById(R.id.hangar_to_wash);
    	hangarToWait = v.findViewById(R.id.hangar_to_wait);
    	
    }
    
    
    
}


