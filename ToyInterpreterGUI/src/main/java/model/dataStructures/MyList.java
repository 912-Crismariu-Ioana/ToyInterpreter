package model.dataStructures;

import model.customExceptions.EmptyContainerException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<T>();

    @Override
    public void add(T element) {
        synchronized (this) {
            list.add(element);
        }
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    synchronized public T pop() throws Exception {
        if (list.size() > 0)
            return list.remove(this.list.size() - 1);
        throw new EmptyContainerException("List is empty!");
    }

    @Override
    synchronized public boolean empty() {
        return list.isEmpty();
    }

    @Override
    synchronized public void clear() {
        list.clear();
    }

    @Override
    synchronized public int size() {
        return list.size();

    }

    @Override
    synchronized public IList<T> deep_copy() {
        IList<T> copy_list = new MyList<>();
        for (T elem : list)
            copy_list.add(elem);
        return copy_list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    synchronized public T peek() {
        if (list.size() > 0)
            return list.get(this.list.size() - 1);
        throw new EmptyContainerException("List is empty!");
    }

    @Override
    public List<T> getContent() {
        return this.list;
    }

    @Override
    synchronized public Iterator<T> iterator() {
        return list.iterator();
    }
}
