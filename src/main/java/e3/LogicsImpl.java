package e3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicsImpl implements Logics {

    private final BoardGame game;
    private final List<Pair<Integer,Integer>> disableElement;
    private Pair<Integer,Integer> currentSelection;


    public BoardGame getGame() {
        return this.game;
    }

    public List<Pair<Integer,Integer>> getDisableElement() {
        return this.disableElement;
    }

    public LogicsImpl(int size) {
        this.game = new BoardGame(size);
        this.disableElement = new ArrayList<Pair<Integer,Integer>>();
    }

    public boolean hit(int row, int col) {
        this.currentSelection = new Pair<>(row,col);
        this.disableElement.removeAll(this.disableElement);
        this.disableElement.add(this.currentSelection);
        if (game.isABomb(currentSelection)) {
            return true;
        }
        if (this.game.closeBombs(this.currentSelection)==0){
            exploreNoBombs(currentSelection);
        }
        if (!game.isABomb(currentSelection) && this.game.closeBombs(this.currentSelection)!=0){
            this.game.addSelections(this.currentSelection);
        }
        return game.winGame();
    }

    public void rightClick(int row, int col) {
        Pair<Integer,Integer> element = new Pair<>(row,col);
        if(!game.getSelections().contains(element)) {
            if (game.isAFlag(element)) {
                game.removeFlag(element);
            } else {
                game.addFlag(element);
            }
        }
    }

    private void exploreNoBombs(Pair<Integer, Integer> element) {
        int nearbyBombs = game.closeBombs(element);
        if (!game.inBoard(element.getX(), element.getY()) ||
            this.game.getSelections().contains(element) ||
            game.isAFlag(element) ||
            game.isABomb(element)) {
            return;
        }

        game.addSelections(element);
        disableElement.add(element);

        if (nearbyBombs > 0) {
            return;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    Pair<Integer, Integer> adjacent = new Pair<>(element.getX() + i, element.getY() + j);
                    exploreNoBombs(adjacent);
                }
            }
        }
    }

    public boolean gameOver() {
        return game.isABomb(this.currentSelection);
    }

}
