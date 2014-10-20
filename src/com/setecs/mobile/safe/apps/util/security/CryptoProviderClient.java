package com.setecs.mobile.safe.apps.util.security;

import java.math.BigInteger;
import java.security.Key;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;


public class CryptoProviderClient {

	private BigInteger[] RSAkeys = new BigInteger[3];
	private Random r = new Random(); // in future secure random

	// --- Generate RSA Keys --------------------------------------
	public void RSA_Keygeneration() {

		// Generate two random prime numbers, 256 bits long.
		BigInteger p = BigInteger.probablePrime(256, r);
		BigInteger q = BigInteger.probablePrime(256, r);

		// modulus
		BigInteger n = p.multiply(q);
		RSAkeys[0] = n;

		// number for calculating private and public exponent
		BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		// private exponent
		BigInteger e = findEncryptKey(totient);
		RSAkeys[1] = e;

		// public exponent
		BigInteger d = findDecryptKey(e, totient);
		RSAkeys[2] = d;

	}

	public BigInteger[] getRSAKeys() {
		return RSAkeys;
	}

	/**
	 * Calculate decryption key given the private exponent and the totient.
	 * 
	 * @param BigInteger
	 *            CryptKey
	 * @param BigInteger
	 *            totient
	 * @return BigInteger decryption key
	 */

	private BigInteger findDecryptKey(BigInteger CryptKey, BigInteger totient) {
		return CryptKey.modInverse(totient);
	}

	/**
	 * Calculate private key given the totient.
	 * 
	 * @param totient
	 * @return encryption key
	 */

	private BigInteger findEncryptKey(BigInteger totient) {

		// generate 512 bits long encryption key
		BigInteger e = new BigInteger(512, r);

		// encryption key can't equal one!
		while (e.equals(BigInteger.ONE)) {
			e = new BigInteger(512, r);
		}

		// the encryption key and the totients gcd (greatest common divisor)
		// must equal one.
		while (!totient.gcd(e).equals(BigInteger.ONE)) {
			e = e.add(BigInteger.ONE);
		}

		return e;
	}

	/**
	 * Encrypt the input data with the RSA algorithm.
	 * 
	 * The input cannot be bigger than the size of the modulus and it cannot be
	 * negative.
	 * 
	 * @param byte[] input
	 * @param BigInteger
	 *            e
	 * @param BigInteger
	 *            n
	 * @return byte[] encrypted data
	 */

	public byte[] RSAEncryption(byte[] input, BigInteger e, BigInteger n) {

		// BigInteger encryptedData = encrypt(new BigInteger(input),n,e);
		BigInteger encryptedData = null;
		try {
			// parsing the message for making sure input is smaller than modulus
			// and not negative.
			encryptedData = encrypt(parseMsg(input, n), n, e);
		}
		catch (BadPaddingException e1) {
			e1.printStackTrace();
		}

		return encryptedData.toByteArray();
	}

	private BigInteger encrypt(BigInteger m, BigInteger n, BigInteger cryptoKey) {
		// int c = m^e mod n

		return m.modPow(cryptoKey, n);
	}

	/**
	 * Help function for making sure the input in the RSA encryption is non
	 * negative and not larger than the modulus.
	 * 
	 * @param byte[] input
	 * @param BigInteger
	 *            n
	 * @return BigInteger result
	 * @throws BadPaddingException
	 */

	private BigInteger parseMsg(byte[] input, BigInteger n) throws BadPaddingException {

		BigInteger m = new BigInteger(1, input);

		if (m.compareTo(BigInteger.ZERO) == -1) {
			throw new BadPaddingException("Input is negative");
		}

		if (m.compareTo(n) >= 0) {
			throw new BadPaddingException("Input is larger than modulus");
		}
		return m;
	}

	/**
	 * Decrypt data encrypted with the RSA algorithm.
	 * 
	 * @param byte[] encryptedMessage
	 * @param BigInteger
	 *            d
	 * @param BigInteger
	 *            n
	 * @return byte[] decrypted data
	 */

	public byte[] RSADecryption(byte[] encryptedMessage, BigInteger d, BigInteger n) {

		BigInteger decryptedData = decrypt(new BigInteger(encryptedMessage), d, n);

		return decryptedData.toByteArray();
	}

	private BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
		// m = c^d mod n

		return c.modPow(d, n);
	}

	/**
	 * Encrypt with the AES algorithm.
	 * 
	 * @param byte[] data
	 * @param Key
	 *            key
	 * @return byte[] encrypted data
	 */

	public byte[] AESEncrypt(byte[] data, Key key) {

		// data for encryption must be multiple of 16
		final int BLOCK_SIZE = 16;
		int ciphertextLength = 0;
		int remainder = data.length % BLOCK_SIZE;

		if (remainder == 0) {
			ciphertextLength = data.length + BLOCK_SIZE;
		}
		else {
			ciphertextLength = data.length - remainder + BLOCK_SIZE;
		}

		byte[] encryptedData = new byte[ciphertextLength];

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipher.doFinal(data, 0, data.length, encryptedData, 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return encryptedData;

	}

	/**
	 * Decrypt data with the AES algorithm.
	 * 
	 * @param byte[] data
	 * @param Key
	 *            key
	 * @return decrypted data
	 */

	public byte[] AESDecrypt(byte[] data, Key key) {

		byte[] decryptedData = new byte[data.length];
		//int numberOfBytes = 0;
		byte[] result = null;

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			//numberOfBytes = cipher.doFinal(data, 0, data.length, decryptedData, 0);
			result = cipher.doFinal(data, 0, data.length);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//result = new byte[numberOfBytes];
		//System.arraycopy(decryptedData, 0, result, 0, numberOfBytes);
		return result;
	}

}
