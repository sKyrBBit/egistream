package other;

import java.util.concurrent.BlockingQueue;

public class Rx<T> {
    private final BlockingQueue<T> q;
    Rx(final BlockingQueue<T> q) {
        this.q = q;
    }
    public T receive() {
        return q.poll();
    }
}
