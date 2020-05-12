package jdk.crac;

public interface Context<R extends Resource> extends Resource {
    @Override
    void beforeCheckpoint() throws CheckpointException;

    @Override
    void afterRestore() throws RestoreException;

    void register(R r);
}
