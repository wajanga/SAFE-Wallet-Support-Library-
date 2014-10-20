package com.setecs.mobile.safe.apps.shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.setecs.mobile.safe.apps.R;
import com.setecs.mobile.safe.apps.util.security.CryptoProviderClient;
import com.setecs.mobile.safe.apps.util.security.DigestProvider;


public class SharedMethods {

	public static final int SERVERPORT = 9661;
	private static final String TAG = "SharedMethods";
	public static boolean closeapp = false;
	public static boolean reset = false;
	public static boolean flag = false;
	public static boolean initFlag = false;
	public static boolean connected = false;
	public static Socket socket = null;
	public static boolean showFirstRun = false;
	private AlertDialog warningDialog;
	protected boolean serverresponse = false;
	private String userMobNo = "";
	private String result = "";
	private final String[] accountlist = new String[10];
	public static String serverIpAddress = "";
	public static boolean appPaused = true;
	public static boolean login = false;
	HashMap<String, String[]> hm = new HashMap<String, String[]>();
	private String msg = "";
	private String nextToken = "";
	private int i = 0;
	private String[] value;
	private byte[] msgBytes;
	private CryptoProviderClient crypto;
	private byte[] decryptMsgBytes;
	private DigestProvider digest;
	private byte[] keyBytes;
	private static ProgressDialog progressDialog;
	public static SecretKeySpec secretKey = null;

	public boolean createSession() {

		//		try {
		//
		//			InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
		//			Log.d("CreateSession", "C: Connecting...");
		//			socket = new Socket(serverAddr, SERVERPORT);
		//			connected = true;
		//			Log.d("CreateSession", "C: Connected...");
		//
		//		}
		//		catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			connected = false;
		//			msg = e.getMessage();
		//			Log.e(TAG, "createSession", e);
		//			progressStop();
		//		}
		return connected;

	}

	public void closeSession() {
		//		try {
		//			if (socket != null)
		//				if (!socket.isClosed()) {
		//
		//					socket.close();
		//					socket = null;
		//					connected = false;
		//					progressStop();
		//				}
		//
		//		}
		//		catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			connected = socket.isConnected();
		//			msg = e.getMessage();
		//			Log.e(TAG, "closeSession", e);
		//			throw new RuntimeException(msg, e);
		//		}
	}

	public void progressStart(Activity activity, String message) {
		progressDialog = ProgressDialog.show(activity, "", message, true);
	}

	public void progressStart(Activity activity, String message, DialogInterface.OnCancelListener listener) {
		progressDialog = ProgressDialog.show(activity, "", message, true, true, listener);
	}

	public void progressStop() {
		if (progressDialog.isShowing())
			progressDialog.dismiss();
	}

	public static boolean isInitFlag() {
		return SharedMethods.initFlag;
	}

	public boolean checkInternetConnection(Activity activity) {

		ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (conMgr.getActiveNetworkInfo() != null

		&& conMgr.getActiveNetworkInfo().isAvailable()

		&& conMgr.getActiveNetworkInfo().isConnected()) {

			return true;

		}
		else {

			Log.v(TAG, "Internet Connection Not Present");

			return false;

		}

	}

