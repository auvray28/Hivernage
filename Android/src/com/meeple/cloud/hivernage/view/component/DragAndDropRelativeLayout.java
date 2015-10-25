package com.meeple.cloud.hivernage.view.component;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DragAndDropRelativeLayout extends RelativeLayout implements OnDragListener{

	private int backgroundColor;
	
	public DragAndDropRelativeLayout(Context context, AttributeSet attrs,	int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DragAndDropRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DragAndDropRelativeLayout(Context context) {
		super(context);
		init();
	}


	private void init() {
		setOnDragListener(this);
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {

		int action = event.getAction();
		
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			// do nothing
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			v.setBackgroundColor(Color.GRAY);
			break;
		case DragEvent.ACTION_DROP:
			// Dropped, reassign View to ViewGroup
			View view = (View) event.getLocalState();
			ViewGroup owner = (ViewGroup) view.getParent();
			owner.removeView(view);
			RelativeLayout container = (RelativeLayout) v;
			
			int x = (int)event.getX();
			int y = (int)event.getY();
			
			LayoutParams lp = new LayoutParams(view.getWidth(), view.getHeight());
			lp.setMargins(x, y, 0, 0);
			
			
			container.addView(view, lp);

			view.setAlpha(1);
			
			break;
		case DragEvent.ACTION_DRAG_EXITED: 
		case DragEvent.ACTION_DRAG_ENDED:      
			if (backgroundColor != -1) {
				v.setBackgroundColor(backgroundColor);
			}
			else {
				v.setBackgroundColor(Color.WHITE);
			}
			break;
		default:
			break;
		}
		return true;
	}

	public void setBackColor(int color) {
		backgroundColor = color;
	}
	
}
