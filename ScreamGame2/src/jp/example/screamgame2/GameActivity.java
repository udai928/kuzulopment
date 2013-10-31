package jp.example.screamgame2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends Activity {
	private changeView right = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackgroundColor(Color.argb(255,255,153,204));
		setContentView(frameLayout);
		
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.screamgame1_3);
		addContentView(iv, new LayoutParams(490, 700));
		
		Intent intent = getIntent();
		if(intent != null){
			String str = intent.getStringExtra("jp.example.screamgame2.GameActivity");
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
		}
	}
	class changeView extends View{
		private Paint paint = new Paint();
	
		public changeView(Context context){
			super(context);
		}
		public void onDraw(Canvas canvas) {
		    Paint paint = new Paint();
		    paint.setColor(Color.argb(255, 255, 255, 255));
		    paint.setAntiAlias(true);
		    canvas.drawCircle(128, 280, 30, paint);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){ 
		if((event.getX()>115 && event.getX()<141)&&(event.getY()>376 && event.getY()<400)){
			Toast.makeText(this, "right!!", Toast.LENGTH_LONG).show();
			right = new changeView(this);
			addContentView(right, new LayoutParams(360, 388));
		}
		if((event.getX()>352 && event.getX()<376)&&(event.getY()>376 && event.getY()<400)){
			Toast.makeText(this, "right!!", Toast.LENGTH_LONG).show();
		}
		
		if((event.getX()>130 && event.getX()<152)&&(event.getY()>547 && event.getY()<557)){
			Toast.makeText(this, "right!!", Toast.LENGTH_LONG).show();
		}
		if((event.getX()>380 && event.getX()<400)&&(event.getY()>547 && event.getY()<557)){
			Toast.makeText(this, "right!!", Toast.LENGTH_LONG).show();
		}

		 android.util.Log.v("MotionEvent",
			        "x = " + String.valueOf(event.getX()) + ", " +
			        "y = " + String.valueOf(event.getY()));
		return true;
	}
}
