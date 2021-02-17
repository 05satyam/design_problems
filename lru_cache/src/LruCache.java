import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class LruCache {
    private Deque<Integer> queue;
    private HashSet<Integer> hashPage;

    private int cacheSize;
    LruCache(int capacity){
        this.cacheSize=capacity;
        this.queue = new LinkedList<>();
        this.hashPage = new HashSet<>();

    }

    public void put(int page){
        if(!hashPage.contains(page)){
            if(queue.size()==cacheSize){
               int last= queue.removeLast();
                hashPage.remove(last);
            }
        }else{
            queue.remove(page);
        }

        queue.addFirst(page);
        hashPage.add(page);
    }

    public void display(){
        Iterator<Integer> itr = queue.iterator();
        while(itr.hasNext()){
            System.out.print(itr.next() + " ");
        }
    }

    public static void main(String args[]){
        LruCache cache = new LruCache(10);
        cache.put(2);
        cache.put(3);
        cache.put(4);
        cache.put(5);
        cache.put(1);
        cache.put(2);
        cache.display();
    }
}
