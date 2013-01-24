package com.barnes.ronaldo.javaapp1;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class LocationDisplay extends GridLayout{
	
	TextView _title;
	TextView _address;
	TextView _city;
	TextView _state;
	TextView _phoneNumber;
	Context _context;
	
	
	
	public LocationDisplay(Context context){
		super(context);
		
		_context = context;
		
		//number of columns
		this.setColumnCount(2);
		
		//Setup text views
		TextView titleLabel = new TextView(_context);
		titleLabel.setText("Name:");
		_title = new TextView(_context);
		TextView addressLabel = new TextView(_context);
		addressLabel.setText("Address:");
		_address = new TextView(_context);
		TextView cityLabel = new TextView(_context);
		cityLabel.setText("City:");
		_city = new TextView(_context);
		TextView stateLabel = new TextView(_context);
		stateLabel.setText("State:");
		_state = new TextView(_context);
		TextView phoneNumberLabel = new TextView(_context);
		phoneNumberLabel.setText("Phone Number:");
		_phoneNumber = new TextView(_context);
		
		this.addView(titleLabel);
		this.addView(_title);
		this.addView(addressLabel);
		this.addView(_address);
		this.addView(cityLabel);
		this.addView(_city);
		this.addView(stateLabel);
		this.addView(_state);
		this.addView(phoneNumberLabel);
		this.addView(_phoneNumber);
		
	}
	
}
