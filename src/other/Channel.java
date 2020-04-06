package other;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;

public class Channel {
    public static <T> Pair<Tx<T>, Rx<T>> setup() {
        BlockingQueue<T> q = new SynchronousQueue<>();
        return new Pair<>(new Tx<>(q), new Rx<>(q));
    }
}
