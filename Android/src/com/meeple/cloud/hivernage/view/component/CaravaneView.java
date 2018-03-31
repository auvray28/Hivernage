package com.meeple.cloud.hivernage.view.component;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.service.Services;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;

public class CaravaneView extends View implements  OnLongClickListener, OnClickListener{
	
	private static final String TAG = "hivernage";
	
	private final static int CARAVANE_VIEW_WIDTH  = 100; 
	private final static int CARAVANE_VIEW_HEIGHT = 70;
	private final static int MARGIN = 10;
	
	private int left   = 10;
	private int top    = 5;
	private int right  = left + CARAVANE_VIEW_WIDTH;
	private int bottom = top + CARAVANE_VIEW_HEIGHT;
			
	private int position_X = 0;
	private int position_Y = 0;
	private float angle = 0;
	
	private int caravaneColor = -1;
	
	private Paint paint = new Paint();
	private LayoutParams lp;
	
	private Caravane caravane_object;
	
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

//	public CaravaneView(Context context, int color) {
//		super(context);
//		
//		this.caravaneColor = color;
//		
//		initView();
//	}
	
	public CaravaneView(Context context, Caravane caravane) {
		super(context);
		
		this.caravane_object = caravane;
		
		this.caravaneColor = getColorForGabarit(caravane.getGabarit());
		
		if (caravane_object != null && caravane_object.getEmplacementHangar() != null) {
			this.position_X = caravane_object.getEmplacementHangar().getPosX();
			this.position_Y = caravane_object.getEmplacementHangar().getPosY();
			this.angle      = (float)caravane_object.getEmplacementHangar().getAngle();
			
			switch(caravane_object.getGabarit().getNom()){
			case "g1": caravaneColor = Color.YELLOW;
				break;
			case "g2": caravaneColor = Color.CYAN;
				break;
			case "g3": caravaneColor = Color.GREEN;
				break;
				default : break;
				
			}
		}
		
		initView();
	}
	
//	public CaravaneView(Context context, int color, int left, int top) {
//		super(context);
//		
//		this.caravaneColor = color;
//		
//		this.position_X = left;
//		this.position_Y = top;
//		this.angle      = 0;
//		
//		initView();
//	}
	
	private void initView() {
		setOnClickListener(this);
		setOnLongClickListener(this);
		
		lp = new LayoutParams(CARAVANE_VIEW_WIDTH + MARGIN, CARAVANE_VIEW_HEIGHT + MARGIN);
		lp.setMargins(position_X, position_Y, 0, 0);
		
		setLayoutParams(lp); // 10 pour les marges
		
		setRotation(angle);
	}
	
    @Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	boolean gauche = true;
    	if (canvas.getWidth() != (CARAVANE_VIEW_WIDTH + MARGIN) || canvas.getHeight() != (CARAVANE_VIEW_HEIGHT + MARGIN)) {
    		try {
    			setLayoutParams(lp);
    		}
    		catch(Exception e) {
    			System.err.println(e);
    		}
    	}
        Log.d(TAG, canvas.getWidth()+" "+canvas.getHeight());
        
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        // le gros rectangle
        canvas.drawRect(left, top, right, bottom, paint);
        
        // Test pour l'orientation
        // le petit carr�
        if (gauche)  { // gauche
        	canvas.drawRect(left-8, CARAVANE_VIEW_HEIGHT/2 -3, left, CARAVANE_VIEW_HEIGHT/2 + 13, paint);
        }
        else { //droite
        	canvas.drawRect(right, CARAVANE_VIEW_HEIGHT/2 -3, right+8, CARAVANE_VIEW_HEIGHT/2 + 13, paint);
        }
        
        
        // on rajoute un peut de couleur dans ce monde de brut
        paint.setStrokeWidth(0);
        if (caravaneColor != -1) {
        	paint.setColor(caravaneColor);
        }
        else {
        	paint.setColor(Color.YELLOW);
        }
        // La couleur du rectangle
        canvas.drawRect(left +3, top + 3, right - 3, bottom - 3, paint );
        // La couleur du petit carr� qui indique l'orientation
        if (gauche)  { // gauche
        	canvas.drawRect( left-5, CARAVANE_VIEW_HEIGHT/2, left, CARAVANE_VIEW_HEIGHT/2 + 10, paint );
        }
        else { //droite
        	canvas.drawRect( right, CARAVANE_VIEW_HEIGHT/2, right +5, CARAVANE_VIEW_HEIGHT/2 + 10, paint );
        }
        
