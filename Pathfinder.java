import java.util.ArrayDeque;
import java.util.Queue;

public class Pathfinder {

    public static boolean isValidMove(byte[] start, byte[] end, Board gameBoard) {
        int width = gameBoard.getWidth();
        int height = gameBoard.getHeight();

        boolean[][] visited = new boolean[width][height];
        Queue<byte[]> queue = new ArrayDeque<>();

        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            byte[] current = queue.poll();

            if (current[0] == end[0] && current[1] == end[1]) {
                return true; // Found a valid path
            }

            // Check adjacent positions
            byte[][] adjacentPositions = {
                    { (byte) (current[0] + 1), current[1] }, // Right
                    { (byte) (current[0] - 1), current[1] }, // Left
                    { current[0], (byte) (current[1] + 1) }, // Down
                    { current[0], (byte) (current[1] - 1) } // Up
            };

            for (byte[] adj : adjacentPositions) {
                if (isValidPosition(adj, width, height) && !visited[adj[0]][adj[1]] && !isBlocked(adj, gameBoard)) {
                    queue.offer(adj);
                    visited[adj[0]][adj[1]] = true;
                }
            }
        }

        return false; // No valid path found
    }

    private static boolean isValidPosition(byte[] position, int width, int height) {
        return position[0] >= 0 && position[0] < width &&
                position[1] >= 0 && position[1] < height;
    }

    private static boolean isBlocked(byte[] position, Board gameBoard) {
        return gameBoard.getBoardBlock(position).isOccupied(); // Check if the block is occupied
    }
}
