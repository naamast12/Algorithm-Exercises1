package org.example;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== תפריט תרגילים ====");
            System.out.println("1. תרגיל ראשון (תיאור של התרגיל)");
            System.out.println("2. קוטר של עץ (Tree Diameter)");
            System.out.println("3. גרף קשיר היטב (Strong Connectivity Checker)");
            System.out.print("הכנס את מספר הבחירה שלך: ");

            int choice = scanner.nextInt();
            System.out.println(); // שורה ריקה להפרדה


            switch (choice) {
                case 1:
                    runFirstExercise();
                    break;
                case 2:
                    runTreeDiameter();
                    break;
                case 3:
                    runStrongConnectivityChecker();
                    break;
                default:
                    System.out.println("בחירה לא חוקית.");
                    break;
            }
        }

        scanner.close();
    }



    // הפעלת תרגיל ראשון
    private static void runFirstExercise() {
        char[][] board = {
                {'R','W','W','W','W','W','W','W'},
                {'B','W','W','W','B','W','R','W'},
                {'B','B','W','W','W','W','W','W'},
                {'W','W','W','W','W','W','B','W'},
                {'W','R','W','W','W','W','B','W'},
                {'W','W','B','W','W','W','W','W'},
                {'W','B','B','B','W','W','B','W'},
                {'W','W','W','W','W','B','W','W'},
        };

        List<RobotGrid.Point> robots = RobotGrid.extractRobotsFromBoard(board);
        RobotGrid.Point result = RobotGrid.findBestMeetingPoint(board, robots);
        printBoardWithRobotsAndTarget(board, robots, result);

        if (result != null) {
            System.out.println("\n✅ Best meeting point: (row:" + result.x + ", column:" + result.y + ")");
            System.out.println("Cell type: " + board[result.x][result.y]);
        } else {
            System.out.println("❌ No common reachable point.");
        }
    }

            private static void printBoardWithRobotsAndTarget(char[][] board, List<RobotGrid.Point> robots, RobotGrid.Point target) {
                int n = board.length;
                char[][] display = new char[n][n];

                // Copy original board
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        display[i][j] = board[i][j] == 'B' ? '█' : ' ';

                // Mark robots
                for (RobotGrid.Point r : robots) {
                    display[r.x][r.y] = 'R';
                }

                // Mark target (unless it's a robot location)
                if (target != null && display[target.x][target.y] != 'R') {
                    display[target.x][target.y] = '*'; // או ★
                }

                // Print
                System.out.println("Board:");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print("[" + display[i][j] + "]");
                    }
                    System.out.println();
                }

    }

    // הפעלת תרגיל TreeDiameter
    private static void runTreeDiameter() {
        // דוגמאות לעצים ומציאת הקוטר שלהם
        TreeDiameter tree1 = new TreeDiameter();
        tree1.addEdge(1, 2);
        tree1.addEdge(2, 3);
        tree1.addEdge(3, 4);
        tree1.addEdge(4, 5);
        System.out.println("Tree 1 Diameter: " + tree1.findTreeDiameter());

        TreeDiameter tree2 = new TreeDiameter();
        tree2.addEdge(1, 2);
        tree2.addEdge(1, 3);
        tree2.addEdge(1, 4);
        tree2.addEdge(1, 5);
        System.out.println("Tree 2 Diameter: " + tree2.findTreeDiameter());

        TreeDiameter tree3 = new TreeDiameter();
        tree3.addEdge(1, 2);
        tree3.addEdge(1, 3);
        tree3.addEdge(2, 4);
        tree3.addEdge(2, 5);
        tree3.addEdge(3, 6);
        tree3.addEdge(3, 7);
        System.out.println("Tree 3 Diameter: " + tree3.findTreeDiameter());

        TreeDiameter tree4 = new TreeDiameter();
        tree4.addEdge(1, 2);
        tree4.addEdge(2, 3);
        tree4.addEdge(2, 4);
        tree4.addEdge(3, 5);
        tree4.addEdge(5, 6);
        tree4.addEdge(4, 7);
        System.out.println("Tree 4 Diameter: " + tree4.findTreeDiameter());

        TreeDiameter tree5 = new TreeDiameter();
        tree5.addEdge(1, 2);
        tree5.addEdge(1, 3);
        tree5.addEdge(3, 4);
        tree5.addEdge(3, 5);
        tree5.addEdge(5, 6);
        tree5.addEdge(6, 7);
        tree5.addEdge(2, 8);
        System.out.println("Tree 5 Diameter: " + tree5.findTreeDiameter());

        // בדיקה לעץ ריק
        TreeDiameter emptyTree = new TreeDiameter();
        System.out.println("Empty Tree Diameter: " + emptyTree.findTreeDiameter());
    }


    // הפעלת תרגיל שלישי - גרף קשיר היטב
    private static void runStrongConnectivityChecker() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("תרגיל שלישי - בדיקת האם גרף מכוון הוא קשיר היטב");

        System.out.print("הכנס את מספר הצמתים בגרף: ");
        int totalVertices = scanner.nextInt();

        System.out.print("הכנס את מספר הקשתות בגרף: ");
        int totalEdges = scanner.nextInt();

        StrongConnectivityChecker checker = new StrongConnectivityChecker(totalVertices);

        System.out.println("הכנס את הקשתות (מקור יעד):");
        for (int i = 0; i < totalEdges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            checker.addEdge(u, v);
        }

        if (checker.isStronglyConnected()) {
            System.out.println("הגרף קשיר היטב.");
        } else {
            System.out.println("הגרף אינו קשיר היטב.");
        }
    }

}