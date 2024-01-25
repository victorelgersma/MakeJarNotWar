package com.thg.accelerator23.connectn.ai.hafizvictor;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.hafizvictor.analysis.GameState;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class MakeJarNotWarConnectFour_DrunkenMaster2000 extends Player {
  private boolean started = false;
  private Map<Counter, Integer> maxInARowByCounter;
  BoardAnalyser boardAnalyser;

  public MakeJarNotWarConnectFour_DrunkenMaster2000(Counter counter) {
    super(counter, "Make Jar--Not War -Hafiz and Victor");
  }

  @Override
  public int makeMove(Board board) {
    int width = board.getConfig().getWidth();

    // check if it is the first move
    if (!started) {

      boardAnalyser = new BoardAnalyser(board.getConfig());
      Counter[][] counters = board.getCounterPlacements();
      boolean isEmptyBoard = Arrays.stream(counters)
              .flatMap(Arrays::stream)
              .allMatch(Objects::isNull);

      // if the board is empty, play first move
      if (isEmptyBoard) {
        started = true; // avoid running this code every more
        return (width / 2) - 1;
      }
    }

    Counter ourCounter = getCounter();
    Counter opponentCounter = getCounter() == X ? O : X;

    GameState gameState = boardAnalyser.calculateGameState(board);
    maxInARowByCounter = gameState.getMaxInARowByCounter();

    int winningMove = findMoveThatWinsTheGameFor(ourCounter, board);
    if (winningMove != -1) {
      return (winningMove);
    } else {
      int moveWhichPreventsOpponentWinning = findMoveThatWinsTheGameFor(opponentCounter, board);
      if (moveWhichPreventsOpponentWinning != -1){
        return moveWhichPreventsOpponentWinning;
      } else {
        int moveThatBlocksOpponentFromIncreasingLongestStreak = findIncrementingMove(opponentCounter, board);
        if (moveThatBlocksOpponentFromIncreasingLongestStreak !=-1) {
          return moveThatBlocksOpponentFromIncreasingLongestStreak;
        } else {
          int moveThatIncreasesOurLongestStreak = findIncrementingMove(ourCounter, board);
          if (moveThatIncreasesOurLongestStreak !=-1) {
            return moveThatIncreasesOurLongestStreak;
          } else {
            return getRandomNumber(0, width);
          }
        }
      }
    }
  }

  private int findIncrementingMove(Counter counter, Board board) {
    int width = board.getConfig().getWidth();
    for (int col = 0; col < width; col++) {
      if (thisMoveIncrementsLongestStreakFor(counter, col, board)) {
        return col;
      }
    }
    return -1;
  }


  // checks whether move will immediately win the game for our Counter
  private int findMoveThatWinsTheGameFor(Counter counter, Board board) {
    int width = board.getConfig().getWidth();
    for (int col = 0; col < width; col++ ) {
      if (isWinningMove(counter, col, board)) {
        return col;
      }
    }
    return -1;
  }

  private boolean isWinningMove(Counter counter, int col, Board board) {
    try {
      Board newBoard = new Board(board, col, counter);
      GameState gameState = boardAnalyser.calculateGameState(newBoard);
      Counter winner = gameState.getWinner();
      return winner == counter;
    } catch (InvalidMoveException e) {
      return false;
    }
  }

  private boolean thisMoveIncrementsLongestStreakFor(Counter counter, int col, Board board) {
    try {
      Board newBoard = new Board(board, col, counter);
      GameState gameState = boardAnalyser.calculateGameState(newBoard);
      if (gameState.getMaxInARowByCounter().get(counter) > maxInARowByCounter.get(counter)) {
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
