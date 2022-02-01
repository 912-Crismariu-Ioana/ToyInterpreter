package model.dataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<T2> implements IHeap<T2> {
    private Map<Integer, T2> heap = new HashMap<Integer, T2>();
    private AtomicInteger next_address = new AtomicInteger(1);

    public MyHeap() {
    }

    private MyHeap(Map<Integer, T2> copy_heap, int next_address) {
        this.heap = copy_heap;
        this.next_address.set(next_address);
    }

    @Override
    public boolean isDefined(int address) {
        synchronized (this) {
            return heap.containsKey(address);
        }
    }

    @Override
    public int allocate(T2 value) {
        int addr = next_address.getAndIncrement();
        synchronized (this) {
            heap.put(addr, value);
        }
        return addr;
    }


    @Override
    public T2 lookup(int address) {
        synchronized (this) {
            return heap.get(address);
        }
    }

    @Override
    public void update(int address, T2 value) {
        synchronized (this) {
            heap.replace(address, value);
        }
    }

    @Override
    synchronized public int size() {
        return heap.size();
    }

    @Override
    synchronized public IHeap<T2> deep_copy() {
        Map<Integer, T2> copy_heap = new HashMap<>(heap);
        return new MyHeap<T2>(copy_heap, next_address.get());
    }

    @Override
    synchronized public Set<Map.Entry<Integer, T2>> entrySet() {
        return heap.entrySet();
    }

    @Override
    synchronized public Map<Integer, T2> getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map<Integer, T2> content) {
        synchronized (this) {
            this.heap = content;
        }
    }

    @Override
    synchronized public Set<Integer> keys() {
        return this.heap.keySet();
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
