package com.thg.accelerator23.connectn.ai.makejarnotwar;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.makejarnotwar.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.makejarnotwar.analysis.GameState;

public class MakeJarNotWarConnectFour_v1 extends Player {

  public MakeJarNotWarConnectFour_v1(Counter counter) {
    super(counter, "Make Jar--Not War -Hafiz and Victor");
  }

  @Override
  public int makeMove(Board board) {
    BoardAnalyser boardAnalyser = new BoardAnalyser(board.getConfig());
    int width = board.getConfig().getWidth();
    int winningMove = findWinningMove(board, boardAnalyser);
    if (winningMove != -1) {
      return (winningMove);
    } else {
      return getRandomNumber(0, width);
    }
  }

  // checks whether move will immediately win the game for our Counter
  private int findWinningMove(Board board, BoardAnalyser boardAnalyser) {
    for (int col = 0; col < board.getConfig().getWidth(); col++ ) {
      if (isWinningMove(board, col, boardAnalyser)) {
        return col;
      }
    }
    return -1;
  }

  private boolean isWinningMove(Board board, int col, BoardAnalyser boardAnalyser) {
    try {
      Board newBoard = new Board(board, col, getCounter());
      GameState gameState = boardAnalyser.calculateGameState(newBoard);
      Counter winner = gameState.getWinner();
      Counter ourCounter = getCounter();
      if (winner == ourCounter) {
        return true;
      } else {
        return false;
      }
    } catch (InvalidMoveException e) {
      return false;
    }
  }

  private int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
