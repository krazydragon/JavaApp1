//Ronaldo Barnes
//Java 1 week 3
//January 2013
//Full Sail University
package com.barnes.ronaldo.javaapp1;

import com.rbarnes.lib.WebInterface;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	Context _context;
	LinearLayout _thisLayout;
	Boolean _connected = false;
	EditText _inputField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_context = this;
		_thisLayout = new LinearLayout(this);
		
		InputForm input = new InputForm(_context, "test", "test");
		
		//Detect form elements
		_inputField = input.getField();
		Button inputButton = input.getButton();
		
		//Detect button click
		inputButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Click Handler",_inputField.getText().toString());
			}
		});
		
		
		//Check network connection
		_connected = WebInterface.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebInterface.getConnectionType(_context));
		}
		
		_thisLayout.addView(input);
		
		setContentView(_thisLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
