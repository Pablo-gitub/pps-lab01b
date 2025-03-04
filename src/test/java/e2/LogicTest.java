package e2;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

  private LogicsImpl logics;
  private int size;

  @BeforeEach
  public void init(){
    this.size = 9;
    this.logics = new LogicsImpl(this.size);
  }

  @Test
  public void testFindPawn(){
      assertNotNull(this.logics.getPawnPosition());
  }

  @Test
  public void testFindKnight(){
      assertNotNull(this.logics.getKnightPosition());
  }

  private List<Pair<Integer, Integer>> invalidMoves(Pair<Integer, Integer> knight) {
    int knightRow = knight.getX();
    int knightCol = knight.getY();
    List<Pair<Integer, Integer>> invalidMoves = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int x = Math.abs(i - knightRow);
        int y = Math.abs(j - knightCol);
        if (!(x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3)) {
          invalidMoves.add(new Pair<>(i, j));
        }
      }
    }
    return invalidMoves;
  }

  private List<Pair<Integer, Integer>> validMoves(Pair<Integer, Integer> knight) {
    int knightRow = knight.getX();
    int knightCol = knight.getY();
    List<Pair<Integer, Integer>> validMoves = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int x = Math.abs(i - knightRow);
        int y = Math.abs(j - knightCol);
        if (x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3) {
          validMoves.add(new Pair<>(i, j));
        }
      }
    }
    return validMoves;
  }

  @Test
  public void testKnightCannotMoveToInvalidPosition() {
    Pair<Integer, Integer> knight = this.logics.getKnightPosition();
    List<Pair<Integer, Integer>> invalidMoves = this.invalidMoves(knight);
    for(Pair<Integer, Integer> invalidMove : invalidMoves){
      this.logics.hit(invalidMove.getX(), invalidMove.getY());
    }
    assertEquals(this.logics.getKnightPosition(), knight);
  }

  @Test
  public void testKnightCanMoveToValidPositions() {
    Pair<Integer, Integer> initialPosition = this.logics.getKnightPosition();
    List<Pair<Integer, Integer>> validMoves = this.validMoves(initialPosition);

    for (Pair<Integer, Integer> validMove : validMoves) {
      this.logics.hit(validMove.getX(), validMove.getY());
      assertEquals(validMove, this.logics.getKnightPosition());
      this.logics.hit(initialPosition.getX(), initialPosition.getY());
    }
  }

  @Test
  public void testEndGame(){
    List<Pair<Integer, Integer>> validMoves = this.validMoves(logics.getKnightPosition());
    boolean endGame;
    while(!validMoves.contains(logics.getPawnPosition())){
      Random random = new Random();
      Pair<Integer, Integer> validMove = validMoves.get(random.nextInt(validMoves.size()));
      endGame = logics.hit(validMove.getX(), validMove.getY());
      assertFalse(endGame);
      validMoves = this.validMoves(logics.getKnightPosition());
    }
    endGame = logics.hit(logics.getPawnPosition().getX(), logics.getPawnPosition().getY());
    assertTrue(endGame);
  }

}
