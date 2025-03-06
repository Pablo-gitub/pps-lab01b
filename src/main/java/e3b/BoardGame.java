package e3b;

import java.util.*;

/**
 * Represents the state of the game board.
 * Manages bombs, flags, and cell selections using dedicated classes.
 */
public class BoardGame {
    private final int size;
    private final Set<Bomb> bombs;
    private final Set<Flag> flags;
    private final Set<Selection> selections;

    public BoardGame(int size) {
        this.size = size;
        this.bombs = new HashSet<>();
        this.flags = new HashSet<>();
        this.selections = new HashSet<>();
        generateBombs();
    }

    /**
     * Generates bomb positions randomly based on a fixed percentage (10% of the board).
     */
    private void generateBombs() {
        int bombNumber = Math.round((size * size * 10) / 100.0f);
        Random random = new Random();
        while (bombs.size() < bombNumber) {
            Pair<Integer, Integer> pos = new Pair<>(random.nextInt(size), random.nextInt(size));
            if (!isBombAt(pos)) {
                bombs.add(new Bomb(pos));
            }
        }
    }

    /**
     * Checks if the given coordinates are within the board.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the position is inside the board, false otherwise
     */
    public boolean inBoard(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Checks if there is a bomb at the specified position.
     *
     * @param pos the position to check
     * @return true if a bomb exists at the position, false otherwise
     */
    public boolean isBombAt(Pair<Integer, Integer> pos) {
        for (Bomb bomb : bombs) {
            if (bomb.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an unmodifiable set of bomb positions.
     *
     * @return a set of positions where bombs are located
     */
    public Set<Pair<Integer, Integer>> getBombPositions() {
        Set<Pair<Integer, Integer>> positions = new HashSet<>();
        for (Bomb bomb : bombs) {
            positions.add(bomb.getPos());
        }
        return Collections.unmodifiableSet(positions);
    }

    /**
     * Adds a selection at the specified position if it is within the board and not a bomb.
     *
     * @param pos the position to select
     */
    public void addSelection(Pair<Integer, Integer> pos) {
        if (inBoard(pos.getX(), pos.getY()) && !isBombAt(pos)) {
            boolean exists = false;
            for (Selection sel : selections) {
                if (sel.getPos().equals(pos)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                selections.add(new Selection(pos));
            }
        }
    }

    /**
     * Returns an unmodifiable set of selected positions.
     *
     * @return the set of selections
     */
    public Set<Selection> getSelections() {
        return Collections.unmodifiableSet(selections);
    }

    /**
     * Adds a flag at the specified position.
     *
     * @param pos the position to flag
     */
    public void addFlag(Pair<Integer, Integer> pos) {
        boolean exists = false;
        for (Flag flag : flags) {
            if (flag.getPos().equals(pos)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            flags.add(new Flag(pos));
        }
    }

    /**
     * Removes a flag from the specified position.
     *
     * @param pos the position to remove the flag from
     */
    public void removeFlag(Pair<Integer, Integer> pos) {
        Flag toRemove = null;
        for (Flag f : flags) {
            if (f.getPos().equals(pos)) {
                toRemove = f;
                break;
            }
        }
        if (toRemove != null) {
            flags.remove(toRemove);
        }
    }

    /**
     * Returns an unmodifiable set of flags.
     *
     * @return the set of flags
     */
    public Set<Flag> getFlags() {
        return Collections.unmodifiableSet(flags);
    }

    /**
     * Checks if the game has been won.
     * The win condition is met when all non-bomb cells are selected.
     *
     * @return true if the win condition is met, false otherwise
     */
    public boolean winGame() {
        int totalCells = size * size;
        int safeCells = totalCells - bombs.size();
        return selections.size() == safeCells;
    }
}
