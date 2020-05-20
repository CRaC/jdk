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
 * {@code Resource} is an abstract entity that should be notified when
 * Java Runtime performs checkpoint or restore (see {@code Core} for definition).
 * Usually {@code Resource} implemented by an object corresponding to some
 * external resource (i.e. of operating system), which is not under full control of the Java Runtime.
 * The {@code Resource}'s implementation task is to align external resource state with a state of the Java object, that is,
 * to provide shutdown procedure for checkpoint and re-initialization for restore operations.
 *
 * {@code Resource} must be registered in a {@code Context} to be notified.
 * For example, {@code Core} provides a global context that dispatches notification
 * to all required {@code Resource}'s and {@code Context}'es.
 *
 */
public interface Resource {

    /**
     * Called in transition to checkpoint.
     *
     * @throws Exception if external resource cannot be shutdown.
     */
    void beforeCheckpoint() throws Exception;

    /**
     * Called in transition from checkpoint to operating state.
     *
     * @throws Exception if external resource cannot be re-initialized.
     */
    void afterRestore() throws Exception;
}
