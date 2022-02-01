package model.dataStructures;

import java.util.Map;
import java.util.Set;

public interface IHeap<T2> {
    boolean isDefined(int address);

    int allocate(T2 value);

    T2 lookup(int id);

    void update(int id, T2 value);

    int size();

    IHeap<T2> deep_copy();

    String toString();

    Set<Map.Entry<Integer, T2>> entrySet();

    Map<Integer, T2> getContent();

    void setContent(Map<Integer, T2> content);

    Set<Integer> keys();
}
