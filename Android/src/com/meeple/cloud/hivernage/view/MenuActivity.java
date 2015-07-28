package com.meeple.cloud.hivernage.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment;

public class MenuActivity extends FragmentActivity {

	private enum MenuBarBtn {
		CLIENT, HANGAR, LAVAGE, CAMPING, AGENDA, WAITING;
	}
	
	private enum PanelMode {
		SIMPLE, DOUBLE;
	}
	
	
	private View btn_Client, btn_Hangar, btn_Lavage, btn_Camping, btn_Agenda, btn_Waiting;
	
	private LinearLayout one_panel, two_panel;
	private FrameLayout little_frame, big_frame, only_frame;
	
	private PanelMode currentPanelMode = PanelMode.DOUBLE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu_layout);
		
		initView();
		
		
		// Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.little_frame) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            clickForClientView();
            
//
//            // Create an instance of ExampleFragment
//            ClientInfoFragment firstFragment = new ClientInfoFragment();
//
//            // In case this activity was started with special instructions from an Intent,
//            // pass the Intent's extras to the fragment as arguments
//            firstFragment.setArguments(getIntent().getExtras());
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.little_frame, firstFragment).commit();
        }
		
		
	}
	
	
	private void initView(){
		//
		// Commun au 2 mode
		//
		btn_Client   = findViewById(R.id.menu_client);
		btn_Hangar   = findViewById(R.id.menu_hangar);
		btn_Lavage   = findViewById(R.id.menu_lavage);
		btn_Camping  = findViewById(R.id.menu_camping);
		btn_Agenda   = findViewById(R.id.menu_agenda);
		btn_Waiting  = findViewById(R.id.menu_waiting);
		
		// En mode 2 panneaux
		//
		two_panel	 = (LinearLayout) findViewById(R.id.two_panel);
		little_frame = (FrameLayout) findViewById(R.id.little_frame);
		big_frame	 = (FrameLayout) findViewById(R.id.big_frame);
		
		// Mode 1 panneau
		//
		one_panel    = (LinearLayout) findViewById(R.id.one_panel);
		only_frame   = (FrameLayout) findViewById(R.id.only_frame);
		
		//
		
		btn_Client.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForClientView();
			}
		});
		
		btn_Hangar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForHangarView();
			}
		});
		
		btn_Lavage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForLavageView();
			}
		});
		
		btn_Camping.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForCampingView();
			}
		});
		
		btn_Agenda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForAgendaView();
			}
		});
		
		btn_Waiting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickForWaitingView();
			}
		});
	}
	
	private void clickForClientView() {
		setSelectedBtn(btn_Client);
		onArticleSelected(MenuBarBtn.CLIENT);
		switchPanelMode(PanelMode.DOUBLE);
	}
	
	private void clickForHangarView() {
		setSelectedBtn(btn_Hangar);
		onArticleSelected(MenuBarBtn.HANGAR);
		switchPanelMode(PanelMode.SIMPLE);
	}
	
	private void clickForLavageView() {
		setSelectedBtn(btn_Lavage);
		onArticleSelected(MenuBarBtn.LAVAGE);
		switchPanelMode(PanelMode.DOUBLE);
	}
	
	private void clickForCampingView() {
		setSelectedBtn(btn_Camping);
		onArticleSelected(MenuBarBtn.CAMPING);
		switchPanelMode(PanelMode.DOUBLE);
	}
	
	private void clickForAgendaView() {
		setSelectedBtn(btn_Agenda);
		onArticleSelected(MenuBarBtn.AGENDA);
		switchPanelMode(PanelMode.SIMPLE);
	}
	
	private void clickForWaitingView() {
		setSelectedBtn(btn_Waiting);
		onArticleSelected(MenuBarBtn.WAITING);
		switchPanelMode(PanelMode.DOUBLE);
	}
	
	public void onArticleSelected(MenuBarBtn position) {
		Fragment newFrag;
		
		
		// TODO faudra regarder le new instance de tes Fragments a Diagonal
		//
		
		switch(position){
		case CLIENT  : newFrag = new ClientListeFragment(); break;// pas bon a changer
		case HANGAR  : 
		case LAVAGE  :
		case CAMPING : newFrag = new CampingListeFragment();break;
		case AGENDA  :
		case WAITING :
		default :  newFrag = new ClientListeFragment(); break; 
		
		}
		
        // Create fragment and give it an argument for the selected article
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.little_frame, newFrag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
	
	
	public void setSelectedBtn(View btnSelected) {
		btn_Agenda.setBackgroundColor(Color.BLUE);
		btn_Client.setBackgroundColor(Color.BLUE);
		btn_Camping.setBackgroundColor(Color.BLUE);
		btn_Hangar.setBackgroundColor(Color.BLUE);
		btn_Waiting.setBackgroundColor(Color.BLUE);
		btn_Lavage.setBackgroundColor(Color.BLUE);
		
		
		btnSelected.setBackgroundColor(Color.GREEN);
	}
	
	public void switchPanelMode(PanelMode newPanelMode){
		if (newPanelMode == currentPanelMode) return;
		
		switch(newPanelMode) {
		case DOUBLE : 
			one_panel.setVisibility(View.GONE);
			two_panel.setVisibility(View.VISIBLE);
			break;
		case SIMPLE :
			one_panel.setVisibility(View.VISIBLE);
			two_panel.setVisibility(View.GONE);			
			break;
		}
		
		currentPanelMode = newPanelMode;
	}
	
	
}
