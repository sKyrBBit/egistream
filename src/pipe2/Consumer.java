package pipe2;

import other.Data;
import other.Rx;

import java.util.concurrent.CopyOnWriteArraySet;

class Consumer extends Thread {
    protected final CopyOnWriteArraySet<Rx<Data>> rx;
    public Consumer() {
        this.rx = new CopyOnWriteArraySet<>();
    }
}
