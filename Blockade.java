import java.awt.Color;

public class Blockade {

    // Regular grid: 11x14 squares, 10x13 lines
    // pieces start on (assuming (0,0) is top right)
    // zero based positioning
    // P2(Yellow): (3,3), (3,7)
    // P1(Red): (10, 3), (10, 7)

    // Movement: 2 orthogonal, 1 diagonal

    // Two wall types
    // Blue walls can only be placed hrizontal
    // Green walls can only be placed vertical
    // Nine of each wall to each player

    // must always have a way to get to each homespace

    public static final Color PLAYER1_COLOR = Color.RED;
    public static final Color PLAYER2_COLOR = Color.YELLOW;
    public static final Color VERTICAL_WALL = Color.BLUE;
    public static final Color HORIZONTAL_WALL = Color.GREEN;

    public static void main(String[] args) {

    }
}