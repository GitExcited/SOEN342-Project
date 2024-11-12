package main;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DatabaseLock {
    //This static class is shared amongst all classes
    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}