package e2;

public interface ChessPiece {

    public boolean validMove(Pair<Integer,Integer> pos);

    public Pair<Integer,Integer> getPosition();

    public void setNewPosition(Pair<Integer,Integer> pos);

}
