package jdk.crac.impl;

import jdk.crac.Resource;
import jdk.crac.impl.AbstractContextImpl;

import java.util.Comparator;
import java.util.Map;

public class OrderedContext extends AbstractContextImpl<Resource, Long> {
    private long order;

    static class ContextComparator implements Comparator<Map.Entry<Resource, Long>> {
        @Override
        public int compare(Map.Entry<Resource, Long> o1, Map.Entry<Resource, Long> o2) {
            return (int)(o2.getValue() - o1.getValue());
        }
    }

    public OrderedContext() {
        super(new ContextComparator());
    }

    @Override
    public synchronized void register(Resource r) {
        register(r, order++);
    }
}
