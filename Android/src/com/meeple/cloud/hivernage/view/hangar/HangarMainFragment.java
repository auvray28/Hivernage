package com.meeple.cloud.hivernage.view.hangar;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.component.CaravaneView;
import com.meeple.cloud.hivernage.view.component.DragAndDropRelativeLayout;
import com.meeple.cloud.hivernage.view.component.HangarView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class HangarMainFragment extends Fragment implements DragAndDropRelativeLayout.onDropListener
{
	private Hangar currentHangar;
	private Hangar WAITING;

	//	private View listeHangar;
	private HangarView  hangarToWait;
	private HangarView currentHangarView;

	private ImageButton btn_prevHangar, btn_nextHangar;
	private TextView    tv_HangarName;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup container, Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		// Inflate the layout for this fragment
		//
		View v = paramLayoutInflater.inflate(R.layout.hangar_main_layout, container, false); 

		initView(v);

		return v;
	}

	private void initView(View v){
		// Initialisation des composants des vues
		//
		currentHangarView = (HangarView) v.findViewById(R.id.hangar_current_hangar_view);
		currentHangarView.setBackColor(Color.WHITE); 
		currentHangarView.setOnDropListener(this);

		hangarToWait = (HangarView)v.findViewById(R.id.hangar_to_wait);
		hangarToWait.setBackColor(Color.rgb(236, 236, 236));
		hangarToWait.setOnDropListener(this);

		btn_prevHangar = (ImageButton) v.findViewById(R.id.btn_prevHangar);
		btn_nextHangar = (ImageButton) v.findViewById(R.id.btn_nextHangar);

		tv_HangarName  = (TextView) v.findViewById(R.id.txt_currentHangar);

		// init des button listener
		//
		tv_HangarName.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				AlertDialog.Builder paramAnonymousView;

				paramAnonymousView = new AlertDialog.Builder(HangarMainFragment.this.getActivity());
				final EditText localEditText = new EditText(HangarMainFragment.this.getActivity());
				paramAnonymousView.setTitle("Nouveau nom du hangar");
				paramAnonymousView.setMessage("Changer le nom de " + currentHangar.getNom());
				paramAnonymousView.setView(localEditText);
				paramAnonymousView.setPositiveButton("Changer", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
					{
						HangarMainFragment.this.hideKeyboard(localEditText);
						String newName = localEditText.getText().toString();
						if (newName.trim().length() > 0) {
							HangarMainFragment.this.currentHangar.setNom(newName);
							HangarMainFragment.this.tv_HangarName.setText(newName);
							Services.hangarService.updateHangar(HangarMainFragment.this.currentHangar);
						}
						else {
							Toast.makeText(getActivity(), "Erreur : nom vide", Toast.LENGTH_SHORT).show();
						}
					}
				});
				paramAnonymousView.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
					{
						paramAnonymous2DialogInterface.dismiss();
						HangarMainFragment.this.hideKeyboard(localEditText);
					}
				});
				paramAnonymousView.show();
			}
		});
		
		
		tv_HangarName.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				AlertDialog.Builder paramMenuItem = new AlertDialog.Builder(getActivity());
				paramMenuItem.setTitle("Suppression d'un hangar");
				paramMenuItem.setMessage("Voulez-vous supprimé : "+ currentHangar.getNom() + "\nToutes les caravanes seront transferé dans Waiting");
				paramMenuItem.setPositiveButton("Supprimer", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						Hangar hangarToDelete = currentHangar;
						showPrevHangar();
						if (currentHangar != hangarToDelete) {
							hangarToDelete.relocateCaravanes(WAITING);
							refreshHangar();
							Services.hangarService.removeHangar(hangarToDelete);
							refreshHangar();
						}
					}
				});
				
				paramMenuItem.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						paramAnonymousDialogInterface.dismiss();
					}
				});
				paramMenuItem.show();
				
				return true;
			}
		});

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

	public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
	{
		//TODO layout for Menu
		paramMenuInflater.inflate(R.menu.hangar_menu, paramMenu);

		super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
	}
	
	

	public boolean onOptionsItemSelected(MenuItem paraMenu) {
		
		AlertDialog.Builder paramMenuItem;

		paramMenuItem = new AlertDialog.Builder(getActivity());
		
		switch( paraMenu.getItemId()) {
		case R.id.new_hangar :
			
			final EditText localEditText = new EditText(getActivity());
			paramMenuItem.setTitle("Ajouter un nouveau hangar");
			paramMenuItem.setMessage("Nom du nouveau hangar");
			paramMenuItem.setView(localEditText);
			paramMenuItem.setPositiveButton("Ajouter", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
				{
					HangarMainFragment.this.hideKeyboard(localEditText);
					String newName = localEditText.getText().toString();
					Services.hangarService.createHangar(new Hangar(newName, 0, 0));
				}
			});
			paramMenuItem.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
				{
					paramAnonymousDialogInterface.dismiss();
					HangarMainFragment.this.hideKeyboard(localEditText);
				}
			});
			
			break;
		}

		
		paramMenuItem.show();
		return true;
	}


	public void showNextHangar(){
		int indexH = Services.hangarService.getAllHangars().indexOf(currentHangar) + 1;

		if (indexH >= Services.hangarService.getAllHangars().size()) indexH = 0;

		Hangar h = Services.hangarService.getAllHangars().get(indexH);

		if(h.getNom().equals("Waiting")) {
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

		if(h.getNom().equals("Waiting")) {
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

		WAITING = Services.hangarService.findHangarByName("Waiting");
		if(hangarToWait != null) {
			hangarToWait.removeAllViews();
			hangarToWait.invalidate();
			hangarToWait.loadHangar(WAITING);
			hangarToWait.invalidate();
		}
		
		if(currentHangar != null && currentHangarView != null) {
			currentHangarView.removeAllViews();
			currentHangarView.loadHangar(currentHangar);
		}
	}



	public void hideKeyboard(View paramView)
	{
		((InputMethodManager)getActivity().getSystemService("input_method")).toggleSoftInput(0, 2);
	}
}


