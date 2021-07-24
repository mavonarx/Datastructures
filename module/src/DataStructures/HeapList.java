package DataStructures;

import java.util.ArrayList;

public class HeapList<T extends Comparable<T>> implements HeapInterface<T>  {

    private ArrayList<T> heap = new ArrayList<T>();
    public boolean min=false;

    //constructor
    public HeapList(boolean type){
        min=type;
    }

    @Override
    public void insert(T value) {
        heap.add(null);
        heap.set(getSize()-1,value);
        heapify(getSize()-1);
    }

    private void heapify(int position){
        if (position==0){
            return;
        }
        if (heap.get(position).compareTo(heap.get((position-1)/2)) > 0 &&!min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
        }
        if (heap.get(position).compareTo(heap.get((position-1)/2)) < 0 &&min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
        }
    }

    private void change(int a, int b){
        T temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
    }

    // checks wich of the childs is bigger - if only 1 child exists return that. if no child exists return -1
    private int biggerChild(int position){

        // no child exists
        if (position * 2 + 2 > getSize()){
            return -1;
        }

        // only left child exists
        if (position * 2 +1 == getSize()-1){
            return position*2+1;
        }

        // left child bigger
        if (heap.get(position*2+1).compareTo(heap.get(position*2+2))>0 &&!min){
            return position*2+1;
        }

        if (heap.get(position*2+1).compareTo(heap.get(position*2+2))<0 && min){
            return position*2+1;
        }
        // right child bigger
        else{
            return position*2+2;
        }
    }

    private void sicker(int position){

        // no child exists
        if (biggerChild(position)==-1){
            return;
        }

        else{
            int a = biggerChild(position);
            if (heap.get(a).compareTo(heap.get(position))>0 && !min){
                change(position, a);
                sicker(a);
                return;
            }
            if (heap.get(a).compareTo(heap.get(position))<0 && min ){
                change(position, a);
                sicker(a);
            }
        }
    }

    public T poll(){
        if (getSize()<=0){
            throw new RuntimeException("Queue is empty - nothing to poll there");
        }
        T result = heap.get(0);
        heap.set(0, heap.get(getSize()-1));
        heap.remove(heap.size()-1);
        sicker(0);
        return result;
    }

    public void clear(){
        heap.clear();
    }

    @Override
    public T sneak() {
        return heap.get(0);
    }

    @Override
    public int getSize() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return (getSize()==0);
    }

    @Override
    public String toString(){
        return heap.toString();
    }
}


