package internal;

import java.util.HashMap;

public class Database <T> {

    private final HashMap<String, T> map;

    public Database() {
        this.map = new HashMap<String, T>();
    }

    public boolean exists(String key) {
        return this.map.containsKey(key);
    }

    public boolean add(String key, T object) {
        if (this.exists(key)) return false;
        this.map.put(key, object);
        return true;
    }

    public boolean remove(String key) {
        if (this.exists(key)) return false;
        this.map.remove(key);
        return true;
    }

    public T get(String key) {
        return this.map.get(key);
    }

}
