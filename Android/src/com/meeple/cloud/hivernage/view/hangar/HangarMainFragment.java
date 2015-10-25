package com.meeple.cloud.hivernage.view.hangar;

import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.component.CaravaneView;
import com.meeple.cloud.hivernage.view.component.DragAndDropRelativeLayout;

public class HangarMainFragment extends Fragment {

	private View listeHangar;
	private DragAndDropRelativeLayout  currentHangarView, hangarToWash, hangarToWait;
	
	private ImageButton btn_prevHangar, btn_nextHangar;
	private TextView    tv_HangarName;
	
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
    	// Initialisation des composants des vues
    	//
    	listeHangar = v.findViewById(R.id.hangar_list_hangar);
    	currentHangarView = (DragAndDropRelativeLayout) v.findViewById(R.id.hangar_current_hangar_view);
    	hangarToWash = (DragAndDropRelativeLayout)v.findViewById(R.id.hangar_to_wash);
    	hangarToWash.setBackColor(Color.argb(170, 30, 144, 255)); //#aa1e90ff
    	
    	
    	hangarToWait = (DragAndDropRelativeLayout)v.findViewById(R.id.hangar_to_wait);
    	
    	
    	btn_prevHangar = (ImageButton) v.findViewById(R.id.btn_nextHangar);
    	btn_nextHangar = (ImageButton) v.findViewById(R.id.btn_prevHangar);
    	
    	tv_HangarName  = (TextView) v.findViewById(R.id.txt_currentHangar);
    	
    	addCaravane(Color.BLUE);
    	addCaravane(Color.RED);
    	addCaravane(Color.GREEN);
    	addCaravane(Color.MAGENTA);
    	addCaravane(-1);    	
    	
    	// init des button listener
    	//
    	btn_nextHangar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showNextHangar();
			}
		});
    	
    	btn_prevHangar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPrevHangar();
			}
		});
    }
    
    
    public void showNextHangar(){
    	
    }
    
    public void showPrevHangar(){
    	
    }
    
    
    public void addCaravane(int backgroundColor) {
    	CaravaneView c = new CaravaneView(this.getActivity(), backgroundColor);
    	currentHangarView.addView(c);
    }
    
}


