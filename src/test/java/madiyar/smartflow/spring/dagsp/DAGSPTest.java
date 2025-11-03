package madiyar.smartflow.spring.dagsp;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DAGSPTest {

    @Test
    public void testShortestPath() {
        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();
        List<List<Integer>> weights = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            weights.add(new ArrayList<>());
        }

        // 0->1 (2), 0->2 (1), 1->3 (3), 2->3 (1)
        graph.get(0).add(1); weights.get(0).add(2);
        graph.get(0).add(2); weights.get(0).add(1);
        graph.get(1).add(3); weights.get(1).add(3);
        graph.get(2).add(3); weights.get(2).add(1);

        DAGShortestPath.Result result = DAGShortestPath.findShortestPaths(graph, weights, 0);

        assertEquals(0, result.distances[0]);
        assertEquals(2, result.distances[1]);
        assertEquals(1, result.distances[2]);
        assertEquals(2, result.distances[3]); // 0->2->3 = 1+1=2
    }
}