package com.meeple.cloud.hivernage.view.component;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;

public class CaravaneView extends View implements OnTouchListener{
	
	private static final String TAG = "hivernage caravane";
	
	private int left   = 5;
	private int top    = 5;
	private int right  = 55;
	private int bottom = 55;
	
	private int caravaneColor = -1;
	
	private Paint paint = new Paint();
	
	public CaravaneView(Context context) {
		super(context);
		initView();
	}
	
	public CaravaneView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}


	public CaravaneView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	
	public CaravaneView(Context context, int color) {
		super(context);
		
		caravaneColor = color;
		
		initView();
	}
	
	private void initView() {
		setOnTouchListener(this);
		setLayoutParams(new LayoutParams(55, 55));
	}
	
    @Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
        Log.d("hivernage", canvas.getWidth()+" "+canvas.getHeight());
    	
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(top, left, right, bottom, paint);
        paint.setStrokeWidth(0);
        if (caravaneColor != -1) {
        	paint.setColor(caravaneColor);
        }
        else {
        	paint.setColor(Color.YELLOW);
        }
        canvas.drawRect(top +3, top + 3, right - 3, bottom - 3, paint );
//        paint.setColor(Color.YELLOW);
//        canvas.drawRect(33, 33, 77, 60, paint );

    }
	

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
//			view.setVisibility(View.INVISIBLE);
			
			view.setAlpha(0.5f);
			
			return true;
		} else {
			return false;
		}
	}

}
