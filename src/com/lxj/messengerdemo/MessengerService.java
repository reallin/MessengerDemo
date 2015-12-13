package com.lxj.messengerdemo;



import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service{
	private final Messenger mMessenger = new Messenger(new MessengerHandler());
	private static class MessengerHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				Log.e("msg from client",  msg.getData().getString("client"));
				Message message = Message.obtain(null,1);
				Messenger messenger = msg.replyTo;//从客户端得到的messenger，用于在客户端中显示
				Bundle bundle = new Bundle();
				
				bundle.putString("reply", "Hi,i am service");
				message.setData(bundle);
				try {
					Thread.sleep(1000);
					messenger.send(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			 default:
	        super.handleMessage(msg);
			}
		}
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return  mMessenger.getBinder();
	}

}
