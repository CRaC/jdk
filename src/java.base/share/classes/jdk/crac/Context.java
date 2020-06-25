package jdk.crac;

/**
 * A {@code Resource} that allows other {@code Resource}s to be registered with it.
 *
 * <p>{@code Context} implementation overrides {@code beforeCheckpoint} and {@code afterRestore}, defining how the notification about checkpoint and restore will be distributed by the {@code Context} hierarchy.
 *
 * <p>A {@code Context} implementor is encouraged to respect properties of the global {@code Context}.
 */
public abstract class Context<R extends Resource> implements Resource {

    /** Creates a {@code Context}.
     */
    protected Context() {
    }

    @Override
    public abstract void beforeCheckpoint(Context<? extends Resource> context)
            throws CheckpointException;

    @Override
    public abstract void afterRestore(Context<? extends Resource> context)
            throws RestoreException;

    /**
     * Registers a {@code Resource} with this {@code Context}.
     *
     * @param resource {@code Resource} to be registered.
     * @throws NullPointerException if {@code resource} is {@code null}
     */
    public abstract void register(R resource);
}
