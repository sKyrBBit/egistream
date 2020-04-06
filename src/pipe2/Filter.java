package pipe2;

import other.*;

import java.util.concurrent.CopyOnWriteArraySet;

class Filter extends Consumer {
    private final CopyOnWriteArraySet<Tx<Data>> tx;
    public Filter() {
        super();
        this.tx = new CopyOnWriteArraySet<>();
    }
    public void attach(Consumer consumer) {
        Pair<Tx<Data>, Rx<Data>> tmp = Channel.setup();
        this.tx.add(tmp.left);
        consumer.rx.add(tmp.right);
    }
}
