package javax.crac;

/**
 * TODO
 */
public class CheckpointException extends CheckpointRestoreException {
    private static final long serialVersionUID = 0;

    /*not-public*/ CheckpointException(jdk.crac.CheckpointRestoreException exception) {
        super(exception);
    }

    /**
     * @param exceptions TODO
     */
    public CheckpointException(Exception[] exceptions) {
        super(exceptions);
    }
}
