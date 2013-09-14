package ro.game.speedball;

import static ro.game.speedball.SpeedBallApplication.REQUEST_BLUETOOTH_ENABLE;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class CreateGameActivity extends Activity
{
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE MEMBERS
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BluetoothAdapter			mBluetoothAdapter;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PROTECTED OVERRIDES
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_creategame);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		//check if the user's device has bluetooth radio
        if (mBluetoothAdapter == null) 
        {
        	AlertDialog.Builder alert = new AlertDialog.Builder(this);

        	alert.setMessage("Your device doesn't have bluetooth radio");
        	alert.setTitle("No Bluetooth radio");
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
        if (!mBluetoothAdapter.isEnabled()) 
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH_ENABLE);
        }
        else
        {
        	startServer();
        }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_BLUETOOTH_ENABLE)
		{
			if(resultCode == RESULT_CANCELED)
			{
				//if the user doesn't want to enable bluetooth then we go back to menu
				finish();
			}
			else
			{
				startServer();
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void startServer()
	{
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
