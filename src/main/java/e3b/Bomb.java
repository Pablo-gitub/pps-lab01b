package e3b;

public class Bomb {
    private boolean isSelected;
    private final Pair<Integer, Integer> pos;

    Bomb(Pair<Integer,Integer> pos){
        this.pos = pos;
        this.isSelected = false;
    }

    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void tap(boolean selected) {
        this.isSelected = !this.isSelected();
    }
}
