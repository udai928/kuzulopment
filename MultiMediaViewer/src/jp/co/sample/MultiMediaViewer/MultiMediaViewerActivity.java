package jp.co.sample.MultiMediaViewer;

import java.io.File;
import java.io.FileOutputStream;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class MultiMediaViewerActivity extends Activity {
	
	private MediaPlayer player = null;
	private Button play = null;
	private Button stop = null;
	private MediaRecorder recorder = null;
	private Button recorderPlay = null;
	private Button recorderStop = null;
	private Button recorderRecord = null;
	private String soundFilePath = null;
	private SurfaceView cameraView = null;
	private Camera camera = null;
	private String pictureFilePath = null;
	private SurfaceView videoCameraView = null;
	private MediaRecorder videoRecorder = null;
	private String videoFilePath = null;
	private boolean isRecording = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        setContentView(view);
        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(new RotateAnimation(0, 360, 300, 400));
        animation.addAnimation(new ScaleAnimation(0.4f, 2, 0.4f, 2, 300, 400));
        animation.setDuration(4000);
        view.startAnimation(animation);
        File dir = new File(Environment.getExternalStorageDirectory(), "MultiMediaViewer");
        if( !dir.exists()){
        	dir.mkdir();
        }
        File file = new File(dir,"record.3gp");
        soundFilePath = file.getAbsolutePath();
        file = new File(dir, "picture.jpg");
        pictureFilePath = file.getAbsolutePath();
        file = new File(dir, "video.3gp");
        videoFilePath = file.getAbsolutePath();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multi_media_viewer, menu);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e){
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		new AlertDialog.Builder(this)
    			.setTitle("終了の確認")
    			.setMessage("本当に終了しますか")
    			.setPositiveButton("はい", new DialogInterface.OnClickListener(){
    				public void onClick(DialogInterface dialog, int which){
    						finish();
    				}
    			})
    			.setNegativeButton("いいえ", new DialogInterface.OnClickListener(){
    				public void onClick(DialogInterface dialog, int which){
    				}
    			})
    			.show();
    		return true;
    	}
    	else
    		return false;
    }
    
    private void setupAudioView(){
    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	play = new Button(this);
    	play.setText("play");
    	play.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			if(player == null){
    				player = MediaPlayer.create(MultiMediaViewerActivity.this, R.raw.fallbackring);
    				player.setOnCompletionListener(new OnCompletionListener(){
    					public void onCompletion(MediaPlayer mp){
    						player.release();player = null;
    					}
    				});
    				player.start();
    			}
    		}
	    });
	    layout.addView(play);
	    stop = new Button(this);
	    stop.setText("stop");;
	    stop.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		if(player != null){
	    			player.stop();
	    			player.release();
	    			player = null;
	    		}
	    	}
	    });
	    layout.addView(stop);
	    setContentView(layout);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.item1:
    		setupAudioView();
    		break;
    	case R.id.item2:
    		setupAudioRecordView();
    		break;
    	case R.id.item3:
    		setupCameraView();
    		break;
    	case R.id.item4:
    		setupVideoView();
    		break;
    	}
    	return true;
    }
    private void setupAudioRecordView(){
    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	recorderPlay = new Button(this);
    	recorderPlay.setText("play");
    	recorderPlay.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			if(player == null){
    				player = new MediaPlayer();
    				try{
    					player.setDataSource(soundFilePath);
    					player.prepare();
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    				player.setOnCompletionListener(new OnCompletionListener(){
    					public void onCompletion(MediaPlayer mp){
    						player.release();
    						player = null;
    					}
    				});
    				player.start();
    			}
    		}
    	});
    	layout.addView(recorderPlay);
    	recorderStop = new Button(this);
    	recorderStop.setText("stop");
    	recorderStop.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			if(player != null){
    				player.stop();
    				player.release();
    				player = null;;
    			}
    			if(recorder != null){
    				recorder.stop();
    				recorder.release();
    				recorder = null;;
    			}
    		}
    	});
    	layout.addView(recorderStop);
    	recorderRecord = new Button(this);
    	recorderRecord.setText("record");
    	recorderRecord.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			if(recorder == null){
    				if(player != null){
        				player.stop();
        				player.release();
        				player = null;;
        			}
    				recorder = new MediaRecorder();
    				try{
    					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    					recorder.setOutputFile(soundFilePath);
    					recorder.prepare();
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    				recorder.start();
    			}
    		}
    	});
    	layout.addView(recorderRecord);
    	setContentView(layout);
    }
    private void setupCameraView(){
    	cameraView = new SurfaceView(this);
    	SurfaceHolder holder = cameraView.getHolder();
    	holder.addCallback(new SurfaceHolder.Callback(){
    		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    			try{
    				WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
    				int r = manager.getDefaultDisplay().getRotation();
    				int d = 0;
    				switch(r){
    				case Surface.ROTATION_0: d = 90; break;
    				case Surface.ROTATION_90: d = 0; break;
    				case Surface.ROTATION_180: d = 270; break;
    				case Surface.ROTATION_270: d = 180; break;
    				}
    				camera.setDisplayOrientation(d);
    				camera.startPreview();
    				
    			}
    			catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    		@SuppressLint("NewApi") public void surfaceCreated(SurfaceHolder holder){
    			try{
    				camera = Camera.open();
    				camera.setPreviewDisplay(holder);
    			}
       			catch(Exception e){
    				e.printStackTrace();
       			}
    		}
    		public void surfaceDestroyed(SurfaceHolder holder){
    			camera.setPreviewCallback(null);
    			camera.stopPreview();
    			camera.release();
    			camera = null;
    		}
    	});
    	holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    	setContentView(cameraView);
    }
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
    @Override
    public boolean onTouchEvent(MotionEvent e){
    	int action = e.getAction() & MotionEvent.ACTION_MASK;
    	if(action == MotionEvent.ACTION_DOWN){
    		if(cameraView != null){
    			callTakePicture();
    		}
    		if(videoCameraView != null){
    			startStopRecord();
    		}
    	}
    	return true;
    }
    private void setupVideoView(){
    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	SurfaceHolder holder = videoCameraView.getHolder();
    	holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    	layout.addView(videoCameraView);
    	setContentView(layout);
    }
    private void startStopRecord(){
    	try{
    		if(isRecording){
    			videoRecorder.stop();
    			videoRecorder.release();
    			videoRecorder = null;
    			isRecording = false;
    		}else{
    			videoRecorder = new MediaRecorder();
    			videoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
    			videoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    			videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    			videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    			videoRecorder.setOutputFile(videoFilePath);
    			videoRecorder.setVideoFrameRate(30);
    			videoRecorder.setPreviewDisplay(videoCameraView.getHolder().getSurface());
    			videoRecorder.prepare();
    			isRecording =true;
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
}