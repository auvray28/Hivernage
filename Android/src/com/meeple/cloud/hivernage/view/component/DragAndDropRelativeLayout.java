package com.meeple.cloud.hivernage.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DragAndDropRelativeLayout extends RelativeLayout implements OnDragListener {

	private Paint paint = new Paint();
	
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

		CaravaneView view = null;
		int action = event.getAction();

		int x = (int)event.getX();
		int y = (int)event.getY();
		
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			// do nothing
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			v.setBackgroundColor(Color.GRAY);
			break;
		case DragEvent.ACTION_DROP:
			// Dropped, reassign View to ViewGroup
			view = (CaravaneView) event.getLocalState();
			ViewGroup owner = (ViewGroup) view.getParent();
			owner.removeView(view);
			RelativeLayout container = (RelativeLayout) v;

			view.setPosition_X(x - view.getCaravaneViewWidth()/2);
			view.setPosition_Y(y - view.getCaravaneViewHeight()/2);
			
			LayoutParams lp = new LayoutParams(view.getWidth(), view.getHeight());
			lp.setMargins(view.getPosition_X(), view.getPosition_Y(), 0, 0);
			
			container.addView(view, lp);
				
			view.setAlpha(1);
			
			container.invalidate();
			
			break;
		case DragEvent.ACTION_DRAG_ENDED:  // Quand le drag s'arrete
			view = (CaravaneView) event.getLocalState();
			if (view != null) {
				view.setAlpha(1);
			}
		case DragEvent.ACTION_DRAG_EXITED: // Quand on sort de la vue
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
