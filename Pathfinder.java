import java.util.ArrayDeque;
import java.util.Queue;

public class Pathfinder {

    public static boolean isValidWallPlacement(int[] start, int[] end, CellPanel[][] gameBoard) {
        int width = gameBoard.length;
        int height = gameBoard[0].length;

        boolean[][] visited = new boolean[width][height];
        Queue<int[]> queue = new ArrayDeque<>();

        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (current[0] == end[0] && current[1] == end[1]) {
                return true; // Found a valid path
            }

            // Check adjacent positions
            int[][] adjacentPositions = {
                    { current[0] + 1, current[1] }, // Right
                    { current[0] - 1, current[1] }, // Left
                    { current[0], current[1] + 1 }, // Down
                    { current[0], current[1] - 1 } // Up
            };

            for (int[] adj : adjacentPositions) {
                if (isValidPosition(adj, width, height) && !visited[adj[0]][adj[1]]
                        && !isBlocked(adj[0], adj[1], gameBoard)) {
                    queue.offer(adj);
                    visited[adj[0]][adj[1]] = true;
                }
            }
        }

        return false; // No valid path found
    }

    private static boolean isBlocked(int x, int y, CellPanel[][] gameBoard) {
        if (x < 0 || y < 0 || x >= gameBoard.length || y >= gameBoard[0].length)
            return true; // Out of bounds are considered blocked

        return gameBoard[x][y].getBlock().isOccupied(); // Check if the block is occupied
    }

    private static boolean isValidPosition(int[] position, int width, int height) {
        return position[0] >= 0 && position[0] < width && position[1] >= 0 && position[1] < height;
    }

}
