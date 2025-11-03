package madiyar.smartflow.spring.topo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class TopoSortTest {

    @Test
    public void testKahnTopologicalSort() {
        int n = 6;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 0->1->2
        graph.get(0).add(1);
        graph.get(1).add(2);
        // 3->4->5
        graph.get(3).add(4);
        graph.get(4).add(5);

        List<Integer> result = TopologicalSort.kahnTopologicalSort(graph);

        assertEquals(n, result.size());
        // Check that dependencies are respected
        assertTrue(result.indexOf(0) < result.indexOf(1));
        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(3) < result.indexOf(4));
        assertTrue(result.indexOf(4) < result.indexOf(5));
    }
}