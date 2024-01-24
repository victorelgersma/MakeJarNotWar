package com.thg.accelerator23.connectn.ai.hafizvictor;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.GameState;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class MakeJarNotWarConnectFour_v1 extends Player {

  public MakeJarNotWarConnectFour_v1(Counter counter) {
    super(counter, "Make Jar--Not War -Hafiz and Victor");
  }

  @Override
  public int makeMove(Board board) {
    BoardAnalyser boardAnalyser = new BoardAnalyser(board.getConfig());

    int width = board.getConfig().getWidth();

    Counter ourCounter = getCounter();
    Counter opponentCounter = getCounter() == X ? O : X;

    int winningMove = findWinningMove(ourCounter, board, boardAnalyser);

    if (winningMove != -1) {
      return (winningMove);
    } else {
      int moveWhichBlocksOpponent = findWinningMove(opponentCounter, board, boardAnalyser);
      if (moveWhichBlocksOpponent != -1){
        return moveWhichBlocksOpponent;
      } else {
        return getRandomNumber(0, width);
      }
    }
  }

  // checks whether move will immediately win the game for our Counter
  private int findWinningMove(Counter counter, Board board, BoardAnalyser boardAnalyser) {
    for (int col = 0; col < board.getConfig().getWidth(); col++ ) {
      if (isWinningMove(counter, col, board, boardAnalyser)) {
        return col;
      }
    }
    return -1;
  }

  private boolean isWinningMove(Counter counter, int col, Board board, BoardAnalyser boardAnalyser) {
    try {
      Board newBoard = new Board(board, col, counter);
      GameState gameState = boardAnalyser.calculateGameState(newBoard);
      Counter winner = gameState.getWinner();
      return winner == counter;
    } catch (InvalidMoveException e) {
      return false;
    }
  }

  private int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
