package org.walkingarchive.app;

/**
 * A utility interface that emulates C#'s anonymous functions and Python's lambda functions
 */
public interface AsyncTaskCallback
{
    /**
     * An anonymous method to run.
     * <p>
     * This must be implemented as an inner type upon instantiation
     * </p>
     * @param o  A single Object parameter, if needed
     */
	public abstract void run(Object o);
}
