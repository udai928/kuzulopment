package jp.co.sample.MultiMediaViewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class MyView extends View implements GestureDetector.OnGestureListener,
	ScaleGestureDetector.OnScaleGestureListener{
	private GestureDetector gestureDetector = null;
	private ScaleGestureDetector scaleGestureDetector = null;
	private Bitmap bitmap = null;
	private float x = 0f;
	private float y = 0f;
	private float scale = 1f;
	
	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.LTGRAY);
		bitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.ic_launcher);
		gestureDetector = new GestureDetector(context, this);
		scaleGestureDetector = new ScaleGestureDetector(context, this);
	}
	@Override
	protected void onDraw(Canvas canvas){
		canvas.translate(x, y);
		canvas.rotate(-20);
		canvas.scale(scale,  scale);
		canvas.scale(0.8f,  0.8f);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(48);
		paint.setAntiAlias(true);
		canvas.drawText("MyView Test", 20, 50, paint);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(3);
		canvas.drawLine(0,  0,  100, 100, paint);
		paint.setColor(Color.BLUE);
		canvas.drawRect(0, 100, 200, 200, paint);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(0,  200, 300, 300, paint);
		canvas.drawBitmap(bitmap, 20, 300, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e){
		if(e.getPointerCount() > 1){
			scaleGestureDetector.onTouchEvent(e);
			return true;
		}
		gestureDetector.onTouchEvent(e);
		return true;	
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		Animation animation = new ScaleAnimation(1, 3, 1, 3, x, y);
		animation.setDuration(2000);
		startAnimation(animation);
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		x -= distanceX;
		y -= distanceY;
		invalidate();
		return true;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		scale *= detector.getScaleFactor();
		invalidate();
		return false;
	}
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}
	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
