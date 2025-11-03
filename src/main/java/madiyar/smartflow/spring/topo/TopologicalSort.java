package madiyar.smartflow.spring.topo;

import java.util.*;
import madiyar.smartflow.spring.*;

public class TopologicalSort {

    public static List<Integer> kahnTopologicalSort(List<List<Integer>> graph) {
        int n = graph.size();
        int[] inDegree = new int[n];

        //  in-degrees
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                inDegree[v]++;
            }
        }

        // initialize queue with nodes having 0 in-degree
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);

            for (int v : graph.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return result;
    }

    public static List<Integer> dfsTopologicalSort(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited, stack);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private static void dfs(int node, List<List<Integer>> graph,
                            boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited, stack);
            }
        }

        stack.push(node);
    }
}