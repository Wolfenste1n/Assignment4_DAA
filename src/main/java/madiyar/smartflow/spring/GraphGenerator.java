package madiyar.smartflow.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class GraphGenerator {

    public static void main(String[] args) {
        generateDatasets();
        System.out.println("Generated all datasets in data/ folder");
    }

    public static void generateDatasets() {
        // small graphs (6-10 nodes)
        generateSmallGraphs();

        // medium graphs (10-20 nodes)
        generateMediumGraphs();

        // large graphs (20-50 nodes)
        generateLargeGraphs();
    }

    private static void generateSmallGraphs() {
        // Graph 1: Simple DAG
        generateGraph(8, 10, false, "small_dag");

        // Graph 2: One cycle
        generateGraph(7, 12, true, "small_cycle");

        // Graph 3: Mixed
        generateGraph(9, 15, true, "small_mixed");
    }

    private static void generateMediumGraphs() {
        // Graph 1: Multiple SCCs
        generateGraph(15, 25, true, "medium_multiscc");

        // Graph 2: Sparse DAG
        generateGraph(18, 20, false, "medium_sparse");

        // Graph 3: Dense with cycles
        generateGraph(12, 35, true, "medium_dense");
    }

    private static void generateLargeGraphs() {
        // Graph 1: Large DAG
        generateGraph(35, 60, false, "large_dag");

        // Graph 2: Many small cycles
        generateGraph(28, 45, true, "large_cycles");

        // Graph 3: Performance test
        generateGraph(50, 120, true, "large_performance");
    }

    private static void generateGraph(int n, int edgeCount, boolean allowCycles, String name) {
        Random random = new Random();
        List<Map<String, Object>> edges = new ArrayList<>();

        for (int i = 0; i < edgeCount; i++) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);

            // For DAGs, ensure u < v to avoid cycles
            if (!allowCycles && u >= v) {
                continue;
            }

            int w = random.nextInt(10) + 1; // weights 1-10

            Map<String, Object> edge = new HashMap<>();
            edge.put("u", u);
            edge.put("v", v);
            edge.put("w", w);
            edges.add(edge);
        }

        Map<String, Object> graphData = new HashMap<>();
        graphData.put("directed", true);
        graphData.put("n", n);
        graphData.put("edges", edges);
        graphData.put("source", 0);
        graphData.put("weight_model", "edge");

        try {
            ObjectMapper mapper = new ObjectMapper();
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    new File("data/" + name + ".json"), graphData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}