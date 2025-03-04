package e2;

public class Pawn implements ChessPiece {
    private Pair<Integer,Integer> position;

    public Pawn(Pair<Integer,Integer> pos) {
        this.position = pos;
    }

    @Override
    public boolean validMove(Pair<Integer, Integer> pos) {
        return false;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    @Override
    public void setNewPosition(Pair<Integer, Integer> pos) {
        if(validMove(pos)) {
            this.position = pos;
        }
    }
}
