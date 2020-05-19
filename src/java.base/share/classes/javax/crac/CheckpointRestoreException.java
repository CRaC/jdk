package javax.crac;

import java.io.PrintStream;

/**
 * {@code CheckpointRestoreException} is a collection of exception(s)
 * that happened on checkpoint or restore due to some or many
 * {@code Resource}'s were unable to succeed in
 * {@code beforeCheckpoint} or {@code afterRestore} calls.
 */
public class CheckpointRestoreException extends Exception {
    private static final long serialVersionUID = -806684803281289037L;

    private Exception[] exceptions;

    /**
     * Constructs a {@code CheckpointRestoreException} with
     * an array of exceptions that caused this exception.
     *
     * @param exceptions Array of exceptions that caused this exception.
     */
    public CheckpointRestoreException(Exception[] exceptions) {
        this.exceptions = exceptions;
    }

    /**
     * Returns array of exceptions that caused this exception.
     *
     * @return Array of exceptions.
     */
    public Exception[] getExceptions() {
        return exceptions;
    }

    /**
     * Prints information about exceptions that caused this exception
     * to a provided {@code PrintStream}.
     *
     * @param stream Stream to print the information to.
     */
    public void printExceptions(PrintStream stream) {
        for (int i = 0; i < exceptions.length; ++i) {
            stream.println("  " + exceptions[i].toString());
        }
    }

    /**
     * Prints information about exceptions that caused this exception to {@code System.err}.
     */
    public void printExceptions() {
        printExceptions(System.err);
    }
}
