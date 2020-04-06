package strm;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Test {
    public static void main(String[] args) {
        Queue<Result<Integer>> q1 = new ArrayBlockingQueue<>(32);
        Queue<Result<Integer>> q2 = new ArrayBlockingQueue<>(32);
        Queue<Result<Integer>> q3 = new ArrayBlockingQueue<>(32);

        Supplier.group(1, 2, 3, 4).supply(q1);
        new Mapper<Integer, Integer>(x -> Result.ok(x * x)).map(q1, q2);
        Reducer.sum().reduce(q2, q3);
        Consumer.<Integer>writeln().consume(q3);
    }
}
