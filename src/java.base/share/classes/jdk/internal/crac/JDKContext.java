package jdk.internal.crac;

import jdk.crac.impl.Context;

import java.util.Comparator;
import java.util.Map;

public class JDKContext extends Context<JDKResource, Void> {
    static class ContextComparator implements Comparator<Map.Entry<JDKResource, Void>> {
        @Override
        public int compare(Map.Entry<JDKResource, Void> o1, Map.Entry<JDKResource, Void> o2) {
            return o2.getKey().getPriority() - o1.getKey().getPriority();
        }
    }

    JDKContext() {
        super(new ContextComparator());
    }

    @Override
    public void register(JDKResource resource) {
        register(resource, null);
    }
}
