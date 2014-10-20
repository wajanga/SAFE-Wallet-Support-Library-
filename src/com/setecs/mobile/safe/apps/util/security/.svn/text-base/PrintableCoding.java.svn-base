package com.setecs.mobile.safe.apps.util.security;

public class PrintableCoding {

	// --------------------------
	public PrintableCoding() {}

	// -------------------------------------------------------------------------------------
	// encodes the input byte array using base64 encoding and returns the result
	// as a String
	public static String encode64(byte[] in_bytes) {

		StringBuffer result = new StringBuffer();
		byte[] input = null;
		byte[] F3 = { (byte) 243, (byte) 243, (byte) 243 };
		byte[] F2 = { (byte) 242, (byte) 242 };
		byte[] F1 = { (byte) 241 };
		int spinner = 0; // counts the number of bytes encoded in base64,
		// it should be reset when it reaches 2.
		byte buffer = (byte) 0;
		byte remains = (byte) 0; // the bits that remain uncoded from a byte.

		int reminder = modulo(in_bytes.length, 3);

		if (reminder == 0)
			input = concat(in_bytes, F3); // padding with F3
		if (reminder == 1) {
			input = concat(in_bytes, F2); // padding with F2
		}
		if (reminder == 2) {
			input = concat(in_bytes, F1); // padding with F1
		}

		for (int i = 0; i < input.length; i++) {
			if (spinner == 0) { // first byte in the base64 scheme
				buffer = input[i];
				remains = (byte) 0;
				remains = input[i];
				buffer = shift_right(buffer, 2);
				remains = shift_left(remains, 6);
				result.append((char) buffer);
			}
			if (spinner == 1) { // second byte in the base64 scheme
				buffer = input[i];
				buffer = shift_right(buffer, 4);
				remains = shift_right(remains, 2);
				buffer = (byte) (buffer | remains);
				remains = input[i];
				remains = shift_left(remains, 4);
				result.append((char) buffer);
			}
			if (spinner == 2) { // third byte in the base64 scheme
				buffer = input[i];
				buffer = shift_right(buffer, 6);
				remains = shift_right(remains, 2);
				buffer = (byte) (buffer | remains);
				result.append((char) buffer);
				// go on to the final byte in the result
				buffer = input[i];
				byte mask = (byte) 63;
				buffer = (byte) (buffer & mask);
				result.append((char) buffer);
			}

			if (spinner == 2)
				spinner = 0;
			else
				spinner++;
		}

		return low2alpha(result.toString());
	}

	// ----------------------------------------------------------------------------
	// Decodes the input String using base64 and returns the result as a byte
	// array.
	public static byte[] decode64(String in_string) {

		byte[] output = null;
		byte[] outbuff = null;
		char[] input = null;
		byte F3 = (byte) 243;
		byte F2 = (byte) 242;
		byte F1 = (byte) 241;
		byte buffer = (byte) 0;
		byte result = (byte) 0;
		int spinner = 0; // counts the number of bytes encoded in base64,
		// it should be reset when it reaches 3.
		int out_length = 0; // the length of the outbuff byte array.
		int output_index = 0; // the position in the outbuff buffer.

		input = (alpha2low(in_string)).toCharArray();
		int input_length = 0;
		input_length = input.length;

		if (input_length < 4)
			return null;

		out_length = (input.length / 4) * 3; // calculates the length of the
		// resulting byte array
		// out_length = 129;
		outbuff = new byte[out_length]; // allocates outbuff buffer

		for (int i = 0; i < input.length; i++) {
			if (spinner == 0) {
				buffer = (byte) input[i];
				buffer = shift_left(buffer, 2);
				result = buffer;
			}
			if (spinner == 1) {
				buffer = (byte) input[i];
				buffer = shift_right(buffer, 4);
				result = (byte) (result | buffer);
				outbuff[output_index] = result;
				output_index++;
				result = (byte) 0;
				buffer = (byte) input[i];
				buffer = shift_left(buffer, 4);
				result = buffer;
			}
			if (spinner == 2) {
				buffer = (byte) input[i];
				buffer = shift_right(buffer, 2);
				result = (byte) (result | buffer);
				outbuff[output_index] = result;
				output_index++;
				result = (byte) 0;
				buffer = (byte) input[i];
				buffer = shift_left(buffer, 6);
				result = buffer;
			}
			if (spinner == 3) {
				buffer = (byte) input[i];
				result = (byte) (result | buffer);
				outbuff[output_index] = result;
				output_index++;
				result = (byte) 0;
			}
			if (spinner == 3)
				spinner = 0;
			else
				spinner++;
		}

		// --- checking for padding bytes ---
		if (outbuff[out_length - 1] == F1) {
			output = subarray(outbuff, 0, out_length - 1);
		}
		else if ((outbuff[out_length - 1] == F2) && (outbuff[out_length - 2] == F2)) {
			output = subarray(outbuff, 0, out_length - 2);
		}
		else if ((outbuff[out_length - 1] == F3) && (outbuff[out_length - 2] == F3) && (outbuff[out_length - 3] == F3)) {
			output = subarray(outbuff, 0, out_length - 3);
		}
		else
			output = outbuff;

		return output;
	}

