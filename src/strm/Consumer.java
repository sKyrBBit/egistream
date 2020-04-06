package strm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Queue;

public class Consumer<R> {
    protected java.util.function.Consumer<R> lambda;
    public Consumer(final java.util.function.Consumer<R> lambda) {
        this.lambda = lambda;
    }
    public void consume(@NotNull final Queue<Result<R>> rx) {
        while (true) {
            Result<R> tmp = rx.poll();
            if (tmp == null) continue; // if rx is empty
            if (tmp.is_err()) { tmp.println(); break; } // if rx is dropped already
            lambda.accept(tmp.unwrap());
        }
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static <R> Consumer<R> write() {
        return new Consumer<>(System.out::print);
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static <R> Consumer<R> writeln() {
        return new Consumer<>(System.out::println);
    }
    @NotNull
    @Contract("_ -> new")
    public static Consumer<Character> fwrite(final String path) {
        DataOutputStream _writer = null;
        try {
            _writer = new DataOutputStream(new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        final DataOutputStream writer = _writer;
        return new Consumer<>(x -> {
            try { writer.writeChar(x); }
            catch (IOException e) { e.printStackTrace(); }
        });
    }
}
