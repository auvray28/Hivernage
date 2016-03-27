package com.meeple.cloud.hivernage.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Hangar;

public class HangarView extends DragAndDropRelativeLayout {

	private Hangar hangar;
	
	private Paint paint = new Paint();
	
	// Je les defs juste pour les tests dans la vue, apr�s elles seront certainement dans une frame layout
	public HangarView(Context context, AttributeSet attrs,	int defStyle) {
		super(context, attrs, defStyle);
	}

	public HangarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HangarView(Context context) {
		super(context);
	}

	
	public HangarView(Context context, Hangar h) {
		super(context);
		loadHangar(h);
	}

	
	public void loadHangar(Hangar h) {
		this.hangar = h;
		
		for (Caravane c : this.hangar.getCaravanes()) {
			CaravaneView.addCaravane(this,c);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        
        if (hangar != null) {
	        for (int i = 1; i < hangar.getLargeur()/100  ; i++ ) {
	        	canvas.drawLine(0, 100*i, getWidth(), 100*i, paint);
	        }
        }
        else {
        	for (int i = 1; i < 5 ; i++ ) {
	        	canvas.drawLine(0, 100*i, getWidth(), 100*i, paint);
	        }
        }
        
	}

	public Hangar getHangar() {
		return hangar;
	}
	
}
