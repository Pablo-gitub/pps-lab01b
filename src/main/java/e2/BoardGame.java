package e2;

import java.util.Random;

public class BoardGame {
    private final Pawn pawn;
    private final Knight knight;
    private final Random random = new Random();
    private final int size;

    public BoardGame(int size) {
        this.size = size;
        this.pawn = new Pawn(this.randomEmptyPosition());
        this.knight = new Knight(this.randomEmptyPosition(), this.size);
    }

    private final Pair<Integer,Integer> randomEmptyPosition(){
        Pair<Integer,Integer> pos = new Pair<>(this.random.nextInt(size),this.random.nextInt(size));
        // Check both pawn and knight to prevent any conflict
        return (this.pawn!=null && this.pawn.getPosition().equals(pos)) || (this.knight!=null && this.knight.getPosition().equals(pos))  ? randomEmptyPosition() : pos;
    }

    public boolean inBoardMove(Pair<Integer, Integer> pos) {
        int row = pos.getX();
        int col = pos.getY();
        return row >= 0 && col >= 0 && row < this.size && col < this.size;
    }

    public Knight getKnight() {
        return knight;
    }

    public Pawn getPawn() {
        return pawn;
    }

    boolean endGame(){
        return this.pawn.getPosition().equals(this.knight.getPosition());
    }

}
