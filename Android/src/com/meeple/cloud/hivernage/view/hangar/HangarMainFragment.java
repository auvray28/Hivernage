package com.meeple.cloud.hivernage.view.hangar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.component.DragAndDropRelativeLayout;

public class HangarMainFragment extends Fragment {

	private Hangar currentHangar;
	
//	private View listeHangar;
	private DragAndDropRelativeLayout  hangarToWash, hangarToWait;
	private HangarView currentHangarView;
	
	private ImageButton btn_prevHangar, btn_nextHangar;
	private TextView    tv_HangarName;
	
	// TODO FRANCOIS Mettre un bouton save dans le Menu
	
	
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
//    	listeHangar = v.findViewById(R.id.hangar_list_hangar);
    	currentHangarView = (HangarView) v.findViewById(R.id.hangar_current_hangar_view);
    	currentHangarView.setBackColor(Color.WHITE); 
    	
    	hangarToWash = (DragAndDropRelativeLayout)v.findViewById(R.id.hangar_to_wash);
    	hangarToWash.setBackColor(Color.argb(170, 30, 144, 255)); //#aa1e90ff
    	
    	
    	hangarToWait = (DragAndDropRelativeLayout)v.findViewById(R.id.hangar_to_wait);
    	hangarToWait.setBackColor(Color.WHITE); 
    	
    	btn_prevHangar = (ImageButton) v.findViewById(R.id.btn_prevHangar);
    	btn_nextHangar = (ImageButton) v.findViewById(R.id.btn_nextHangar);
    	
    	tv_HangarName  = (TextView) v.findViewById(R.id.txt_currentHangar);
    	
    	
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
    	
    	
    	Hangar porch = Services.hangarService.findHangarByName("Porcherie");
    	loadHangar(porch);
    }
    
    
    public void showNextHangar(){
    	int indexH = Services.hangarService.getAllHangars().indexOf(currentHangar) + 1;
    	
    	if (indexH >= Services.hangarService.getAllHangars().size()) indexH = 0;
    	
    	loadHangar(Services.hangarService.getAllHangars().get(indexH));
    }
    
    public void showPrevHangar(){
    	int indexH = Services.hangarService.getAllHangars().indexOf(currentHangar) - 1;
    	
    	if (indexH < 0 ) indexH = Services.hangarService.getAllHangars().size()-1;
    	
    	loadHangar(Services.hangarService.getAllHangars().get(indexH));
    }
    
    
    public void loadHangar(Hangar h){
    	if(currentHangar != h) {
    		
    		if (currentHangar != null) {
    			Services.hangarService.updateHangar(currentHangar);
    			currentHangarView.removeAllViews();
    		}
    		
    		currentHangar = h;
	    	tv_HangarName.setText(currentHangar.getNom());
	    	currentHangarView.loadHangar(currentHangar);
    	}
    }

    
    
}


