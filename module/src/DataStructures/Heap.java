package DataStructures;

import java.util.Arrays;
@SuppressWarnings("unchecked")
public class Heap<T extends Comparable<T>>  implements HeapInterface<T> {
    private int size;
    private T [] heap;
    private static final int DEFAULT_CAPACITY=1000;
    public int capacity;
    public boolean minHeap =true;

    public Heap(){

        heap= (T[]) new Comparable[DEFAULT_CAPACITY];
        capacity=DEFAULT_CAPACITY;
    }

    public Heap(boolean type){
        minHeap =type;
    }

    public Heap(int maxCapacity){
        heap= (T[]) new Comparable[maxCapacity];
        capacity=maxCapacity;
    }

    public Heap (int maxCapacity, boolean type){
        this(maxCapacity);
        minHeap = type;
    }

    public T [] get(){
        return heap;
    }

    public void insert(T value) {
        if (size>=capacity){
            throw new RuntimeException("Heap is full");
        }
        size++;
        heap[size-1]= value;
        bubbleUp(size-1);
    }

    private void bubbleUp(int position){
        if (position==0){
            return;
        }

        //minHeap
        if (heap[position].compareTo(heap[((position-1)/2)]) <0 && minHeap){
            swap(position,(position-1)/2);
            bubbleUp((position-1)/2);
            return;
        }

        //maxHeap
        if (heap[position].compareTo(heap[((position-1)/2)]) >0 && !minHeap){
            swap(position,(position-1)/2);
            bubbleUp((position-1)/2);
        }
    }

    @Override
    public boolean isEmpty() {
        return (getSize()<=0);
    }

    public T remove(){
        return poll();
    }

    private void swap(int position1, int position2){
        T temp = heap[position1];
        heap[position1]=heap[position2];
        heap[position2]=temp;
    }

    // checks witch of the children is bigger - if only 1 child exists return that. if no child exists return -1
    private int getBiggerChild(int position){
        int leftChild = position*2+1;
        int rightChild = position*2+2;

        // no child exists
        if (rightChild > size){
            return -1;
        }

        // only left child exists
        if (leftChild == size){
            return leftChild;
        }

        // left child bigger
            // in a MinHeap
        if (heap[(leftChild)].compareTo(heap[(rightChild)])<0 && minHeap){
            return leftChild;
        }

            // in a MaxHeap
        if (heap[(leftChild)].compareTo(heap[(rightChild)])>0 && !minHeap){
            return leftChild;
        }
        // right child bigger
        else{
            return rightChild;
        }
    }

    private boolean childExists(int position){
        return getBiggerChild(position) != -1;
    }

    private void sicker(int position){
        // child exists
        if (childExists(position)){
            int biggerChild = getBiggerChild(position);
            // swaps the values if the child has more priority - then recursively goes down the tree
                // in a MinHeap
            if (heap[biggerChild].compareTo(heap[position])<0 && minHeap){
                swap(position, biggerChild);
                sicker(biggerChild);
                return;
            }
                // in a MaxHeap
            if (heap[biggerChild].compareTo(heap[position])>0 && !minHeap){
                swap(position, biggerChild);
                sicker(biggerChild);
            }
        }
    }


    public T poll(){
        if (isEmpty()){
            throw new RuntimeException("Queue is empty");
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

    public String getHeapAsString(){
        return Arrays.toString(heap);
    }

}



