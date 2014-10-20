package com.setecs.mobile.safe.apps.shared;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;


@SuppressLint("Registered")
public class SocketService extends Service {

	public static Socket socket;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	private final IBinder myBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public SocketService getService() {
			return SocketService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		socket = new Socket();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service created ...", Toast.LENGTH_LONG).show();
		Runnable connect = new connectSocket();
		new Thread(connect).start();
	}

	class connectSocket implements Runnable {

		@Override
		public void run() {
			SocketAddress socketAddress = new InetSocketAddress(SharedMethods.serverIpAddress, SharedMethods.SERVERPORT);
			try {
				socket.connect(socketAddress);
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			socket.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket = null;
	}

}
