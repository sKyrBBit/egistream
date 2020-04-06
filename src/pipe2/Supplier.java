package pipe2;

import other.*;

import java.util.concurrent.CopyOnWriteArraySet;

class Supplier extends Thread {
    private final CopyOnWriteArraySet<Tx<Data>> tx;
    public Supplier() {
        this.tx = new CopyOnWriteArraySet<>();
    }
    public void attach(Consumer consumer) {
        Pair<Tx<Data>, Rx<Data>> tmp = Channel.setup();
        this.tx.add(tmp.left);
        consumer.rx.add(tmp.right);
    }
}

// volatile
// blocking queue