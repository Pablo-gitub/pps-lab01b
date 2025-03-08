package e3b;

public interface Logics {

    public void leftClick(Pair<Integer,Integer>pos);

    public void rightClick(Pair<Integer,Integer>pos);

    public String stringCell(int row,int col);

    public boolean cellState(int row,int col);

    public boolean isGameOver();

    public boolean isVictory();
}
