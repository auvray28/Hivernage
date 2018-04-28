package com.meeple.cloud.hivernage.view.camping;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.service.Services;
import com.meeple.cloud.hivernage.view.adapters.ListeEmplacementCampingAdapter;
import com.meeple.cloud.hivernage.view.camping.CampingListeFragment.CampingListInterface;
import com.meeple.cloud.hivernage.view.object.MyCalendar;

public class CampingInfoFragment extends Fragment {

	public interface CampingInfoInterface {
		public void refreshList();
	}
	
	private CampingInfoInterface mCallback;
	
	private Camping camping;
	
	private TextView camping_name, camping_phone, camping_mail, camping_contact_name1, camping_contact_name2, camping_contact_tel1, camping_contact_tel2,camping_info;
	private TextView emplacement_str, emplacement_client_name, emplacement_entree, emplacement_sortie;
	private ListView caravanes_liste;
	
	private ListeEmplacementCampingAdapter lca;
	
	public static CampingInfoFragment newInstance(int campingId) {
		
		CampingInfoFragment myFragment = new CampingInfoFragment();
		
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
        
        camping = Services.campingService.findCampingById(getArguments().getInt("campingId", 0));

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.camping_info_layout, container, false); 
        
        initView(v);

        fillCampingInformation(camping);

        // Inflate the layout for this fragment
        return v;
    }

	private void initView(View v) {
		camping_name  = (TextView) v.findViewById(R.id.camping_info_name);
		camping_phone = (TextView) v.findViewById(R.id.camping_phone);
		camping_mail  = (TextView) v.findViewById(R.id.camping_mail);
		camping_info  = (TextView) v.findViewById(R.id.camping_details);
		
		camping_contact_name1 = (TextView) v.findViewById(R.id.txt_camp_contact_name1);
		camping_contact_tel1  = (TextView) v.findViewById(R.id.txt_camp_contact_tel1);
		camping_contact_name2 = (TextView) v.findViewById(R.id.txt_camp_contact_name2);
		camping_contact_tel2  = (TextView) v.findViewById(R.id.txt_camp_contact_tel2);
		
		
		caravanes_liste = (ListView) v.findViewById(R.id.camping_liste_caravanes_inside);
		
		emplacement_str         = (TextView) v.findViewById(R.id.camping_liste_emplacement_str);
		emplacement_client_name = (TextView) v.findViewById(R.id.camping_liste_emplacement_client);
		emplacement_entree      = (TextView) v.findViewById(R.id.camping_liste_emplacement_entree);
		emplacement_sortie      = (TextView) v.findViewById(R.id.camping_liste_emplacement_sortie);
		
		emplacement_str.setVisibility(View.INVISIBLE);
		emplacement_client_name.setVisibility(View.INVISIBLE);
		emplacement_entree.setVisibility(View.INVISIBLE);
		emplacement_sortie.setVisibility(View.INVISIBLE);
		
	} 
	

	private void fillCampingInformation(Camping camping2) {

		camping_name.setText(camping2.getNom());
		camping_phone.setText(camping2.getTel());
		camping_mail.setText(camping2.getMail());
		camping_info.setText("Prix : " + camping2.getPrix());
		
		camping_contact_name1.setText(camping2.getContact_nom_1());
		camping_contact_tel1.setText(camping2.getContact_tel_1());
		camping_contact_name2.setText(camping2.getContact_nom_2());
		camping_contact_tel2.setText(camping2.getContact_tel_2());

		lca = new ListeEmplacementCampingAdapter(getActivity(), camping.getEmplacements());
		caravanes_liste.setAdapter(lca);
		
		caravanes_liste.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				fillEmplacementInformation(lca.getItem(position));
			}
		});
		
	}

	public void fillEmplacementInformation(EmplacementCamping item) {
		emplacement_str.setVisibility(View.VISIBLE);
		emplacement_client_name.setVisibility(View.VISIBLE);
		emplacement_entree.setVisibility(View.VISIBLE);
		emplacement_sortie.setVisibility(View.VISIBLE);
		
		
		emplacement_str.setText(item.getEmplacement());
		emplacement_client_name.setText(item.getCaravane().getClient().getFullName());
		emplacement_entree.setText(MyCalendar.formatter.format(item.getEntree()));
		emplacement_sortie.setText(MyCalendar.formatter.format(item.getSortie()));
	}

	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (CampingInfoInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ClientListInterface");
        }
    }

}
