package be.vmm.gsc.tracing.util;

/**
 * Created by s.vanmieghem on 16/12/2015.
 */
public class Timer {
    private long startmillis;
    private long lastDelta;

    public Timer() {
    }

    // create and start or hold if false
    public Timer(boolean start) {
        if (start) start();
    }

    public Timer start() {
        startmillis = System.currentTimeMillis();
        lastDelta = startmillis;
        return this;
    }

    public void logDelta() {
        System.out.println((System.currentTimeMillis()-startmillis) + "/" + (System.currentTimeMillis()-lastDelta));
        lastDelta = System.currentTimeMillis();
    }

    public void logDelta(String label) {
        System.out.println(label +" : "+ (System.currentTimeMillis()-startmillis) + "/" + (System.currentTimeMillis()-lastDelta));
        lastDelta = System.currentTimeMillis();
    }
}
