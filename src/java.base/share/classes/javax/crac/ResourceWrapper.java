package javax.crac;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

class ResourceWrapper extends WeakReference<Resource> implements jdk.crac.Resource {
    private static WeakHashMap<Resource, ResourceWrapper> weakMap = new WeakHashMap<>();

    // Create strong reference to avoid losing the Resource.
    // It's set unconditionally in beforeCheckpoint and cleaned in afterRestore
    // (latter is called regardless of beforeCheckpoint result).
    private Resource strongRef;

    private final Context<Resource> context;

    public ResourceWrapper(Context<Resource> context, Resource resource) {
        super(resource);
        weakMap.put(resource, this);
        strongRef = null;
        this.context = context;
    }

    @Override
    public String toString() {
        return "ResourceWrapper[" + get().toString() + "]";
    }

    @Override
    public void beforeCheckpoint(jdk.crac.Context<? extends jdk.crac.Resource> context)
            throws Exception {
        Resource r = get();
        strongRef = r;
        if (r != null) {
            try {
                r.beforeCheckpoint(this.context);
            } catch (CheckpointException e) {
                Exception newException = new jdk.crac.CheckpointException();
                for (Throwable t : e.getSuppressed()) {
                    newException.addSuppressed(t);
                }
                throw newException;
            }
        }
    }

    @Override
    public void afterRestore(jdk.crac.Context<? extends jdk.crac.Resource> context) throws Exception {
        Resource r = get();
        strongRef = null;
        if (r != null) {
            try {
                r.afterRestore(this.context);
            } catch (RestoreException e) {
                Exception newException = new jdk.crac.RestoreException();
                for (Throwable t : e.getSuppressed()) {
                    newException.addSuppressed(t);
                }
                throw newException;
            }
        }
    }
}
