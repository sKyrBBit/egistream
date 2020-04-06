package pipe1;

import other.*;

class Supplier extends Thread {
    private final Tx<Data> tx;
    public Supplier(Tx<Data> tx) {
        this.tx = tx;
    }
    @Override
    public void run() {
        tx.transmit(Data.ERROR);
    }
}
