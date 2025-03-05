package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    Knight knight;

    @BeforeEach
    void init(){
        this.knight = new Knight(new Pair<>(4, 4), 8);
    }

    @Test
    public void testKnightValidMove() {
        Pair<Integer, Integer> validMove = new Pair<>(6, 5);
        assertTrue(knight.validMove(validMove));
    }

    @Test
    public void testKnightInvalidMove() {
        Pair<Integer, Integer> invalidMove = new Pair<>(5, 5);
        assertFalse(knight.validMove(invalidMove));
    }

    @Test
    public void testKnightOutOfBounds() {
        Pair<Integer, Integer> outOfBounds = new Pair<>(-1, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> knight.setNewPosition(outOfBounds));
    }

}
