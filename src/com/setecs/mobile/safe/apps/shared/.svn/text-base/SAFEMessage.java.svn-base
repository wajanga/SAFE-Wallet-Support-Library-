package com.setecs.mobile.safe.apps.shared;

import android.util.Base64;

import com.setecs.mobile.safe.apps.util.security.DigestProvider;
import com.setecs.mobile.safe.apps.util.security.PrintableCoding;
import com.setecs.mobile.safe.apps.util.security.SymmetricCryption;


public class SAFEMessage {

	private final DigestProvider dp_ = new DigestProvider();
	private final SymmetricCryption sc_ = new SymmetricCryption();
	byte[] symmetric_key_AsBytes = new byte[] { (byte) 0x01,
			(byte) 0x23,
			(byte) 0x45,
			(byte) 0x67,
			(byte) 0x89,
			(byte) 0xab,
			(byte) 0xcd,
			(byte) 0xef, };

	byte walletSAFEMessageSystemCode; // Transfer to Bank Server
	byte SAFEMessageHashAlgID = 0x1; // SHA-1
	byte SAFEMessageEncAlgID = 0x1; // AES
	byte SAFEMessageSignAlgID = 0x1; // RSA-512

	String phoneNo = new String();
	String clientMessage = new String();
	String SAFEcommand = new String();
	String parameter_1 = new String();
	String parameter_2 = new String();

	String serverMessage = new String();

	// --------------------------
	public SAFEMessage() {}

