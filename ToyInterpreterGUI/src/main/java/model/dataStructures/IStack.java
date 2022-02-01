package model.dataStructures;

public interface IStack<T> {
    T pop() throws Exception;

    void push(T v);

    boolean isEmpty();

    int size();

    IStack<T> deep_copy();

    String toString();
}
