package e3b;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Represents the game board for the "Prato Fiorito" (Minesweeper-like) game.
 * It manages the board's cells and bomb positions based on a selected difficulty.
 */
public class BoardGame {

    /**
     * Enumeration for the difficulty levels.
     */
    enum Difficulty { EASY, MEDIUM, HARD }

    private final Difficulty difficulty;
    final int size;
    Set<Pair<Integer, Integer>> bombs;
    Set<Cell> cells;

    /**
     * Constructs a BoardGame with the given size and default difficulty (EASY).
     *
     * @param size the size of the board (size x size)
     */
    BoardGame(int size) {
        this.size = size;
        this.difficulty = Difficulty.EASY;
        bombs = new HashSet<>();
        cells = new HashSet<>();
        setBombs();
        setCells();
    }

    /**
     * Constructs a BoardGame with the given size and specified difficulty.
     *
     * @param size the size of the board (size x size)
     * @param difficulty the difficulty level
     */
    BoardGame(int size, Difficulty difficulty) {
        this.size = size;
        this.difficulty = difficulty;
        bombs = new HashSet<>();
        cells = new HashSet<>();
        setBombs();
        setCells();
    }

    /**
     * Generates bomb positions based on the board size and difficulty.
     * The percentage of bombs is determined by the difficulty level.
     */
    private void setBombs() {
        int percentage;
        Random random = new Random();
        switch (difficulty) {
            case EASY -> percentage = 10;
            case MEDIUM -> percentage = 15;
            case HARD -> percentage = 20;
            default -> throw new IllegalStateException("Unexpected difficulty: " + difficulty);
        }
        int numberBombs = Math.round((size * size * percentage) / 100.0f);
        while (this.bombs.size() < numberBombs) {
            Pair<Integer, Integer> bomb = new Pair<>(random.nextInt(this.size), random.nextInt(this.size));
            this.bombs.add(bomb);
        }
    }

    /**
     * Creates all cells for the board and stores them.
     */
    private void setCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(new Pair<>(i, j));
                this.cells.add(cell);
            }
        }
    }

    /**
     * Checks if the specified position contains a bomb.
     *
     * @param pos the position to check
     * @return true if there is a bomb at the position, false otherwise
     */
    public boolean isABomb(Pair<Integer, Integer> pos) {
        return this.bombs.contains(pos);
    }

    /**
     * Retrieves the cell at the specified position.
     *
     * @param pos the position of the cell
     * @return the Cell at that position, or null if not found
     */
    public Cell getCell(Pair<Integer, Integer> pos) {
        return this.cells.stream()
                .filter(cell -> cell.getCellPosition().equals(pos))
                .findFirst()
                .orElse(null);
    }

    /**
     * Applies a flag on the cell at the specified position.
     *
     * @param pos the position of the cell to flag
     */
    public void applyFlag(Pair<Integer, Integer> pos) {
        getCell(pos).applyFlag();
    }

    /**
     * Disables (reveals) the cell at the specified position.
     *
     * @param pos the position of the cell to disable
     */
    public void disableCell(Pair<Integer, Integer> pos) {
        getCell(pos).disable();
    }

    /**
     * Re-enables the cell at the specified position (removes flag).
     *
     * @param pos the position of the cell to re-enable
     */
    public void disableFlag(Pair<Integer, Integer> pos) {
        getCell(pos).enableCell();
    }

    /**
     * Returns the set of bomb positions.
     *
     * @return the set of positions where bombs are located
     */
    public Set<Pair<Integer, Integer>> getBombs() {
        return this.bombs;
    }

    /**
     * Returns the number of bombs adjacent to the cell at the specified position.
     *
     * @param pos the position of the cell
     * @return the number of nearby bombs
     */
    public int nearbyBombsToPos(Pair<Integer, Integer> pos) {
        return getCell(pos).numberNearbyBombs(this.getBombs());
    }

    /**
     * Checks if the win condition is met.
     * The win condition is achieved when the number of disabled cells equals the number of safe cells.
     *
     * @return true if the win condition is met, false otherwise
     */
    public boolean isVictory() {
        long disabledCells = cells.stream().filter(Cell::isDisabled).count();
        return disabledCells == (this.size * this.size - this.bombs.size());
    }
}
