package Algorithms.Searching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * You are given an array of variable pairs equations and an array of real numbers values, 
 * where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.
 * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
 * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
 * Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.
 * Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.
 * 
 * Example 1:
 * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
 * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
 * Explanation: 
 * Given: a / b = 2.0, b / c = 3.0
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? 
 * return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
 * note: x is undefined => -1.0
 * 
 * Example 2:
 * Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
 * Output: [3.75000,0.40000,5.00000,0.20000]
 * 
 * Example 3:
 * Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
 * Output: [0.50000,2.00000,-1.00000,-1.00000]
 */

public class EvaluateDivision {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        buildGraph(equations, values, graph);
        double[] res = new double[queries.size()];
        Arrays.fill(res, -1);
        for(int i = 0; i < res.length; i++) {
            List<String> query = queries.get(i);
            String a = query.get(0);
            String b = query.get(1);
            if(!graph.containsKey(a) || !graph.containsKey(b)) {
                continue;
            }
            dfs(graph, a, b, res, i, 1.0, new HashSet<>());
        }

        return res;
    }

    private void dfs(Map<String, Map<String, Double>> graph, String a, String b, double[] res, int index, double temp, Set<String> visited) {
        visited.add(a);
        if(graph.get(a).isEmpty()) {
            return;
        }

        if(graph.get(a).containsKey(b)) {
            res[index] = graph.get(a).get(b) * temp;
            return;
        }

        for(String next : graph.get(a).keySet()) {
            dfs(graph, next, b, res, index, graph.get(a).get(next) * temp, visited);
        }

    }

    private void buildGraph(List<List<String>> equations, double[] values, Map<String, Map<String, Double>> graph) {
        int index = 0;
        for(List<String> list : equations) {
            String a = list.get(0);
            String b = list.get(1);

            graph.putIfAbsent(a, new HashMap<>());
            graph.get(a).put(b, values[index]);

            graph.putIfAbsent(b, new HashMap<>());
            graph.get(b).put(a, 1.0 / values[index]);

            graph.get(a).put(a, 1.0);
            graph.get(b).put(b, 1.0);
            index++;
        }
    }
}
