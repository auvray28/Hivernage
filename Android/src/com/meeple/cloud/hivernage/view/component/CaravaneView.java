package com.meeple.cloud.hivernage.view.component;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.service.Services;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CaravaneView extends View {
	
	private static final String TAG = "hivernage";
	
	private final static int BASE_CARAVANE_VIEW_WIDTH  = 110; 
	private final static int BASE_CARAVANE_VIEW_HEIGHT = 80;
	private final static int MARGIN = 10;
	
	private int left,top, right, bottom;
			
	private int position_X = 0;
	private int position_Y = 0;
	private float angle = 0;
	
	private int caravaneColor = -1;
	
	private Paint paint = new Paint();
	private LayoutParams lp;
	
	private Caravane caravane_object;
	
	private GestureDetector gestureDetector;
	
	public CaravaneView(Context context, Caravane caravane) {
		super(context);
		
		this.caravane_object = caravane;
		
		this.caravaneColor = getColorForGabarit(caravane.getGabarit());
		
		if (caravane_object != null && caravane_object.getEmplacementHangar() != null) {
			this.position_X = caravane_object.getEmplacementHangar().getPosX();
			this.position_Y = caravane_object.getEmplacementHangar().getPosY();
			this.angle      = (float)caravane_object.getEmplacementHangar().getAngle();
		}
		
		initView();
	}
	
	private void initView() {
		gestureDetector = new GestureDetector(getContext(), new GestureListener());
		
		left   = 10;
		top    = 5;
		right  = left + BASE_CARAVANE_VIEW_WIDTH + getAdditionalWidthWithGabarit(caravane_object.getGabarit());
		bottom = top + BASE_CARAVANE_VIEW_HEIGHT;
		
		lp = new LayoutParams(BASE_CARAVANE_VIEW_WIDTH + MARGIN + getAdditionalWidthWithGabarit(caravane_object.getGabarit()), BASE_CARAVANE_VIEW_HEIGHT + MARGIN);
		lp.setMargins(position_X, position_Y, 0, 0);
		
		setLayoutParams(lp); // 10 pour les marges
		
		setRotation(angle);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			onSingleClick();
			return super.onSingleTapConfirmed(e);
		}


		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
			onLongClick();
		}

		
		@Override
		public boolean onDoubleTap(MotionEvent e) {

			onDoubleClick();
			return true;
		}
	}
	
	
    @Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	boolean gauche = true;
    	if (canvas.getWidth() != (BASE_CARAVANE_VIEW_WIDTH + MARGIN + getAdditionalWidthWithGabarit(caravane_object.getGabarit())) || canvas.getHeight() != (BASE_CARAVANE_VIEW_HEIGHT + MARGIN)) {
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
        	canvas.drawRect(left-8, BASE_CARAVANE_VIEW_HEIGHT/2 -3, left, BASE_CARAVANE_VIEW_HEIGHT/2 + 13, paint);
        }
        else { //droite
        	canvas.drawRect(right, BASE_CARAVANE_VIEW_HEIGHT/2 -3, right+8, BASE_CARAVANE_VIEW_HEIGHT/2 + 13, paint);
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
        	canvas.drawRect( left-5, BASE_CARAVANE_VIEW_HEIGHT/2, left, BASE_CARAVANE_VIEW_HEIGHT/2 + 10, paint );
        }
        else { //droite
        	canvas.drawRect( right, BASE_CARAVANE_VIEW_HEIGHT/2, right +5, BASE_CARAVANE_VIEW_HEIGHT/2 + 10, paint );
        }
        
        // La plaque d'immatriculation + nom du client au milieu
        //
        paint.setColor(Color.BLACK);
        paint.setTextSize(18);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        
        int marge = 7;
        
        if (getAngle() >= 90 && getAngle() < 270) {
        	canvas.rotate(180, 0, 0);
        	canvas.drawText( caravane_object.getPlaque(), -(right-marge), -(top + 10), paint);
        	canvas.drawText( caravane_object.getMarque(), -(right-marge), -(top + 27), paint);
        	
            paint.setTextSize(15);
        	canvas.drawText( caravane_object.getClient().getNom(), -(right-marge), - (bottom - 20), paint);
        	canvas.drawText( caravane_object.getClient().getPrenom(), -(right-marge), -(bottom - 35), paint);
        }
        else {
        	canvas.drawText( caravane_object.getPlaque(), left+marge, bottom - 10, paint);
        	canvas.drawText( caravane_object.getMarque(), left+marge, bottom - 27, paint);
        	
            paint.setTextSize(15);
        	canvas.drawText( caravane_object.getClient().getNom(), left+marge, top + 20, paint);
        	canvas.drawText( caravane_object.getClient().getPrenom(), left+marge, top + 35, paint);
        }
        
    }
	

	public void onSingleClick() {
		setAngle(getAngle()+30);
		Log.d(TAG, "Angle : " + getAngle());
		invalidate();
		Services.caravaneService.update(this.caravane_object);	
	}
	
//	@Override
//	public boolean onLongClick(final View view) {
	public boolean onLongClick() {
		final View view = this;
		
		ClipData data = ClipData.newPlainText("", "");
		
		// pour etre franc j'ai copier coller un truc du net et le resultat est pas mal...
		// j'ai meme pas regarder les maths qu'il y a derriere mais dois pas etre bien compliques...
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
	
	public void onDoubleClick() {
		AlertDialog.Builder popShowClient;
		Client tapClient     = caravane_object.getClient();
		StringBuilder clientInfo = new StringBuilder();
		TextView localTextView   = new TextView(this.getContext());

		clientInfo.append("\tAdresse\t\t: " + tapClient.getAdresse() + "\n");
		clientInfo.append("\tTelephone\t: " + tapClient.getTelephone() + "\n");
		clientInfo.append("\tMail\t\t\t: " + tapClient.getMail() + "\n");
		clientInfo.append("\tAcompte\t\t: " + tapClient.getCurrentAcompte() + "\n");
		clientInfo.append("\tObservation\t: " + tapClient.getObservation() + "\n");
		
		//
		localTextView.setText(clientInfo.toString());
		//
		popShowClient = new AlertDialog.Builder(this.getContext());
		popShowClient.setTitle("Info Client : " + tapClient.getFullName());
		popShowClient.setView(localTextView);
		popShowClient.show();
		
		
	}

	private int getColorForGabarit(Gabarit g) {
		
		if(caravane_object != null && caravane_object.getGabarit() != null) {
			switch(caravane_object.getGabarit().getNom()){
			case "g1": 
				return Color.YELLOW;
			case "g2": 
				return Color.CYAN;
			case "g3": 
				return Color.GREEN;
			case "g4":
				return Color.BLUE;
			case "g5":
				return Color.MAGENTA;
			default  : 
				return Color.WHITE;
			}
		}
		else {
			return Color.WHITE;
		}
	}
	
	private int getAdditionalWidthWithGabarit(Gabarit g) {
		
		if(caravane_object != null && caravane_object.getGabarit() != null) {
			switch(caravane_object.getGabarit().getNom()){
			case "g1": 
				return 0;
			case "g2": 
				return 10;
			case "g3": 
				return 20;
			case "g4":
				return 30;
			case "g5":
				return 40;
			default  : 
				return 0;
			}
		}
		else {
			return 0;
		}
	}
	
	public int getCaravaneViewWidth(){
		return BASE_CARAVANE_VIEW_WIDTH + MARGIN + getAdditionalWidthWithGabarit(caravane_object.getGabarit());
	}

	public int getCaravaneViewHeight(){
		return BASE_CARAVANE_VIEW_HEIGHT + MARGIN;
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

	
	
}
