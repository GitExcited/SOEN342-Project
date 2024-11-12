package tdg;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DatabaseLock {
    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}