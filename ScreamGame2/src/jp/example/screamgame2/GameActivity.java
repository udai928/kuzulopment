package jp.example.screamgame2;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.annotation.SuppressLint;
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
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi") public class GameActivity extends Activity {
	private MediaPlayer player = null;
	private changeView1 right1 = null;
	private changeView2 right2 = null;
	private Bitmap resize_pic, pic;
	private Camera camera = null;
//	private String pictureFilePath = null;
	private MediaRecorder videoRecorder = null;
	private String videoFilePath = null;
	private int numberOfCameras = 0;
	private int cameraId = 0;
	private boolean isRecording;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackgroundColor(Color.argb(255,255,153,204));
		setContentView(frameLayout);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
		public void onDraw(Canvas canvas1) {//一個目の正解表示設定
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
		
		public changeView2(Context context2){//二個目の正解表示設定
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
		isRecording = false;
		switch(event.getAction()){ 
		case MotionEvent.ACTION_DOWN :
			if((event.getX()>100 && event.getX()<150)&&(event.getY()>370 && event.getY()<415) || 
					(event.getX()>340 && event.getX()<390)&&(event.getY()>370 && event.getY()<415)){
				
				Toast.makeText(this, "right1!!", Toast.LENGTH_SHORT).show();//正解の表示
				right1 = new changeView1(this);//正解の丸
				addContentView(right1, new LayoutParams(500, 500));//丸表示

				startRecord();//カメラスタート
			}
			if((event.getX()>125 && event.getX()<165)&&(event.getY()>540 && event.getY()<563)||
					(event.getX()>385 && event.getX()<405)&&(event.getY()>540 && event.getY()<563)){
				
				Toast.makeText(this, "right2!!", Toast.LENGTH_SHORT).show();
				right2 = new changeView2(this);
				addContentView(right2, new LayoutParams(500, 700));//表示範囲（）
				
				pic = BitmapFactory.decodeResource(getResources(), R.drawable.screampic1_2);
				resize_pic = Bitmap.createScaledBitmap(pic, 100, 100, true);//リサイズ
				
				ImageView img = new ImageView(this);
				img.setImageBitmap(resize_pic);
				setContentView(img, new LayoutParams(1000, 1000));//恐怖画像表示
				
				sound();//音声メソッド	
				
				new Handler().postDelayed( delayStop, 2000);//2000msc録画を停止
			}
			 android.util.Log.v("MotionEvent", "x = " + String.valueOf(event.getX()) + ", " + "y = " + String.valueOf(event.getY()));
			 break;	
		}
		return true;
	}

    private void startRecord(){

    	try{
    		if(isRecording != false){
    			videoRecorder.stop();
    			videoRecorder.release();
    			videoRecorder = null;
    			camera.lock();
    			isRecording = false;
    		}else{    			
	        	
	    		numberOfCameras = Camera.getNumberOfCameras(); //フロントカメラ、バックカメラの判別
	    		CameraInfo cameraInfo = new CameraInfo();
	    		for (int i = 0; i < numberOfCameras; i++) {
	    			Camera.getCameraInfo(i, cameraInfo);
	    			android.util.Log.v("cameraId", ""+i);
	    			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT){
	    				cameraId = i;
	    			}
	    		}
//	    		camera = Camera.open(cameraId); 
//	    		camera.unlock();
	    		android.util.Log.v("set camera","ok");

	    		videoRecorder = new MediaRecorder();
	        	
	    		videoRecorder.setVideoSource(cameraId);
	    		videoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    		videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    		videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	    		videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

	    		File dir = new File(Environment.getExternalStorageDirectory(), "screamGame2_file");//録画用ファイル作成
	    	    if( !dir.exists()){
	    	       dir.mkdir();
	    	    }
	    		File file = new File(dir, "screamGame2_video.3gp");//録画用ファイル
	    		videoFilePath = file.getAbsolutePath();	    		
	    		videoRecorder.setOutputFile(videoFilePath); // 動画の出力先となるファイルパスを指定
	    		
	    		videoRecorder.setVideoFrameRate(30);// 動画のフレームレートを指定
	    		videoRecorder.setVideoSize(320, 240); // 動画のサイズを指定
	    		videoRecorder.prepare();
	    		videoRecorder.start();
	    		isRecording = true;
	    		android.util.Log.v("videoRecorder","start!!!");
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		android.util.Log.v("videoRecorder","strat error......");
    	}
    }
   
    private final Runnable delayStop = new Runnable() {
    	@Override
    	public void run(){
        	try{
        		videoRecorder.stop();
    			videoRecorder.release();
    			videoRecorder = null;
    			isRecording = false;
        		android.util.Log.v("videoRecorder","stop!!!");
        	}
        	catch(Exception e){
        		e.printStackTrace();
        		android.util.Log.v("videoRecorder","stop error......");
        	}
    	}
    };
    private void sound(){    	
	    player = MediaPlayer.create(GameActivity.this , R.raw.takao_voice1_2);
		if (player.isPlaying()) { //再生中
	        player.stop();
	        try {
	            player.prepare();
	        } catch (IllegalStateException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    else { //停止中
	        player.start();
	        android.util.Log.v("player","voice start!");
	    }
    }
}
