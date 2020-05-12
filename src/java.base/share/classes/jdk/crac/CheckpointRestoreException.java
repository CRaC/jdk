package jdk.crac;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

public class CheckpointRestoreException extends Exception {
    private static final long serialVersionUID = 0;

    protected Exception exceptions[];

    public CheckpointRestoreException(Exception[] exceptions) {
        ArrayList<Exception> newExceptions = new ArrayList<>(exceptions.length);

        for (int i = 0; i < exceptions.length; ++i) {
            if (exceptions[i] instanceof CheckpointRestoreException) {
                CheckpointRestoreException e = (CheckpointRestoreException)exceptions[i];
                Collections.addAll(newExceptions, e.getExceptions());
            } else {
                newExceptions.add(exceptions[i]);
            }
        }
        this.exceptions = newExceptions.toArray(Exception[]::new);
    }

    public Exception[] getExceptions() {
        return exceptions;
    }

    public void printExceptions(PrintStream s) {
        for (int i = 0; i < exceptions.length; ++i) {
            s.println("  " + exceptions[i].toString());
        }
    }

    public void printExceptions() {
        printExceptions(java.lang.System.err);
    }
}
