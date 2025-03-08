package e3b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

public class CellTest {

    @Test
    void testInitialStateIsEnabled() {
        Pair<Integer, Integer> pos = new Pair<>(3, 3);
        Cell cell = new Cell(pos);
        boolean result = cell.isEnabled() && !cell.isDisabled() && !cell.isFlag();
        assertTrue(result);
    }

    @Test
    void testApplyFlagChangesState() {
        Pair<Integer, Integer> pos = new Pair<>(3, 3);
        Cell cell = new Cell(pos);
        cell.applyFlag();
        boolean result = cell.isFlag() && !cell.isEnabled() && !cell.isDisabled();
        assertTrue(result);
    }

    @Test
    void testDisableChangesState() {
        Pair<Integer, Integer> pos = new Pair<>(3, 3);
        Cell cell = new Cell(pos);
        cell.disable();
        boolean result = cell.isDisabled() && !cell.isEnabled() && !cell.isFlag();
        assertTrue(result);
    }

    @Test
    void testEnableCellRestoresState() {
        Pair<Integer, Integer> pos = new Pair<>(3, 3);
        Cell cell = new Cell(pos);
        cell.applyFlag();
        cell.enableCell();
        cell.disable();
        cell.enableCell();
        boolean result = cell.isEnabled() && !cell.isDisabled() && !cell.isFlag();
        assertTrue(result);
    }

    @Test
    void testNumberNearbyBombs() {
        Pair<Integer, Integer> pos = new Pair<>(5, 5);
        Cell cell = new Cell(pos);
        Set<Pair<Integer, Integer>> bombs = new HashSet<>();
        bombs.add(new Pair<>(4, 4));
        bombs.add(new Pair<>(5, 4));
        bombs.add(new Pair<>(6, 6));
        int count = cell.numberNearbyBombs(bombs);
        assertEquals(3, count);
    }

    @Test
    void testNumberNearbyBombsEdgeCase() {
        Pair<Integer, Integer> pos = new Pair<>(0, 0);
        Cell cell = new Cell(pos);
        Set<Pair<Integer, Integer>> bombs = new HashSet<>();
        bombs.add(new Pair<>(0, 1));
        bombs.add(new Pair<>(1, 0));
        bombs.add(new Pair<>(1, 1));
        int count = cell.numberNearbyBombs(bombs);
        assertEquals(3, count);
    }

    @Test
    void testNumberNearbyBombsNoBombs() {
        Pair<Integer, Integer> pos = new Pair<>(2, 2);
        Cell cell = new Cell(pos);
        Set<Pair<Integer, Integer>> bombs = new HashSet<>();
        int count = cell.numberNearbyBombs(bombs);
        assertEquals(0, count);
    }
}
