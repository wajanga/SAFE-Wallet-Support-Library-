package com.setecs.mobile.safe.apps.shared;

import static com.setecs.mobile.safe.apps.shared.Constants.SERVERPORT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import android.util.Log;


public class ChangeSafePinThread implements Callable<String> {

	private static final String TAG = "ChangeSafePinThread";
	private String userMobNo = "";
	private String clientMsg = "";
	private String walletMsg = "";
	private StringBuffer sb;
	private String receivedData = "";
	private String processGWMessage = "";
	private String oldpins = "";
	private String newpins = "";
	private String msg = "";
	private final SharedMethods cv = new SharedMethods();
	private String serverIpAddress = "";
	private boolean connected = false;

	public ChangeSafePinThread(String userMobNo, String serverIpAddress, String oldpins, String newpins) {
		// TODO Auto-generated constructor stub
		this.userMobNo = userMobNo;
		this.oldpins = oldpins;
		this.newpins = newpins;
		this.serverIpAddress = serverIpAddress;

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
			clientMsg = "sp" + " " + oldpins + " " + newpins;
			walletMsg = "(" + userMobNo + ";" + clientMsg + ")";
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
					Log.e(TAG, "ChangeSafePinThread", e);
					throw new RuntimeException(msg, e);
				}

			}

			socket.close();
			connected = false;

			Log.d("ClientActivity", "C: Closed.");

		}
		catch (Exception e) {
			cv.closeSession();

			msg = e.getMessage();
			Log.e(TAG, "ChangeSafePinThread", e);
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
