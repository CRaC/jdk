package jdk.test.javax.crac;

import javax.crac.*;

/**
 * @test
 */
public class ResourceTest {
    static class CRResource implements Resource {
        String id;
        boolean[] throwCond;
        int nCalls = 0;
        CRResource(String id, boolean... throwCond) {
            this.id = id;
            this.throwCond = throwCond;
        }

        void maybeException(String callId) throws Exception {
            boolean t = nCalls < throwCond.length ? throwCond[nCalls] : throwCond[throwCond.length - 1];
            System.out.println(id + " " + callId + "(" + nCalls + ") throw? " + t);
            ++nCalls;
            if (t) {
                throw new RuntimeException(id);
            }
        }

        @Override
        public void beforeCheckpoint() throws Exception {
            maybeException("beforeCheckpoint");
        }

        @Override
        public void afterRestore() throws Exception {
            maybeException("afterRestore");
        }
    }

    static class SingleContext implements Context {
        private Resource r;

        @Override
        public void beforeCheckpoint() throws CheckpointException {
            try {
                r.beforeCheckpoint();
            } catch (Exception e) {
                throw new CheckpointException(new Exception[] { e });
            }
        }

        @Override
        public void afterRestore() throws RestoreException {
            try {
                r.afterRestore();
            } catch (Exception e) {
                throw new RestoreException(new Exception[] { e });
            }
        }

        @Override
        public void register(Resource r) {
            this.r = r;
        }

        public SingleContext(Resource r) {
            register(r);
        }
    }

    static public void main(String[] args) throws Exception {
        Core.getGlobalContext().register(
            new CRResource("One", true, false));
        Core.getGlobalContext().register(
            new SingleContext(
                new CRResource("Two", false, true, false, true)));
        //System.gc();
        int tries = 2;
        for (int i = 0; i < 2; ++i) {
            try {
                javax.crac.Core.tryCheckpointRestore();
            } catch (CheckpointRestoreException e) {
                System.out.println(e);
                e.printExceptions();
            }
        }
        System.out.println("DONE");
    }
}
