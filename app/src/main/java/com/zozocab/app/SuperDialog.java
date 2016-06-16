package com.zozocab.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



/**
 * @author Sherif
 * 
 * Use this class to create Dialogs
 * 
 * Add as much dialogs as you want
 * 
 * Finally call SuperDialog.createDialog to create a dialog from anywhere
 *
 */
public class SuperDialog extends AppCompatActivity {

	//Edit these : Add as much dialogs as you want
	public final static int DIALOG_1 = 0;
	public final static int DIALOG_2 = 1;
	public final static int DIALOG_3 = 2;
	public final static int DIALOG_4 = 3;
	public final static int DIALOG_ERROR = 4;
	
	//Now edit this function


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialog(this.getIntent().getExtras().getInt("dialog"));
    }

    /**
     * @author Sherif
     * @param dialog The dialog you want to show
     * @param context The context from which you are calling
     * 
     * this function will create a global dialog for you
     * the dialog will appear no matter which activity or screen is showing
     */
    public static void createDialog(int dialog, Context context){
    	Intent myIntent = new Intent(context, SuperDialog.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("dialog", dialog);
    	myIntent.putExtras(bundle);
    	myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(myIntent);
    }

}