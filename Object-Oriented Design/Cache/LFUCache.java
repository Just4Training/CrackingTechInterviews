/*
 * Design and implement a data structure for a Least Frequently Used (LFU) cache.
 * Implement the LFUCache class:
 * - LFUCache(int capacity) Initializes the object with the capacity of the data structure.
 * - int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
 * - void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
 *   When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting a new item.
 *   For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.
 * To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.
 * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use counter for a key in the cache is incremented either a get or put operation is called on it.
 * The functions get and put must each run in O(1) average time complexity.
 */

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    /*
     1. Save all key's frequency
     2. Track the size of the all cache
     3. get(key) not exist or frequency++
     4. put(key, value) key exists - modify value, call get(), or add new node, oversize...
    */
    
    class Node {
        int key;
        int val;
        int count; // visited times
        Node prev;
        Node next;
        
        public Node(int k, int v) {
            key = k;
            val = v;
            count = 1;
        }
    }

    Map<Integer, Node> map;
    
    class DLList {
        Node head;
        Node tail;    
        int len;
        
        public DLList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            len = 0;
        }
        
        public void addHead(Node node) {
            Node next = head.next;
            head.next = node;
            node.prev = head;
            node.next = next;
            next.prev = node;
            
            map.put(node.key, node);
            len++;
        }
        
        public void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            len--;
            map.remove(node.key);
        }
        
        public void removeTail() {
            Node preTail = tail.prev;
            removeNode(preTail);
        }
    }

    Map<Integer, DLList> freq;
    int size, capacity;
    int maxFreq;
    
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
        size = 0;
        maxFreq = 0;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        
        Node node = map.get(key);
        int prevFreq = node.count; // find node.count
        DLList prevList = freq.get(prevFreq);
        prevList.removeNode(node);
        
        int curFreq = prevFreq + 1;
        maxFreq = Math.max(maxFreq, curFreq);
        DLList curList = freq.getOrDefault(curFreq, new DLList());
        node.count++;
        curList.addHead(node);
        
        freq.put(prevFreq, prevList);
        freq.put(curFreq, curList);
        
        return node.val;
    }
    
    public void put(int key, int value) {
        if(capacity == 0) {
            return;
        }
        
        if(map.containsKey(key)) {
            map.get(key).val = value;
            get(key);
            return;
        }
        
        Node node = new Node(key, value);
        DLList curList = freq.getOrDefault(1, new DLList());
        curList.addHead(node);
        size++;
        
        if(size > capacity) {
            if(curList.len > 1) {
                curList.removeTail();
            }
            else {
                for(int i = 2; i <= maxFreq; i++) {
                    if(freq.containsKey(i) && freq.get(i).len > 0) {
                        freq.get(i).removeTail();
                        break;
                    }
                }
            }
            
            size--;
        }
        
        freq.put(1, curList);
    }
}
