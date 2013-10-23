package jp.co.sample.MultiMediaViewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View{

	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.LTGRAY);
	}
	@Override
	protected void onDraw(Canvas canvas){
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
	}

}
