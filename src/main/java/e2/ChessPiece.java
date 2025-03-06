package e2;

/**
 * Interface representing a generic chess piece.
 */
public interface ChessPiece {

    /**
     * Determines if moving to the specified position is a valid move for the piece.
     *
     * @param pos the target position as a Pair of coordinates
     * @return true if the move is valid, false otherwise
     */
    public boolean validMove(Pair<Integer,Integer> pos);

    /**
     * Gets the current position of the chess piece.
     *
     * @return the current position as a Pair of coordinates
     */
    public Pair<Integer,Integer> getPosition();

    /**
     * Sets a new position for the chess piece if the move is valid.
     *
     * @param pos the new position as a Pair of coordinates
     */
    public void setNewPosition(Pair<Integer,Integer> pos);

}
