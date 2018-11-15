package com.stubhub.delivery.exception;

/**
 * To be thrown when there is no type available in the destinations registry.
 *
 * @author Pavel Savinov
 */
public class NoSuchDestinationTypeException extends Exception {

	public NoSuchDestinationTypeException() {
		super();
	}

	public NoSuchDestinationTypeException(Throwable t) {
		super(t);
	}

}
