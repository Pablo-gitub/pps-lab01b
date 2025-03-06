package e3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicsImplTest {
    private Logics logics;

    @BeforeEach
    void setUp() {
        int size = 10;
        logics = new LogicsImpl(size);
    }

    @Test
    void testInitialGameOver() {
        assertFalse(logics.isGameOver());
    }

    @Test
    void testAddFlag() {
        int row = 0, col = 0;
        logics.toggleFlag(row, col);
        String display = logics.getCellDisplayValue(row, col);
        assertEquals("F", display);
    }

    @Test
    void testDisableFlag() {
        int row = 0, col = 0;
        logics.toggleFlag(row, col);
        logics.toggleFlag(row, col);
        String display = logics.getCellDisplayValue(row, col);
        assertEquals("", display);
    }

    @Test
    void testIsCellEnabled() {
        int row = 0, col = 0;
        logics.selectCell(row, col);
        assertFalse(logics.isCellEnabled(row, col));
    }

    @Test
    void testVictoryCondition() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!logics.isBomb(i, j)) {
                    logics.selectCell(i, j);
                }
            }
        }
        assertTrue(logics.isVictory());
    }

    @Test
    void testGameOverCondition() {
        int i = 0;
        boolean isBomb = false;
        while (i < 10 && !logics.isGameOver()) {
            int j = 0;
            while (j < 10 && !logics.isGameOver()) {
                if (logics.isBomb(i, j)) {
                    logics.selectCell(i, j);
                }
                j++;
            }
            i++;
        }
        assertTrue(logics.isGameOver());
    }


}
