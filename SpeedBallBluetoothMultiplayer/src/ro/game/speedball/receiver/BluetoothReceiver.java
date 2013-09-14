package ro.game.speedball.receiver;

import ro.game.speedball.JoinGameActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothReceiver extends BroadcastReceiver
{
	private JoinGameActivity activity;
	
	public BluetoothReceiver(JoinGameActivity activity)
	{
		this.activity = activity;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 String action = intent.getAction();
        // When discovery finds a device
        if (action.equals(BluetoothDevice.ACTION_FOUND)) 
        {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            activity.addDevice(device);
        }
        else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED))
        {
        	activity.setRescanEnabled(false);
        }
        else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
        {
        	activity.setRescanEnabled(true);
        }
	}
}
