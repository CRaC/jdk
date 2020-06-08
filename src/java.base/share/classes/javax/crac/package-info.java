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

/**
 * Provides an interface for the checkpoint/restore function.
 *
 * <p>The checkpoint/restore allows to create an image out of the current Java Runtime instance and its state.
 * New instances can be created from the image, restoring at the point of checkpoint.
 *
 * <p>Notification about checkpoint or restore allows to manage resources that cannot be stored in the image.
 *
 * <ul>
 * <li>{@link Core} allows to request checkpoint/restore and provides register for notification.
 * </li>
 * <li>{@link Resource} is interface for notification.
 * </li>
 * <li>{@link Context} is used to group {@code Resource}s by their semantic.
 * </li>
 * <li>{@link CheckpointException} and {@link RestoreException} are used to report checkpoint or restore problems.
 * </li>
 * </ul>
 *
 * {@code beforeCheckpoint} and {@code afterRestore} methods of {@code Resource} are invoked as a notification for checkpoint and restore, respectively.
 * A {@code Resource} may be incapable to perform action associated with notification, in this case one of the methods throws an exception.
 *
 * <p>A {@code Context} is a {@code Resource}, that allows other {@code Resource}s to be registered with it.
 * A class may extend {@code Context} and by overwriting {@code beforeCheckpoint} and {@code afterRestore} define custom rules of notification processing, such as order of notification.
 * A {@code Context} may be registered with other {@code Context}, forming {@code Context} hierarchy.
 *
 * <p>Java Standard Library maintains a global {@code Context}, that receives notification about checkpoint/restore from the system and distributes it by the hierarchy.
 * The global {@code Context} properties are:
 * <ul>
 * <li>All {@code Resource}s registered in the global {@code Context} are notified about checkpoint.
 * </li>
 * <li>Order of checkpoint notification is the reverse order of registration.
 * Restore notification order is reverse of the checkpoint one, that is, forward order of registration.
 * </li>
 * <li>For a single {@code Resource}, {@code afterRestore} is always invoked, regardless of {@code beforeCheckpoint} have thrown an exception or not.
 * </li>
 * <li>All {@code Resource}s are notified about checkpoint or restore.
 * </li>
 * <li>If a {@code Resrouce} registered in this {@code Context} throws exception during checkpoint or restore notification, the {@code Context} throws {@code CheckpointException} or {@code RestoreException}, which suppresses exceptions thrown by all {@code Resource}s for corresponding notification.
 * </li>
 * <li>If a {@code Context} registered in the global {@code Context} throws {@code CheckpointException} or {@code RestoreException}, exceptions suppressed by the original exception and all other possible exceptions from registered {@code Resource}s are suppressed by a single exception from the global {@code Context}.
 * </li>
 * </ul>
 *
 * In addition, the global {@code Context} may have additional {@code Resource}s registered by an implementation.
 *
 * <p>A {@code Context} implementor is encouraged to define {@code Context}es with the properties of the global {@code Context}.
 *
 * <p>Checkpoint/restore sequence is:
 * <ul>
 * <li>The global {@code Context} is notified about checkpoint.
 * If a {@code CheckpointException} is thrown, it is postponed.
 * </li>
 * <li>If the global {@code Context} have thrown {@code CheckpointException}, this step is skipped.
 * Otherwise, the Java instance is terminated.
 * An another instance is started at the point of execution at checkpoint.
 * </li>
 * <li>The global {@code Context} is notified about restore.
 * If the global {@code Context} throws a {@code RestoreException} and a {@code CheckpointException} was thrown during checkpoint notification, exceptions suppressed during restore notification are suppressed by the former {@code CheckpointException} and it is thrown by the sequence.
 * Otherwise, if the global {@code Context} throws a {@code RestoreException}, it is thrown by the sequence.
 * </li>
 * </ul>
 *
 * @since 15
*/

package javax.crac;

