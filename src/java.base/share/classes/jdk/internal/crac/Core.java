package jdk.internal.crac;

import java.io.FileDescriptor;

public class Core {
    private static JDKContext JDKContext;

    private static native void registerPersistent0(FileDescriptor fd);

    static {
        JDKContext = new JDKContext();
        jdk.crac.Core.getGlobalContext().register(JDKContext);
    }

    public static JDKContext getJDKContext() {
        return JDKContext;
    }

    public static void registerPersistent(FileDescriptor fd) {
        registerPersistent0(fd);
    }
}
