package e3;

import java.util.List;
import java.util.Map;

public interface Logics {

    public boolean hit(int row, int col);

    public BoardGame getGame();

    void rightClick(int row, int col);

    public List<Pair<Integer,Integer>> getDisableElement();
}
