package e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a chess board containing various chess pieces.
 */
public class BoardGame {
    private final List<ChessPiece> pieces;
    private final Random random = new Random();
    private final int size;

    /**
     * Constructs a BoardGame with the specified board size.
     * Initializes the game by adding a Pawn and a Knight at random empty positions.
     *
     * @param size the size of the chess board
     */
    public BoardGame(int size) {
        this.size = size;
        this.pieces = new ArrayList<>();
        this.pieces.add(new Pawn(this.randomEmptyPosition()));
        this.pieces.add(new Knight(this.randomEmptyPosition(), this.size));
    }

    /**
     * Generates a random empty position on the board that is not occupied by any piece.
     *
     * @return a random empty position as a Pair of coordinates
     */
    private Pair<Integer, Integer> randomEmptyPosition() {
        Pair<Integer, Integer> pos = new Pair<>(this.random.nextInt(size), this.random.nextInt(size));
        boolean isOccupied = pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(pos));
        return isOccupied ? randomEmptyPosition() : pos;
    }

    /**
     * Checks if the given position is within the bounds of the board.
     *
     * @param pos the position as a Pair of coordinates
     * @return true if the position is within the board, false otherwise
     */
    public boolean inBoardMove(Pair<Integer, Integer> pos) {
        int row = pos.getX();
        int col = pos.getY();
        return row >= 0 && col >= 0 && row < this.size && col < this.size;
    }

    /**
     * Retrieves the Knight from the list of chess pieces.
     *
     * @return the Knight if found, otherwise null
     */
    public Knight getKnight() {
        return pieces.stream()
                .filter(piece -> piece instanceof Knight)
                .map(piece -> (Knight) piece)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves the Pawn from the list of chess pieces.
     *
     * @return the Pawn if found, otherwise null
     */
    public Pawn getPawn() {
        return pieces.stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .findFirst()
                .orElse(null);
    }

    /**
     * Determines if the game has ended.
     * The game ends when the Pawn and the Knight occupy the same position.
     *
     * @return true if the game has ended, false otherwise
     */
    public boolean endGame(){
        return this.getPawn().getPosition().equals(this.getKnight().getPosition());
    }

}
