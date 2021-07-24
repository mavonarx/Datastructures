package DataStructures;

import java.util.Arrays;
public class Heap<T extends Comparable<T>>  implements HeapInterface<T> {
    private int size;
    private T [] heap;
    private static final int DEFAULT_CAPACITY=1000;
    public int capacity;
    public boolean min=true;

    public Heap(){
        heap= (T[]) new Comparable[DEFAULT_CAPACITY];
        capacity=DEFAULT_CAPACITY;
    }

    public Heap(boolean type){
        min=type;
    }

    public Heap(int maxCapacity){
        heap= (T[]) new Comparable[maxCapacity];
        capacity=maxCapacity;
    }

    public Heap (int maxCapacity, boolean type){
        this(maxCapacity);
        min = type;
    }

    public T [] get(){
        return heap;
    }

    public void insert(T value) {
        if (size>capacity){
            throw new RuntimeException("Heap is full");
        }
        heap[size]= value;
        heapify(size);
        size++;
    }

    private void heapify(int position){
        if (position==0){
            return;
        }

        if (heap[position].compareTo(heap[((position-1)/2)]) <0 && min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
            return;
        }

        if (heap[position].compareTo(heap[((position-1)/2)]) >0 && !min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
        }
    }

    @Override
    public boolean isEmpty() {
        return (getSize()==0);
    }

    public T remove(){
        return poll();
    }

    private void change(int position1, int position2){
        T temp = heap[position1];
        heap[position1]=heap[position2];
        heap[position2]=temp;
    }

    // checks wich of the childs is bigger - if only 1 child exists return that. if no child exists return -1
    private int biggerChild(int position){

        // no child exists
        if (position * 2 + 2 > size){
            return -1;
        }

        // only left child exists
        if (position * 2 +1 == size){

            return position*2+1;

        }

        // left child bigger
        if (heap[(position*2+1)].compareTo(heap[(position*2+2)])<0 && min){
            return position*2+1;
        }

        if (heap[(position*2+1)].compareTo(heap[(position*2+2)])>0 && !min){
            return position*2+1;
        }
        // right child bigger
        else{
            return position*2+2;
        }
    }

    private void sicker(int position){

        // child exists
        if (!(biggerChild(position)==-1)){
            int a = biggerChild(position);
            if (heap[a].compareTo(heap[position])<0 && min){
                change(position, a);
                sicker(a);
                return;
            }

            if (heap[a].compareTo(heap[position])>0 && !min){
                change(position, a);
                sicker(a);
            }
        }
    }

    public T poll(){
        if (size<=0){
            throw new RuntimeException("Queue is empty - nothing to poll here");
        }
        T result = heap[0];
        heap[0]=heap[size-1];
        size--;
        sicker(0);
        return result;
    }



    public void clear(){
        size =0;
    }

    @Override
    public T sneak() {
        return heap[0];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        return Arrays.toString(heap);
    }

}



