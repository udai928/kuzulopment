package jp.example.screamgame2;

import java.io.FileOutputStream;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi") public class GameActivity extends Activity {
	private MediaPlayer player = null;
	private changeView1 right1 = null;
	private changeView2 right2 = null;
	private Bitmap resize_pic, pic;
	private SurfaceView cameraView = null;
	private Camera camera = null;
	private String pictureFilePath = null;
	private MediaRecorder videoRecorder = null;
	private String videoFilePath = null;
	private boolean isRecording = false;	
	
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
		if((event.getX()>110 && event.getX()<146)&&(event.getY()>380 && event.getY()<405) || 
				(event.getX()>348 && event.getX()<381)&&(event.getY()>380 && event.getY()<405)){
			Toast.makeText(this, "right1!!", Toast.LENGTH_SHORT).show();//正解の表示
			right1 = new changeView1(this);//正解の丸
			addContentView(right1, new LayoutParams(500, 500));//丸表示
			startRecord();//カメラスタート
			
		}
		if((event.getX()>125 && event.getX()<165)&&(event.getY()>540 && event.getY()<563)||
				(event.getX()>385 && event.getX()<405)&&(event.getY()>540 && event.getY()<563)){
			//Toast.makeText(this, "right2!!", Toast.LENGTH_SHORT).show();
			//right2 = new changeView2(this);
			//addContentView(right2, new LayoutParams(500, 700));//表示範囲（）
			
			//画像表示、音声、録画停止
			
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.screampic1_2);
			resize_pic = Bitmap.createScaledBitmap(pic, 100, 100, true);
			
			ImageView img = new ImageView(this);
			img.setImageBitmap(resize_pic);
			setContentView(img, new LayoutParams(700, 700));//恐怖画像表示
//音声メソッド				
//			player = MediaPlayer.create(GameActivity.this , );
//			player.setOnCompletionListener(new OnCompletionListener(){
//				public void onCompletion(MediaPlayer mp){
//					player.release();player = null;
//				}
//			});
//			player.start();
			stopRecord();//少し遅らせて録画を停止handler?
			
		}
		
		 android.util.Log.v("MotionEvent",
			        "x = " + String.valueOf(event.getX()) + ", " +
			        "y = " + String.valueOf(event.getY()));
		return true;
	}
//	private void setupCameraView(){
//    	cameraView = new SurfaceView(this);
//    	SurfaceHolder holder = cameraView.getHolder();
//    	holder.addCallback(new SurfaceHolder.Callback(){
//    		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
//    			try{
//    				WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
//    				int r = manager.getDefaultDisplay().getRotation();
//    				int d = 0;
//    				switch(r){
//    				case Surface.ROTATION_0: d = 90; break;
//    				case Surface.ROTATION_90: d = 0; break;
//    				case Surface.ROTATION_180: d = 270; break;
//    				case Surface.ROTATION_270: d = 180; break;
//    				}
//    				camera.setDisplayOrientation(d);
//    				camera.startPreview();
//    				
//    			}
//    			catch(Exception e){
//    				e.printStackTrace();
//    			}
//    		}
//    		@SuppressLint("NewApi") public void surfaceCreated(SurfaceHolder holder){
//    			try{
//    				camera = Camera.open();
//    				camera.setPreviewDisplay(holder);
//    			}
//       			catch(Exception e){
//    				e.printStackTrace();
//       			}
//    		}
//    		public void surfaceDestroyed(SurfaceHolder holder){
//    			camera.setPreviewCallback(null);
//    			camera.stopPreview();
//    			camera.release();
//    			camera = null;
//    		}
//    	});
//    	holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    	setContentView(cameraView);
//    }
    private void callTakePicture(){
    	try{
    		camera.takePicture(null, null, new Camera.PictureCallback(){
    			public void onPictureTaken(byte[] data, Camera camera){    			
    				try{
    					FileOutputStream fos = new FileOutputStream(pictureFilePath);
    					fos.write(data);
    					fos.close();
    					camera.startPreview();
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		});
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    private void startRecord(){
    	try{	
    		videoRecorder = new MediaRecorder();
    		videoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
    		videoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    		videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    		videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
    		videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    		videoRecorder.setOutputFile("/sdcard/sample.3gp"); // 動画の出力先となるファイルパスを指定
    		videoRecorder.setVideoFrameRate(30);// 動画のフレームレートを指定
    		videoRecorder.setVideoSize(320, 240); // 動画のサイズを指定
    		videoRecorder.prepare();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	//カメラ起動,録画開始(カメラID＝１がインカメラ)
		camera = Camera.open(1);
		
    }
    private void stopRecord(){
    	try{
    		videoRecorder.stop();
			videoRecorder.release();
			videoRecorder = null;
			isRecording = false;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	
}
