package e3;

import java.util.*;

/**
 * Represents the state of the Minesweeper board.
 * Implements bomb generation, cell selections, flag management,
 * and provides methods to query the board status.
 */
public class BoardGame {
    public enum Difficulty {
        EASY, MIDDLE, DIFFICULT
    }

    private final int size;
    private final Set<Pair<Integer, Integer>> bombs;
    private final Set<Pair<Integer, Integer>> selections;
    private final Set<Pair<Integer, Integer>> flags;
    private final Difficulty difficulty;

    /**
     * Initializes the board with a given size and sets up the bombs.
     *
     * @param size the dimension of the board (size x size)
     */
    public BoardGame(int size) {
        this.size = size;
        this.difficulty = Difficulty.EASY;  // You could add a constructor parameter to choose difficulty
        this.bombs = new HashSet<>();
        this.selections = new HashSet<>();
        this.flags = new HashSet<>();
        generateBombs();
    }

    /**
     * Generates bomb positions on the board based on the difficulty setting.
     */
    private void generateBombs() {
        int bombNumber = calculateNumberOfBombs();
        Random random = new Random();
        while (bombs.size() < bombNumber) {
            Pair<Integer, Integer> bombPos = new Pair<>(random.nextInt(size), random.nextInt(size));
            bombs.add(bombPos); // HashSet ensures uniqueness
        }
    }

    /**
     * Calculates the number of bombs based on board size and difficulty.
     *
     * @return the total number of bombs to be placed on the board
     */
    private int calculateNumberOfBombs() {
        int percentage;
        switch (difficulty) {
            case EASY -> percentage = 10;
            case MIDDLE -> percentage = 15;
            case DIFFICULT -> percentage = 20;
            default -> throw new IllegalStateException("Unexpected difficulty: " + difficulty);
        }
        return Math.round((size * size * percentage) / 100.0f);
    }

    /**
     * Returns an unmodifiable set of bomb positions.
     *
     * @return bomb positions
     */
    public Set<Pair<Integer, Integer>> getBombs() {
        return Collections.unmodifiableSet(bombs);
    }

    /**
     * Returns an unmodifiable set of selected cell positions.
     *
     * @return selected cell positions
     */
    public Set<Pair<Integer, Integer>> getSelections() {
        return Collections.unmodifiableSet(selections);
    }

    /**
     * Returns an unmodifiable set of flagged cell positions.
     *
     * @return flagged cell positions
     */
    public Set<Pair<Integer, Integer>> getFlags() {
        return Collections.unmodifiableSet(flags);
    }

    /**
     * Checks if the given coordinates are within the board.
     *
     * @param row the row index
     * @param col the column index
     * @return true if inside the board, false otherwise
     */
    public boolean inBoard(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Determines if the specified cell contains a bomb.
     *
     * @param pos the cell position
     * @return true if the cell is a bomb
     */
    public boolean isABomb(Pair<Integer, Integer> pos) {
        return bombs.contains(pos);
    }

    /**
     * Determines if the specified cell is flagged.
     *
     * @param pos the cell position
     * @return true if the cell is flagged
     */
    public boolean isAFlag(Pair<Integer, Integer> pos) {
        return flags.contains(pos);
    }

    /**
     * Adds a flag to the specified cell, if it is not already selected.
     *
     * @param pos the cell position
     */
    public void addFlag(Pair<Integer, Integer> pos) {
        if (!selections.contains(pos)) {
            flags.add(pos);
        }
    }

    /**
     * Removes a flag from the specified cell.
     *
     * @param pos the cell position
     */
    public void removeFlag(Pair<Integer, Integer> pos) {
        flags.remove(pos);
    }

    /**
     * Returns the number of bombs adjacent to the specified cell.
     *
     * @param pos the cell position
     * @return the count of neighboring bombs
     */
    public int closeBombs(Pair<Integer, Integer> pos) {
        int count = 0;
        for (Pair<Integer, Integer> neighbor : getNeighbors(pos)) {
            if (bombs.contains(neighbor)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Marks a cell as selected if it is valid (inside the board, not already selected, and not a bomb).
     *
     * @param pos the cell position
     */
    public void addSelections(Pair<Integer, Integer> pos) {
        if (inBoard(pos.getX(), pos.getY()) && !selections.contains(pos) && !isABomb(pos)) {
            selections.add(pos);
        }
    }

    /**
     * Checks if the game has been won (all non-bomb cells are selected).
     *
     * @return true if the game is won, false otherwise
     */
    public boolean winGame() {
        return selections.size() == (size * size - bombs.size());
    }

    /**
     * Returns a list of valid neighboring cells for the given cell.
     *
     * @param pos the cell position
     * @return a list of adjacent cells within the board
     */
    private List<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> pos) {
        List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int neighborRow = pos.getX() + i;
                int neighborCol = pos.getY() + j;
                if (inBoard(neighborRow, neighborCol)) {
                    neighbors.add(new Pair<>(neighborRow, neighborCol));
                }
            }
        }
        return neighbors;
    }
}
