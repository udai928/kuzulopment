package jp.co.sample.MultiMediaViewer;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

public class MultiMediaViewerActivity extends Activity {

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
}
