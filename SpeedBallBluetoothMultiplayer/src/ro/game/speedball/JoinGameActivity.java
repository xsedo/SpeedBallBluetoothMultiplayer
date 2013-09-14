package ro.game.speedball;

import java.util.ArrayList;
import java.util.Set;

import ro.game.speedball.receiver.BluetoothReceiver;
import static ro.game.speedball.SpeedBallApplication.REQUEST_BLUETOOTH_ENABLE;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class JoinGameActivity extends Activity 
{
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE MEMBERS
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ListView					mDevicesList;
	private Button						mRescanButton;
	private BluetoothAdapter			mBluetoothAdapter;
	private ArrayList<String>			mArrayAdapter;
	private ArrayList<BluetoothDevice>	mDevices;
	private BluetoothReceiver			mReceiver;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PROTECTED OVERRIDES
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_joingame);
		
		mDevicesList      = (ListView)findViewById(R.id.devicesList);
		mRescanButton     = (Button)findViewById(R.id.rescanButton);
		mReceiver         = null;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mRescanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchForDevices();
			}
		});
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
        	searchForDevices();
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
				searchForDevices();
			}
		}
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		if(mReceiver != null) unregisterReceiver(mReceiver);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addDevice(BluetoothDevice device)
	{
		// Add the name and address to an array adapter to show in a ListView
        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        mDevices.add(device);
        updateList();
	}
	
	public void setRescanEnabled(Boolean enabled)
	{
		mRescanButton.setEnabled(enabled);
		if(enabled)
		{
			mRescanButton.setText(R.string.rescan);
		}
		else
		{
			mRescanButton.setText(R.string.scanning);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void searchForDevices()
	{
		mArrayAdapter = new ArrayList<String>();
		mDevices      = new ArrayList<BluetoothDevice>();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if (pairedDevices.size() > 1) 
		{
		    for (BluetoothDevice device : pairedDevices) 
		    {
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        mDevices.add(device);
		    }
		    updateList();
		}
		// Register the BroadcastReceiver
		if(mReceiver == null)
		{
			mReceiver = new BluetoothReceiver(this);
			IntentFilter filter = new IntentFilter();
			filter.addAction(BluetoothDevice.ACTION_FOUND);
			filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
			filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			registerReceiver(mReceiver, filter);
		}
		mBluetoothAdapter.startDiscovery();
	}
	
	private void updateList()
	{
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArrayAdapter);
        mDevicesList.setAdapter(adapter);
        mDevicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {	
			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) 
			{
				//TODO: connect to device and start game
				mBluetoothAdapter.cancelDiscovery();
			}
		});
	}
}
