package e3b;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardGameTest {
    private BoardGame board;
    private final int size = 10;

    @BeforeEach
    void setUp() {
        board = new BoardGame(size);
    }

    @Test
    void testBoardInitialization() {
        int bombCount = board.getBombs().size();
        // Due to the switch-case fall-through in setBombs, default difficulty results in hard (20% bombs)
        boolean result = bombCount == (int)(size * size * 10/ 100f);
        assertTrue(result);
    }

    @Test
    void testGetCellNotNull() {
        Cell cell = board.getCell(new Pair<>(5, 5));
        boolean result = cell != null;
        assertTrue(result);
    }

    @Test
    void testIsABombForBombPositions() {
        boolean result = board.getBombs().stream().allMatch(pos -> board.isABomb(pos));
        assertTrue(result);
    }

    @Test
    void testNearbyBombsToPosRange() {
        int nearby = board.nearbyBombsToPos(new Pair<>(5, 5));
        boolean result = nearby >= 0 && nearby <= 9;
        assertTrue(result);
    }

    @Test
    void testVictoryCondition() {
        for (Cell cell : board.cells) {
            if (!board.isABomb(cell.getCellPosition())) {
                board.disableCell(cell.getCellPosition());
            }
        }
        boolean result = board.isVictory();
        assertTrue(result);
    }
}
