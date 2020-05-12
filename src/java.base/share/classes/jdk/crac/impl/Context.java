// Copyright 2017-2019 Azul Systems, Inc.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
//
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jdk.crac.impl;

import jdk.crac.CheckpointException;
import jdk.crac.Resource;
import jdk.crac.RestoreException;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Context<R extends Resource, P> implements jdk.crac.Context<R> {
    private static final boolean DEBUG = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    return Boolean.parseBoolean(
                            System.getProperty("jdk.crac.ResourceManager.debug"));
                }});

    private WeakHashMap<R, P> checkpointQ = new WeakHashMap<>();
    private List<R> restoreQ = null;
    private Comparator<Map.Entry<R, P>> comparator;

    protected Context(Comparator<Map.Entry<R, P>> comparator) {
        this.comparator = comparator;
    }

    protected synchronized void register(R resource, P payload) {
        checkpointQ.put(resource, payload);
    }

    @Override
    public synchronized void beforeCheckpoint() throws CheckpointException {
        List<R> resources = checkpointQ.entrySet().stream()
            .sorted(comparator)
            .map(e -> e.getKey())
            .collect(Collectors.toList());

        ArrayList<Exception> exceptions = new ArrayList<>();
        for (Resource r : resources) {
            if (DEBUG) {
                System.err.println("jdk.crac.ResourceManager beforeCheckpoint " + r.toString());
            }
            try {
                r.beforeCheckpoint();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }

        Collections.reverse(resources);
        restoreQ = resources;

        if (0 < exceptions.size()) {
            throw new CheckpointException(exceptions.toArray(new Exception[exceptions.size()]));
        }
    }

    @Override
    public synchronized void afterRestore() throws RestoreException {
        ArrayList<Exception> exceptions = new ArrayList<>();
        for (Resource r : restoreQ) {
            if (DEBUG) {
                System.err.println("jdk.crac.ResourceManager afterRestore " + r.toString());
            }
            try {
                r.afterRestore();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        restoreQ = null;

        if (0 < exceptions.size()) {
            throw new RestoreException(exceptions.toArray(new Exception[exceptions.size()]));
        }
    }
}
