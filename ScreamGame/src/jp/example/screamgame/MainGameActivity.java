package jp.example.screamgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	        
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
	    Button button = new Button(this);
	    button.setText("ボタンウィジェット");
	    layout.addView(button);
	    
	    
		Intent intent = getIntent();
		if(intent != null){
            String str = intent.getStringExtra("jp.example.screamgame.MainGameActivity");
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_game, menu);
		return true;
	}

	 static public class DrawTest extends View {
		 public DrawTest(Context context) {
	            super(context);
	     }
		 @Override
		 protected void onDraw(Canvas canvas) {
			 Paint paint = new Paint();
			 paint.setStrokeWidth(12);
			 paint.setColor(Color.argb(255, 0, 0, 255));
			 canvas.drawPoint(10, 20, paint);
	         float[] pts = {50, 50, 60, 60, 70, 70};
	         canvas.drawPoints(pts, paint);
	        }
	    }
	

}
