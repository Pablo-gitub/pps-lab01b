package e3b;

import java.util.Set;

public class Cell {
    enum StateCell{ENABLE , DISABLE, FLAG}
    final Pair<Integer, Integer> cellPosition;
    StateCell stateCell;

    Cell(Pair<Integer, Integer> cellPosition){
        stateCell = StateCell.ENABLE;
        this.cellPosition = cellPosition;
    }

    void enableCell(){
        stateCell = StateCell.ENABLE;
    }

    void applyFlag(){
        this.stateCell = StateCell.FLAG;
    }

    void disable(){
        this.stateCell = StateCell.DISABLE;
    }

    public Pair<Integer, Integer> getCellPosition() {
        return this.cellPosition;
    }

    int numberNearbyBombs(Set<Pair<Integer,Integer>> bombs){
        int count = 0;
        Pair<Integer, Integer> temp;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                temp = new Pair<>(this.cellPosition.getX()+i,this.cellPosition.getY()+j);
                if(bombs.contains(temp)){
                    count++;
                }
            }
        }
        return count;
    }

    boolean isFlag(){
        return this.stateCell == StateCell.FLAG;
    }

    boolean isEnabled(){
        return this.stateCell == StateCell.ENABLE;
    }

    boolean isDisabled(){
        return this.stateCell == StateCell.DISABLE;
    }
}
