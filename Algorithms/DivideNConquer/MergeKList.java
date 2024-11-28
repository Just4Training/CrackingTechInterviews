package Algorithms.DivideNConquer;

/*
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

 

Example 1:

Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6
 */

import Algorithms.DataStructure.ListNode;

public class MergeKList {
    public ListNode mergeKLists(ListNode[] list) {
        if(list.length == 0) {
            return null;
        }
        
        if(list.length == 1) {
            return list[0];
        }
        
        if(list.length == 2) {
            return merge(list[0], list[1]);
        }
        
        int mid = list.length / 2;
        
        ListNode[] firstHalf = new ListNode[mid];
        // copy list
        for(int i = 0; i < mid; i++) {
            firstHalf[i] = list[i];
        }
        
        ListNode[] secondHalf = new ListNode[list.length - mid];
        for(int i = mid; i < list.length; i++) {
            secondHalf[i - mid] = list[i];
        }
        
        ListNode first = mergeKLists(firstHalf);
        ListNode second = mergeKLists(secondHalf);
        
        return merge(first, second);
    }
    
    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode result = head;
        
        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                head.next = l1;
                l1 = l1.next;
            }
            else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        
        if(l1 != null) {
            head.next = l1;
        }
        if(l2 != null) {
            head.next = l2;
        }
        
        return result.next;
    }
}
