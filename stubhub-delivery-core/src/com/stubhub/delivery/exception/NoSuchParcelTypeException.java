package com.stubhub.delivery.exception;

/**
 * To be thrown when there is no type available in the parcels registry.
 *
 * @author Pavel Savinov
 */
public class NoSuchParcelTypeException extends Exception {

	public NoSuchParcelTypeException() {
		super();
	}

	public NoSuchParcelTypeException(Throwable t) {
		super(t);
	}

}
