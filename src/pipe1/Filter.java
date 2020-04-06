package pipe1;

import other.*;

class Filter extends Consumer {
    private final Tx<Data> tx;
    public Filter(Rx<Data> rx, Tx<Data> tx) {
        super(rx);
        this.tx = tx;
    }
}

// volatile
// blocking queue