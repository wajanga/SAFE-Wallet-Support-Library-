package com.setecs.mobile.safe.apps.util.security;

import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import android.util.Log;


public class CryptoProviderClientExtended extends CryptoProviderClient {

	// Logging tag
	private static final String TAG = "CRYPTO_TEST";

	public byte[] Encrypt(byte[] data, Key key, String algorithm, String mode, String padding) {
		byte[] encryptedData = null;
		Cipher cipher;
		try {
			if ((mode != null) && (padding != null)) {
				// cipher = Cipher.getInstance(algorithm+"/"+mode+"/"+padding);
				cipher = Cipher.getInstance("AES");
			}
			else {
				// cipher = Cipher.getInstance(algorithm);
				cipher = Cipher.getInstance("AES");
			}
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(data);

		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (NoSuchPaddingException e) {
			Log.e(TAG, "No Such Padding " + e.getMessage());
		}
		catch (InvalidKeyException e) {
			// e.printStackTrace();
			Log.e(TAG, "Invalid Key " + e.getMessage());
		}
		catch (IllegalBlockSizeException e) {
			Log.e(TAG, "Illegal Block size " + e.getMessage());
		}
		catch (BadPaddingException e) {
			Log.e(TAG, "Badding Padding " + e.getMessage());
		}
		return encryptedData;
	}

	public byte[] Encrypt(byte[] data, String algorithm, String mode, String padding) {
		byte[] encryptedData = null;
		Cipher cipher;
		try {
			// KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(new SecureRandom());
			Key key = generator.generateKey();
			if ((mode != null) && (padding != null)) {
				// cipher = Cipher.getInstance(algorithm+"/"+mode+"/"+padding);
				cipher = Cipher.getInstance("AES");
			}
			else {
				// cipher = Cipher.getInstance(algorithm);
				cipher = Cipher.getInstance("AES");
			}
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(data);

		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (NoSuchPaddingException e) {
			Log.e(TAG, "No Such Padding " + e.getMessage());
		}
		catch (InvalidKeyException e) {
			// e.printStackTrace();
			Log.e(TAG, "Invalid Key " + e.getMessage());
		}
		catch (IllegalBlockSizeException e) {
			Log.e(TAG, "Illegal Block size " + e.getMessage());
		}
		catch (BadPaddingException e) {
			Log.e(TAG, "Badding Padding " + e.getMessage());
		}
		return encryptedData;
	}

	public byte[] Decrypt(byte[] data, Key key, String algorithm, String mode, String padding) {
		byte[] decryptedData = null;
		Cipher cipher;
		try {
			if ((mode != null) && (padding != null))
				cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
			else
				cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedData = cipher.doFinal(data);

		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (NoSuchPaddingException e) {
			Log.e(TAG, "No Such Padding " + e.getMessage());
		}
		catch (InvalidKeyException e) {
			// e.printStackTrace();
			Log.e(TAG, "Invalid Key " + e.getMessage());
		}
		catch (IllegalBlockSizeException e) {
			Log.e(TAG, "Illegal Block size " + e.getMessage());
		}
		catch (BadPaddingException e) {
			Log.e(TAG, "Badding Padding " + e.getMessage());
		}
		return decryptedData;
	}

	public byte[] Decrypt(byte[] data, String algorithm, String mode, String padding) {
		byte[] decryptedData = null;
		Cipher cipher;
		try {
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(new SecureRandom());
			Key key = generator.generateKey();
			if ((mode != null) && (padding != null))
				cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
			else
				cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedData = cipher.doFinal(data);

		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (NoSuchPaddingException e) {
			Log.e(TAG, "No Such Padding " + e.getMessage());
		}
		catch (InvalidKeyException e) {
			// e.printStackTrace();
			Log.e(TAG, "Invalid Key " + e.getMessage());
		}
		catch (IllegalBlockSizeException e) {
			Log.e(TAG, "Illegal Block size " + e.getMessage());
		}
		catch (BadPaddingException e) {
			Log.e(TAG, "Badding Padding " + e.getMessage());
		}
		return decryptedData;
	}

	public SecretKey generateSecretKey(String algorithm) {
		KeyGenerator keyGen = null;
		try {
			keyGen = KeyGenerator.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		SecretKey key = keyGen.generateKey();
		return key;
	}

	public byte[] generateEncodedSecretKey(String algorithm) {
		SecretKey key = generateSecretKey(algorithm);
		byte[] keyBytes = key.getEncoded();
		return keyBytes;
	}

	public KeyPair generateAsymmetricKeys(String algorithm) {
		KeyPairGenerator keyGen;
		KeyPair keypair = null;
		try {
			keyGen = KeyPairGenerator.getInstance(algorithm);
			keyGen.initialize(1024);
			keypair = keyGen.genKeyPair();
		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		return keypair;
	}

	public byte[] getEncodedPrivateKey(KeyPair keypair) {
		PrivateKey privateKey = keypair.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		return privateKeyBytes;
	}

	public byte[] getEncodedPublicKey(KeyPair keypair) {
		PublicKey publicKey = keypair.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		return publicKeyBytes;
	}

	public DHParameterSpec generateKeyAgreementParameters() {
		AlgorithmParameterGenerator paramGen;
		DHParameterSpec dhSpec = null;
		try {
			// Generate DH Parameter Spefication
			// Very slow on Android. Should ideally be done outside Android
			paramGen = AlgorithmParameterGenerator.getInstance("DH");
			paramGen.init(1024, new SecureRandom());
			AlgorithmParameters params = paramGen.generateParameters();
			dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (InvalidParameterSpecException e) {
			Log.e(TAG, "Invalid Parameter Specification " + e.getMessage());
		}
		return dhSpec;
	}

	public byte[] generateKeyAgreementSecret() {
		BigInteger p = new BigInteger("1234567890", 16);
		BigInteger g = new BigInteger("1234567890", 16);
		int l = 1023;
		KeyPairGenerator keyGen;
		byte[] keyBytes = null;
		try {
			keyGen = KeyPairGenerator.getInstance("DH");
			keyGen.initialize(new DHParameterSpec(p, g, l));
			KeyPair kp = keyGen.generateKeyPair();

			KeyAgreement KeyAgree = KeyAgreement.getInstance("DH");
			KeyAgree.init(kp.getPrivate());
			KeyAgree.doPhase(kp.getPublic(), true);
			keyBytes = KeyAgree.generateSecret();
		}
		catch (InvalidAlgorithmParameterException e) {
			Log.e(TAG, "Invalid Algorithm Parameter " + e.getMessage());
		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		catch (InvalidKeyException e) {
			Log.e(TAG, "Invalid Key " + e.getMessage());
		}
		return keyBytes;
	}

	public byte[] generateRandomNumber() {
		// Get 1024 random bits
		byte[] bytes = new byte[1024 / 8];
		// Create a secure random number generator
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(bytes);
		}
		catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "No Such Algorithm " + e.getMessage());
		}
		return bytes;
	}

}
