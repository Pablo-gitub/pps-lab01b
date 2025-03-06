package e3;

/**
 * Implementation of the Logics interface.
 */
public class LogicsImpl implements Logics {
    private final BoardGame game;
    private boolean gameOver;

    public LogicsImpl(int size) {
        this.game = new BoardGame(size);
        gameOver = false;
    }

    @Override
    public boolean selectCell(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);

        if (game.isAFlag(pos)) {
            game.removeFlag(pos);
        }
        game.addSelections(pos);

        if (game.isABomb(pos)) {
            gameOver = true;
            return true;
        }

        if (game.closeBombs(pos) == 0) {
            nearby(pos);
        }
        return false;
    }

    @Override
    public void toggleFlag(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        if (!game.getSelections().contains(pos)) {
            if (game.isAFlag(pos)) {
                game.removeFlag(pos);
            } else {
                game.addFlag(pos);
            }
        }
    }

    @Override
    public String getCellDisplayValue(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        if (game.isAFlag(pos)) return "F";
        if (game.getSelections().contains(pos)) {
            return game.isABomb(pos) ? "*" : bombsNearbyText(pos);
        }
        return "";
    }

    @Override
    public boolean isCellEnabled(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        return !game.getSelections().contains(pos) || game.isAFlag(pos);
    }

    @Override
    public boolean isVictory() {
        return game.winGame();
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isBomb(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        return game.isABomb(pos);
    }

    /**
     * Returns a string representing the number of adjacent bombs.
     *
     * @param pos the position of the cell
     * @return a string with the count of adjacent bombs or an empty string if none
     */
    private String bombsNearbyText(Pair<Integer, Integer> pos) {
        int count = game.closeBombs(pos);
        return count == 0 ? "" : String.valueOf(count);
    }

    /**
     * Recursively explores adjacent cells if there are no bombs nearby.
     *
     * @param pos the starting cell position for exploration
     */
    private void exploreNoBombs(Pair<Integer, Integer> pos) {
        if (!isValidExploration(pos)) return;
        game.addSelections(pos);
        if (game.closeBombs(pos) > 0) return;
        nearby(pos);
    }

    private void nearby(Pair<Integer, Integer> pos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    exploreNoBombs(new Pair<>(pos.getX() + i, pos.getY() + j));
                }
            }
        }
    }

    /**
     * Checks if the specified cell position is valid for exploration.
     *
     * @param pos the cell position
     * @return true if the cell can be explored, false otherwise
     */
    private boolean isValidExploration(Pair<Integer, Integer> pos) {
        return game.inBoard(pos.getX(), pos.getY()) &&
                !game.getSelections().contains(pos) &&
                !game.isAFlag(pos) &&
                !game.isABomb(pos);
    }
}
