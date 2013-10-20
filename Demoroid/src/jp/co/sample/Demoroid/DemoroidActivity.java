package jp.co.sample.Demoroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DemoroidActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	android.util.Log.v("タグ１","開始メッセージ");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demoroid);
        android.util.Log.v("タグ２","終了メッセージ");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demoroid, menu);
        return true;
    }
    
}
//github test