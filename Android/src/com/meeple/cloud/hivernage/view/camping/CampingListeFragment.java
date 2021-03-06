package com.meeple.cloud.hivernage.view.camping;

import java.util.Comparator;
import java.util.Locale;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.adapters.ListeCampingAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class CampingListeFragment extends Fragment implements TextWatcher {

	// L'image du clear	pour l'EditText
	//
	private  Drawable IMG_CLEAR;
	
	public enum OrderCampingsBy {
		ALPHABETIC(Camping.Comparators.ALPHABETIC), PRIX_CROISSANT(Camping.Comparators.PRIX_CROISSANT),
		PRIX_DECROISSANT(Camping.Comparators.PRIX_DECROISSANT);
		
		private Comparator<Camping> comparator;
		
		private OrderCampingsBy(Comparator<Camping> comparator){
			this.comparator = comparator;
		}
		
		public Comparator<Camping> getComparator() {
			return this.comparator;
		}
		
	}
	
	public interface CampingListInterface {
		public void displayCampingInfo(int campingID);

		public void displayNewCampingView();
	}
	
	private EditText searchCampings;
	private ListView listeCampings;
	private ListeCampingAdapter listeCampingsAdapter;
	
	private Spinner orderBy;
	
	private Button btn_addCamping;
	
	private CampingListInterface mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        // If activity recreated (such as from screen rotate), restore
	        // the previous article selection set by onSaveInstanceState().
	        // This is primarily necessary when in the two-pane layout.
	        if (savedInstanceState != null) {
//	            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
	        }
	        
	        IMG_CLEAR = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
	        
	        // Ouias je sais c'est le meme layout que pour ClientListFragment mais en meme temps �a affiche la meme chose ^^^
	        //
	        View v = inflater.inflate(R.layout.client_liste_layout, container, false); 
	        
	        initView(v);
	        // Inflate the layout for this fragment
	        return v;
	    }
	    
	    private void initView(View v){
	    	searchCampings = (EditText) v.findViewById(R.id.client_liste_edittexte);
	    	searchCampings.addTextChangedListener(this);
	    	
	    	IMG_CLEAR.setBounds(0, 0, IMG_CLEAR.getIntrinsicWidth()-10, IMG_CLEAR.getIntrinsicHeight()-10);
	    	searchCampings.setOnTouchListener(new OnTouchListener() {
	    	    @Override
	    	    public boolean onTouch(View v, MotionEvent event) {
	    	        if (searchCampings.getCompoundDrawables()[2] == null) {
	    	            return false;
	    	        }
	    	        if (event.getAction() != MotionEvent.ACTION_UP) {
	    	            return false;
	    	        }
	    	        if (event.getX() > searchCampings.getWidth() - searchCampings.getPaddingRight() - IMG_CLEAR.getIntrinsicWidth()) {
	    	        	searchCampings.setText("");
	    	        	searchCampings.setCompoundDrawables(null, null, null, null);
	    	        }
	    	        return false;
	    	    }
	    	});
	    	
	    	listeCampings  = (ListView) v.findViewById(R.id.client_liste_listview);
	    	
	    	orderBy       = (Spinner) v.findViewById(R.id.client_liste_spinner);
	    	
	    	orderBy.setAdapter(new ArrayAdapter<OrderCampingsBy>(getActivity().getApplicationContext(), R.layout.spinner_layout, OrderCampingsBy.values()));
	    	orderBy.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					listeCampingsAdapter.reorderCampingsBy((OrderCampingsBy) parent.getItemAtPosition(pos));
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) { 
					// Nothing Todo
				}
			});
	    	
	    	listeCampingsAdapter = new ListeCampingAdapter(getActivity().getApplicationContext(), Services.campingService.getAllCampings());
	    	listeCampings.setAdapter(listeCampingsAdapter);

	    	listeCampings.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
					// On veux afficher les infos du client
					//
					mCallback.displayCampingInfo( ((Camping)parent.getItemAtPosition(pos)).getCampingId());
				}
			});
	    	listeCampings.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
					new AlertDialog.Builder(CampingListeFragment.this.getActivity())
					.setTitle("Supprimer Camping")
					.setMessage("Êtes-vous sûre de vouloir supprimer ce camping ?")
					.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
						{
							CampingListeFragment.this.deleteCamping(pos);
						}
					})
					.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
						{
							paramAnonymous2DialogInterface.dismiss();
						}
					})
					.setIcon(android.R.drawable.ic_dialog_alert).show();
					return true;				
				}
			});
	    	
	    	
	    	btn_addCamping = (Button) v.findViewById(R.id.btn_addclient);
	    	btn_addCamping.setText(R.string.ajouter_camping);
	    	btn_addCamping.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mCallback.displayNewCampingView();
				}
			});
	    }

	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);

	        // This makes sure that the container activity has implemented
	        // the callback interface. If not, it throws an exception
	        try {
	            mCallback = (CampingListInterface) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement ClientListInterface");
	        }
	    }
	    
		public void refreshCamping() {
			if (listeCampingsAdapter != null) {
				listeCampingsAdapter.notifyDataSetChanged();
			}
		}
		
		private void deleteCamping(int paramInt)  {
			Camping localCamping = (Camping)this.listeCampingsAdapter.getViewedList().get(paramInt);
			if (this.listeCampingsAdapter.getDataList().contains(localCamping)) {
				this.listeCampingsAdapter.getDataList().remove(localCamping);
			}
			Services.campingService.removeCamping(localCamping);
			this.listeCampingsAdapter.notifyDataSetChanged();
		}
	    
	    /***** TextWatcher ****/
	    
		@Override
		public void afterTextChanged(Editable arg0) {
			searchCampings.setCompoundDrawables(null, null, searchCampings.getText().toString().equals("") ? null : IMG_CLEAR, null);
			
			String text = searchCampings.getText().toString().toLowerCase(Locale.getDefault());
			listeCampingsAdapter.filter(text);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	    
	    
}
