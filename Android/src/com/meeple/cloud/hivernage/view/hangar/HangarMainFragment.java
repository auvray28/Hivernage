package com.meeple.cloud.hivernage.view.hangar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.component.CaravaneView;
import com.meeple.cloud.hivernage.view.component.DragAndDropRelativeLayout;
import com.meeple.cloud.hivernage.view.component.HangarView;

public class HangarMainFragment extends Fragment implements DragAndDropRelativeLayout.onDropListener{

	private Hangar currentHangar;
	private Hangar LAVAGE, WAITING;
	
//	private View listeHangar;
	private HangarView  hangarToWash, hangarToWait;
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
    	currentHangarView.setOnDropListener(this);
    	
    	hangarToWash = (HangarView)v.findViewById(R.id.hangar_to_wash);
    	hangarToWash.setBackColor(Color.argb(170, 30, 144, 255)); //#aa1e90ff
    	hangarToWash.setOnDropListener(this);
    	
    	hangarToWait = (HangarView)v.findViewById(R.id.hangar_to_wait);
    	hangarToWait.setBackColor(Color.WHITE);
    	hangarToWait.setOnDropListener(this);
    	
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
    	
    	if( currentHangar == null) {
	    	Hangar porch = Services.hangarService.findHangarByName("Porcherie");
	    	loadHangar(porch);
    	}
    	else {
    		loadHangar(currentHangar);
    	}
    	
    	refreshHangar();
    }
    
    
    
    public void showNextHangar(){
    	int indexH = Services.hangarService.getAllHangars().indexOf(currentHangar) + 1;
    	
    	if (indexH >= Services.hangarService.getAllHangars().size()) indexH = 0;
    	
    	Hangar h = Services.hangarService.getAllHangars().get(indexH);
    	
    	if(h.getNom().equals("Lavage") || h.getNom().equals("Waiting")) {
    		currentHangar = h;
    		showNextHangar();
    	}
    	else {
    		loadHangar(h);
    	}
    }
    
    public void showPrevHangar(){
    	int indexH = Services.hangarService.getAllHangars().indexOf(currentHangar) - 1;
    	
    	if (indexH < 0 ) indexH = Services.hangarService.getAllHangars().size()-1;
    	
    	Hangar h = Services.hangarService.getAllHangars().get(indexH);
    	
    	if(h.getNom().equals("Lavage") || h.getNom().equals("Waiting")) {
    		currentHangar = h;
    		showPrevHangar();
    	}
    	else {
    		loadHangar(h);
    	}
    }
    
    
    public void loadHangar(Hangar h){
    	if(currentHangar != h) {
    		
    		currentHangarView.removeAllViews();
    		
    		currentHangar = h;
	    	tv_HangarName.setText(currentHangar.getNom());
	    	currentHangarView.loadHangar(currentHangar);
    	}
    }

    
	@Override
	public void onDropCaravane(DragAndDropRelativeLayout dropview, CaravaneView cv) {
		Caravane c = cv.getCaravane();
		Hangar from = c.getEmplacementHangar().getHangar();
		Hangar to;
		
		Log.d("Hivernage", "From : "+from.getNom());
		
		if(dropview instanceof HangarView) {
			to = ((HangarView)dropview).getHangar();
			Log.d("Hivernage", "To : "+to.getNom());
			
			from.removeCaravane(c);
			to.addCaravane(c);
			
			c.setEmplacementHangar(new EmplacementHangar(cv.getPosition_X(), cv.getPosition_Y(), cv.getAngle(), to));
			
			Services.caravaneService.update(c);
		}
	}
	
	
	public void refreshHangar(){
    	
    	LAVAGE  = Services.hangarService.findHangarByName("Lavage");
    	if(hangarToWash != null) {
    		hangarToWash.removeAllViews();
    		hangarToWash.loadHangar(LAVAGE);
    	}

    	WAITING = Services.hangarService.findHangarByName("Waiting");
    	if(hangarToWait != null) {
    		hangarToWait.removeAllViews();
    		hangarToWait.loadHangar(WAITING);
    	}
    	
    	if(currentHangar != null && currentHangarView != null) {
    		currentHangarView.removeAllViews();
    		currentHangarView.loadHangar(currentHangar);
    	}
	}
    
}


