package e2;

public class Knight implements ChessPiece{
    private Pair<Integer,Integer> position;
    private final int size;

    public Knight(Pair<Integer, Integer> pos, int size){
        setPosition(pos);
        this.size = size;
    }

    @Override
    public boolean validMove(Pair<Integer, Integer> pos) {
        int row = pos.getX();
        int col = pos.getY();
        if (row<0 || col<0 || row >= this.size || col >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        // Below a compact way to express allowed moves for the knight
        int x = row-this.position.getX();
        int y = col-this.position.getY();
        return x != 0 && y != 0 && Math.abs(x) + Math.abs(y) == 3;
    }

    @Override
    public void setNewPosition(Pair<Integer, Integer> pos) {
        if (validMove(pos)) {
            this.position = pos;
        }
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }
}
