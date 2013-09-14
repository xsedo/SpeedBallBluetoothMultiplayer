package ro.game.speedball;

import ro.game.speedball.opengl.Level01Renderer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class GameActivity extends Activity
{
	private GLSurfaceView mGLSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mGLSurfaceView = new GLSurfaceView(this);
		 
	    // Check if the system supports OpenGL ES 2.0.
	    final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
	    final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
	 
	    if (supportsEs2)
	    {
	        // Request an OpenGL ES 2.0 compatible context.
	        mGLSurfaceView.setEGLContextClientVersion(2);
	 
	        // Set the renderer to our demo renderer, defined below.
	        mGLSurfaceView.setRenderer(new Level01Renderer());
	    }
	    else
	    {
	    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

        	alert.setMessage("Your device doesn't support OpenGL ES 2.0 or higher");
        	alert.setTitle("OpenGL support");
        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					finish();
				}
			});
        	alert.create().show();
	        return;
	    }
	 
	    setContentView(mGLSurfaceView);
	}

	@Override
	protected void onResume()
	{
	    super.onResume();
	    mGLSurfaceView.onResume();
	}
	 
	@Override
	protected void onPause()
	{
	    super.onPause();
	    mGLSurfaceView.onPause();
	}
}
