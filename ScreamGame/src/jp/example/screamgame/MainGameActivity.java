package jp.example.screamgame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainGameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		WindowManager windowManager = getWindowManager();
		Display disp = windowManager.getDefaultDisplay();
		int width = disp.getWidth();
		int height = disp.getHeight();
		
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(new MyView(this));
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

	static public class MyView extends View {
		Paint paint = new Paint();
		private float x1=10;
		private float x2=50;
		private float x3=80;
		private float y=60;//画面外からスタートさせるから、負の数
		private final TimerTask task = new TimerTask(){
			public void run(){
				y+=1;
				postInvalidate();
			}
		};
		
		public MyView(Context context) {
	           super(context);
	    }
		@Override
		protected void onDraw(Canvas canvas){			 
			paint.setStrokeWidth(12);
			paint.setColor(Color.argb(255, 0, 0, 255));
			float[] pts = {x1, y, x2, y, x3, y};
			canvas.drawPoints( pts, paint);
			Timer timer = new Timer();
			timer.schedule(task, 0L, 100L);
		}
//		public void onPause(){
//			timer.cancel();
//			timer = null;
//		}
	}
}
