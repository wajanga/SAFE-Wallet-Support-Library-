package com.setecs.mobile.safe.apps.util.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SymmetricCryption {

	private static int AES_blocksize = 32;
	private static int DES_blocksize = 16;
	private static byte[] ivBytesForAES = "tyrgfhsjcbzloiqa".getBytes();
	private static byte[] ivBytesForDES = { (byte) 0x00,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x00 };

	public SymmetricCryption() {

	}

	public byte[] AESEncryptor(byte[] cleartext, byte[] key) {

		byte[] ciphertext = null;
		byte[] symmetrickey;
		SecretKeySpec keySpec;
		symmetrickey = key;
		int ciphertextLength = 0;
		int remainder = cleartext.length % AES_blocksize;
		IvParameterSpec iv = new IvParameterSpec(ivBytesForAES, 0, ivBytesForAES.length);
		int numofBytes = 0;

		try {

			Cipher AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			keySpec = new SecretKeySpec(symmetrickey, 0, symmetrickey.length, "AES");
			if (remainder == 0) {
				ciphertextLength = cleartext.length + AES_blocksize;
			}
			else {
				ciphertextLength = cleartext.length - remainder + AES_blocksize;
			}
			ciphertext = new byte[ciphertextLength];
			AES.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			numofBytes = AES.doFinal(cleartext, 0, cleartext.length, ciphertext, 0);
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}

		catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidKeyException e) {
			System.out.println(e.toString());
		}

		catch (ShortBufferException e) {
			e.printStackTrace();
		}

		catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		}

		catch (BadPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.toString());
		}

		catch (NullPointerException e) {
			System.out.println(e.toString());
		}
		byte[] result = new byte[numofBytes];
		System.arraycopy(ciphertext, 0, result, 0, numofBytes);
		return result;

	}

	public byte[] AESDecryptor(byte[] cihpertext, byte[] key) {

		byte[] cleartext = new byte[cihpertext.length];
		byte[] symmetrickey = key;
		SecretKeySpec keySpec;
		IvParameterSpec iv = new IvParameterSpec(ivBytesForAES, 0, ivBytesForAES.length);
		int numofBytes = 0;
		try {
			Cipher AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			keySpec = new SecretKeySpec(symmetrickey, 0, symmetrickey.length, "AES");
			AES.init(Cipher.DECRYPT_MODE, keySpec, iv);
			numofBytes = AES.doFinal(cihpertext, 0, cihpertext.length, cleartext, 0);
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}

		catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidKeyException e) {
			System.out.println(e.toString());
		}

		catch (ShortBufferException e) {
			System.out.println(e.toString());
		}

		catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		}

		catch (BadPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.toString());
		}

		catch (NullPointerException e) {
			System.out.println(e.toString());
		}

		byte[] result = new byte[numofBytes];
		System.arraycopy(cleartext, 0, result, 0, numofBytes);
		return result;

	}

	public byte[] DESEncryptor(byte[] cleartext, byte[] key) {

		byte[] ciphertext = null;
		byte[] symmetrickey;
		SecretKeySpec keySpec;
		symmetrickey = key;
		int ciphertextLength = 0;
		int remainder = cleartext.length % DES_blocksize;
		int numofBytes = 0;

		try {

			// Cipher DES = Cipher.getInstance("DES/CBC/NoPadding");
			Cipher DES = Cipher.getInstance("DES");
			keySpec = new SecretKeySpec(symmetrickey, 0, symmetrickey.length, "DES");
			if (remainder == 0) {
				ciphertextLength = cleartext.length + DES_blocksize;
			}
			else {
				ciphertextLength = cleartext.length - remainder + DES_blocksize;
			}
			ciphertext = new byte[ciphertextLength];
			DES.init(Cipher.ENCRYPT_MODE, keySpec);
			numofBytes = DES.doFinal(cleartext, 0, cleartext.length, ciphertext, 0);
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}

		catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidKeyException e) {
			System.out.println(e.toString());
		}

		catch (ShortBufferException e) {
			e.printStackTrace();
		}

		catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		}

		catch (BadPaddingException e) {
			System.out.println(e.toString());
		}

		catch (NullPointerException e) {
			System.out.println(e.toString());
		}
		byte[] result = new byte[numofBytes];
		System.arraycopy(ciphertext, 0, result, 0, numofBytes);
		return result;

	}

	public byte[] DESDecryptor(byte[] cihpertext, byte[] key) {

		byte[] cleartext = new byte[cihpertext.length];
		byte[] symmetrickey = key;
		SecretKeySpec keySpec;
		IvParameterSpec iv = new IvParameterSpec(ivBytesForDES, 0, ivBytesForDES.length);
		int numofBytes = 0;
		try {
			Cipher DES = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// Cipher DES = Cipher.getInstance("DES");
			keySpec = new SecretKeySpec(symmetrickey, 0, symmetrickey.length, "DES");
			DES.init(Cipher.DECRYPT_MODE, keySpec, iv);
			// DES.init(Cipher.DECRYPT_MODE, keySpec);
			numofBytes = DES.doFinal(cihpertext, 0, cihpertext.length, cleartext, 0);
		}

		catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}

		catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		}

		catch (InvalidKeyException e) {
			System.out.println(e.toString());
		}
		catch (ShortBufferException e) {
			System.out.println(e.toString());
		}

		catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		}

		catch (BadPaddingException e) {
			e.printStackTrace();
		}

		catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.toString());
		}

		catch (NullPointerException e) {
			System.out.println(e.toString());
		}

		byte[] result = new byte[numofBytes];
		System.arraycopy(cleartext, 0, result, 0, numofBytes);
		return result;

	}

	public static void main(String args[]) {
		SymmetricCryption se = new SymmetricCryption();
		byte[] ciphertmp = se.DESEncryptor("as 900002".getBytes(), "01234567".getBytes());
		String temp = new String(ciphertmp);
		System.out.println("The DES cipher text is: " + temp);
		byte[] cleartext = se.DESDecryptor(ciphertmp, "01234567".getBytes());
		String temp1 = new String(cleartext);
		System.out.println("The DES clear text is: " + temp1);
	}
}
