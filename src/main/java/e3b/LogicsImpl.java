package e3b;

import java.util.*;
import e3b.Pair;

/**
 * Implementation of the game logic for "Prato Fiorito".
 * Handles cell selection, flag toggling, and game status using BoardGame.
 */
public class LogicsImpl implements Logics {
    private final BoardGame game;
    private boolean gameOver;

    /**
     * Constructs a LogicsImpl with a board of the specified size.
     *
     * @param size the size of the board
     */
    public LogicsImpl(int size) {
        this.game = new BoardGame(size);
        this.gameOver = false;
    }

    /**
     * Processes a cell selection.
     * If the selected cell contains a bomb, sets game over.
     * Otherwise, adds a selection and, if no nearby bombs, explores adjacent cells.
     *
     * @param row the row of the selected cell
     * @param col the column of the selected cell
     * @return true if a bomb is selected (game over), false otherwise
     */
    @Override
    public boolean selectCell(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);

        if (hasFlagAt(pos)) {
            game.removeFlag(pos);
        }

        if (game.isBombAt(pos)) {
            gameOver = true;
            return true;
        }

        game.addSelection(pos);
        Selection sel = new Selection(pos);
        if (!sel.hasNearbyBombs(game.getBombPositions())) {
            exploreNoBombs(pos);
        }
        return false;
    }

    /**
     * Iteratively explores adjacent cells with no nearby bombs using BFS.
     *
     * @param start the starting position for exploration
     */
    private void exploreNoBombs(Pair<Integer, Integer> start) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(start);
        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    Pair<Integer, Integer> neighbor = new Pair<>(current.getX() + i, current.getY() + j);
                    if (game.inBoard(neighbor.getX(), neighbor.getY()) && !visited.contains(neighbor)) {
                        if (!game.isBombAt(neighbor)) {
                            game.addSelection(neighbor);
                            Selection neighborSel = new Selection(neighbor);
                            if (!neighborSel.hasNearbyBombs(game.getBombPositions())) {
                                queue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Toggles a flag at the specified cell.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     */
    @Override
    public void toggleFlag(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        if (hasFlagAt(pos)) {
            game.removeFlag(pos);
        } else {
            game.addFlag(pos);
        }
    }

    /**
     * Checks if there is a flag at the specified position.
     *
     * @param pos the position to check
     * @return true if a flag exists at the position, false otherwise
     */
    private boolean hasFlagAt(Pair<Integer, Integer> pos) {
        for (Flag flag : game.getFlags()) {
            if (flag.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the display value for the specified cell.
     * Displays "F" if flagged, "*" if selected and is a bomb, or the count of nearby bombs.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the string to display for the cell
     */
    @Override
    public String getCellDisplayValue(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        if (hasFlagAt(pos)) {
            return "F";
        }
        boolean selected = false;
        for (Selection sel : game.getSelections()) {
            if (sel.getPos().equals(pos)) {
                selected = true;
                break;
            }
        }
        if (selected) {
            if (game.isBombAt(pos)) {
                return "*";
            }
            Selection sel = new Selection(pos);
            int count = sel.countNearbyBombs(game.getBombPositions());
            return count == 0 ? "" : String.valueOf(count);
        }
        return "";
    }

    /**
     * Determines if the specified cell is enabled.
     * A cell is enabled if it has not been selected or if it is flagged.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if the cell is enabled, false otherwise
     */
    @Override
    public boolean isCellEnabled(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        boolean selected = false;
        for (Selection sel : game.getSelections()) {
            if (sel.getPos().equals(pos)) {
                selected = true;
                break;
            }
        }
        return !selected || hasFlagAt(pos);
    }

    /**
     * Checks if the win condition is met.
     *
     * @return true if all safe cells have been selected, false otherwise
     */
    @Override
    public boolean isVictory() {
        return game.winGame();
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if there is a bomb at the specified cell.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if a bomb exists at the cell, false otherwise
     */
    @Override
    public boolean isBomb(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        return game.isBombAt(pos);
    }
}
