package e3b;

/**
 * Defines the game logic interface for handling user interactions
 * in the "Prato Fiorito" game. Implementations of this interface
 * handle left-clicks, right-clicks, and provide methods to retrieve
 * the current state of a cell as well as overall game status.
 */
public interface Logics {

    /**
     * Processes a left-click action on a cell at the given position.
     * Typically, this action reveals the cell and may trigger further exploration.
     *
     * @param pos the position of the cell as a Pair of integers (row, column)
     */
    public void leftClick(Pair<Integer, Integer> pos);

    /**
     * Processes a right-click action on a cell at the given position.
     * Typically, this action toggles a flag on the cell.
     *
     * @param pos the position of the cell as a Pair of integers (row, column)
     */
    public void rightClick(Pair<Integer, Integer> pos);

    /**
     * Returns a string representation of the cell at the specified row and column.
     * The string may be empty, a number indicating nearby bombs, a flag ("F"),
     * or a bomb ("*") if the game is over.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return a string representing the state of the cell
     */
    public String stringCell(int row, int col);

    /**
     * Returns the enabled state of the cell at the specified row and column.
     * A cell is considered enabled if it is still interactable.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the cell is enabled, false otherwise
     */
    public boolean cellState(int row, int col);

    /**
     * Indicates whether the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver();

    /**
     * Indicates whether the win condition has been achieved.
     *
     * @return true if the game has been won, false otherwise
     */
    public boolean isVictory();
}
