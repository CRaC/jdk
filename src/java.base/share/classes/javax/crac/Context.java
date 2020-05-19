package javax.crac;

/**
 * {@code Context} is a group of {@code Resource}'s with some order
 * defined for {@code Resource} handling.
 *
 * {@code Context} is a {@code Resource} itself, so it should be
 * registered in some other {@code Context} to be notified.
 * Implementation of {@code Context} should notify all registered
 * {@code Resources} on checkpoint and restore.
 *
 * {@code Core} provides a global context that dispatches notification
 * to all required {@code Resource}'s and {@code Context}'es.
 */
public interface Context<R extends Resource> extends Resource {
    @Override
    void beforeCheckpoint() throws CheckpointException;

    @Override
    void afterRestore() throws RestoreException;

    /**
     * Registers {@code Resource} in this {@code Context}.
     *
     * @param resource {@code Resource} to be registered.
     */
    void register(R resource);
}
