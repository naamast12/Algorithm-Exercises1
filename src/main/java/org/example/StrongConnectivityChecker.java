package org.example;

import java.util.*;

public class StrongConnectivityChecker {

    private Map<Integer, List<Integer>> graph = new HashMap<>();
    private int totalVertices;

    public StrongConnectivityChecker(int totalVertices) {
        this.totalVertices = totalVertices;
    }

    public void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    public boolean isStronglyConnected() {
        // בדיקת קשירות רגילה
        if (!bfs(graph, 0)) {
            return false;
        }

        Map<Integer, List<Integer>> reversed = reverseGraph();
        return bfs(reversed, 0);
    }

    private boolean bfs(Map<Integer, List<Integer>> g, int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : g.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == totalVertices;
    }

    private Map<Integer, List<Integer>> reverseGraph() {
        Map<Integer, List<Integer>> reversed = new HashMap<>();
        for (int u = 0; u < totalVertices; u++) {
            for (int v : graph.getOrDefault(u, new ArrayList<>())) {
                reversed.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            }
        }
        return reversed;
    }
}
