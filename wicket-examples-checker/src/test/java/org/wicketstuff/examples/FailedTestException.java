package org.wicketstuff.examples;

public class FailedTestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedTestException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
