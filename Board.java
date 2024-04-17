public class Board {
    private Block[][] gameBoard;

    public Board(byte width, byte height) {
        this.gameBoard = new Block[width][height];
        initiateBlankBoard();
    }

    private void initiateBlankBoard() {
        for (byte x = 0; x < gameBoard.length; x++) {
            for (byte y = 0; y < gameBoard[x].length; y++) {
                gameBoard[x][y] = new Block();
            }
        }
    }

    public Block getBoardBlock(int x, int y) {
        return this.gameBoard[x][y];
    }

    public int getWidth() {
        return gameBoard.length;
    }

    public int getHeight() {
        if (gameBoard.length == 0) {
            return 0;
        }
        return gameBoard[0].length;
    }
}
