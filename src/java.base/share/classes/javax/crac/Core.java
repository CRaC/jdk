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

/**
 * Core interface to checkpoint/restore subsystem.
 */
public class Core {
    static class NullContext extends Context<Resource> {
        @Override
        public void beforeCheckpoint(Context<? extends Resource> context) throws CheckpointException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void afterRestore(Context<? extends Resource> context) throws RestoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void register(Resource resource) {
            // Do nothing
        }
    }

    private static final Context<Resource> nullContext = new NullContext();

    /**
     * Gets the global {@link Context}. {@code Resource}s registered in this
     * context for checkpoint will be notified in reverse order of registration
     * and for restore in reverse order of checkpoint notification
     * (forward order of registration).
     *
     * @return the global {@code Context}
     */
    public static Context<Resource> getGlobalContext() {
        return nullContext;
    }

    /**
     * Performs checkpoint/restore sequence.
     * <ul>
     * <li>{@code Resource}s registered in the global {@code Context} are
     * notified about transition to checkpoint.
     * </li>
     * <li>A set of platform-dependent checks are made and
     * implementation-specific and configuration-specific actions may be
     * performed.
     * </li>
     * <li>{@code Resource}s are notified about restore.
     * </li>
     * </ul>
     *
     * @throws CheckpointException if some {@code Resource}s threw during
     * checkpoint notification
     * @throws CheckpointException if some of platform-dependent checks are
     * failed
     * @throws RestoreException if some {@code Resource]}s threw during restore
     * notification
     */
    public static void tryCheckpointRestore() throws
            CheckpointException,
            RestoreException {
        throw new RuntimeException("unimplemented");
    }
}
