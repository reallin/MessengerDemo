package com.lxj.messengerdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView txvTextView ;
	private Messenger mService;
	private Messenger clientMessenger = new Messenger(new MessengerHandler());
	private static class MessengerHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 1:
				Log.e("msg from service",  msg.getData().getString("reply"));
				break;
			 default:
	        super.handleMessage(msg);
			}
		}
		
	}
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			 mService = new Messenger(service);
			Message msgMessage = Message.obtain(null,0);
			Bundle b = new Bundle();
			b.putString("client", "Hi,i am client");
			msgMessage.setData(b);
			msgMessage.replyTo = clientMessenger;
			try {
				mService.send(msgMessage);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}


	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txvTextView = (TextView)super.findViewById(R.id.txv);
		Intent i = new Intent(this,MessengerService.class);
		bindService(i, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	   @Override
	    protected void onDestroy() {
	        unbindService(connection);
	        super.onDestroy();
	    }

}
