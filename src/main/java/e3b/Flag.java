package e3b;

public class Flag {
    private final Pair<Integer, Integer> pos;

    Flag(Pair<Integer,Integer> pos){
        this.pos = pos;
    }

    public Pair<Integer, Integer> getPos() {
        return this.pos;
    }
}
