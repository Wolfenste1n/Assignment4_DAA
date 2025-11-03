package madiyar.smartflow.spring.dagsp;

import java.util.*;
import madiyar.smartflow.spring.*;
import madiyar.smartflow.spring.topo.TopologicalSort;

public class DAGShortestPath {

    public static class Result {
        public int[] distances;
        public int[] predecessors;
        public int longestPathLength;
        public List<Integer> criticalPath;

        public Result(int n) {
            distances = new int[n];
            predecessors = new int[n];
            Arrays.fill(predecessors, -1);
        }
    }

    public static Result findShortestPaths(List<List<Integer>> graph,
                                           List<List<Integer>> weights,
                                           int source) {
        int n = graph.size();
        Result result = new Result(n);
        Arrays.fill(result.distances, Integer.MAX_VALUE);
        result.distances[source] = 0;

        List<Integer> topoOrder = TopologicalSort.dfsTopologicalSort(graph);

        // process nodes in topological order
        for (int u : topoOrder) {
            if (result.distances[u] != Integer.MAX_VALUE) {
                for (int i = 0; i < graph.get(u).size(); i++) {
                    int v = graph.get(u).get(i);
                    int weight = weights.get(u).get(i);

                    if (result.distances[u] + weight < result.distances[v]) {
                        result.distances[v] = result.distances[u] + weight;
                        result.predecessors[v] = u;
                    }
                }
            }
        }

        // find longest path
        findCriticalPath(result, graph, weights);

        return result;
    }

    private static void findCriticalPath(Result result, List<List<Integer>> graph,
                                         List<List<Integer>> weights) {
        int n = graph.size();
        int[] longestDist = new int[n];
        int[] pred = new int[n];
        Arrays.fill(pred, -1);
        Arrays.fill(longestDist, Integer.MIN_VALUE);

        // initialize for all nodes reachable from source
        for (int i = 0; i < n; i++) {
            if (result.distances[i] != Integer.MAX_VALUE) {
                longestDist[i] = 0;
            }
        }

        List<Integer> topoOrder = TopologicalSort.dfsTopologicalSort(graph);

        for (int u : topoOrder) {
            if (longestDist[u] != Integer.MIN_VALUE) {
                for (int i = 0; i < graph.get(u).size(); i++) {
                    int v = graph.get(u).get(i);
                    int weight = weights.get(u).get(i);

                    if (longestDist[u] + weight > longestDist[v]) {
                        longestDist[v] = longestDist[u] + weight;
                        pred[v] = u;
                    }
                }
            }
        }

        // find the maximum distance
        int maxDist = Integer.MIN_VALUE;
        int endNode = -1;
        for (int i = 0; i < n; i++) {
            if (longestDist[i] > maxDist) {
                maxDist = longestDist[i];
                endNode = i;
            }
        }

        result.longestPathLength = maxDist;
        result.criticalPath = reconstructPath(pred, endNode);
    }

    private static List<Integer> reconstructPath(int[] predecessors, int endNode) {
        List<Integer> path = new ArrayList<>();
        if (predecessors[endNode] == -1) return path;

        int current = endNode;
        while (current != -1) {
            path.add(current);
            current = predecessors[current];
        }
        Collections.reverse(path);
        return path;
    }
}