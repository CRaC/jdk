package javax.crac.impl;

import javax.crac.CheckpointException;
import javax.crac.Resource;
import javax.crac.RestoreException;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * TODO
 */
public class ResourceWrapper extends WeakReference<Resource> implements jdk.crac.Resource {
    private static WeakHashMap<Resource, ResourceWrapper> weakMap = new WeakHashMap<>();

    // Create strong reference to avoid losing the Resource.
    // It's set unconditionally in beforeCheckpoint and cleaned in afterRestore
    // (latter is called regardless of beforeCheckpoint result).
    private Resource strongRef;

    /**
     * @param referent TODO
     */
    public ResourceWrapper(Resource referent) {
        super(referent);
        weakMap.put(referent, this);
        strongRef = null;
    }

    @Override
    public void beforeCheckpoint() throws Exception {
        Resource r = get();
        strongRef = r;
        if (r != null) {
            try {
                r.beforeCheckpoint();
            } catch (CheckpointException e) {
                throw new jdk.crac.CheckpointException(e.getExceptions());
            }
        }
    }

    @Override
    public void afterRestore() throws Exception {
        Resource r = get();
        strongRef = null;
        if (r != null) {
            try {
                r.afterRestore();
            } catch (RestoreException e) {
                throw new jdk.crac.RestoreException(e.getExceptions());
            }
        }
    }

    @Override
    public String toString() {
        return "ResourceWrapper[" + get().toString() + "]";
    }
}
