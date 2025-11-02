package madiyar.smartflow.spring;

public class Metrics {
    private long startTime;
    private long endTime;

    // SCC metrics
    public int dfsVisits;
    public int edgesTraversed;

    // Topological sort metrics
    public int queuePushes;
    public int queuePops;

    // Shortest path metrics
    public int relaxations;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void reset() {
        dfsVisits = 0;
        edgesTraversed = 0;
        queuePushes = 0;
        queuePops = 0;
        relaxations = 0;
    }
}