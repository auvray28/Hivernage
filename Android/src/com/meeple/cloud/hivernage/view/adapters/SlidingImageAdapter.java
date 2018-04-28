package com.meeple.cloud.hivernage.view.adapters;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SlidingImageAdapter extends PagerAdapter {


	private ArrayList<Bitmap> IMAGES;
	private LayoutInflater inflater;


	public SlidingImageAdapter(Context context, ArrayList<Bitmap> IMAGES) {
		this.IMAGES = IMAGES;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return IMAGES.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

		assert imageLayout != null;
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

		imageView.setImageBitmap(IMAGES.get(position));

		view.addView(imageLayout, 0);

		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}


}