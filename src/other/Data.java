package other;

import java.util.ArrayList;
import java.util.List;

public enum Data {
    NUMBER {
        private int value;
        @Override public Data setValue(int value) { this.value = value; return this; }
        @Override public String toString() { return "" + value; }
    },
    STRING {
        private String value;
        @Override public Data setValue(String value) { this.value = value; return this; }
        @Override public String toString() { return "" + value; }
    },
    BOOLEAN {
        private boolean value;
        @Override public Data setValue(boolean value) { this.value = value; return this; }
        @Override public String toString() { return "" + value; }
    },
    LIST {
        private final List<Data> values = new ArrayList<>();
        @Override public Data setValue(Data value) { values.add(value); return this; }
        @Override public String toString() { return "" + values; }
    },
    ERROR,
    NIL;
    public Data setValue(int value)     { return this; }
    public Data setValue(String value)  { return this; }
    public Data setValue(boolean value) { return this; }
    public Data setValue(Data value)    { return this; }
}
