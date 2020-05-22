package jdk.crac;

public abstract class Context<R extends Resource> implements Resource {

    @Override
    public abstract void beforeCheckpoint(Context<? extends Resource> context)
            throws CheckpointException;

    @Override
    public abstract void afterRestore(Context<? extends Resource> context)
            throws RestoreException;

    public abstract void register(R resource);
}