	// ---------------------------------------------------------------------------
	public String createGWMessage(String security_services, String walletMessage) {

		byte walletSAFEMessageTag;
		byte[] walletSAFEMessagePhoneNoPayload;
		byte[] walletSAFEMessageClientMessLength = new byte[2];
		byte[] walletSAFEMessageClientMessPayload;
		byte[] walletSAFEMessageClientMessHash;
		byte[] walletSAFEMessageClientMessEnc;
		byte[] walletSAFEMessageClientMessEncLength = new byte[2];
		//		int walletSAFEMessageHashSign_length = 0;
		//		int walletSAFEMessageEnvelope_length = 0;
		byte[] walletSAFEMessage = null;

		parseCommand(walletMessage);

		// --- Clear message --------
		if (security_services.equals("0")) {

			walletSAFEMessageSystemCode = 0x0;
			walletSAFEMessageTag = 0x0;
			walletSAFEMessagePhoneNoPayload = phoneNo.getBytes();
			walletSAFEMessageClientMessLength = convertIntToBytePair(clientMessage.length());
			walletSAFEMessageClientMessPayload = clientMessage.getBytes();

			// --- Create SAFEMessage ----------------------
			walletSAFEMessage = new byte[6 + phoneNo.length() + 2 + walletSAFEMessageClientMessPayload.length];

			walletSAFEMessage[0] = walletSAFEMessageSystemCode;
			walletSAFEMessage[1] = walletSAFEMessageTag;
			walletSAFEMessage[2] = SAFEMessageHashAlgID;
			walletSAFEMessage[3] = SAFEMessageEncAlgID;
			walletSAFEMessage[4] = SAFEMessageSignAlgID;

			// --- Load phone number ---------------------
			walletSAFEMessage[5] = (byte) phoneNo.length();
			for (int i = 0; i < phoneNo.length(); i++) {
				walletSAFEMessage[6 + i] = walletSAFEMessagePhoneNoPayload[i];
			}

			// --- Load client message ---------------------
			walletSAFEMessage[6 + phoneNo.length()] = walletSAFEMessageClientMessLength[0];
			walletSAFEMessage[6 + phoneNo.length() + 1] = walletSAFEMessageClientMessLength[1];
			for (int i = 0; i < walletSAFEMessageClientMessPayload.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + i] = walletSAFEMessageClientMessPayload[i];
			}
		}
		// --- Hashing of the message --------
		else if (security_services.equals("1")) {

			walletSAFEMessageSystemCode = 0x1;
			walletSAFEMessageTag = 0x1;
			walletSAFEMessagePhoneNoPayload = phoneNo.getBytes();
			walletSAFEMessageClientMessLength = convertIntToBytePair(clientMessage.length());
			walletSAFEMessageClientMessPayload = clientMessage.getBytes();
			walletSAFEMessageClientMessHash = dp_.digest(walletSAFEMessageClientMessPayload);

			// --- Create SAFEMessage ----------------------
			walletSAFEMessage = new byte[6 + phoneNo.length()
					+ 2
					+ walletSAFEMessageClientMessPayload.length
					+ walletSAFEMessageClientMessHash.length];

			walletSAFEMessage[0] = walletSAFEMessageSystemCode;
			walletSAFEMessage[1] = walletSAFEMessageTag;
			walletSAFEMessage[2] = SAFEMessageHashAlgID;
			walletSAFEMessage[3] = SAFEMessageEncAlgID;
			walletSAFEMessage[4] = SAFEMessageSignAlgID;

			// --- Load phone number ---------------------
			walletSAFEMessage[5] = (byte) phoneNo.length();
			for (int i = 0; i < phoneNo.length(); i++) {
				walletSAFEMessage[6 + i] = walletSAFEMessagePhoneNoPayload[i];
			}

			// --- Load client message ---------------------
			walletSAFEMessage[6 + phoneNo.length()] = walletSAFEMessageClientMessLength[0];
			walletSAFEMessage[6 + phoneNo.length() + 1] = walletSAFEMessageClientMessLength[1];
			for (int i = 0; i < walletSAFEMessageClientMessPayload.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + i] = walletSAFEMessageClientMessPayload[i];
			}
			// --- Load client message hash ---------------------
			for (int i = 0; i < walletSAFEMessageClientMessHash.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + walletSAFEMessageClientMessPayload.length + i] = walletSAFEMessageClientMessHash[i];
			}
			System.out.println("The HASH value is : " + new String(walletSAFEMessageClientMessHash));
		}

		// --- Encryption of the message --------
		else if (security_services.equals("2")) {

			walletSAFEMessageSystemCode = 0x1;
			walletSAFEMessageTag = 0x2;
			walletSAFEMessagePhoneNoPayload = phoneNo.getBytes();
			walletSAFEMessageClientMessPayload = clientMessage.getBytes();
			walletSAFEMessageClientMessEnc = sc_
					.DESEncryptor(walletSAFEMessageClientMessPayload, symmetric_key_AsBytes);
			walletSAFEMessageClientMessLength = convertIntToBytePair(walletSAFEMessageClientMessEnc.length);

			// --- Create SAFEMessage ----------------------
			walletSAFEMessage = new byte[6 + phoneNo.length() + 2 + walletSAFEMessageClientMessEnc.length];

			walletSAFEMessage[0] = walletSAFEMessageSystemCode;
			walletSAFEMessage[1] = walletSAFEMessageTag;
			walletSAFEMessage[2] = SAFEMessageHashAlgID;
			walletSAFEMessage[3] = SAFEMessageEncAlgID;
			walletSAFEMessage[4] = SAFEMessageSignAlgID;

			// --- Load phone number ---------------------
			walletSAFEMessage[5] = (byte) phoneNo.length();
			for (int i = 0; i < phoneNo.length(); i++) {
				walletSAFEMessage[6 + i] = walletSAFEMessagePhoneNoPayload[i];
			}

			// --- Load encrypted client message ---------------------
			walletSAFEMessage[6 + phoneNo.length()] = walletSAFEMessageClientMessLength[0];
			walletSAFEMessage[6 + phoneNo.length() + 1] = walletSAFEMessageClientMessLength[1];
			for (int i = 0; i < walletSAFEMessageClientMessEnc.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + i] = walletSAFEMessageClientMessEnc[i];
			}
		}
		// --- Encryption and Hashing of the message --------
		else if (security_services.equals("3")) {

			walletSAFEMessageSystemCode = 0x1;
			walletSAFEMessageTag = 0x3;
			walletSAFEMessagePhoneNoPayload = phoneNo.getBytes();
			walletSAFEMessageClientMessEnc = sc_.DESEncryptor(clientMessage.getBytes(), symmetric_key_AsBytes);
			// walletSAFEMessageClientMessHash =
			// dp_.digest(clientMessage.getBytes());
			walletSAFEMessageClientMessHash = dp_.digest(walletSAFEMessageClientMessEnc);
			walletSAFEMessageClientMessEncLength = convertIntToBytePair(walletSAFEMessageClientMessEnc.length);

			// --- Create SAFEMessage ----------------------
			walletSAFEMessage = new byte[6 + phoneNo.length()
					+ 2
					+ walletSAFEMessageClientMessEnc.length
					+ walletSAFEMessageClientMessHash.length];

			walletSAFEMessage[0] = walletSAFEMessageSystemCode;
			walletSAFEMessage[1] = walletSAFEMessageTag;
			walletSAFEMessage[2] = SAFEMessageHashAlgID;
			walletSAFEMessage[3] = SAFEMessageEncAlgID;
			walletSAFEMessage[4] = SAFEMessageSignAlgID;

			// --- Load phone number ---------------------
			walletSAFEMessage[5] = (byte) phoneNo.length();
			for (int i = 0; i < phoneNo.length(); i++) {
				walletSAFEMessage[6 + i] = walletSAFEMessagePhoneNoPayload[i];
			}

			// --- Load encrypted client message ---------------------
			walletSAFEMessage[6 + phoneNo.length()] = walletSAFEMessageClientMessEncLength[0];
			walletSAFEMessage[6 + phoneNo.length() + 1] = walletSAFEMessageClientMessEncLength[1];
			for (int i = 0; i < walletSAFEMessageClientMessEnc.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + i] = walletSAFEMessageClientMessEnc[i];
			}

			// --- Load client message hash ---------------------
			for (int i = 0; i < walletSAFEMessageClientMessHash.length; i++) {
				walletSAFEMessage[6 + phoneNo.length() + 2 + walletSAFEMessageClientMessEnc.length + i] = walletSAFEMessageClientMessHash[i];
			}
		}
		//return PrintableCoding.encode64(walletSAFEMessage) + "\n";
		//return Base64.encodeToString(walletSAFEMessage, Base64.DEFAULT) + "\n";
		return Base64.encodeToString(walletSAFEMessage, Base64.NO_WRAP) + "\n";
	}

	// ---------------------------------------------------------------
	public String processGWMessage(String serverGWMessage) {

		byte[] serverSAFEMessage;
		int phoneNo_length = 0;
		int serverMessage_length = 0;
		byte[] serverMessageAsBytes;
		byte[] serverMessageHash;
		byte[] serverMessageHash_New;
		byte[] serverMessageEnc;
		int serverMessageEnc_length = 0;

		// --- Detach "\n" at the end -----------------
		serverGWMessage = serverGWMessage.substring(0, serverGWMessage.length() - 1);
		serverSAFEMessage = PrintableCoding.decode64(serverGWMessage);

		// --- Clear Reply ----------------------------
		if (serverSAFEMessage[1] == 0) {

			phoneNo_length = serverSAFEMessage[5];
			serverMessage_length = convertBytePairToInt(serverSAFEMessage[6 + phoneNo_length],
					serverSAFEMessage[6 + phoneNo_length + 1]);

			// --- Extract server message -----------------------
			serverMessageAsBytes = new byte[serverMessage_length];
			for (int i = 0; i < serverMessage_length; i++) {
				serverMessageAsBytes[i] = serverSAFEMessage[6 + phoneNo_length + 2 + i];
			}

			serverMessage = new String(serverMessageAsBytes);
		}

		// --- Hashed Reply ----------------------------
		else if (serverSAFEMessage[1] == 1) {

			phoneNo_length = serverSAFEMessage[5];
			serverMessage_length = convertBytePairToInt(serverSAFEMessage[6 + phoneNo_length],
					serverSAFEMessage[6 + phoneNo_length + 1]);

			// --- Extract server message ----------------------
			serverMessageAsBytes = new byte[serverMessage_length];
			for (int i = 0; i < serverMessage_length; i++) {
				serverMessageAsBytes[i] = serverSAFEMessage[6 + phoneNo_length + 2 + i];
			}

			serverMessage = new String(serverMessageAsBytes);

			// --- Extract server message hash ----------------------
			serverMessageHash = new byte[20];
			for (int i = 0; i < 20; i++) {
				serverMessageHash[i] = serverSAFEMessage[6 + phoneNo_length + 2 + serverMessage_length + i];
			}
			serverMessageHash_New = dp_.digest(serverMessageAsBytes);
			serverMessage = new String(serverMessageAsBytes);
			for (int i = 0; i < 20; i++) {
				if (serverMessageHash[i] != serverMessageHash_New[i]) {
					serverMessage = "***";
					break;
				}
			}
		}

		// --- Encrypted Reply ----------------------------
		else if (serverSAFEMessage[1] == 2) {

			phoneNo_length = serverSAFEMessage[5];
			serverMessageEnc_length = convertBytePairToInt(serverSAFEMessage[6 + phoneNo_length],
					serverSAFEMessage[6 + phoneNo_length + 1]);

			// --- Extract server encrypted message -----------------------
			serverMessageEnc = new byte[serverMessageEnc_length];
			for (int i = 0; i < serverMessageEnc_length; i++) {
				serverMessageEnc[i] = serverSAFEMessage[6 + phoneNo_length + 2 + i];
			}

			serverMessage = new String(sc_.DESDecryptor(serverMessageEnc, symmetric_key_AsBytes));
		}

		// --- Encrypted and Hashed Reply ----------------------------
		else if (serverSAFEMessage[1] == 3) {

			phoneNo_length = serverSAFEMessage[5];
			serverMessage_length = convertBytePairToInt(serverSAFEMessage[6 + phoneNo_length],
					serverSAFEMessage[6 + phoneNo_length + 1]);

			// --- Extract encrypted server message ----------------------
			serverMessageEnc = new byte[serverMessage_length];
			for (int i = 0; i < serverMessage_length; i++) {
				serverMessageEnc[i] = serverSAFEMessage[6 + phoneNo_length + 2 + i];
			}
			serverMessageAsBytes = sc_.DESDecryptor(serverMessageEnc, symmetric_key_AsBytes);
			serverMessage = new String(serverMessageAsBytes);

			// --- Extract server message hash ----------------------
			serverMessageHash = new byte[20];
			for (int i = 0; i < 20; i++) {
				serverMessageHash[i] = serverSAFEMessage[6 + phoneNo_length + 2 + serverMessage_length + i];
			}

			// --- Verify Hash -------------------------------------
			serverMessageHash_New = dp_.digest(serverMessageEnc);
			serverMessage = new String(serverMessageAsBytes);
			for (int i = 0; i < 20; i++) {
				if (serverMessageHash[i] != serverMessageHash_New[i]) {
					serverMessage = "***";
					break;
				}
			}
		}

		return serverMessage;
	}

	// ---------------------------------------------------------------
	private void parseCommand(String receivedCommand) {
		phoneNo = receivedCommand.substring(1, receivedCommand.indexOf(";"));
		clientMessage = receivedCommand.substring(receivedCommand.indexOf(";") + 1, receivedCommand.length() - 1);
	}

	// ------------------------------------------------------
	// --- Convert an int into a two-byte array ---
	public static byte[] convertIntToBytePair(int data) {

		byte[] result = new byte[2];
		int low_byte = data % 256;
		int high_byte = ((data - low_byte) / 256);
		result[0] = (new Integer(high_byte)).byteValue();
		//result[0] = (byte) ((byte) (high_byte) & 0xff);
		result[1] = (new Integer(low_byte)).byteValue();
		//result[1] = (byte) ((byte) low_byte & 0xff);

		return result;
	}

	// ----------------------------------------------------------
	public static int convertBytePairToInt(byte msb, byte lsb) {
		int imsb, ilsb;
		if (msb < 0)
			imsb = msb + 256;
		else
			imsb = msb;
		if (lsb < 0)
			ilsb = lsb + 256;
		else
			ilsb = lsb;
		return (imsb * 256 + ilsb);
	}
}
