package com.vinyl.exception;

public class KeyHolderException extends Exception {

	private static final String EXCEPTION_MESSAGE = "Couldn't extract key from keyholder";

	public KeyHolderException() {
		super(EXCEPTION_MESSAGE);
	}

}
