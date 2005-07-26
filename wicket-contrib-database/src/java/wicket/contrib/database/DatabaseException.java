/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

/**
 * Runtime exception class for database problems.
 */
public final class DatabaseException extends RuntimeException
{
	/**
	 * Construct.
	 */
	public DatabaseException()
	{
		super();
	}

	/**
	 * Construct.
	 * @param message
	 */
	public DatabaseException(String message)
	{
		super(message);
	}

	/**
	 * Construct.
	 * @param cause
	 */
	public DatabaseException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Construct.
	 * @param message
	 * @param cause
	 */
	public DatabaseException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
