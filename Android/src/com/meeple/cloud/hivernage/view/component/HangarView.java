package com.meeple.cloud.hivernage.view.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.Locale;

import com.meeple.cloud.hivernage.HivernageApplication;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Hangar;

public class HangarView extends DragAndDropRelativeLayout {

	private static final int DELTA_X = 50;
	private static final int DELTA_Y = 100;

	private Hangar hangar;

	private Paint paint = new Paint();

	// Je les defs juste pour les tests dans la vue, aprés elles seront certainement dans une frame layout
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

		boolean showUEClient = getContext().getSharedPreferences(HivernageApplication.TAG_PREFS, Activity.MODE_PRIVATE).getBoolean("showUEClient", false);
		
		if (hangar != null) {
			for (Caravane c : this.hangar.getCaravanes()) {
				CaravaneView.addCaravane(this,c, showUEClient);
			}
			this.invalidate();
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		this.paint.setColor(Color.rgb(127, 127, 127));
		this.paint.setAlpha(85);
		this.paint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
		
		if ((this.hangar != null) && (this.hangar.getNom().equals("Waiting")))
		{
			this.paint.setTextSize(50.0F);
			canvas.save();
			canvas.rotate(-45.0F, canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 + 100);
			canvas.drawText(this.hangar.getNom().toUpperCase(Locale.FRENCH), canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 + 100, this.paint);
			canvas.restore();
		}
		else {
			this.paint.setTextSize(35.0F);
			canvas.save();
			canvas.rotate(-90.0F, canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 + 100);
			canvas.drawText("Entrée principale", canvas.getWidth()*7 / 16, 50, this.paint);
			canvas.restore();
		}
	}

	public Hangar getHangar() {
		return hangar;
	}

}
