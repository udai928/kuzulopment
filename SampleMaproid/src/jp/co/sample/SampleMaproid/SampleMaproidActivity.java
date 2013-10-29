package jp.co.sample.SampleMaproid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;

public class SampleMaproidActivity extends Activity implements SensorEventListener{
	private float x = 0;
	private float y = 0;
	private SensorView view = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new SensorView(this);
        setContentView(view);
    }

    class SensorView extends View{
    	private Paint paint = new Paint();
    	
    	public SensorView(Context context){
    		super(context);
    		paint.setColor(Color.GREEN);
    	}
    	public void onDraw(Canvas canvas){
    		canvas.translate(getWidth() / 2, getHeight() / 2);
    		canvas.drawRect(0,  -20, x, 20, paint);
    		canvas.drawRect(-20,  0, 20, y, paint);
    	}
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_maproid, menu);
        return true;
    }
    @Override
    public void onResume(){
    	super.onPause();
    	SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    	Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause(){
    	super.onPause();
    	SensorManager sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    	sensorManager.unregisterListener(this);
    }


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
			x = -event.values[0] * 40f;
			y = event.values[1] * 40f;
			view.invalidate();
		}
	}
    
}
