package strm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Supplier<T> {
    protected java.util.function.Supplier<Result<T>> lambda;
    public Supplier(final java.util.function.Supplier<Result<T>> lambda) {
        this.lambda = lambda;
    }
    public void supply(@NotNull final Queue<Result<T>> tx) {
        while (true) {
            Result<T> tmp = lambda.get();
            while (true) {
                boolean ok = tx.offer(tmp);
                if (ok) break; // if tx has capacity
            }
            if (tmp.is_err()) break;
        }
    }
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static Supplier<Character> read() {
        return new Supplier<>(() -> {
            try {
                int tmp = System.in.read();
                if (0 <= tmp && tmp < 255) return Result.ok((char) tmp);
                else return Result.err("supplier has been dropped already");
            }
            catch (IOException e) { return Result.err("supplier has been dropped already"); }
        });
    }
    @NotNull
    @Contract("_ -> new")
    public static Supplier<Character> fread(final String path) {
        DataInputStream _reader = null;
        try {
            _reader = new DataInputStream(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        final DataInputStream reader = _reader;
        return new Supplier<>(() -> {
            try { return Result.ok(reader.readChar()); }
            catch (IOException e) { return Result.err("supplier has been dropped already"); }
        });
    }
    @NotNull
    @Contract("_, _ -> new")
    public static Supplier<Integer> range(final int begin, final int end) {
        Iterator<Integer> iterator = IntStream.range(begin, end).iterator();
        return new Supplier<>(() -> {
            if (iterator.hasNext()) { return Result.ok(iterator.next()); }
            else { return Result.err("supplier has been dropped already"); }
        });
    }
    @SafeVarargs
    @NotNull
    @Contract("_ -> new")
    public static <T> Supplier<T> group(final T ... item) {
        Iterator<T> iterator = Arrays.stream(item).iterator();
        return new Supplier<>(() -> {
            if (iterator.hasNext()) { return Result.ok(iterator.next()); }
            else { return Result.err("supplier has been dropped already"); }
        });
    }
}