	public void serverNotResponding(final Activity activity) {
		// TODO Auto-generated method stub
		warningDialog = new AlertDialog.Builder(activity).create();

		warningDialog.setTitle("Alert");

		warningDialog.setMessage("Server not responding!");

		warningDialog.setIcon(R.drawable.warningicon32x32);
		warningDialog.setButton("Try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (!createSession()) {
					serverNotResponding(activity);
				}
				else {
					Toast.makeText(activity.getApplicationContext(), "Connection established", Toast.LENGTH_LONG)
							.show();

				}

			}
		});
		warningDialog.setButton2("Abort", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				activity.finish();
			}
		});
		warningDialog.show();
	}

	public String readConfigurationFile(Context context, String recordName, String confFileName) {
		// TODO Auto-generated method stub
		String readString = null;

		try {

			FileInputStream f = context.openFileInput(confFileName);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String> readObject = (HashMap<String, String>) s.readObject();

			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(recordName))
					readString = readObject.get(key);
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readConfigurationFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readConfigurationFile", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "readConfigurationFile", e);
			throw new RuntimeException(msg, e);
		}

		return readString;
	}

	public void fetchSaveAccountList(Activity activity, String MOBILENO, String CONFIGFILENAME, String ACCOUNTLIST,
			String ACCOUNTLISTFILENAME) {
		//		if (SharedMethods.connected) {

		userMobNo = readConfigurationFile(activity, MOBILENO, CONFIGFILENAME);

		if (!SharedMethods.serverIpAddress.equals("")) {
			FutureTask<String> task = new FutureTask<String>(new GetAccountsListThread(userMobNo,
					SharedMethods.serverIpAddress));
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.submit(task);
			try {
				result = task.get();
				if (result.contains("SETECS Account"))

				{
					StringTokenizer st = new StringTokenizer(result);
					while (st.hasMoreTokens()) {
						nextToken = (st.nextToken()).trim();
						if (Character.isDigit(nextToken.charAt(0))) {
							accountlist[i] = nextToken;
							i++;
						}

					}
					//						accountlist = result.substring(result.indexOf("SAFE Account:") + 16,
					//								result.indexOf("SAFE Account:") + 25);
					try {
						hm.put(ACCOUNTLIST, accountlist);

						FileOutputStream fStream = activity.openFileOutput(ACCOUNTLISTFILENAME, Context.MODE_PRIVATE);
						ObjectOutputStream oStream = new ObjectOutputStream(fStream);
						oStream.writeObject(hm);

						oStream.flush();
						oStream.close();
						fStream.close();

					}
					catch (Exception e) {
						msg = e.getMessage();
						Log.e(TAG, "fetchSaveAccountList", e);
						throw new RuntimeException(msg, e);
					}

				}
			}
			catch (Exception e) {
				msg = e.getMessage();
				Log.e(TAG, "fetchSaveAccountList", e);
				throw new RuntimeException(msg, e);
			}
			es.shutdown();

		}
	}

	public String fetchAccountList(Activity activity, String ACCOUNTLIST, String ACCOUNTLISTFILENAME) {
		value = new String[10];

		try {

			FileInputStream f = activity.openFileInput(ACCOUNTLISTFILENAME);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String[]> readObject = (HashMap<String, String[]>) s.readObject();
			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(ACCOUNTLIST)) {
					value = readObject.get(key);
				}
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		return value[0];
	}

	public String[] fetchAccounts(Activity activity, String ACCOUNTLIST, String ACCOUNTLISTFILENAME) {
		value = new String[10];

		try {

			FileInputStream f = activity.openFileInput(ACCOUNTLISTFILENAME);
			ObjectInputStream s = new ObjectInputStream(f);
			@SuppressWarnings("unchecked")
			HashMap<String, String[]> readObject = (HashMap<String, String[]>) s.readObject();
			Iterator<String> myVeryOwnIterator = readObject.keySet().iterator();
			while (myVeryOwnIterator.hasNext()) {
				String key = myVeryOwnIterator.next();
				if (key.equals(ACCOUNTLIST)) {
					value = readObject.get(key);
				}
			}
			s.close();
			f.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "fetchAccountList", e);
			throw new RuntimeException(msg, e);
		}
		return value;
	}

	public byte[] encrypt(String message) {
		msgBytes = message.getBytes();
		crypto = new CryptoProviderClient();
		return crypto.AESEncrypt(msgBytes, secretKey);
	}

	public byte[] decrypt(byte[] msg) {

		crypto = new CryptoProviderClient();
		decryptMsgBytes = crypto.AESDecrypt(msg, secretKey);

		return decryptMsgBytes;
	}

	public byte[] decrypt(byte[] msg, byte[] key) {
		digest = new DigestProvider();
		keyBytes = digest.pinDigest(key);
		SecretKeySpec theKey = new SecretKeySpec(keyBytes, "AES");
		secretKey = theKey;
		crypto = new CryptoProviderClient();
		decryptMsgBytes = crypto.AESDecrypt(msg, theKey);

		return decryptMsgBytes;
	}
}
