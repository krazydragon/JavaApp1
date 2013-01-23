//Ronaldo Barnes
//Java 1 week 3
//January 2013
//Full Sail University
package com.barnes.ronaldo.javaapp1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	Context _context;
	LinearLayout _thisLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_context = this;
		_thisLayout = new LinearLayout(this);
		
		setContentView(_thisLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
