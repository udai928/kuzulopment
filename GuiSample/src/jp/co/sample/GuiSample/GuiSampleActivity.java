package jp.co.sample.GuiSample;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class GuiSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_sample);
        Button button =(Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		EditText edit = (EditText)findViewById(R.id.editText1);
        		if (edit.getText().length() == 0){
        				TextView text = (TextView)findViewById(R.id.textView1);
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
    		break;
    	case R.id.item3:
    		android.util.Log.v("onOptionsItemSelected","Menu3");
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
}
