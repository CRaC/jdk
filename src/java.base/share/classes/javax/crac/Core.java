package javax.crac;

import jdk.crac.impl.OrderedContext;

import javax.crac.impl.ContextWrapper;
import javax.crac.impl.ResourceWrapper;

/**
 * TODO
 */
public class Core {
    private static final Context<Resource> globalContext = new ContextWrapper(new OrderedContext());
    static {
        jdk.crac.Core.getGlobalContext().register(new ResourceWrapper(globalContext));
    }

    /**
     * @return TODO
     */
    public static Context<Resource> getGlobalContext() {
        return globalContext;
    }

    /**
     * @throws CheckpointException TODO
     * @throws RestoreException TODO
     */
    public static void tryCheckpointRestore() throws
        CheckpointException,
        RestoreException {
        try {
            jdk.crac.Core.tryCheckpointRestore();
        } catch (jdk.crac.CheckpointException e) {
            throw new CheckpointException(e.getExceptions());
        } catch (jdk.crac.RestoreException e) {
            throw new RestoreException(e.getExceptions());
        }
    }
}
