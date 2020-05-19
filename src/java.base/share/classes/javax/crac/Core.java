package javax.crac;

/**
 * Checkpoint/Restore-after-Checkpoint mechanism
 * allows to capture an image of the running Java Platform
 * and then create another instances of Java starting from
 * the point of checkpoint.
 *
 * Checkpoint/Restore is not always possible because some
 * state for the Java instance may be stored externally, like in
 * resources of operating system or distributed to remote nodes.
 * C/RaC provides a {@code Resource}'s and {@code Context}'es notion,
 * that are notified when the system checkpoints and restores,
 * allowing to manage such external state.
 *
 * So, Checkpoint consists of preparation for the image creation
 * (notification of {@code Resource} for checkpoint),
 * the image creation and  termination of current instance.
 * Restore is creation of new Java instance and notification of
 * {@code Resources}.
 *
 * The checkpoint is initiated by Java code by calling
 * {@code tryCheckpointRestore}, while all other configuration
 * is performed by command-line flags.
 *
 */
public class Core {
    static class NullContext implements Context<Resource> {

        @Override
        public void beforeCheckpoint() throws CheckpointException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void afterRestore() throws RestoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void register(Resource resource) {
            // Do nothing
        }
    }

    private static final Context<Resource> nullContext = new NullContext();

    /**
     * Returns a global {@code Context}, that is a first context receiving
     * checkpoint and restore notification.
     *
     * @return a global {@code Context}
     */
    public static Context<Resource> getGlobalContext() {
        return nullContext;
    }

    /**
     * Perform checkpoint. Returns after restore.
     *
     * @throws CheckpointException if checkpoint was aborted due to {@code Resource}(s) failed to checkpoint.
     * @throws RestoreException if, during restore, some {@code Resources]}(s) failed after restore.
     */
    public static void tryCheckpointRestore() throws
        CheckpointException,
        RestoreException {
        throw new CheckpointException(new Exception[] { new UnsupportedOperationException() });
    }
}
