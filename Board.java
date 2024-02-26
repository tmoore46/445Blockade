public class Board {

    private Block[][] gameBoard;

    public Board(byte width, byte height) {
        this.gameBoard = new Block[width][height];
        initiateBlankBoard();

    }

    private void initiateBlankBoard() {
        for (byte x = 0; x < gameBoard.length; x++) {
            for (byte y = 0; y < gameBoard.length; y++) {
                gameBoard[x][y] = new Block();
            }
        }
    }

    public Block getBoardBlock(byte[] position) {
        return this.gameBoard[position[0]][position[1]];
    }
}
