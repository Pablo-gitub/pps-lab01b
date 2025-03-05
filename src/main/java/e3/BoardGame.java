package e3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGame {
    public enum Difficulty {
        EASY, MIDDLE, DIFFICULT
    }
    private final int size;
    private final List<Pair<Integer,Integer>> bombs;
    private final List<Pair<Integer,Integer>> selections;
    private final List<Pair<Integer,Integer>> flags;
    private final Difficulty difficulty;


    public BoardGame(int size) {
        this.size = size;
        difficulty = Difficulty.EASY;
        this.bombs = new ArrayList<>();
        this.selections = new ArrayList<>();
        this.flags = new ArrayList<>();
        setBomb();
    }

    private void setBomb() {
        int bombNumber = numberOfBomb();
        Random random = new Random();
        while (this.bombs.size() < bombNumber){
            Pair<Integer,Integer> newBomb = new Pair<>(random.nextInt(this.size), random.nextInt(this.size));
            if(!this.bombs.contains(newBomb)){
                this.bombs.add(newBomb);
            }
        }
    }

    public List<Pair<Integer, Integer>> getFlags() {
        return this.flags;
    }

    public List<Pair<Integer, Integer>> getBombs() {
        return this.bombs;
    }

    public List<Pair<Integer, Integer>> getSelections() {
        return this.selections;
    }

    private int numberOfBomb() {
        int percentage = switch (this.difficulty) {
            case EASY -> 10;
            case MIDDLE -> 15;
            case DIFFICULT -> 20;
            default -> throw new IllegalStateException("Unexpected value: " + difficulty);
        };
        return Math.round((this.size * this.size * percentage) / 100.0f);
    }

    public boolean isABomb(Pair<Integer,Integer> element){
        return this.bombs.contains(element);
    }

    public boolean isAFlag(Pair<Integer,Integer> element){
        return this.flags.contains(element);
    }

    public void addFlag(Pair<Integer,Integer> element){
        if(!this.selections.contains(element) && !this.flags.contains(element)){
            this.flags.add(element);
        }
    }

    public void removeFlag(Pair<Integer,Integer> element){
        this.flags.remove(element);
    }

    public boolean inBoard(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public int closeBombs(Pair<Integer, Integer> element){
        int closeBombs = 0;
        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){
                Pair<Integer,Integer> closeElement = new Pair<>(element.getX()+i, element.getY()+j );
                if(inBoard(closeElement.getX(),closeElement.getY()) && this.bombs.contains(closeElement)){
                    closeBombs++;
                }
            }
        }
        return closeBombs;
    }

    public void addSelections(Pair<Integer, Integer> element){
        if(!isABomb(element) && inBoard(element.getX(),element.getY()) && !this.selections.contains(element)){
            this.selections.add(element);
        }
    }

    public boolean winGame() {
        return this.selections.size() == (this.size * this.size - this.bombs.size());
    }
}
