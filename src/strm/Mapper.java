package strm;

import org.jetbrains.annotations.NotNull;

import java.util.Queue;

public class Mapper<R, T> {
    protected java.util.function.Function<R, Result<T>> lambda;
    public Mapper(final java.util.function.Function<R, Result<T>> lambda) {
        this.lambda = lambda;
    }
    public void map(@NotNull final Queue<Result<R>> rx, final Queue<Result<T>> tx) {
        while (true) {
            Result<R> tmp1 = rx.poll();
            if (tmp1 == null) continue; // if rx is empty
            if (tmp1.is_err()) { // if rx is dropped already
                while (true) {
                    boolean ok = tx.offer(Result.err("filter has been dropped already"));
                    if (ok) break; // if tx has capacity
                }
                break;
            }
            Result<T> tmp2 = lambda.apply(tmp1.unwrap());
            while (true) {
                boolean ok = tx.offer(tmp2);
                if (ok) break; // if tx has capacity
            }
            if (tmp2.is_err()) break;
        }
    }
}
