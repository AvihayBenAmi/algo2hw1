
import java.util.*;

public class Algo2Assignment {

    // -- שאלה 1: רובוטים על לוח --
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // למטה, למעלה, ימין, שמאל

    public static Point findOptimalCell(char[][] grid, List<Point> robots) {
        int n = grid.length;
        int[][] sumDistances = new int[n][n];

        for (Point robot : robots) {
            int[][] dist = bfs(grid, robot);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][j] != -1)
                        sumDistances[i][j] += dist[i][j];
        }

        int minSum = Integer.MAX_VALUE;
        Point bestCell = null;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 'W' && sumDistances[i][j] < minSum) {
                    minSum = sumDistances[i][j];
                    bestCell = new Point(i, j);
                }
        return bestCell;
    }

    private static int[][] bfs(char[][] grid, Point start) {
        int n = grid.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist)
            Arrays.fill(row, -1);

        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        dist[start.x][start.y] = 0;

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            for (int[] dir : directions) {
                int nx = p.x + dir[0], ny = p.y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < n && ny < n && grid[nx][ny] == 'W' && dist[nx][ny] == -1) {
                    dist[nx][ny] = dist[p.x][p.y] + 1;
                    queue.add(new Point(nx, ny));
                }
            }
        }
        return dist;
    }

    // -- שאלה 2: קוטר של עץ --
    public static int findTreeDiameter(List<List<Integer>> tree) {
        int farthest = bfsTree(tree, 0)[0]; // שלב ראשון
        int[] res = bfsTree(tree, farthest); // שלב שני
        return res[1]; // הקוטר
    }

    private static int[] bfsTree(List<List<Integer>> tree, int start) {
        int n = tree.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        dist[start] = 0;

        int farthestNode = start, maxDist = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : tree.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    queue.add(v);
                    if (dist[v] > maxDist) {
                        maxDist = dist[v];
                        farthestNode = v;
                    }
                }
            }
        }
        return new int[]{farthestNode, maxDist};
    }

    // -- שאלה 3: קישוריות חזקת --
    public static boolean isStronglyConnected(List<List<Integer>> graph) {
        int n = graph.size();
        if (!canReachAll(graph, 0)) return false;

        List<List<Integer>> reversed = reverseGraph(graph);
        return canReachAll(reversed, 0);
    }

    private static boolean canReachAll(List<List<Integer>> graph, int start) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
        for (boolean v : visited)
            if (!v) return false;
        return true;
    }

    private static List<List<Integer>> reverseGraph(List<List<Integer>> graph) {
        int n = graph.size();
        List<List<Integer>> reversed = new ArrayList<>();
        for (int i = 0; i < n; i++)
            reversed.add(new ArrayList<>());

        for (int u = 0; u < n; u++)
            for (int v : graph.get(u))
                reversed.get(v).add(u);
        return reversed;
    }


}
//modification