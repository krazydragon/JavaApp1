//Ronaldo Barnes
//Java 1 week 3
//January 2013
//Full Sail University

package com.barnes.ronaldo.javaapp1;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InputForm extends LinearLayout {

	EditText _inputField;
	Button _inputButton;
	
	//Create form
	public InputForm(Context context, String hint, String buttonText) {
		super(context);
		
		LayoutParams lParams;
		
		//create input field
		_inputField = new EditText(context);
		lParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_inputField.setLayoutParams(lParams);
		_inputField.setHint(hint);
		
		//create button
		_inputButton = new Button(context);
		_inputButton.setText(buttonText);
		
		//add input field and button to view
		this.addView(_inputField);
		this.addView(_inputButton);
		
		//set layout parameters for form
		lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lParams);
	}
	
	//Capture user input
	public EditText getField(){
		return _inputField;
	}
	
	//Capture button
	public Button getButton(){
		return _inputButton;
	}
	
}