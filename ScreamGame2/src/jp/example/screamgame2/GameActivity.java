package jp.example.screamgame2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region.Op;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends Activity {
	private changeView1 right1 = null;
	private changeView2 right2 = null;
	private Bitmap resize_pic, pic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackgroundColor(Color.argb(255,255,153,204));
		setContentView(frameLayout);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.screamgame1_3);
		addContentView(iv, new LayoutParams(490, 700));
		
		Intent intent = getIntent();
		if(intent != null){
			String str = intent.getStringExtra("jp.example.screamgame2.GameActivity");
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
		}
	}
	class changeView1 extends View{
		private Paint paint1 = new Paint();
		private Paint paint2 = new Paint();
		private Path clipPath1 = new Path();
		private Path clipPath2 = new Path();

		public changeView1(Context context1){
			super(context1);
		}
		public void onDraw(Canvas canvas1) {
			clipPath1.addCircle(130, 280, 25, Path.Direction.CW);
		    canvas1.clipPath(clipPath1, Op.DIFFERENCE);
		    paint1.setColor(Color.RED);
		    paint1.setAntiAlias(true);
		    canvas1.drawCircle(130, 280, 30, paint1);
		    
		    clipPath2.addCircle(377, 280, 25, Path.Direction.CW);
		    canvas1.clipPath(clipPath2, Op.DIFFERENCE);
		    paint2.setColor(Color.RED);
		    paint2.setAntiAlias(true);
		    canvas1.drawCircle(377, 280, 30, paint2);
		    
		}
	}
	class changeView2 extends changeView1{
		private Paint paint3 = new Paint();
		private Paint paint4 = new Paint();
		private Path clipPath3 = new Path();
		private Path clipPath4 = new Path();
		
		public changeView2(Context context2){
			super(context2);
		}
		public void onDraw(Canvas canvas2) {
			clipPath3.addCircle(145, 454, 25, Path.Direction.CW);
		    canvas2.clipPath(clipPath3, Op.DIFFERENCE);
		    paint3.setColor(Color.RED);
		    paint3.setAntiAlias(true);
		    canvas2.drawCircle(145, 454, 30, paint3);
		    
		    clipPath4.addCircle(390, 454, 25, Path.Direction.CW);
		    canvas2.clipPath(clipPath4, Op.DIFFERENCE);
		    paint4.setColor(Color.RED);
		    paint4.setAntiAlias(true);
		    canvas2.drawCircle(390, 454, 30, paint4);
		    
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){ 
		if((event.getX()>110 && event.getX()<146)&&(event.getY()>380 && event.getY()<405) || 
				(event.getX()>348 && event.getX()<381)&&(event.getY()>380 && event.getY()<405)){
			Toast.makeText(this, "right1!!", Toast.LENGTH_SHORT).show();
			right1 = new changeView1(this);
			addContentView(right1, new LayoutParams(500, 500));
			//カメラ起動開始
			
		}
		if((event.getX()>125 && event.getX()<165)&&(event.getY()>540 && event.getY()<563)||
				(event.getX()>385 && event.getX()<405)&&(event.getY()>540 && event.getY()<563)){
			//Toast.makeText(this, "right2!!", Toast.LENGTH_SHORT).show();
			//right2 = new changeView2(this);
			//addContentView(right2, new LayoutParams(500, 700));//表示範囲（）
			//画像表示、音声
			
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.screampic1_2);
			resize_pic = Bitmap.createScaledBitmap(pic, 100, 100, true);
			ImageView img = new ImageView(this);
			img.setImageBitmap(resize_pic);
			setContentView(img, new LayoutParams(700, 700));
		}
		
		 android.util.Log.v("MotionEvent",
			        "x = " + String.valueOf(event.getX()) + ", " +
			        "y = " + String.valueOf(event.getY()));
		return true;
	}
	
}
