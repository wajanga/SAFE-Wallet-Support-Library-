package com.setecs.mobile.safe.apps.util.security;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DigestProvider {

	// private Random r = new Random();
	// private byte[] salt = null;

	public DigestProvider() {}

	public byte[] digest(byte[] data) {

		/*
		 * Cannot return a digest who generates a negative BigInteger
		 */
		MessageDigest md;

		byte[] digest = new byte[20];
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(data, 0, data.length);
			md.digest(digest, 0, digest.length);

		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (DigestException e) {
			e.printStackTrace();
		}
		// BigInteger digestAsBigInt = new BigInteger(digest);
		// // if digest is negative convert to positive, help function for
		// encryption
		// if(digestAsBigInt.compareTo(BigInteger.ZERO) == -1){
		// digestAsBigInt = digestAsBigInt.abs();
		// digest = digestAsBigInt.toByteArray();
		// }
		return digest;
	}

	/**
	 * Digest the pin. Use this only for the pin.
	 * 
	 * @param byte[] data
	 * @return byte[] digest
	 */

	public byte[] pinDigest(byte[] data) {

		MessageDigest md;

		byte[] digest = new byte[16];

		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data, 0, data.length);
			md.digest(digest, 0, digest.length);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (DigestException e) {
			e.printStackTrace();
		}

		return digest;
	}
}
