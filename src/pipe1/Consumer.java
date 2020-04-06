package pipe1;

import other.Data;
import other.Rx;

class Consumer extends Thread {
    protected final Rx<Data> rx;
    public Consumer(Rx<Data> rx) {
        this.rx = rx;
    }
}
