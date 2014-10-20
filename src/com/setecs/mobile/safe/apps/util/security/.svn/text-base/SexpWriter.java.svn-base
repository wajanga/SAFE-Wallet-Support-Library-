package com.setecs.mobile.safe.apps.util.security;

/*
 * SexpWriter.java
 *
 * Nick Goffee, August 2004
 */

import java.io.IOException;
import java.io.OutputStream;


/**
 * A SexpWriter wraps an OutputStream and provides basic utility functions for
 * beginning and ending an S-expression list and outputting S-expression atoms
 * (strings or data).
 */
public class SexpWriter {

	OutputStream os;

	public SexpWriter(OutputStream os) {
		this.os = os;
	}

	public void startList() throws IOException {
		os.write('(');
	}

	public void endList() throws IOException {
		os.write(')');
	}

	public void writeData(byte[] data) throws IOException {
		os.write(Integer.toString(data.length).getBytes());
		os.write(':');
		os.write(data);
	}

	public void writeString(String s) throws IOException {
		writeData(s.getBytes());
	}

}