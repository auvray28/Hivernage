package com.meeple.cloud.hivernage.view.camping;

import com.meeple.cloud.hivernage.R;
import com.meeple.cloud.hivernage.view.clients.NewClientFragment.NewClientInterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewCampingFragment extends Fragment {

	public interface NewCampingInterface {
		public void refreshList();
	}
	
	private NewCampingInterface mCallback;
	
	public static NewCampingFragment newInstance() {
		
		NewCampingFragment myFragment = new NewCampingFragment();
		
//		Bundle args = new Bundle();
//	    args.putInt("campingId", campingId);
//	    myFragment.setArguments(args);

		return myFragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        

        View v = inflater.inflate(R.layout.new_client_layout, container, false); 
        
        initView(v);
        // Inflate the layout for this fragment
        return v;
    }
	
	
	
	private void initView(View v) {
		
	}
	
	
	
	 @Override
   public void onAttach(Activity activity) {
       super.onAttach(activity);

       // This makes sure that the container activity has implemented
       // the callback interface. If not, it throws an exception
       try {
           mCallback = (NewCampingInterface) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                   + " must implement NewClientInterface");
       }
   }
	
}
