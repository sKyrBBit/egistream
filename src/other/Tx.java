package other;

import java.util.concurrent.BlockingQueue;

public class Tx<T> {
    private final BlockingQueue<T> q;
    Tx(final BlockingQueue<T> q) {
        this.q = q;
    }
    public void transmit(T data) {
        while(true) {
            boolean ok = q.offer(data);
            if (ok) break;
        }
    }
}
