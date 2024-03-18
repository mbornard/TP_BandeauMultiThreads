package bandeau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BandeauLock extends Bandeau {
    private Lock lock = new ReentrantLock();

    public void lock() { lock.lock();  }

    public void unlock() { lock.unlock();  }
}
