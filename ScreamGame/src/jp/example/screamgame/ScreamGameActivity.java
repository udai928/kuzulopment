package jp.example.screamgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScreamGameActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scream_game);
        Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scream_game, menu);
        return true;
    }
    
    @Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClassName("jp.example.screamgame","jp.example.screamgame.MainGameActivity");
		intent.putExtra("jp.example.screamgame.MainGameActivity", "main game running");
		startActivity(intent);
		android.util.Log.v("running!!!!!!!!!!!!!!!!!!", "a");
    }
    
}
