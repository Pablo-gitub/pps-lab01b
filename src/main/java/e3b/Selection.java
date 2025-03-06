package e3b;

import e3b.Pair;

import java.util.Set;

public class Selection {
    private final Pair<Integer, Integer> pos;

    Selection(Pair<Integer, Integer> pos) {
        this.pos = pos;
    }

    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    public boolean hasNearbyBombs(Set<Pair<Integer,Integer>> bombs){
        for (Pair<Integer,Integer> bomb : bombs){
            if(isNearby(bomb, this.pos)){
                return true;
            }
        }
        return false;
    }

    private boolean isNearby(Pair<Integer, Integer> bomb, Pair<Integer, Integer> pos) {
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(pos.getX()+i == bomb.getX() && pos.getY()+j == bomb.getY()){
                    return true;
                }
            }
        }
        return false;
    }

    public int countNearbyBombs(Set<Pair<Integer,Integer>> bombs){
        int count = 0;
        for (Pair<Integer,Integer> bomb : bombs){
            if(isNearby(bomb, this.pos)){
                count++;
            }
        }
        return count;
    }
}
