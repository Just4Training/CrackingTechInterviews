/**
 * Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#').
 * You are given a string array sentences and an integer array times both of length n where sentences[i] is a previously typed sentence and times[i] is the corresponding number of times the sentence was typed.
 * For each input character except '#', return the top 3 historical hot sentences that have the same prefix as the part of the sentence already typed.
 * 
 * Here are the specific rules:
 * The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
 * The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same hot degree, use ASCII-code order (smaller one appears first).
 * If less than 3 hot sentences exist, return as many as you can.
 * When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.
 * 
 * Implement the AutocompleteSystem class:
 * AutocompleteSystem(String[] sentences, int[] times) Initializes the object with the sentences and times arrays.
 * List<String> input(char c) This indicates that the user typed the character c.
 * Returns an empty array [] if c == '#' and stores the inputted sentence in the system.
 * Returns the top 3 historical hot sentences that have the same prefix as the part of the sentence already typed. If there are fewer than 3 matches, return them all.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutocompleteSystem {
    class TrieNode implements Comparable<TrieNode> {
        TrieNode[] children;
        String s;
        int times;
        List<TrieNode> hot;

        TrieNode() {
            children = new TrieNode[128];
            s = null;
            times = 0;
            hot = new ArrayList<>();
        }

        public int compareTo(TrieNode node) {
            if(this.times == node.times) {
                return this.s.compareTo(node.s);
            }

            return node.times - this.times;
        }

        public void update(TrieNode node) {
            if(!this.hot.contains(node)) {
                this.hot.add(node);
            }

            Collections.sort(hot);

            if(hot.size() > 3) {
                hot.remove(hot.size() - 1);
            }
        }
    }

    TrieNode root;
    TrieNode curr;
    StringBuilder sb;

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        curr = root;
        sb = new StringBuilder();

        for(int i = 0; i < times.length; i++) {
            add(sentences[i], times[i]);
        }        
    }
    
    private void add(String sentence, int time) {
        TrieNode temp = root;

        List<TrieNode> visited = new ArrayList<>();
        for(char c : sentence.toCharArray()) {
            if(temp.children[c] == null) {
                temp.children[c] = new TrieNode();
            }

            temp = temp.children[c];
            visited.add(temp);
        }

        temp.s = sentence;
        temp.times += time;

        for(TrieNode node : visited) {
            node.update(temp);
        }
    }

    public List<String> input(char c) {
        List<String> res = new ArrayList<>();
        if (c == '#') {
            add(sb.toString(), 1);
            sb = new StringBuilder();
            curr = root;
            return res;
        }

        sb.append(c);
        if (curr != null) {
            curr = curr.children[c];
        }

        if (curr == null) return res;
        for (TrieNode node : curr.hot) {
            res.add(node.s);
        }

        return res;        
    }
}
