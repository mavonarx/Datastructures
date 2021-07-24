package DataStructures;

public interface HeapInterface<T> {
    public T poll ();
    public void insert (T value);
    public void clear();
    public T sneak();
    public int getSize();
    public boolean isEmpty();
}
