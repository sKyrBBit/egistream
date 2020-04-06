package strm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;

public class Reducer<R, T> {
    private T tmp;
    protected java.util.function.BiFunction<R, T, T> lambda;
    private Reducer(final T init, final java.util.function.BiFunction<R, T, T> lambda) {
        this.tmp = init;
        this.lambda = lambda;
    }
    public void reduce(@NotNull final Queue<Result<R>> rx, final Queue<Result<T>> tx) {
        while (true) {
            Result<R> tmp1 = rx.poll();
            if (tmp1 == null) continue; // if rx is empty
            if (tmp1.is_err()) { // if rx is dropped already
                while (true) {
                    boolean ok = tx.offer(Result.ok(tmp));
                    if (ok) break; // if tx has capacity
                }
                while (true) {
                    boolean ok = tx.offer(Result.err("filter has been dropped already"));
                    if (ok) break; // if tx has capacity
                }
                break;
            }
            tmp = lambda.apply(tmp1.unwrap(), tmp);
        }
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static <R> Reducer<R, Integer> count() {
        return new Reducer<>(0, (x, tmp) -> ++tmp);
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static Reducer<Integer, Integer> sum() {
        return new Reducer<>(0, Integer::sum);
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static Reducer<Integer, Integer> max() {
        return new Reducer<>(Integer.MIN_VALUE, Integer::max);
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static Reducer<Integer, Integer> min() {
        return new Reducer<>(Integer.MAX_VALUE, Integer::min);
    }
}
