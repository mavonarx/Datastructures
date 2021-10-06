package DataStructures;

public interface Heap<T> {
    public T poll ();
    public void insert (T element);
    public void clear();
    public T sneak();
    public int getSize();
    public boolean isEmpty();
}
