/*
 * Copyright (c) 2018, 2020, Azul Systems, Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Azul Systems, 385 Moffett Park Drive, Suite 115, Sunnyvale,
 * CA 94089 USA or visit www.azul.com if you need additional information or
 * have any questions.
 */

package javax.crac;

import jdk.crac.impl.OrderedContext;

/**
 * Runtime interface to checkpoint/restore service.
 */
public class Core {

    /** This class is not instantiable. */
    private Core() {
    }

    private static final Context<Resource> globalContext = new ContextWrapper(new OrderedContext());
    static {
        jdk.crac.Core.getGlobalContext().register(new ResourceWrapper(null, globalContext));
    }

    /**
     * Gets the global {@code Context} for checkpoint/restore notifications.
     *
     * @return the global {@code Context}
     */
    public static Context<Resource> getGlobalContext() {
        return globalContext;
    }

    /**
     * Requests checkpoint. Returns after restore completed.
     *
     * @throws CheckpointException if an exception occured during checkpoint notification
     * @throws RestoreException if an exception occured during restore notification
     */
    public static void tryCheckpointRestore() throws
            CheckpointException,
            RestoreException {
        try {
            jdk.crac.Core.tryCheckpointRestore();
        } catch (jdk.crac.CheckpointException e) {
            CheckpointException newException = new CheckpointException();
            for (Throwable t : e.getSuppressed()) {
                newException.addSuppressed(t);
            }
            throw newException;
        } catch (jdk.crac.RestoreException e) {
            RestoreException newException = new RestoreException();
            for (Throwable t : e.getSuppressed()) {
                newException.addSuppressed(t);
            }
            throw newException;
        }
    }

    /* called by VM */
    private static void tryCheckpointRestoreInternal() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            try {
                tryCheckpointRestore();
            } catch (CheckpointException | RestoreException e) {
                for (Throwable t : e.getSuppressed()) {
                    t.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
