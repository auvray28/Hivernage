package com.meeple.cloud.hivernage.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment;
import com.meeple.cloud.hivernage.view.clients.ClientInfoFragment;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.ClientListInterface;
import com.meeple.cloud.hivernage.view.hangar.HangarMainFragment;

public class MenuActivity extends FragmentActivity implements ClientListInterface{

	private enum MenuBarBtn {
		CLIENT, HANGAR, LAVAGE, CAMPING, AGENDA, WAITING;
	}
	
	private enum PanelMode {
		SIMPLE, DOUBLE;
	}
	
	boolean doubleBackToExitPressedOnce = false;
	
	private View btn_Client, btn_Hangar, btn_Lavage, btn_Camping, btn_Agenda, btn_Waiting;
	
	private LinearLayout one_panel, two_panel;
	private FrameLayout little_frame, big_frame, only_frame;
	
	private PanelMode currentPanelMode = PanelMode.DOUBLE;
	private MenuBarBtn currentMenu;
	
	private HangarMainFragment   hangarFragment;
	private ClientListeFragment  clientFragment;
	private CampingListeFragment campingFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu_layout);
		
		initView();
		
		// Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (little_frame != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            clickForHangarView();
            
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
		
		// Initialisation des fragments
		
		hangarFragment = new HangarMainFragment();
		clientFragment = new ClientListeFragment();
		campingFragment = new CampingListeFragment();
		
		// Init Bouton Event
		
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
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.CLIENT);
	}
	
	private void clickForHangarView() {
		setSelectedBtn(btn_Hangar);
		switchPanelMode(PanelMode.SIMPLE);
		onArticleSelected(MenuBarBtn.HANGAR);
	}
	
	private void clickForLavageView() {
		setSelectedBtn(btn_Lavage);
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.LAVAGE);
	}
	
	private void clickForCampingView() {
		setSelectedBtn(btn_Camping);
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.CAMPING);
	}
	
	private void clickForAgendaView() {
		setSelectedBtn(btn_Agenda);
		switchPanelMode(PanelMode.SIMPLE);
		onArticleSelected(MenuBarBtn.AGENDA);
	}
	
	private void clickForWaitingView() {
		setSelectedBtn(btn_Waiting);
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.WAITING);
	}
	
	public void onArticleSelected(MenuBarBtn newMenu) {
		
		if (currentMenu != newMenu) {
		
			Fragment newFrag;
			
			currentMenu = newMenu;
			
			// TODO faudra regarder le new instance de tes Fragments a Diagonal
			//
			switch(newMenu){
			case CLIENT  : newFrag = clientFragment; break;// pas bon a changer
			case HANGAR  : newFrag = hangarFragment; break;
			case LAVAGE  :
			case CAMPING : newFrag = campingFragment;break;
			case AGENDA  :
			case WAITING :
			default :  newFrag = clientFragment; break; 
			
			}

			/*
			 * Attention une erreur pop quand tu vas de agenda vers un autre qui contient aussi clientFragment
			 * n'en tiens pas compte (pas trouvé un moyen et en plus ne popera plus quand tous auront un fragment different)
			 */
			
	        // Create fragment and give it an argument for the selected article
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	        // Replace whatever is in the fragment_container view with this fragment,
	        // and add the transaction to the back stack so the user can navigate back
	        if ( currentPanelMode == PanelMode.DOUBLE) {
	        	transaction.replace(R.id.little_frame, newFrag);
	        }
	        else {
	        	transaction.replace(R.id.only_frame, newFrag);
	        }
//	        transaction.addToBackStack(null);
	
	        // Commit the transaction
	        transaction.commit();
		}
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

	
	@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Cliquer une deuxième fois pour quitter", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	} 
	
	
	/*************  ClientListInterface  **********/
	
	@Override
	public void displayClientInfo(int clientId) {
		// On ferme le clavier dans un premier temps
		//
		View view = this.getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		Fragment newFrag = ClientInfoFragment.newInstance(clientId);
		
		if (two_panel != null) {
			
	        // Create fragment and give it an argument for the selected article
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	        // Replace whatever is in the fragment_container view with this fragment,
	        // and add the transaction to the back stack so the user can navigate back
	        transaction.replace(R.id.big_frame, newFrag);
//	        transaction.addToBackStack(null);

	        // Commit the transaction
	        transaction.commit();
			
		}
	}
	
	/**********************************************/
}
