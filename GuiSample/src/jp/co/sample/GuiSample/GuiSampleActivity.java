package jp.co.sample.GuiSample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class GuiSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_sample);
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
}
