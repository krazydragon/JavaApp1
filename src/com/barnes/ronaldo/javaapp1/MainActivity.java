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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity implements OnClickListener {

	Context _context;
	Boolean _connected = false;
	InputForm _input;
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
	GridLayout _resultView;
	LinearLayout _inputLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		 _inputLayout = (LinearLayout)findViewById(R.id.InputFormLayout);
		_context = this;
		_input = new InputForm(_context, "Please enter zipcode", "Submit");
		_resultView = (GridLayout)findViewById(R.id.resultGridLayout);
		_oldLocation = getOldLocation();
		_connected = WebInterface.getConnectionStatus(_context);
		//Check network connection and local storage
		String tmpToast;
		if(_connected){
			
			if(checkStorage()){
				tmpToast = (String)WebInterface.getConnectionType(_context) + " is connected and saved file was found!";
			}else{
				tmpToast = (String)WebInterface.getConnectionType(_context) + " is connected and no files found!";;
			}
			_toast = Toast.makeText(_context, tmpToast, Toast.LENGTH_LONG);
			_toast.show();
		}else{
			if(checkStorage()){
				tmpToast = "Loading saved infomation no network connection found!";
				displayResults();
			}else{
				tmpToast = "No internet connection and no saved file found";
			}
			_toast = Toast.makeText(_context, tmpToast, Toast.LENGTH_LONG);
			_toast.show();
		}
		//Detect form elements
	    _inputField = _input.getField();
		Button inputButton = (Button)findViewById(R.id.inputButton);
		Button cookieButton = (Button)findViewById(R.id.cookieButton);
		Button pieButton = (Button)findViewById(R.id.pieButton);
		Button cakeButton = (Button)findViewById(R.id.cakeButton);
		Button candyButton = (Button)findViewById(R.id.candyButton);
		//Detect button click
		cookieButton.setOnClickListener(this);
		pieButton.setOnClickListener(this);
		cakeButton.setOnClickListener(this);
		candyButton.setOnClickListener(this);
		inputButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	//Display results
	private void displayResults(){
		
		
		TextView tempTitle = (TextView)findViewById(R.id.titleValue);
		TextView tempAddress = (TextView)findViewById(R.id.addressValue);
		TextView tempCity = (TextView)findViewById(R.id.cityValue);
		TextView tempState = (TextView)findViewById(R.id.stateValue);
		TextView tempPhone = (TextView)findViewById(R.id.phoneValue);
		tempTitle.setText(_titleStr);
		tempAddress.setText(_addressStr);
		tempCity.setText(_cityStr);
		tempState.setText(_stateStr);
		tempPhone.setText(_phoneStr);
		_resultView.setVisibility(View.VISIBLE);
		_inputLayout.setVisibility(View.INVISIBLE);
		
		
	}
	//
	private Boolean checkStorage(){
		if(_oldLocation != null){
			_titleStr = _oldLocation.get("Title").toString();
			_addressStr = _oldLocation.get("Address").toString();
			_cityStr = _oldLocation.get("City").toString();
			_stateStr = _oldLocation.get("State").toString();
			_phoneStr = _oldLocation.get("Phone").toString();
			return true;
		}else{
			_oldLocation = new HashMap<String, String>();
			_titleStr = "";
			_addressStr = "";
			_cityStr = "";
			_stateStr = "";
			_phoneStr = "";
			return false;
		}
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
						_titleStr = location.getString("Title");
						_addressStr = location.getString("Address");
						_cityStr = location.getString("City");
						_stateStr = location.getString("State");
						_phoneStr = location.getString("Phone");
						_oldLocation.put("Title",  _titleStr);
						_oldLocation.put("Address", _addressStr);
						_oldLocation.put("City", _cityStr);
						_oldLocation.put("State", _stateStr);
						_oldLocation.put("Phone", _phoneStr );
						//Save File
						FileInterface.storeObjectFile(_context, "oldLocation", _oldLocation, false);
						//Show data
						//Add Location Display
						displayResults();
						
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RadioGroup inputGroup = (RadioGroup)findViewById(R.id.inputRadioGroup);
		ImageView dessertView = (ImageView)findViewById(R.id.dessert_view);
		switch(v.getId()){
		case R.id.inputButton:
			int selectedButtonId = inputGroup.getCheckedRadioButtonId();
			RadioButton selectedButton = (RadioButton) findViewById(selectedButtonId);
			Spinner inputSpinner = (Spinner) findViewById(R.id.inputSpinner);
			String buttonText = (String) selectedButton.getText();
			String spinnerText = String.valueOf(inputSpinner.getSelectedItem());
			
			getLocations(buttonText,spinnerText);
		case R.id.cookieButton:
			dessertView.setImageResource(R.drawable.cookies);
			break;
		case R.id.pieButton:
			dessertView.setImageResource(R.drawable.pies);
			break;
		case R.id.cakeButton:
			dessertView.setImageResource(R.drawable.cakes);
			break;
		case R.id.candyButton:
			dessertView.setImageResource(R.drawable.candy);
			break;
		}
	}

}
