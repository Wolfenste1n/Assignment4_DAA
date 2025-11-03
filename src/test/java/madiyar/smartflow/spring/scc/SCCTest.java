package madiyar.smartflow.spring.scc;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SCCTest {

    @Test
    public void testSimpleGraph() {
        int n = 5;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 0->1->2->0 (cycle)
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);

        // 3->4
        graph.get(3).add(4);

        SCCAlgorithm scc = new SCCAlgorithm(n, graph);
        List<List<Integer>> components = scc.findSCCs();

        assertEquals(3, components.size());

        // check that cycle nodes are in same component
        boolean foundCycleComponent = false;
        for (List<Integer> comp : components) {
            if (comp.size() == 3) {
                assertTrue(comp.contains(0));
                assertTrue(comp.contains(1));
                assertTrue(comp.contains(2));
                foundCycleComponent = true;
            }
        }
        assertTrue(foundCycleComponent);
    }
}