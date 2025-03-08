package e3b;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BoardGame {
    enum Difficulty {EASY, MEDIUM, HARD}
    private final Difficulty difficulty;
    final int size;
    Set<Pair<Integer,Integer>> bombs;
    Set<Cell> cells;

    BoardGame(int size){
        this.size = size;
        this.difficulty = Difficulty.EASY;
        bombs = new HashSet<>();
        cells = new HashSet<>();
        setBombs();
        setCells();
    }

    BoardGame(int size, Difficulty difficulty){
        this.size = size;
        this.difficulty = difficulty;
        setBombs();
        setCells();
    }

    private void setBombs() {
        int percentage;
        Random random = new Random();
        switch (difficulty) {
            case EASY -> percentage = 10;
            case MEDIUM -> percentage = 15;
            case HARD -> percentage = 20;
            default -> throw new IllegalStateException("Unexpected difficulty: " + difficulty);
        }
        int numberBombs = Math.round((size * size * percentage) / 100.0f);
        while(this.bombs.size()<numberBombs){
            Pair<Integer,Integer> bomb = new Pair<>(random.nextInt(this.size), random.nextInt(this.size));
            this.bombs.add(bomb);
        }
    }

    private void setCells() {
        for ( int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                Cell cell = new Cell(new Pair<>(i,j));
                this.cells.add(cell);
            }
        }
    }

    public boolean isABomb(Pair<Integer,Integer> pos){
        return this.bombs.contains(pos);
    }

    public Cell getCell(Pair<Integer,Integer> pos){
        return this.cells.stream()
                .filter(cell -> cell.getCellPosition().equals(pos))
                .findFirst()
                .orElse(null);
    }

    public void applyFlag(Pair<Integer,Integer> pos){
        getCell(pos).applyFlag();
    }

    public void disableCell(Pair<Integer,Integer> pos){
        getCell(pos).disable();
    }

    public void disableFlag(Pair<Integer,Integer> pos){
        getCell(pos).enableCell();
    }

    public Set<Pair<Integer,Integer>> getBombs(){
        return this.bombs;
    }

    public int nearbyBombsToPos(Pair<Integer,Integer> pos){
        return getCell(pos).numberNearbyBombs(this.getBombs());
    }

    public boolean isVictory(){
        long disabledCells = cells.stream().filter(Cell::isDisabled).count();
        return disabledCells == (this.size * this.size - this.bombs.size());
    }

}
