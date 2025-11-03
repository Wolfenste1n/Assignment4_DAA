package madiyar.smartflow.spring.scc;

import madiyar.smartflow.spring.*;
import java.util.*;

public class SCCAlgorithm {
    private int n;
    private List<List<Integer>> graph;
    private List<List<Integer>> reverseGraph;
    private boolean[] visited;
    private int[] order;
    private int[] component;
    private List<List<Integer>> components;
    private int orderIndex;
    private int componentCount;

    public SCCAlgorithm(int n, List<List<Integer>> graph) {
        this.n = n;
        this.graph = graph;
        this.reverseGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            reverseGraph.add(new ArrayList<>());
        }
        // reverse graph
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                reverseGraph.get(v).add(u);
            }
        }
    }

    public List<List<Integer>> findSCCs() {
        visited = new boolean[n];
        order = new int[n];
        orderIndex = 0;

        // first DFS pass (on reverse graph)
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        // second DFS pass (on original graph)
        visited = new boolean[n];
        component = new int[n];
        components = new ArrayList<>();
        componentCount = 0;

        for (int i = n - 1; i >= 0; i--) {
            int node = order[i];
            if (!visited[node]) {
                components.add(new ArrayList<>());
                dfs2(node);
                componentCount++;
            }
        }

        return components;
    }

    private void dfs1(int node) {
        visited[node] = true;
        for (int neighbor : reverseGraph.get(node)) {
            if (!visited[neighbor]) {
                dfs1(neighbor);
            }
        }
        order[orderIndex++] = node;
    }

    private void dfs2(int node) {
        visited[node] = true;
        component[node] = componentCount;
        components.get(componentCount).add(node);

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs2(neighbor);
            }
        }
    }

    public List<List<Integer>> buildCondensationGraph() {
        List<List<Integer>> condensation = new ArrayList<>();
        for (int i = 0; i < componentCount; i++) {
            condensation.add(new ArrayList<>());
        }

        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                if (component[u] != component[v]) {
                    condensation.get(component[u]).add(component[v]);
                }
            }
        }

        // remove duplicates
        for (int i = 0; i < componentCount; i++) {
            condensation.set(i, new ArrayList<>(new HashSet<>(condensation.get(i))));
        }

        return condensation;
    }
}