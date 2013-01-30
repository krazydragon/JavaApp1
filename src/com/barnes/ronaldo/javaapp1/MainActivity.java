//Ronaldo Barnes
//Java 1 week 3
//January 2013
//Full Sail University
package com.barnes.ronaldo.javaapp1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;




import com.rbarnes.lib.FileInterface;
import com.rbarnes.lib.WebInterface;
import com.rbarnes.other.Dessert;



import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	Context _context;
	LinearLayout _thisLayout;
	Boolean _connected = false;
	InputForm _input;
	RadioGroup _dessertOptions;
	ArrayList<Dessert> _desserts;
	HashMap<String, String> _oldLocation;
	EditText _inputField;
	LocationDisplay _location;
	Toast _toast;
	JSONObject _tempLocation;
	String _titleStr;
	String _addressStr;
	String _cityStr;
	String _stateStr;
	String _phoneStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_context = this;
		_thisLayout = new LinearLayout(this);
		LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		_thisLayout.setLayoutParams(lParams); 
		_thisLayout.setOrientation(LinearLayout.VERTICAL);
		_input = new InputForm(_context, "Please enter zipcode", "Submit");
		_oldLocation = getOldLocation();
		if(_oldLocation != null){
			_toast = Toast.makeText(_context, "No network conntection last search is loaded.", Toast.LENGTH_LONG);
			_titleStr = _oldLocation.get("Title").toString();
			_addressStr = _oldLocation.get("Address").toString();
			_cityStr = _oldLocation.get("City").toString();
			_stateStr = _oldLocation.get("State").toString();
			_phoneStr = _oldLocation.get("Phone").toString();
		}else{
			_oldLocation = new HashMap<String, String>();
			_toast = Toast.makeText(_context, "No network or files found" , Toast.LENGTH_LONG);
			_titleStr = "";
			_addressStr = "";
			_cityStr = "";
			_stateStr = "";
			_phoneStr = "";
		}
		//Display introduction text
		TextView introView = new TextView(this);
	    introView.setText("Find the closest dessert to you");
		
	    
		
		//Detect form elements
	    _inputField = _input.getField();
		Button inputButton = _input.getButton();
		
		//Detect button click
		inputButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Check network connection
				_connected = WebInterface.getConnectionStatus(_context);
				if(_connected){
					Log.i("NETWORK CONNECTION", WebInterface.getConnectionType(_context));
					int selectedButtonId = _dessertOptions.getCheckedRadioButtonId();
					RadioButton selectedButton = (RadioButton) _dessertOptions.findViewById(selectedButtonId);
					String buttonText = (String) selectedButton.getText();
					
					getLocations(buttonText,_inputField.getText().toString());
				}else{
					//Show data
					//Add Location Display
					_location = new LocationDisplay(_context, _titleStr, _addressStr, _cityStr, _stateStr, _phoneStr);
					_thisLayout.addView(_location);
					_toast.show();
				}
				
				
				
				
				
			}
		});
		
		//Set up dessert options
		_desserts = new ArrayList<Dessert>();
		_desserts.add(new Dessert("Cookies"));
		_desserts.add(new Dessert("Cakes"));
		_desserts.add(new Dessert("Pies"));
		_desserts.add(new Dessert("Candy"));
				
		String[] dessertNames = new String[_desserts.size()];
		for(int i=0; i<_desserts.size(); i++){
			dessertNames[i] = _desserts.get(i).getName();
		}
				
		_dessertOptions = InputForm.getGroup(this, dessertNames);
		
		
		
		
		_thisLayout.addView(introView);
		_thisLayout.addView(_dessertOptions);
		_thisLayout.addView(_input);
		
		
		setContentView(_thisLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	//Submit search
	private void getLocations(String dessert, String zipCode){
		String baseUrl = "http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=qJIjRlbV34GJZfg2AwqSWVV03eeg8SpTQKy5PZqSfjlRrItt5hS2n3PIysdPU_CCIQlCGXIGjoTDESp3l42Ueic3O1EaYXU-&query="+dessert+"&zip="+zipCode+"&results=1&output=json";
		URL finalURL;
		try{
			finalURL = new URL(baseUrl);
			LocationRequest lr = new LocationRequest();
			lr.execute(finalURL);
			
		}catch(MalformedURLException e){
			Log.e("BAD URL","MALFORMED URL");
			finalURL = null;
		}
	}
	
	//Look for saved HashMap
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getOldLocation(){
		Object stored = FileInterface.readObjectFile(_context, "oldLocation", false);
		
		HashMap<String, String> oldLocation;
		if(stored == null){
			Log.i("OLD LOCATION", "NO OLD LOCATION FILE FOUND");
			oldLocation = null;
		}else{
			oldLocation = (HashMap<String, String>) stored;
		}
		return oldLocation;
	}
	//get results
	private class LocationRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			
			for(URL url: urls){
				
				response = WebInterface.getUrlStringResponse(url);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try{
				JSONObject json = new JSONObject(result);
				JSONObject locations = json.getJSONObject("ResultSet");
				if(locations.getString("totalResultsAvailable").compareTo("0")==0){
					_toast = Toast.makeText(_context, "No Results" , Toast.LENGTH_SHORT);
					_toast.show();
				}else{
					JSONObject location = locations.getJSONObject("Result");
					if(location != null){
						_toast = Toast.makeText(_context, "Saving File.", Toast.LENGTH_SHORT);
						_toast.show();
						_oldLocation.put("Title", location.getString("Title"));
						_oldLocation.put("Address", location.getString("Address"));
						_oldLocation.put("City", location.getString("City"));
						_oldLocation.put("State", location.getString("State"));
						_oldLocation.put("Phone", location.getString("Phone"));
						
						
						
						
						
						//Save File
						FileInterface.storeObjectFile(_context, "oldLocation", _oldLocation, false);
						//Show data
						//Add Location Display
						_location = new LocationDisplay(_context, location.getString("Title"), location.getString("Address"), location.getString("City"), location.getString("State"), location.getString("Phone"));
						_thisLayout.addView(_location);
					}else{
						_toast = Toast.makeText(_context, "Something went wrong" , Toast.LENGTH_SHORT);
						_toast.show();
					}
				}
				
			}catch(JSONException e){
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
			
		}
	}

}
