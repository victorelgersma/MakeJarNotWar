package com.thg.accelerator23.connectn.ai.makejarnotwar;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import org.junit.jupiter.api.Test;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
import static org.junit.jupiter.api.Assertions.*;

class MakeJarNotWarConnectFour_v1Test {
    private Board testBoard5_by_4 = new Board(new GameConfig(3,3,3));
    private MakeJarNotWarConnectFour_v1 ai = new MakeJarNotWarConnectFour_v1(Counter.O);


    @Test
    void choosesRandomInt() {
        int chosenInt = ai.makeMove(testBoard5_by_4);
        assertTrue(chosenInt >= 0 && chosenInt <= 9);
    }

    @Test
    void takesAnEasyWin() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_v1 player = new MakeJarNotWarConnectFour_v1(O);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,    null, null, null, null};
        counters[3] = new Counter[] {null,    null, null, null, null};
        counters[2] = new Counter[] {null,    O,    null, null, null};
        counters[1] = new Counter[] {null,    O,    null,    X, null};
        counters[0] = new Counter[] {null,    X,    O,       X, null};
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,3));
        int chosenMove = player.makeMove(board);

        assertEquals(1, chosenMove);
    }

    // utility method in order to convert human readable Counter to an acceptable counter for the GameConfig class
    private Counter[][] rotateBoard(Counter[][] board) {
        Counter[][] newBoard = new Counter[board[0].length][board.length];
        for (int i = 0; i < board[0].length; i++) {
            for (int j = board.length - 1; j >= 0; j--) {
                newBoard[i][j] = board[j][i];
            }
        }
        return newBoard;
    }
}