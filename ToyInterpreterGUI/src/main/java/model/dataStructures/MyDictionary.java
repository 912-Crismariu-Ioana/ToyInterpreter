package model.dataStructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements IDictionary<T1, T2> {

    private Map<T1, T2> dict = new HashMap<>();

    @Override
    public boolean isDefined(T1 id) {
        synchronized (this) {
            return dict.containsKey(id);
        }
    }

    @Override
    public void add(T1 id, T2 value) {
        synchronized (this) {
            dict.put(id, value);
        }
    }

    @Override
    public T2 lookup(T1 id) {
        synchronized (this) {
            return dict.get(id);
        }
    }

    @Override
    public void update(T1 id, T2 value) {
        synchronized (this) {
            dict.replace(id, value);
        }
    }

    @Override
    public T2 remove(T1 id) {
        synchronized (this) {
            return dict.remove(id);
        }

    }

    @Override
    synchronized public int size() {
        return dict.size();
    }

    @Override
    synchronized public IDictionary<T1, T2> deep_copy() {
        IDictionary<T1, T2> copy_dict = new MyDictionary<>();
        for (T1 id : dict.keySet())
            copy_dict.add(id, dict.get(id));
        return copy_dict;
    }

    @Override
    synchronized public Set<T1> keys() {
        return this.dict.keySet();
    }

    @Override
    synchronized public Collection<T2> values() {
        return this.dict.values();
    }

    @Override
    public String toString() {
        return dict.toString();
    }

    @Override
    synchronized public Map<T1, T2> getContent() {
        return this.dict;
    }

    @Override
    public void setContent(Map<T1, T2> content) {
        synchronized (this) {
            this.dict = content;
        }
    }
}