        // La plaque d'immatriculation + nom du client au milieu
        //
        paint.setColor(Color.BLACK);
        paint.setTextSize(18);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        
        if (getAngle() >= 90 && getAngle() < 270) {
        	canvas.rotate(180, 0, 0);
        	canvas.drawText( caravane_object.getPlaque(), -(right-5), -(top + 10), paint);
        	
            paint.setTextSize(15);
        	canvas.drawText( caravane_object.getClient().getNom(), -(right-10), - (bottom - 20), paint);
        	canvas.drawText( caravane_object.getClient().getPrenom(), -(right-10), -(bottom - 35), paint);
        }
        else {
        	canvas.drawText( caravane_object.getPlaque(), left+5, bottom - 10, paint);
        	
            paint.setTextSize(15);
        	canvas.drawText( caravane_object.getClient().getNom(), left+10, top + 20, paint);
        	canvas.drawText( caravane_object.getClient().getPrenom(), left+10, top + 35, paint);
        }
        
    }
	

	@Override
	public void onClick(View v) {
		setAngle(getAngle()+30);
		Log.d(TAG, "Angle : " + getAngle());
		invalidate();
		Services.caravaneService.update(this.caravane_object);	
	}
	

	@Override
	public boolean onLongClick(final View view) {
		
		ClipData data = ClipData.newPlainText("", "");
//		DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		
		// pour �tre franc j'ai copi� coll� un truc du net et le resultat est pas mal...
		// j'ai meme pas regarder les maths qu'il y a derriere mais dois pas etre bien compliqu�s...
		//
		double rotationRad = Math.toRadians(view.getRotation());
	    final int w = (int) (view.getWidth() * view.getScaleX());
	    final int h = (int) (view.getHeight() * view.getScaleY());
	    double s = Math.abs(Math.sin(rotationRad));
	    double c = Math.abs(Math.cos(rotationRad));
	    final int width = (int) (w * c + h * s);
	    final int height = (int) (w * s + h * c);
	    
	    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view) {
	        @Override
	        public void onDrawShadow(Canvas canvas) {
	            canvas.scale(view.getScaleX(), view.getScaleY(), width / 2, height / 2);
	            canvas.rotate(view.getRotation(), width / 2, height / 2);
	            canvas.translate((width - view.getWidth()) / 2, (height - view.getHeight()) / 2);
	            super.onDrawShadow(canvas);
	        }

	        @Override
	        public void onProvideShadowMetrics(Point shadowSize,
	                Point shadowTouchPoint) {
	            shadowSize.set(width, height);
	            shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
	        }
	    };
		
		view.startDrag(data, shadowBuilder, view, 0);
//			view.setVisibility(View.INVISIBLE);
		// peut etre remettre l'invisible au vu des view fantomes qui apparaissent pendant le drag
		
		view.setAlpha(0.5f);
		
		return true;
	}
	
	

	private int getColorForGabarit(Gabarit g) {
		return Color.BLUE;
	}
	
	public int getCaravaneViewWidth(){
		return CARAVANE_VIEW_WIDTH + MARGIN;
	}

	public int getCaravaneViewHeight(){
		return CARAVANE_VIEW_HEIGHT + MARGIN;
	}

	public int getPosition_X() {
		return position_X;
	}

	public void setPosition_X(int position_X) {
		this.position_X = position_X;
		
		if (caravane_object != null && this.caravane_object.getEmplacementHangar() != null) {
			this.caravane_object.getEmplacementHangar().setPosX(position_X);
		}
	}

	public int getPosition_Y() {
		return position_Y;
	}

	public void setPosition_Y(int position_Y) {
		this.position_Y = position_Y;
		if (caravane_object != null && this.caravane_object.getEmplacementHangar() != null) {
			this.caravane_object.getEmplacementHangar().setPosY(position_Y);
		}
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		while (angle > 360) angle -= 360;
		
		this.angle = angle;
		
		if (caravane_object != null && this.caravane_object.getEmplacementHangar() != null) {
			this.caravane_object.getEmplacementHangar().setAngle(angle);
		}
		
		setRotation(this.angle);
	}
	
	public Caravane getCaravane(){
		return caravane_object;
	}
	
    public static void addCaravane(ViewGroup v, Caravane c) {
    	if (!c.getClient().isEuropeenClient()) {
    		CaravaneView cv = new CaravaneView(v.getContext(), c);
    		v.addView(cv);
    	}
    }
    
    
//    public static void addCaravane(ViewGroup v, int backgroundColor) {
//    	CaravaneView cv = new CaravaneView(v.getContext(), backgroundColor);
//    	v.addView(cv);
//    }
    
//    public static void addCaravaneAt(ViewGroup v, int backgroundColor, int left, int top) {
//    	CaravaneView c = new CaravaneView(v.getContext(), backgroundColor, left, top);
//    	v.addView(c);
//    }

}
