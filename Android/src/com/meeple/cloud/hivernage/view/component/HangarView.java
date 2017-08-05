package com.meeple.cloud.hivernage.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.Locale;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Hangar;

public class HangarView extends DragAndDropRelativeLayout {

	private static final int DELTA_X = 50;
	private static final int DELTA_Y = 100;

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

		if (hangar != null) {
			for (Caravane c : this.hangar.getCaravanes()) {
				CaravaneView.addCaravane(this,c);
			}
			this.invalidate();
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if ((this.hangar != null) && ((this.hangar.getNom().equals("Lavage")) || (this.hangar.getNom().equals("Waiting"))))
		{
			this.paint.setColor(Color.rgb(127, 127, 127));
			this.paint.setAlpha(85);
			this.paint.setTextSize(50.0F);
			this.paint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
			canvas.save();
			canvas.rotate(-45.0F, canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 + 100);
			canvas.drawText(this.hangar.getNom().toUpperCase(Locale.FRENCH), canvas.getWidth() / 2 - 50, canvas.getHeight() / 2 + 100, this.paint);
			canvas.restore();
		}
	}

	public Hangar getHangar() {
		return hangar;
	}

}
