package jp.co.sample.SampleMaproid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;

public class SampleMaproidActivity extends Activity implements SensorEventListener, LocationListener{
	private float x = 0;
	private float y = 0;
	private SensorView view = null;
	private float[] accValues  = new float[3];
	private boolean hasAccValues = false;
	private float[] magValues = new float[3];
	private boolean hasMagValues = false;
	private float[] R1 = new float[16];
	private float[] R2 = new float[16];
	private float[] I = new float[16];
	private float[] azimuthPitchRoll = new float[3];
	private float px = 0;
	private float py = 0;
	private double latitude = 0;
	private double longitude = 0;
	
	
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
    		paint.setColor(Color.BLACK);
    		paint.setTextSize(36);
    		canvas.drawText("Latitude:" + latitude, 10,  40,  paint);
    		canvas.drawText("Longitude:" + longitude, 10,  80,  paint);
    		canvas.translate(getWidth() / 2, getHeight() / 2);
    		canvas.save();
    		canvas.rotate((float)Math.toDegrees(azimuthPitchRoll[0]));
    		paint.setColor(Color.GRAY);
    		canvas.drawCircle(0,  0,  150,  paint);
    		paint.setColor(Color.BLUE);
    		canvas.drawCircle(0, -150, 30, paint);
    		canvas.restore();
    		paint.setColor(Color.BLACK);
    		paint.setTextSize(56);
    		canvas.drawText("N", -28, -160, paint);
    		canvas.drawText("S", -28, 240, paint);
    		canvas.drawText("W", -210, 30, paint);
    		canvas.drawText("E", 160, 30, paint);
    		paint.setColor(Color.GREEN); 
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
    	sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    	sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    	sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    	if(sensor != null){
    		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    	}
    	LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, this);
    }
    @Override
    public void onPause(){
    	super.onPause();
    	SensorManager sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    	sensorManager.unregisterListener(this);
    	LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    			locationManager.removeUpdates(this);
    }


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		final float alpha = 0.8f;
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
			px = px * (1.0f - alpha) + event.values[0] * alpha;
			py = py * (1.0f - alpha) + event.values[1] * alpha;
			x = -px * 40f;
			y = py * 40f;
			copyValues(accValues, event.values);
			hasAccValues = true;
		}else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			copyValues(magValues, event.values);
			hasMagValues = true;
		}else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
android.util.Log.v("Samoke Maproid", "on touch sennsor running!!");
			view.setBackgroundColor((event.values[0] == 0) ? Color.GRAY : Color.WHITE);
		}
		if(hasAccValues && hasMagValues){
			SensorManager.getRotationMatrix(R1, I, accValues, magValues);
			SensorManager.remapCoordinateSystem(R1,  SensorManager.AXIS_X,  SensorManager.AXIS_Z,  R2);
			SensorManager.getOrientation(R2, azimuthPitchRoll);
			hasAccValues = hasMagValues = false;
			view.invalidate();
		}
		
	}
	
	private void copyValues(float[] dst, float[]src){
		dst[0] = src[0];
		dst[1] = src[1];
		dst[2] = src[2];
	}
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
    
}
