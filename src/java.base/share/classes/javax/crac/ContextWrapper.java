package javax.crac;

class ContextWrapper extends Context<Resource> {
    private final jdk.crac.Context<jdk.crac.Resource> context;

    public ContextWrapper(jdk.crac.Context<jdk.crac.Resource> context) {
        this.context = context;
    }

    private static jdk.crac.Context<? extends jdk.crac.Resource> convertContext(
            Context<? extends Resource> context) {
        return context instanceof ContextWrapper ?
                ((ContextWrapper)context).context :
                null;
    }

    @Override
    public void beforeCheckpoint(Context<? extends Resource> context)
            throws CheckpointException {
        try {
            this.context.beforeCheckpoint(convertContext(context));
        } catch (jdk.crac.CheckpointException e) {
            Throwable[] throwables = e.getSuppressed();
            CheckpointException newException = new CheckpointException();
            for (int i = 0; i < throwables.length; ++i) {
                newException.addSuppressed(throwables[i]);
            }
            throw newException;
        }
    }

    @Override
    public void afterRestore(Context<? extends Resource> context)
            throws RestoreException {
        try {
            this.context.afterRestore(convertContext(context));
        } catch (jdk.crac.RestoreException e) {
            Throwable[] throwables = e.getSuppressed();
            RestoreException newException = new RestoreException();
            for (int i = 0; i < throwables.length; ++i) {
                newException.addSuppressed(throwables[i]);
            }
            throw newException;
        }
    }

    @Override
    public void register(Resource r) {
        ResourceWrapper wrapper = new ResourceWrapper(this, r);
        context.register(wrapper);
    }

    @Override
    public String toString() {
        return "ContextWrapper[" + context.toString() + "]";
    }
}

