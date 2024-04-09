import java.awt.Color;

public class Settings {

    // GUI Settings
    public class GUISettings {
        public static final int GRID_WIDTH = 14;
        public static final int GRID_HEIGHT = 11;

        public static final Color PLAYER1_BGCOLOR = new Color(8323072);
        public static final Color PLAYER2_BGCOLOR = new Color(11053056);

        public static final Color PLAYER1_TEXT_COLOR = new Color(10724259);
        public static final Color PLAYER2_TEXT_COLOR = new Color(2697513);

        public static final String HORIZONTAL_TEXT = "Horizontal Walls: ";
        public static final String VERTICAL_TEXT = "Vertical Walls: ";

    }

    // CellPanel Settings

    public class CellSettings {
        public static final short CELL_PADDING = 2;
        public static final short CELL_PIECE_SIZE = 36;
        public static final Color CELL_HORIZONTAL_WALL_COLOR = new Color(2662480);
        public static final Color CELL_VERTICAL_WALL_COLOR = new Color(2642080);
        public static final short CELL_SIZE = 50;
    }

}
