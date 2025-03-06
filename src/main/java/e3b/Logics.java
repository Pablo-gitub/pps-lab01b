package e3b;

/**
 * This interface defines the methods for the game logic.
 */
public interface Logics {
    /**
     * Processes a left-click on a cell.
     * If the cell contains a bomb, returns true (indicating game over).
     * Otherwise, the cell is selected and adjacent cells are explored if necessary.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if a bomb is found, false otherwise
     */
    boolean selectCell(int row, int col);

    /**
     * Toggles the flag on the specified cell.
     * If a flag is present, it will be removed; otherwise, a flag is added.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    void toggleFlag(int row, int col);

    /**
     * Returns the string to display for the specified cell.
     * It returns "F" for a flagged cell, "*" for a bomb cell (when selected),
     * or the number of adjacent bombs, or an empty string.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the display value for the cell
     */
    String getCellDisplayValue(int row, int col);

    /**
     * Checks if the cell should be enabled.
     * A cell is disabled if it has been selected (unless it is flagged).
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the cell is enabled, false otherwise
     */
    boolean isCellEnabled(int row, int col);

    /**
     * Determines if the game has been won.
     * The game is won if all non-bomb cells have been selected.
     *
     * @return true if the game is won, false otherwise
     */
    boolean isVictory();

    /**
     * Checks if the specified cell contains a bomb.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the cell contains a bomb, false otherwise
     */
    boolean isBomb(int row, int col);

    /**
     * Returns true if the game is over.
     */
    boolean isGameOver();
}
