package e3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameTest {
    private BoardGame board;

    @BeforeEach
    void setUp() {
        board = new BoardGame(10); // Griglia 10x10
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(board);
    }

    @Test
    void testBombPlacement() {
        int expectedBombs = 10;
        List<Pair<Integer, Integer>> bombList = board.getBombs();
        assertEquals(expectedBombs, bombList.size());
    }

    @Test
    void testIsABomb() {
        for (Pair<Integer, Integer> bomb : board.getBombs()) {
            assertTrue(board.isABomb(bomb));
        }
    }

    @Test
    void testInBoard() {
        assertTrue(board.inBoard(0, 0));
        assertTrue(board.inBoard(9, 9));
        assertFalse(board.inBoard(-1, 5));
        assertFalse(board.inBoard(10, 10));
    }

    @Test
    void testCloseBombs() {
        Pair<Integer, Integer> testCell = new Pair<>(5, 5);
        int bombCount = board.closeBombs(testCell);
        assertTrue(bombCount >= 0 && bombCount <= 8);
    }

    @Test
    void testAddNoBombs() {
        Pair<Integer, Integer> safeCell = new Pair<>(0, 0);
        if (!board.isABomb(safeCell)) {
            board.addNoBombs(safeCell);
            assertTrue(board.getNoBombs().contains(safeCell));
        }
    }

    @Test
    void testWinCondition() {
        assertFalse(board.winGame());
    }

    @Test
    void testWinCondition2() {
        for(int i = 0 ; i < 10; i++) {
            for(int j = 0 ; j < 10; j++) {
                Pair<Integer,Integer> element = new Pair<>(i, j);
                this.board.addNoBombs(element);
            }
        }
        assertTrue(board.winGame());
    }
}
