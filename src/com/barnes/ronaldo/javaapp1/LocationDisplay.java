package com.barnes.ronaldo.javaapp1;






import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class LocationDisplay extends GridLayout{
	
	TextView _title;
	TextView _address;
	TextView _city;
	TextView _state;
	TextView _phoneNumber;
	Context _context;
	
	
	
	public LocationDisplay(Context context, JSONObject location) throws JSONException{
		super(context);
		
		_context = context;
		String title;
		String address;
		String city;
		String state;
		String phone;
		//number of columns
		this.setColumnCount(2);
		 if (location != null) {
			title = location.getString("Title");
			address = location.getString("Address");
			city = location.getString("City");
			state = location.getString("State");
			phone = location.getString("Phone");
	        } 
	        else 
	        {
	        	title = "";
				address = "";
				city = "";
				state = "";
				phone = "";
	        }
		
		
		//Setup text views
		TextView titleLabel = new TextView(_context);
		titleLabel.setText("Name:");
		_title = new TextView(_context);
		_title.setText(title);
		TextView addressLabel = new TextView(_context);
		addressLabel.setText("Address:");
		_address = new TextView(_context);
		_address.setText(address);
		TextView cityLabel = new TextView(_context);
		cityLabel.setText("City:");
		_city = new TextView(_context);
		_city.setText(city);
		TextView stateLabel = new TextView(_context);
		stateLabel.setText("State:");
		_state = new TextView(_context);
		_state.setText(state);
		TextView phoneNumberLabel = new TextView(_context);
		phoneNumberLabel.setText("Phone Number:");
		_phoneNumber = new TextView(_context);
		_phoneNumber.setText(phone);
		
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
