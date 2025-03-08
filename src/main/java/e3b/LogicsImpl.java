package e3b;

public class LogicsImpl implements Logics {
    private final int size;
    private final BoardGame game;
    private boolean gameOver;

    public LogicsImpl(int size) {
        this.size = size;
        this.game = new BoardGame(size);
        this.gameOver = false;
    }

    public void leftClick(Pair<Integer,Integer>pos){
        if(!game.getCell(pos).isDisabled()){
            game.disableCell(pos);
            if(game.isABomb(pos)){
                gameOver = true;
            } else if (game.nearbyBombsToPos(pos)==0){
                exploreNearby(pos);
            }
        }
    }

    /**
     * Recursively explores adjacent cells starting from the given position.
     * For each neighbor that is within the board, not disabled, not a bomb,
     * and not flagged, the cell is disabled. If the cell has zero nearby bombs,
     * its neighbors are further explored.
     *
     * @param pos the starting cell position for exploration
     */
    private void exploreNearby(Pair<Integer, Integer> pos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Pair<Integer, Integer> nearbyCell = new Pair<>(pos.getX() + i, pos.getY() + j);
                if (inBoard(nearbyCell)) {
                    Cell cell = game.getCell(nearbyCell);
                    if (cell != null &&
                        !cell.isDisabled() &&
                        !game.isABomb(nearbyCell) &&
                        !game.getCell(nearbyCell).isFlag()) {

                        game.disableCell(nearbyCell);

                        if (game.nearbyBombsToPos(nearbyCell) == 0) {
                            exploreNearby(nearbyCell);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks whether the specified cell position is within the board boundaries.
     *
     * @param nearbyCell the cell position to check
     * @return true if the position is within bounds, false otherwise
     */
    private boolean inBoard(Pair<Integer, Integer> nearbyCell) {
        return nearbyCell.getX()>=0 && nearbyCell.getX()<size && nearbyCell.getY()>=0 && nearbyCell.getY()<size;
    }

    public void rightClick(Pair<Integer,Integer>pos){
        if(game.getCell(pos).isFlag()){
            game.disableFlag(pos);
        } else if(game.getCell(pos).isEnabled()){
            game.applyFlag(pos);
        }
    }

    public String stringCell(int row,int col){
        Pair<Integer,Integer> pos = new Pair<>(row,col);
        if (game.getCell(pos).isFlag()){
            return "F";
        } else if (isGameOver() && game.isABomb(pos)){
            return "*";
        } else if (game.getCell(pos).isEnabled()){
            return "";
        } else {
            int nearlyBombs = game.nearbyBombsToPos(pos);
            return nearlyBombs != 0 ? String.valueOf(nearlyBombs) : "";
        }
    }

    public boolean cellState(int row,int col){
        Pair<Integer,Integer> pos = new Pair<>(row,col);
        return !game.getCell(pos).isDisabled();
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean isVictory(){
        return game.isVictory() && !this.gameOver;
    }

}
