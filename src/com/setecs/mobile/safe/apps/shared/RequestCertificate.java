package com.setecs.mobile.safe.apps.shared;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.setecs.mobile.safe.apps.R;
import com.setecs.mobile.safe.apps.util.security.PrintableCoding;


public class RequestCertificate extends Activity {

	private static final String TAG = "RequestCertificate";
	String FILENAME = "pin_file";
	HashMap<String, byte[]> hma = new HashMap<String, byte[]>();
	private KeyPair kpair;
	private byte[] dataEncrypted;
	private BigInteger pubkExponent;
	private BigInteger pkMudulos;
	private String pkMudulosstr = "";
	private String pubkExponentstr = "";
	private String pkcs10request = "";
	private String CertReq = "";
	private byte[] dataToEncrypt;
	private byte[] exponentBytes;
	private String pkcs10requestStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.request_cert);

		//		((Button) findViewById(R.id.btn_certreq)).setOnClickListener(certReqButtonListener);
		//		((Button) findViewById(R.id.btn_certreq)).setEnabled(false);
	}

	protected OnClickListener certReqButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			//			if (buttonId == R.id.btn_certreq) {
			//				certificate_request();
			//			}

		}
	};
	private String msg = "";

	protected void certificate_request() {
		// TODO Auto-generated method stub
		prepareReq();
	}

	private void prepareReq() {
		// TODO Auto-generated method stub

		sendCertReq();

	}

	private void sendCertReq() {
		// TODO Auto-generated method stub

		kpair = generateKeyPair();

		try {
			Socket socket = new Socket("130.237.215.188", 9241);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			String strBuffer = "";
			strBuffer = strBuffer + "RequestPKCS10" + "|";

			String userDN = "US|MD|Silver Spring|SETECS Inc|IT|Security Administrator|-|127.0.0.1|";
			strBuffer = strBuffer + PrintableCoding.encode64(userDN.getBytes()) + "|";

			// PubStr is the encoded Base64 string of public key
			KeyFactory fact = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pub = fact.getKeySpec(kpair.getPublic(), RSAPublicKeySpec.class);
			pubkExponent = pub.getPublicExponent();
			exponentBytes = pubkExponent.toByteArray();
			new java.math.BigInteger("+13213165465465465464645");
			pkMudulos = pub.getModulus();
			java.math.BigInteger m = new java.math.BigInteger(1, pkMudulos.toByteArray());
			m.toByteArray();

			// if (pkMudulosBytes[0] == 1) {
			// byte[] tmp = new byte[pkMudulosBytes.length - 1];
			// System.arraycopy(pkMudulosBytes, 1, tmp, 0, tmp.length);
			// pkMudulosBytes = tmp;
			// }

			pkMudulosstr = PrintableCoding.encode64(pkMudulos.toString().getBytes());
			pubkExponentstr = PrintableCoding.encode64(exponentBytes);
			strBuffer = strBuffer + pkMudulosstr + "|";

			strBuffer = strBuffer + pubkExponentstr;

			out.writeUTF(strBuffer);

			// -- recieve the data from server to encrypt

			String dataToEncrypt_str;

			dataToEncrypt_str = in.readUTF();
			dataToEncrypt = PrintableCoding.decode64(dataToEncrypt_str);
			// padding
			byte[] paddedData = new byte[128];
			paddedData[0] = 0x00;
			paddedData[1] = 0x01;
			for (int i = 0; i < 128 - 3 - dataToEncrypt.length; i++) {
				paddedData[2 + i] = (byte) 0xFF;
			}
			paddedData[128 - 1 - dataToEncrypt.length] = 0x00;
			for (int i = 0; i < dataToEncrypt.length; i++) {
				paddedData[128 - dataToEncrypt.length + i] = dataToEncrypt[i];
			}
			// -- encrypte the data----------
			dataEncrypted = encrypt(dataToEncrypt, kpair.getPrivate());

			// -- send encrypted signature back to server to be packaged
			out.writeUTF(PrintableCoding.encode64(dataEncrypted));

			// --- read pkcs10request
			pkcs10request = in.readUTF();

			pkcs10requestStr = PrintableCoding.encode64(pkcs10request.getBytes());
			CertReq = "RequestCertificate|" + pkcs10requestStr + "|136";
			out.writeUTF(CertReq);

			// --- read Certificate

			in.readUTF();

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "RequestCertificate", e);
			throw new RuntimeException(msg, e);
		}

	}

	// private PublicKey generatePK(BigInteger pubkExponent2, BigInteger
	// pkMudulos2) {
	// // TODO Auto-generated method stub
	//
	// PublicKey pubKey=null;
	// try {
	// RSAPublicKeySpec keySpec = new RSAPublicKeySpec(pkMudulos2,
	// pubkExponent2);
	// KeyFactory fact = KeyFactory.getInstance("RSA");
	// pubKey = fact.generatePublic(keySpec);
	// return pubKey;
	//
	//
	// } catch (NoSuchAlgorithmException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvalidKeySpecException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return pubKey;
	// }

	public static byte[] encrypt(byte[] encrptdByte, PrivateKey privateKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptionByte = null;
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		encryptionByte = cipher.doFinal(encrptdByte);
		return encryptionByte;
	}

	public static byte[] decrypt(byte[] encrptdByte, PublicKey publicKeys) throws NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		byte[] encryptionByte = null;
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKeys);
		encryptionByte = cipher.doFinal(encrptdByte);
		// String n =new String(encryptionByte);
		return encryptionByte;

	}

	private KeyPair generateKeyPair() {
		// TODO Auto-generated method stub
		KeyPairGenerator keyGen;
		KeyPair keypair = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			keypair = keyGen.generateKeyPair();

		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
			Log.e(TAG, "generateKeyPair", e);
			throw new RuntimeException(msg, e);
		}
		return keypair;
	}

} // end class