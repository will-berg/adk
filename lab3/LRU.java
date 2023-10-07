import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LRU<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRU(int capacity) {
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Entry<K, V> entry) {
        return size() > this.capacity;
    }
}