package e3b;

import java.util.Set;

/**
 * Represents a cell in the Prato Fiorito game.
 */
public class Cell {

    /**
     * Enumeration representing the state of a cell.
     */
    enum StateCell { ENABLE, DISABLE, FLAG }

    private final Pair<Integer, Integer> cellPosition;
    private StateCell stateCell;

    /**
     * Constructs a Cell with the specified position.
     * The cell is initially set to ENABLE.
     *
     * @param cellPosition the position of the cell
     */
    Cell(Pair<Integer, Integer> cellPosition) {
        stateCell = StateCell.ENABLE;
        this.cellPosition = cellPosition;
    }

    /**
     * Sets the cell state to ENABLE.
     */
    void enableCell() {
        stateCell = StateCell.ENABLE;
    }

    /**
     * Sets the cell state to FLAG.
     */
    void applyFlag() {
        this.stateCell = StateCell.FLAG;
    }

    /**
     * Sets the cell state to DISABLE.
     */
    void disable() {
        this.stateCell = StateCell.DISABLE;
    }

    /**
     * Returns the position of the cell.
     *
     * @return the cell position as a Pair of coordinates
     */
    public Pair<Integer, Integer> getCellPosition() {
        return this.cellPosition;
    }

    /**
     * Counts and returns the number of bomb positions that are near this cell.
     *
     * @param bombs the set of bomb positions
     * @return the number of nearby bombs
     */
    int numberNearbyBombs(Set<Pair<Integer, Integer>> bombs) {
        int count = 0;
        Pair<Integer, Integer> temp;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                temp = new Pair<>(this.cellPosition.getX() + i, this.cellPosition.getY() + j);
                if (bombs.contains(temp)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if the cell is flagged.
     *
     * @return true if the cell state is FLAG, false otherwise
     */
    boolean isFlag() {
        return this.stateCell == StateCell.FLAG;
    }

    /**
     * Checks if the cell is enabled.
     *
     * @return true if the cell state is ENABLE, false otherwise
     */
    boolean isEnabled() {
        return this.stateCell == StateCell.ENABLE;
    }

    /**
     * Checks if the cell is disabled.
     *
     * @return true if the cell state is DISABLE, false otherwise
     */
    boolean isDisabled() {
        return this.stateCell == StateCell.DISABLE;
    }
}
