package com.setecs.mobile.safe.apps.shared;

import static com.setecs.mobile.safe.apps.shared.Constants.SERVERPORT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import android.util.Log;


public class GetAccountsListThread implements Callable<String> {

	private static final String TAG = "GetAccountsListThread";
	private String clientMsg;
	private String walletMsg;
	private String processGWMessage;
	private StringBuffer sb;
	private String receivedData;
	private final String MobileNo;
	private String msg = "";
	private final SharedMethods cv = new SharedMethods();
	private String serverIpAddress = "";
	private boolean connected = false;

	public GetAccountsListThread(String MobileNo, String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
		this.MobileNo = MobileNo;
	}

	@Override
	public String call() throws Exception {
		try {

			InetAddress serverAddr = InetAddress.getByName(serverIpAddress);

			Log.d("ClientActivity", "C: Connecting...");

			Socket socket = new Socket(serverAddr, SERVERPORT);

			connected = true;
			SAFEMessage gwmsg = new SAFEMessage();
			String safemsg = "";
			clientMsg = "al";
			walletMsg = "(" + MobileNo + ";" + clientMsg + ")";
			safemsg = gwmsg.createGWMessage("0", walletMsg);

			if (connected) {

				try {

					Log.d("ClientActivity", "C: Sending command.");

					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					DataInputStream in = new DataInputStream(socket.getInputStream());
					// WHERE YOU ISSUE THE COMMANDS

					out.write(safemsg.getBytes());
					out.flush();

					String serverGWMessage = readStringData(in);
					processGWMessage = gwmsg.processGWMessage(serverGWMessage);

					Log.d("ClientActivity", "C: Sent.");

				}
				catch (Exception e) {
					cv.closeSession();

					msg = e.getMessage();
					Log.e(TAG, "GetAccountsListThread", e);
					processGWMessage = "SocketException";
				}

			}

			socket.close();
			connected = false;

			Log.d("ClientActivity", "C: Closed.");

		}
		catch (Exception e) {
			cv.closeSession();

			msg = e.getMessage();
			Log.e(TAG, "GetAccountsListThread", e);
			processGWMessage = "SocketException";
		}
		return processGWMessage;
	}

	private String readStringData(DataInputStream in) {
		// TODO Auto-generated method stub
		try {
			int c = 0;
			int i = 0;
			sb = new StringBuffer();
			while ((c = in.read()) != -1) {
				sb.append((char) c);
				if (c == '\n')
					break;
				System.out.print(i + "\t" + c);
				i++;
			}
			receivedData = sb.toString();
		}
		catch (Exception e1) {
			cv.closeSession();

			msg = e1.getMessage();
			Log.e(TAG, "readStringData", e1);
			throw new RuntimeException(msg, e1);
		}
		return receivedData;

	}

}
