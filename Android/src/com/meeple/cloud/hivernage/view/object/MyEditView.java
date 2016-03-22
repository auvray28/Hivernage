package com.meeple.cloud.hivernage.view.object;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditView extends EditText {

	private String originalText;
	
	public MyEditView(Context context) {
		super(context);
		baseInit();
	}
	
	public MyEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		baseInit();
	}
	
	public MyEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		baseInit();
	}
	
	private void baseInit() {
		setBackgroundDrawable(null);
	}
	
	public void setOriginalText(String txt) {
		this.originalText = txt;
		setText(txt);
	}
	
	public boolean isTxtDiffFromTxt(){
		return this.originalText.equals(getText().toString());
	}

}
