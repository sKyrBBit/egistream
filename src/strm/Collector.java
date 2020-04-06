package strm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;

public class Collector<R, T> {
    private T tmp;
    protected java.util.function.BiConsumer<R, T> lambda;
    private Collector(final T init, final java.util.function.BiConsumer<R, T> lambda) {
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
            lambda.accept(tmp1.unwrap(), tmp);
        }
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static <T> Collector<T, List<T>> toList() {
        return new Collector<>(List.of(), (x, tmp) -> tmp.add(x));
    }
}
