package e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
    @Test
    public void testPawnGetPosition() {
        Pawn pawn = new Pawn(new Pair<>(4, 4)); // Mossa diagonale non valida per il pedone
        assertEquals(new Pair<>(4,4), pawn.getPosition());
    }

    @Test
    public void testPawnMove() {//pawn can't move
        Pawn pawn = new Pawn(new Pair<>(0, 0));
        pawn.setNewPosition(new Pair<>(4, 5));
        assertEquals(new Pair<>(0,0),pawn.getPosition());
    }
}
