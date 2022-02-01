package model.dataStructures;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<T1, T2> {
    boolean isDefined(T1 id);

    void add(T1 id, T2 value);

    T2 lookup(T1 id);

    void update(T1 id, T2 value);

    T2 remove(T1 id);

    int size();

    IDictionary<T1, T2> deep_copy();

    Set<T1> keys();

    Collection<T2> values();

    String toString();

    Map<T1, T2> getContent();

    void setContent(Map<T1, T2> content);
}
