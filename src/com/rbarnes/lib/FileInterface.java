package com.rbarnes.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import android.content.Context;
import android.util.Log;

public class FileInterface {
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos =  new FileOutputStream(file);
			}else{
				fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			fos.write(content.getBytes());
			fos.close();
			
			
		}catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
public static Boolean storeObjectFile(Context context, String filename, Object content, Boolean external){
	try{
		File file;
		FileOutputStream fos;
		ObjectOutputStream oos;
		//Check if external storage flag is set
		if(external){
			file = new File(context.getExternalFilesDir(null), filename);
			fos =  new FileOutputStream(file);
		}else{
			Log.i("FILEOUT, ","WORKS");
			fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
		}
		oos =  new ObjectOutputStream(fos);
		oos.writeObject(content);
		oos.close();
		fos.close();
	}catch(IOException e){
		Log.e("WRITE ERROR", filename);
	}
		return true;
	}
	
}
