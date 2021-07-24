package DataStructures;
import java.util.Arrays;

public class HeapPrimitiveInt {
    private int size;
    private final int [] heap;
    boolean min = true;


    // constructor max cap
    public HeapPrimitiveInt(int maxCapacity){
        heap = new int [maxCapacity];
        size=0;
    }

    // basic constructor with no arg. cap of 10000000
    public HeapPrimitiveInt(){
        heap = new int [10000000];
        size=0;
    }

    public void insert(int value) {
        heap[size]=value;
        heapify(size);
        size++;
    }

    private void heapify(int position){
        if (position==0){
            return;
        }
        if (heap[position] > heap[((position-1)/2)] && !min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
            return;
        }

        if (heap[position] < heap[((position-1)/2)]&&min){
            change(position,(position-1)/2);
            heapify((position-1)/2);
        }
    }


    public boolean isEmpty() {
        return (getSize()==0);
    }

    public int remove(){
        return poll();
    }

    private void change(int a, int b){
        int temp = heap[a];
        heap[a]=heap[b];
        heap[b]=temp;
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
        if (heap[(position*2+1)]> heap[(position*2+2)] && !min){
            return position*2+1;
        }

        if (heap[(position*2+1)]< heap[(position*2+2)]&& min){
            return position*2+1;
        }
        // right child bigger
        else{
            return position*2+2;
        }
    }

    private void sicker(int position){

        // no child exists
        if (!(biggerChild(position)==-1)){
            int a = biggerChild(position);
            if (heap[a]> heap[position] && !min){
                change(position, a);
                sicker(a);
            }

            if (heap[a]< heap[position] && min){
                change(position, a);
                sicker(a);
            }
        }
    }

    public int poll(){
        if (size<=0){
            throw new RuntimeException("Queue is empty - nothing to poll there");
        }
        int result = heap[0];
        heap[0]=heap[size-1];
        size--;
        sicker(0);
        return result;
    }

    public void clear(){
        size =0;
    }


    public int sneak() {
        return heap[0];
    }


    public int getSize() {
       return size;
    }


    public String toString(){
        return Arrays.toString(heap);
    }

}
