package org.example;

import java.util.*;

public class TreeDiameter {
    // מבנה נתונים לייצוג העץ
    private Map<Integer, List<Integer>> tree = new HashMap<>();

    // הוספת קשת בין שני צמתים
    public void addEdge(int node1, int node2) {
        tree.putIfAbsent(node1, new ArrayList<>());
        tree.putIfAbsent(node2, new ArrayList<>());
        tree.get(node1).add(node2);
        tree.get(node2).add(node1);
    }

    // פונקציית BFS למציאת הצומת הרחוק ביותר והמרחק אליו
    private int[] bfs(int start) {
        Queue<Integer> nodeQueue = new LinkedList<>();
        Map<Integer, Integer> distances = new HashMap<>();

        nodeQueue.offer(start);
        distances.put(start, 0);
        int farthestNode = start;
        int maxDistance = 0;

        while (!nodeQueue.isEmpty()) {
            int current = nodeQueue.poll();
            int currentDistance = distances.get(current);

            for (int neighbor : tree.getOrDefault(current, new ArrayList<>())) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, currentDistance + 1);
                    nodeQueue.offer(neighbor);

                    // בדיקה אם זה הצומת הרחוק ביותר
                    if (distances.get(neighbor) > maxDistance) {
                        maxDistance = distances.get(neighbor);
                        farthestNode = neighbor;
                    }
                }
            }
        }

        return new int[]{farthestNode, maxDistance};
    }

    // פונקציה למציאת קוטר העץ
    public int findTreeDiameter() {
        if (tree.isEmpty()) {
            System.out.println("The tree is empty.");
            return 0;
        }

        int startNode = tree.keySet().iterator().next();
        int[] firstBFS = bfs(startNode);
        int farthestNode = firstBFS[0];
        int[] secondBFS = bfs(farthestNode);
        return secondBFS[1];
    }
}