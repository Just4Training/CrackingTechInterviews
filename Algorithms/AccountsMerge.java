package Algorithms;
/**
 * Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
 * Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. 
 * Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
 * After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
 * 
 * Example 1:
 * Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"], ["John","johnsmith@mail.com","john00@mail.com"], ["Mary","mary@mail.com"], ["John","johnnybravo@mail.com"]]
 * Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"], ["Mary","mary@mail.com"], ["John","johnnybravo@mail.com"]]
 * 
 * Explanation:
 * The first and second John's are the same person as they have the common email "johnsmith@mail.com".
 * The third John and Mary are different people as none of their email addresses are used by other accounts.
 * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
 * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
 * 
 * Example 2:
 * Input: accounts = [
 *     ["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],
 *     ["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],
 *     ["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],
 *     ["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],
 *     ["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
 * 
 * Output: [
 *     ["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],
 *     ["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],
 *     ["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],
 *     ["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],
 *     ["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]
 */

 import java.util.HashSet;
 import java.util.Set;
 import java.util.Map;
 import java.util.HashMap;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collections;
 import java.util.List;
 
 public class AccountsMerge {
     public List<List<String>> accountsMerge(List<List<String>> accounts) {
         List<List<String>> res = new ArrayList<>();
         Map<String, Set<String>> graph = new HashMap<>();
         Map<String, String> e2n = new HashMap<>();
 
         buildGraph(accounts, graph, e2n);
 
         Set<String> visited = new HashSet<>();
         for(String email : e2n.keySet()) {
             String name = e2n.get(email);
             List<String> list = new ArrayList<>();
             // Set add return boolean
             // true if element not in set
             // false if element already in set
             if(visited.add(email)) {
                 dfs(graph, list, email, visited);
                 Collections.sort(list);
                 list.add(0, name);
                 res.add(list);
             }
         }
 
         return res;
     }
 
     private void dfs(Map<String, Set<String>> graph, List<String> list, String email, Set<String> visited) {
         list.add(email);
         if(graph.get(email) == null || graph.get(email).isEmpty()) {
             return;
         }
 
         for(String neighbor : graph.get(email)) {
             if(visited.add(neighbor)) {
                 dfs(graph, list, neighbor, visited);
             }
         }
     }
 
     private void buildGraph(List<List<String>> accounts, Map<String, Set<String>> graph, Map<String, String> e2n) {
         for(List<String> account : accounts) {
             String name = account.get(0);
             for(int i = 1; i < account.size(); i++) {
                 String email = account.get(i);
                 e2n.put(email, name);
                 graph.putIfAbsent(email, new HashSet<>());
                 if(i == 1) {
                     continue;
                 }
                 String prev = account.get(i - 1);
                 graph.get(prev).add(email);
                 graph.get(email).add(prev);
             }
         }
     }
 
     public static void main(String[] args) {
        AccountsMerge obj = new AccountsMerge();
         String[][] accounts = {
             {"John", "johnsmith@mail.com", "john_newyork@mail.com"},
             {"John", "johnsmith@mail.com", "john00@mail.com"},
             {"Mary", "mary@mail.com"},
             {"John", "johnnybravo@mail.com"}
         };
 
         // Convert the 2D array to a List of Lists
         List<List<String>> accountsList = new ArrayList<>();
         for (String[] account : accounts) {
             accountsList.add(Arrays.asList(account));
         }
 
         System.out.println(obj.accountsMerge(accountsList));
     }
 }
 