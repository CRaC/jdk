package javax.crac;

import java.io.PrintStream;

/**
 * TODO
 */
public class CheckpointRestoreException extends Exception {
    private static final long serialVersionUID = 0;

    private jdk.crac.CheckpointRestoreException exception;

    /*not-public*/ CheckpointRestoreException(jdk.crac.CheckpointRestoreException exception) {
        this.exception = exception;
    }

    /**
     * @param exceptions TODO
     */
    public CheckpointRestoreException(Exception[] exceptions) {
        this.exception = new jdk.crac.CheckpointRestoreException(exceptions);
    }

    /**
     * @return TODO
     */
    public Exception[] getExceptions() {
        return exception.getExceptions();
    }

    /**
     * @param s TODO
     */
    public void printExceptions(PrintStream s) {
        exception.printExceptions(s);
    }

    /**
     * TODO
     */
    public void printExceptions() {
        exception.printExceptions(System.err);
    }
}
