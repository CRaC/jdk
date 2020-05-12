package javax.crac.impl;

import javax.crac.CheckpointException;
import javax.crac.Resource;
import javax.crac.RestoreException;

/**
 * TODO
 */
public class ContextWrapper implements javax.crac.Context<Resource> {
    private final jdk.crac.Context<jdk.crac.Resource> ctx;

    @Override
    public void beforeCheckpoint() throws CheckpointException {
        try {
            ctx.beforeCheckpoint();
        } catch (jdk.crac.CheckpointException e) {
            throw new CheckpointException(e.getExceptions());
        }
    }

    @Override
    public void afterRestore() throws RestoreException {
        try {
            ctx.afterRestore();
        } catch (jdk.crac.RestoreException e) {
            throw new RestoreException(e.getExceptions());
        }
    }

    /**
     * @param ctx TODO
     */
    public ContextWrapper(jdk.crac.Context<jdk.crac.Resource> ctx) {
        this.ctx = ctx;
    }

    @Override
    public void register(Resource r) {
        ResourceWrapper wrapper = new ResourceWrapper(r);
        ctx.register(wrapper);
    }

    @Override
    public String toString() {
        return "ContextWrapper[" + ctx.toString() + "]";
    }
}

