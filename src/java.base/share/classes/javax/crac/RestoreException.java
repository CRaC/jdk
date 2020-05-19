package javax.crac;

/**
 * {@code RestoreException} is a collection of exception(s)
 * that happened on restore due to some or many
 * {@code Resource}'s were unable to succeed in
 * {@code afterRestore}.
 */
public class RestoreException extends CheckpointRestoreException {
    private static final long serialVersionUID = -4091592505524280559L;

    /**
     * Constructs a {@code RestoreException} with
     * an array of exceptions that caused this exception.
     *
     * @param exceptions Array of exceptions that caused this exception.
     */
    public RestoreException(Exception[] exceptions) {
        super(exceptions);
    }
}


