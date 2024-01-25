package com.thg.accelerator23.connectn.ai.hafizvictor;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.GameState;

import java.util.Arrays;
import java.util.Objects;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class MakeJarNotWarConnectFour_DrunkenMaster2000 extends Player {
  private boolean started = false;

  public MakeJarNotWarConnectFour_DrunkenMaster2000(Counter counter) {
    super(counter, "Make Jar--Not War -Hafiz and Victor");
  }

  @Override
  public int makeMove(Board board) {
    int width = board.getConfig().getWidth();

    BoardAnalyser boardAnalyser = new BoardAnalyser(board.getConfig());
    Counter[][] counters = board.getCounterPlacements();

    // check if it is the first move
    if (!started) {
      boolean isEmptyBoard = Arrays.stream(counters)
              .flatMap(Arrays::stream)
              .allMatch(Objects::isNull);

      // if the board is empty, play first move
      if (isEmptyBoard) {
        started = true; // avoid running this code a second time
        return (width / 2) - 1;
      }
    }

    Counter ourCounter = getCounter();
    Counter opponentCounter = getCounter() == X ? O : X;

    boardAnalyser.calculateGameState(board);


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
