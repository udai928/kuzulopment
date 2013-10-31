package jp.example.screamgame2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackgroundColor(Color.argb(255,255,255,100));
		setContentView(frameLayout);
		View view =  getLayoutInflater().inflate(R.layout.activity_main, null);
		addContentView(view,new LayoutParams(490, 700));
        Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClassName("jp.example.screamgame2","jp.example.screamgame2.GameActivity");
		intent.putExtra("jp.example.screamgame2.GameActivity", "find three maistakes in the picture!");
		startActivity(intent);
		android.util.Log.v("running!!!!!!!!!!!!!!!!!!", "a");
	}    
    
}
