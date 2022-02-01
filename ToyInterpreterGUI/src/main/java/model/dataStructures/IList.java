package model.dataStructures;

import java.util.List;
import java.util.Map;

public interface IList<T> extends Iterable<T> {
    void add(T element);

    void remove(T element);

    T pop() throws Exception;

    boolean empty();

    void clear();

    int size();

    IList<T> deep_copy();

    String toString();

    T peek();

    List<T> getContent();
}
