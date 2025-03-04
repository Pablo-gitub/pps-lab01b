package e2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private final Pawn pawn;
	private final Knight knight;
	private final Random random = new Random();
	private final int size;
	 
    public LogicsImpl(int size){
    	this.size = size;
        this.pawn = new Pawn(this.randomEmptyPosition());
        this.knight = new Knight(this.randomEmptyPosition(), this.size);
    }
    
	private final Pair<Integer,Integer> randomEmptyPosition(){
    	Pair<Integer,Integer> pos = new Pair<>(this.random.nextInt(size),this.random.nextInt(size));
    	// the recursive call below prevents clash with an existing pawn
    	return this.pawn!=null && this.pawn.getPosition().equals(pos) ? randomEmptyPosition() : pos;
    }
    
	@Override
	public boolean hit(int row, int col) {
		Pair<Integer,Integer> pos = new Pair<>(row,col);
		this.knight.setNewPosition(pos);
		return this.pawn.getPosition().equals(this.knight.getPosition());
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return this.knight.getPosition().equals(new Pair<>(row,col));
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return this.pawn.getPosition().equals(new Pair<>(row,col));
	}

	public Pair<Integer,Integer> getPawnPosition() {
		return this.pawn.getPosition();
	}

	public Pair<Integer,Integer> getKnightPosition() {
		return this.knight.getPosition();
	}
}
