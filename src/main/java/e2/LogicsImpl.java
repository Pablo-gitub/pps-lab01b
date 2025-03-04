package e2;

public class LogicsImpl implements Logics {

	private final BoardGame game;
	 
    public LogicsImpl(int size){
    	game = new BoardGame(size);
    }
    
	@Override
	public boolean hit(int row, int col) {
		Pair<Integer,Integer> pos = new Pair<>(row,col);
		if(this.game.inBoardMove(pos)){
			this.game.getKnight().setNewPosition(pos);
		}
		return this.game.endGame();
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return this.game.getKnight().getPosition().equals(new Pair<>(row,col));
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return this.game.getPawn().getPosition().equals(new Pair<>(row,col));
	}

	public Pair<Integer,Integer> getPawnPosition() {
		return this.game.getPawn().getPosition();
	}

	public Pair<Integer,Integer> getKnightPosition() {
		return this.game.getKnight().getPosition();
	}
}
