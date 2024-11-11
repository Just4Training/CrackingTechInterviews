/*
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * The functions get and put must each run in O(1) average time complexity.
 */

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    // define two direction node
    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    int capacity;
    Map<Integer, Node> map;
    Node preHead;
    Node postTail;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.preHead = new Node(-1, -1);
        this.postTail = new Node(-1, -1);

        preHead.next = postTail;
        postTail.prev = preHead;
    }

    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            remove(key);
            addHead(key, node.val);
            return node.val;
        }

        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            remove(key);
        }
        addHead(key, value);
    }

    private void remove(int key) {
        Node node = map.get(key);
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        map.remove(key);
    }

    private void addHead(int key, int value) {
        Node node = new Node(key, value);
        Node head = preHead.next;
        preHead.next = node;
        node.next = head;
        node.prev = preHead;
        head.prev = node;
        map.put(key, node);

        if(map.size() > capacity) {
            Node tail = postTail.prev;
            remove(tail.key);
        }
    }
 }