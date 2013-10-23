package jp.co.sample.MultiMediaViewer;

import java.io.File;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
    	recorderRecord.setText("play");
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
}