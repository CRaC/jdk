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
 * Provides interface for interacting with checkpoint/restore.
 *
 * <ul>
 * <li>{@link Core} allows to request checkpoint/restore and register for
 * notification.
 * </li>
 * <li>{@link Resource} and {@link Context} are interfaces for
 * notification.
 * </li>
 * </ul>
 *
 * @since 15
 */
package javax.crac;
