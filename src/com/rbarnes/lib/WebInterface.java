package com.rbarnes.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebInterface {
	static Boolean _connected = false;
	static String _connectionType = "Unavailable";
	
	//Get the type of network connection
	public static String getConnectionType(Context context){
		netInfo(context);
		return _connectionType;
	}
	//Check to see if there is an Internet connection
	public static Boolean getConnectionStatus(Context context) {
		netInfo(context);
		return _connected;
	}
	//Retrieve network info.
	private static void netInfo(Context context){
		ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cManager.getActiveNetworkInfo();
		if(nInfo != null){
			
			if(nInfo.isConnected()){
				_connectionType = nInfo.getTypeName();
				_connected = true;
				
			}
		}
	}
}