	// -----------------------------------------------------------------------------------------
	// shift_left, instead of the << operator (introduced for consistency with
	// the next method)
	public static byte shift_left(byte b, int positions) {
		b <<= positions;
		return b;
	}

	// -----------------------------------------------------------------------------------------
	// shift_right, more reliable then the >>> operator (right shift and fill
	// with zeroes.)
	public static byte shift_right(byte b, int positions) {

		byte mask = (byte) 127;
		if (positions == 0)
			return b;
		b >>= 1;
		b = (byte) (b & mask);
		if (positions > 1)
			b >>= (positions - 1);
		return b;
	}

	// ---------------------------------------------------------
	// returns another byte array which is a substring of the original, from
	// position
	// "from" and with the length "length".
	public static byte[] subarray(byte[] array, int offset, int length) {

		byte[] result;
		int i;
		result = new byte[length];
		for (i = 0; i < length; i++)
			result[i] = array[i + offset];
		return result;
	}

	// ---------------------------------------------------------
	// modulo function
	public static int modulo(int number, int divisor) {

		int result;
		result = number - ((number / divisor) * divisor);
		return result;
	}

	// ---------------------------------------------------------
	// concatenates two byte arrays and returns the result
	public static byte[] concat(byte[] one, byte[] two) {

		byte[] result;
		int i;
		int length = one.length + two.length;
		result = new byte[length];
		for (i = 0; i < one.length; i++)
			result[i] = one[i];
		for (i = 0; i < two.length; i++)
			result[i + one.length] = two[i];
		return result;
	}

	// ---------------------------------------------------------
	// maps the bytes from 0 - 63 to alphanumerical characters.
	public static String low2alpha(String in_str) {

		int i;
		int byte_nr;
		char[] input;
		String output;
		StringBuffer outbuff = new StringBuffer();
		char[] trans_table = { 'A',
				'B',
				'C',
				'D',
				'E',
				'F',
				'G',
				'H',
				'I',
				'J',
				'K',
				'L',
				'M',
				'N',
				'O',
				'P',
				'Q',
				'R',
				'S',
				'T',
				'U',
				'V',
				'W',
				'X',
				'Y',
				'Z',
				'a',
				'b',
				'c',
				'd',
				'e',
				'f',
				'g',
				'h',
				'i',
				'j',
				'k',
				'l',
				'm',
				'n',
				'o',
				'p',
				'q',
				'r',
				's',
				't',
				'u',
				'v',
				'w',
				'x',
				'y',
				'z',
				'0',
				'1',
				'2',
				'3',
				'4',
				'5',
				'6',
				'7',
				'8',
				'9',
				'+',
				'/' };

		input = in_str.toCharArray();

		for (i = 0; i < input.length; i++) {
			byte_nr = input[i];
			outbuff.append(trans_table[byte_nr]);
		}

		output = outbuff.toString();
		return output;
	}

	// ---------------------------------------------------------
	// maps the alphanumerical characters to bytes in 0-63
	public static String alpha2low(String in_str) {

		int i;
		int byte_nr;
		char[] input;
		String output;
		StringBuffer outbuff = new StringBuffer();

		input = in_str.toCharArray();

		for (i = 0; i < input.length; i++) {
			byte_nr = input[i];
			if (byte_nr == 43)
				byte_nr = 62; // + sign
			else if (byte_nr == 47)
				byte_nr = 63; // / sign
			else if ((byte_nr >= 48) && (byte_nr <= 57))
				byte_nr = byte_nr + 4; // numbers
			else if ((byte_nr >= 65) && (byte_nr <= 90))
				byte_nr = byte_nr - 65; // capital letters
			else
				byte_nr = byte_nr - 71; // small letters
			outbuff.append((char) byte_nr);
		}

		output = outbuff.toString();
		return output;
	}
}
