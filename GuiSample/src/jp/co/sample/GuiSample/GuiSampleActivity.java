package jp.co.sample.GuiSample;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuiSampleActivity extends Activity implements Runnable{	
	private ProgressDialog progressDialog = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_sample);
        //progressDialog = ProgressDialog.show(this,"","Loding...", true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        new Thread(this).start();
        Button button =(Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		EditText edit = (EditText)findViewById(R.id.editText1);
        		if (edit.getText().length() == 0){
        				TextView text = (TextView)findViewById(R.id.dialogMsg);
        				text.setText("Error input user");
        		}
        	}
        });
        button.setEnabled(false);
        EditText edit = (EditText)findViewById(R.id.editText1);
		edit.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View v, int keyCode, KeyEvent event){
				Button button = (Button)findViewById(R.id.button1);
				button.setEnabled(((EditText)v).getText().length() > 0 ? true : false);
				return false;
			}		
		});
		//↓は、javaのコードから画面のチェックボックスを追加する
		LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout2);
		CheckBox check = new CheckBox(this);
		check.setText(R.string.remember);
		check.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				android.util.Log.v("OnCheckedChanged","check " + isChecked);
			}
		});
		layout.addView(check);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gui_sample, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){	
    	case R.id.item1:
    		android.util.Log.v("onOptionsItemSelected","Menu1");
    		showDialog();
    		break;
    	case R.id.item2:
    		android.util.Log.v("onOptionsItemSelected","Menu2");
    		showChooseColorDialog();
    		break;
    	case R.id.item3:
    		android.util.Log.v("onOptionsItemSelected","Menu3");
    		showCustomDialog();
    		break;
    	case R.id.item4:
    		android.util.Log.v("onOptionsItemSelected","Menu4");
    		finish();
    		break;
    	}
    	return true;
    }
    
    private void showDialog(){
    	new AlertDialog.Builder(this)	
    		.setTitle(R.string.dialog_title)
    		.setMessage(R.string.dialog_message)
    		.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener(){
    			public void onClick(DialogInterface diaog, int which){
    				android.util.Log.v("dialog", "onClick yes");
    			}
    		})
    		.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener(){
    			public void onClick(DialogInterface diaog, int which){
    				android.util.Log.v("dialog", "onClick no");
    			}
    		})
    		.show();
    }
    private void showChooseColorDialog(){
    	CharSequence[] items ={"Red", "Green", "Blue"};
    	new AlertDialog.Builder(this)
    		.setTitle("Choose Color")
    		.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
//    		.setMultiChoiceItems(items, , new DialogInterface.OnClickListener(){
    	    			public void onClick(DialogInterface dialog, int which){
    				LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout2);
    				switch(which){
    				case 0:
    					layout.setBackgroundColor(Color.RED);
    				break;
    				case 1:
    					layout.setBackgroundColor(Color.GREEN);
    				break;
    				case 2:
    					layout.setBackgroundColor(Color.BLUE);
    				break;
    				}
    			}
    		})
    		.show();
    }


	@Override
	public void run() {
		try{
			Thread.sleep(1000);
			progressDialog.setProgress(25);
			Thread.sleep(1000);
			progressDialog.setProgress(50);
			Thread.sleep(1000);
			progressDialog.setProgress(75);
			Thread.sleep(1000);
			progressDialog.setProgress(100);
		}catch(InterruptedException e){
			e.printStackTrace();
		}if(progressDialog != null){
			progressDialog.dismiss();
			progressDialog =null;
		}
	}
	private void showCustomDialog(){
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Custom Dialog");
		TextView text =(TextView)dialog.findViewById(R.id.dialogMsg);
		text.setText("ダイアログメッセージを変更");
		dialog.show();
	}
}
