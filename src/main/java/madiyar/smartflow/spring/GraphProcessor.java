package madiyar.smartflow.spring;

import madiyar.smartflow.spring.scc.SCCAlgorithm;
import madiyar.smartflow.spring.topo.TopologicalSort;
import madiyar.smartflow.spring.dagsp.DAGShortestPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.*;

public class GraphProcessor {
    private int n;
    private List<List<Integer>> graph;
    private List<List<Integer>> weights;
    private Metrics metrics;

    public GraphProcessor(int n) {
        this.n = n;
        this.graph = new ArrayList<>();
        this.weights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            weights.add(new ArrayList<>());
        }
        this.metrics = new Metrics();
    }

    public void addEdge(int u, int v, int w) {
        graph.get(u).add(v);
        weights.get(u).add(w);
    }

    public void processGraph(int source) {
        System.out.println("=== Graph Analysis ===");
        System.out.println("Nodes: " + n);
        System.out.println("Source: " + source);

        // 1. find SCCs
        metrics.startTimer();
        SCCAlgorithm scc = new SCCAlgorithm(n, graph);
        List<List<Integer>> components = scc.findSCCs();
        metrics.stopTimer();

        System.out.println("\n=== Strongly Connected Components ===");
        System.out.println("Number of SCCs: " + components.size());
        for (int i = 0; i < components.size(); i++) {
            System.out.println("SCC " + i + ": " + components.get(i) +
                    " (size: " + components.get(i).size() + ")");
        }
        System.out.println("SCC Time: " + metrics.getElapsedTime() + " ns");

        // 2. build condensation graph and topological sort
        metrics.startTimer();
        List<List<Integer>> condensation = scc.buildCondensationGraph();
        List<Integer> topoOrder = TopologicalSort.kahnTopologicalSort(condensation);
        metrics.stopTimer();

        System.out.println("\n=== Topological Order ===");
        System.out.println("Condensation graph order: " + topoOrder);
        System.out.println("Topo Sort Time: " + metrics.getElapsedTime() + " ns");

        // 3. shortest paths in DAG
        metrics.startTimer();
        DAGShortestPath.Result spResult = DAGShortestPath.findShortestPaths(
                graph, weights, source);
        metrics.stopTimer();

        System.out.println("\n=== Shortest Paths from Source " + source + " ===");
        for (int i = 0; i < n; i++) {
            if (spResult.distances[i] != Integer.MAX_VALUE) {
                System.out.println("Distance to " + i + ": " + spResult.distances[i]);
            } else {
                System.out.println("Distance to " + i + ": unreachable");
            }
        }

        System.out.println("\n=== Critical Path ===");
        System.out.println("Critical path length: " + spResult.longestPathLength);
        System.out.println("Critical path: " + spResult.criticalPath);
        System.out.println("Shortest Path Time: " + metrics.getElapsedTime() + " ns");
    }

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(
                    new File("data/tasks.json"),
                    new TypeReference<Map<String, Object>>() {}
            );

            int n = (Integer) data.get("n");
            List<Map<String, Object>> edges = (List<Map<String, Object>>) data.get("edges");
            int source = (Integer) data.get("source");

            GraphProcessor processor = new GraphProcessor(n);
            for (Map<String, Object> edge : edges) {
                int u = (Integer) edge.get("u");
                int v = (Integer) edge.get("v");
                int w = (Integer) edge.get("w");
                processor.addEdge(u, v, w);
            }

            processor.processGraph(source);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}