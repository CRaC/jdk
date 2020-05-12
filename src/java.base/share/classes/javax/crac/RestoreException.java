package javax.crac;

/**
 * TODO
 */
public class RestoreException extends CheckpointRestoreException {
    private static final long serialVersionUID = 0;

    /*not-public*/ RestoreException(jdk.crac.CheckpointRestoreException exception) {
        super(exception);
    }

    /**
     * @param exceptions TODO
     */
    public RestoreException(Exception[] exceptions) {
        super(exceptions);
    }
}


