package other;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pair<Tx<Data>, Rx<Data>> tmp = Channel.setup();
        while (true) {
            Data data = tmp.right.receive();
            if (data != null) System.out.println(data);
            if (data == Data.ERROR) break;
        }
        for (Data data: List.of(Data.NUMBER.setValue(1), Data.NUMBER.setValue(2), Data.NUMBER.setValue(3), Data.ERROR)) {
            tmp.left.transmit(data);
        }
//        Worker supplier = new Worker();
//        supplier.setName("supplier");
//        Worker consumer = new Worker();
//        consumer.setName("consumer");
//        supplier.receive(Data.Data.NUMBER.setValue(1));
//        supplier.receive(Data.Data.NUMBER.setValue(2));
//        supplier.receive(Data.Data.NUMBER.setValue(3));
//        supplier.receive(Data.Data.ERROR);
//        supplier.start();
    }
}
