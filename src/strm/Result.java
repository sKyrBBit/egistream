package strm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Result<T> {
    private final T value;
    private final String message;
    private Result(final T value, final String message) {
        this.value = value;
        this.message = message;
    }
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> Result<T> ok(final T value) {
        return new Result<>(value, null);
    }
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> Result<T> err(final String message) {
        return new Result<>(null, message);
    }
    public boolean is_err() { return value == null; }
    public T unwrap() {
        if (value == null) { System.err.println("unwrap on none"); System.exit(1); }
        return value;
    }
    public void println() {
        if (is_err()) System.out.println(message);
        else System.out.println(value);
    }
}
