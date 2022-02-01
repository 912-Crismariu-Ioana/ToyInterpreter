package model.dataStructures;

import model.customExceptions.EmptyContainerException;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack = new Stack<>();

    @Override
    public T pop() throws Exception {
        if (stack.size() > 0)
            return stack.pop();
        throw new EmptyContainerException("Stack is empty!");
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public IStack<T> deep_copy() {
        IStack<T> copy_stack = new MyStack<>();
        for (T elem : stack)
            copy_stack.push(elem);
        return copy_stack;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
