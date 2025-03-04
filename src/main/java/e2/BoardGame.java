package e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGame {
    private final List<ChessPiece> pieces;
    private final Random random = new Random();
    private final int size;

    public BoardGame(int size) {
        this.size = size;
        this.pieces = new ArrayList<>();
        this.pieces.add(new Pawn(this.randomEmptyPosition()));
        this.pieces.add(new Knight(this.randomEmptyPosition(), this.size));
    }

    private Pair<Integer, Integer> randomEmptyPosition() {
        Pair<Integer, Integer> pos = new Pair<>(this.random.nextInt(size), this.random.nextInt(size));
        boolean isOccupied = pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(pos));
        return isOccupied ? randomEmptyPosition() : pos;
    }


    public boolean inBoardMove(Pair<Integer, Integer> pos) {
        int row = pos.getX();
        int col = pos.getY();
        return row >= 0 && col >= 0 && row < this.size && col < this.size;
    }

    public Knight getKnight() {
        return pieces.stream()
                .filter(piece -> piece instanceof Knight)
                .map(piece -> (Knight) piece)
                .findFirst()
                .orElse(null);
    }

    public Pawn getPawn() {
        return pieces.stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .findFirst()
                .orElse(null);
    }

    boolean endGame(){
        return this.getPawn().getPosition().equals(this.getKnight().getPosition());
    }

}
