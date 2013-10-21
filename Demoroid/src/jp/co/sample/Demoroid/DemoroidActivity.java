package jp.co.sample.Demoroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DemoroidActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demoroid);
        android.util.Log.v("Demoroid","onCreat()");
    }

	@Override
	protected void onStart(){
		super.onStart();
		android.util.Log.v("Demoroid","onStart()");
	}
	@Override
	protected void onRestart(){
		super.onRestart();
		android.util.Log.v("Demoroid","onRestart()");
	}
	@Override
	protected void onResume(){
		super.onResume();
		android.util.Log.v("Demoroid","onResume()");
	}
	@Override
	protected void onPause(){
		super.onPause();
		android.util.Log.v("Demoroid","onPause()");
	}
	@Override
	protected void onStop(){
		super.onStop();
		android.util.Log.v("Demoroid","onStop()");
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		android.util.Log.v("Demoroid","onDestroy()");
	}
}	