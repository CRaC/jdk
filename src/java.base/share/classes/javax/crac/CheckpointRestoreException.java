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

import java.io.PrintStream;

/**
 * {@code CheckpointRestoreException} is a collection of exception(s)
 * that happened on checkpoint or restore due to some or many
 * {@code Resource}'s were unable to succeed in
 * {@code beforeCheckpoint} or {@code afterRestore} calls.
 */
public class CheckpointRestoreException extends Exception {
    private static final long serialVersionUID = -806684803281289037L;

    private Exception[] exceptions;

    /**
     * Constructs a {@code CheckpointRestoreException} with
     * an array of exceptions that caused this exception.
     *
     * @param exceptions Array of exceptions that caused this exception.
     */
    public CheckpointRestoreException(Exception[] exceptions) {
        this.exceptions = exceptions;
    }

    /**
     * Returns array of exceptions that caused this exception.
     *
     * @return Array of exceptions.
     */
    public Exception[] getExceptions() {
        return exceptions;
    }

    /**
     * Prints information about exceptions that caused this exception
     * to a provided {@code PrintStream}.
     *
     * @param stream Stream to print the information to.
     */
    public void printExceptions(PrintStream stream) {
        for (int i = 0; i < exceptions.length; ++i) {
            stream.println("  " + exceptions[i].toString());
        }
    }

    /**
     * Prints information about exceptions that caused this exception to {@code System.err}.
     */
    public void printExceptions() {
        printExceptions(System.err);
    }
}
