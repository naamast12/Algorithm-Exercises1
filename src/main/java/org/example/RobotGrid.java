package org.example;
import java.util.*;

public class RobotGrid {

    public static class Point {
        public int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }
    }

    static int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

    public static Point findBestMeetingPoint(char[][] board, List<Point> robots) {
        int n = board.length;
        int[][] sumDistances = new int[n][n];
        int[][] visitCounts = new int[n][n];

        for (Point robot : robots) {
            bfs(board, robot, sumDistances, visitCounts);
        }

        int minSum = Integer.MAX_VALUE;
        Point bestPoint = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'W' && visitCounts[i][j] == robots.size()) {
                    if (sumDistances[i][j] < minSum) {
                        minSum = sumDistances[i][j];
                        bestPoint = new Point(i, j);
                    }
                }
            }
        }

//        printDistanceTable(board, sumDistances, bestPoint);
//        printVisitCounts(board, visitCounts);

        return bestPoint;
    }

    private static void bfs(char[][] board, Point start, int[][] sumDistances, int[][] visitCounts) {
        int n = board.length;

        if (start.x < 0 || start.y < 0 || start.x >= n || start.y >= n || board[start.x][start.y] != 'W') {
            return;
        }

        boolean[][] visited = new boolean[n][n];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;
        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                Point p = queue.poll();
                sumDistances[p.x][p.y] += distance;
                visitCounts[p.x][p.y]++;

                for (int[] dir : directions) {
                    int nx = p.x + dir[0], ny = p.y + dir[1];
                    if (nx >= 0 && nx < n && ny >= 0 && ny < n &&
                            board[nx][ny] == 'W' && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.add(new Point(nx, ny));
                    }
                }
            }
            distance++;
        }
    }

    // טבלת סכום מרחקים עם ⭐ על התא האופטימלי
    private static void printDistanceTable(char[][] board, int[][] distances, Point bestPoint) {
        int n = board.length;
        System.out.println("\n📊 Distance Table:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'W') {
                    if (bestPoint != null && bestPoint.x == i && bestPoint.y == j) {
                        System.out.printf("⭐%2d ", distances[i][j]); // מסמן את המשבצת הכי טובה
                    } else {
                        System.out.printf(" %2d  ", distances[i][j]);
                    }
                } else {
                    System.out.print("  -  ");
                }
            }
            System.out.println();
        }
    }

    // טבלת כמות הרובוטים שהגיעו לכל משבצת
    private static void printVisitCounts(char[][] board, int[][] counts) {
        int n = board.length;
        System.out.println("\n👣 Visit Counts Table:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'W') {
                    System.out.printf(" %d  ", counts[i][j]);
                } else {
                    System.out.print(" -  ");
                }
            }
            System.out.println();
        }
    }

    // חילוץ רובוטים מהלוח (מסומנים כ־'R') והפיכת התאים שלהם ל־'W'
    public static List<Point> extractRobotsFromBoard(char[][] board) {
        List<Point> robots = new ArrayList<>();
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'R') {
                    robots.add(new Point(i, j));
                    board[i][j] = 'W'; // מאפשר תנועה משם
                }
            }
        }
        return robots;
    }
}
