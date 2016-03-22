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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.agenda.AgendaListFragment;
import com.meeple.cloud.hivernage.view.camping.CampingInfoFragment;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment.CampingListInterface;
import com.meeple.cloud.hivernage.view.clients.ClientInfoFragment;
import com.meeple.cloud.hivernage.view.clients.ClientInfoFragment.ClientInfoInterface;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment;
import com.meeple.cloud.hivernage.view.clients.ClientListeFragment.ClientListInterface;
import com.meeple.cloud.hivernage.view.clients.NewClientFragment;
import com.meeple.cloud.hivernage.view.clients.NewClientFragment.NewClientInterface;
import com.meeple.cloud.hivernage.view.hangar.HangarMainFragment;

public class MenuActivity extends FragmentActivity implements ClientListInterface, CampingListInterface, ClientInfoInterface, NewClientInterface{

	private enum MenuBarBtn {
		CLIENT, HANGAR, LAVAGE, CAMPING, AGENDA, WAITING;
	}
	
	private enum PanelMode {
		SIMPLE, DOUBLE;
	}
	
	private int defaultMenuBarBtn = Color.rgb(34, 34, 34);
	private int defaultMenuBarTxt = Color.rgb(243, 243, 243);
	
	private int selectedMenuBarBtn = Color.rgb(243, 243, 243);
	private int selectedMenuBarTxt = Color.rgb(34, 34, 34);
	
	boolean doubleBackToExitPressedOnce = false;
	
	private View btn_Client, btn_Hangar, btn_Lavage, btn_Camping, btn_Agenda, btn_Waiting;
	private TextView txt_Client, txt_Hangar, txt_Lavage, txt_Camping, txt_Agenda, txt_Waiting;
	
	private LinearLayout one_panel, two_panel;
	private FrameLayout little_frame, big_frame, only_frame;
	
	private PanelMode currentPanelMode = PanelMode.DOUBLE;
	private MenuBarBtn currentMenu;
	
	private HangarMainFragment   hangarFragment;
	private ClientListeFragment  clientFragment;
	private CampingListeFragment campingFragment;
	private AgendaListFragment agendaFragment;
	
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
            
            //TODO First Page
            clickForHangarView();
            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            
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
		btn_Waiting.setVisibility(View.GONE);
		btn_Lavage.setVisibility(View.GONE);
		
		txt_Client   = (TextView) findViewById(R.id.txt_client);
		txt_Hangar   = (TextView) findViewById(R.id.txt_hangar);
		txt_Lavage   = (TextView) findViewById(R.id.txt_lavage);
		txt_Camping  = (TextView) findViewById(R.id.txt_camping);
		txt_Agenda   = (TextView) findViewById(R.id.txt_agenda);
		txt_Waiting  = (TextView) findViewById(R.id.txt_waiting);
		
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
		agendaFragment = new AgendaListFragment();
		
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
//				Calendar beginTime = Calendar.getInstance();
//				beginTime.set(2016, Calendar.JULY, 2, 15, 30);
//				Calendar endTime = Calendar.getInstance();
//				endTime.set(2016, Calendar.JULY, 3, 15, 30);
//				Intent intent = new Intent(Intent.ACTION_INSERT)
//				        .setData(Events.CONTENT_URI)
//				        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
//				        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
//				        .putExtra(Events.TITLE, "Yoga")
//				        .putExtra(Events.DESCRIPTION, "Group class")
//				        .putExtra(Events.EVENT_LOCATION, "The gym")
//				        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
////				        .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
//				startActivity(intent);
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
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.CLIENT);
		setSelectedBtn(btn_Client);
	}
	
	private void clickForHangarView() {
		switchPanelMode(PanelMode.SIMPLE);
		onArticleSelected(MenuBarBtn.HANGAR);
		setSelectedBtn(btn_Hangar);
	}
	
	private void clickForLavageView() {
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.LAVAGE);
		setSelectedBtn(btn_Lavage);
	}
	
	private void clickForCampingView() {
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.CAMPING);
		setSelectedBtn(btn_Camping);
	}
	
	private void clickForAgendaView() {
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.AGENDA);
		setSelectedBtn(btn_Agenda);
	}
	
	private void clickForWaitingView() {
		switchPanelMode(PanelMode.DOUBLE);
		onArticleSelected(MenuBarBtn.WAITING);
		setSelectedBtn(btn_Waiting);
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
			case AGENDA  : newFrag = agendaFragment;break;
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
		btn_Agenda.setBackgroundColor(defaultMenuBarBtn);
		btn_Client.setBackgroundColor(defaultMenuBarBtn);
		btn_Camping.setBackgroundColor(defaultMenuBarBtn);
		btn_Hangar.setBackgroundColor(defaultMenuBarBtn);
		btn_Waiting.setBackgroundColor(defaultMenuBarBtn);
		btn_Lavage.setBackgroundColor(defaultMenuBarBtn);
		
		txt_Agenda.setTextColor(defaultMenuBarTxt);
		txt_Client.setTextColor(defaultMenuBarTxt);
		txt_Camping.setTextColor(defaultMenuBarTxt);
		txt_Hangar.setTextColor(defaultMenuBarTxt);
		txt_Waiting.setTextColor(defaultMenuBarTxt);
		txt_Lavage.setTextColor(defaultMenuBarTxt);
		
		btnSelected.setBackgroundColor(selectedMenuBarBtn);
		
		((TextView) findViewByStringId(btnSelected, "txt_"+ currentMenu.toString().toLowerCase())).setTextColor(selectedMenuBarTxt);
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
	
	
	public View findViewByStringId(View parent, String name) {
		int id = parent.getContext().getResources().getIdentifier("id/"+name, "id", parent.getContext().getPackageName());
		return parent.findViewById(id);
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
	
	@Override
	public void displayNewClientView() {
		Fragment newFrag = NewClientFragment.newInstance();
		
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
	
	/*************  CampingListInterface  **********/
	
	@Override
	public void displayCampingInfo(int campingId) {
		// On ferme le clavier dans un premier temps
		//
		View view = this.getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		Fragment newFrag = CampingInfoFragment.newInstance(campingId);
		
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


	@Override
	public void refreshClientList() {
		clientFragment.refreshClient();
	}

	
	/**********************************************/
	
}
