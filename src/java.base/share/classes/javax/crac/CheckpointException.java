package javax.crac;

/**
 * {@code CheckpointException} is a collection of exception(s)
 * that happened on checkpoint due to some or many
 * {@code Resource}'s were unable to succeed in
 * {@code beforeCheckpoint} or {@code afterRestore}.
 */
public class CheckpointException extends CheckpointRestoreException {
    private static final long serialVersionUID = 6859967688386143096L;

    /**
     * Constructs a {@code CheckpointException} with
     * an array of exceptions that caused this exception.
     *
     * @param exceptions Array of exceptions that caused this exception.
     */
    public CheckpointException(Exception[] exceptions) {
        super(exceptions);
    }
}
