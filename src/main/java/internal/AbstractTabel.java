package internal;

import java.util.HashMap;

public abstract class AbstractTabel <T> {

    private HashMap<String, T> map;

    public AbstractTabel() {
        this.map = new HashMap<String, T>();
    }

    /**
     * precondition: id not null
     * @param id
     * @return true if exists, otherwise false
     */
    public boolean eksisterer(String id) {
        assert id != null;

        return this.map.containsKey(id);
    }

    /**
     * precondition: id & object not null
     * @param id
     * @param object
     * @return false if id is already in use
     */
    public boolean gem(String id, T object) {
        assert id != null && object != null;

        if (this.eksisterer(id)) return false;
        this.map.put(id, object);
        return true;
    }

    /**
     * precondition: id not null
     * @param id
     * @return true if id removed, otherwise false
     */
    public boolean fjern(String id) {
        assert id != null;

        if (!this.eksisterer(id)) return false;
        this.map.remove(id);
        return true;
    }

    /**
     * precondition: id not null
     * @param id
     * @return
     */
    public T get(String id) {
        assert id != null;

        return this.map.get(id);
    }

}
