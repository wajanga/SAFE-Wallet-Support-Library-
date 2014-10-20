package com.setecs.mobile.safe.apps.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;


/**
 * UUID generator
 */
public class UUIDGenerator {

	private static String baseUUID = null;

	// public static void main(String args[])
	// {
	// String uuid=getUUID();
	// }

	/**
	 * MD5 a random string with localhost/date etc will return 128 bits
	 * construct a string of 18 characters from those bits.
	 * 
	 * @return string
	 */

	public static String getUUID(int hash) {
		// if (baseUUID == null) {
		getInitialUUID(Integer.toString(hash));
		// }
		// long i = ++incrementingValue;
		// if(i >= Long.MAX_VALUE || i < 0){
		// incrementingValue = 0;
		// i = 0;
		// }
		//		Date date = new Date();
		// String currentDateTimeString =
		// DateFormat.getDateInstance().format(new Date());
		int day = Calendar.getInstance().get(Calendar.DATE);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String cDate = Integer.toString(day) + "0" + Integer.toString(month);
		//		String sighash = Integer.toString(hash);
		String subUUid = baseUUID.substring(0, 10);

		return formatedUUid(baseUUID + cDate + subUUid);
	}

	private static String formatedUUid(String string) {
		// TODO Auto-generated method stub
		String uuid = new StringBuffer(string).insert(8, "-").toString();
		uuid = new StringBuffer(uuid).insert(13, "-").toString();
		uuid = new StringBuffer(uuid).insert(18, "-").toString();
		uuid = new StringBuffer(uuid).insert(23, "-").toString();

		return uuid;
	}

	protected static synchronized void getInitialUUID(String sigHash) {
		// if (baseUUID != null) {
		// return;
		// }
		// if (myRand == null) {
		// myRand = new Random();
		// }
		// long rand = myRand.nextLong();
		// String sid;
		// try {
		// sid = InetAddress.getLocalHost().toString();
		// } catch (UnknownHostException e) {
		// sid = Thread.currentThread().getName();
		// }
		StringBuffer sb = new StringBuffer();
		sb.append(sigHash);
		// sb.append(":");
		// sb.append(Long.toString(rand));
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			// todo have to be properly handled
		}
		md5.update(sb.toString().getBytes());
		byte[] array = md5.digest();
		StringBuffer sb2 = new StringBuffer();
		for (int j = 0; j < array.length; ++j) {
			int b = array[j] & 0xFF;
			sb2.append(Integer.toHexString(b));
		}
		// int begin = myRand.nextInt();
		int begin = Integer.parseInt(sigHash.trim());
		if (begin < 0)
			begin = begin * -1;
		begin = begin % 8;
		baseUUID = sb2.toString().substring(begin, begin + 18).toUpperCase();
	}

}
