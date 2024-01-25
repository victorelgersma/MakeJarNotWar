package com.thg.accelerator23.connectn.ai.hafizvictor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import org.junit.jupiter.api.Test;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
import static org.junit.jupiter.api.Assertions.*;

class MakeJarNotWarConnectFour_v1Test {
    private Board testboard_3_by_3 = new Board(new GameConfig(3,3,3));
    private MakeJarNotWarConnectFour_DrunkenMaster2000 ai = new MakeJarNotWarConnectFour_DrunkenMaster2000(Counter.O);

    @Test
    void choosesRandomInt() {
        int chosenInt = ai.makeMove(testboard_3_by_3);
        assertTrue(chosenInt >= 0 && chosenInt <= 2);
    }

    @Test
    void takesAnEasyWin() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(O);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,    null, null, null, null};
        counters[3] = new Counter[] {null,    null, null, null, null};
        counters[2] = new Counter[] {null,    O,    null, null, null};
        counters[1] = new Counter[] {null,    O,    null,    X, null};
        counters[0] = new Counter[] {null,    X,    O,       X, null};
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,3));

        // Act
        int chosenMove = player.makeMove(board);

        // Assert
        assertEquals(1, chosenMove);
    }

    @Test
    void takesAnEasyWin_Edge_Of_Board() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(X);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,    null, null, null, null};
        counters[3] = new Counter[] {null,    null, null, null, null};
        counters[2] = new Counter[] {null,    O,    null, null, null};
        counters[1] = new Counter[] {null,    O,    null, null, X};
        counters[0] = new Counter[] {null,    X,    O,    null, X};
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,3));
        // Act
        int chosenMove = player.makeMove(board);

        // Assert
        assertEquals(4, chosenMove);
    }

    @Test
    void takesAnEasyWin_ForDiagonalCase() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(X);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,    null, null, null, null};
        counters[3] = new Counter[] {null,    null, null, null, null};
        counters[2] = new Counter[] {null,    O,    null, null, null};
        counters[1] = new Counter[] {null,    O,    X,    O,    X   };
        counters[0] = new Counter[] {null,    X,    O,    X,    X   };
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,3));
        // Act
        int chosenMove = player.makeMove(board);

        // Assert
        assertEquals(3, chosenMove);
    }

    @Test
    void blocksOpponentFromWinningInEasyCase() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(O);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,    null, null, null, null};
        counters[3] = new Counter[] {null,    null, null, null, null};
        counters[2] = new Counter[] {null,    null, null, null, null};
        counters[1] = new Counter[] {null,    null, X,    null, X   };
        counters[0] = new Counter[] {null,    O,    X,    O,    O   };
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,3));

        // Act
        int chosenMove = player.makeMove(board);
        // Assert
        assertEquals(2, chosenMove);
    }


    // Not sure why this test is passing : I expect it to fail because there are no checks for available columns
    @Test
    void doesNotForfeitTheGameByPlayingInACompletedColumn() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(O);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {X,X,null,X,O};
        counters[3] = new Counter[] {O,X,O,O,X};
        counters[2] = new Counter[] {X,O,X,O,O};
        counters[1] = new Counter[] {O,X,O,X,X};
        counters[0] = new Counter[] {X,O,X,O,O};
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,4));

        // Act
        int chosenMove = player.makeMove(board);
        // Assert
        assertEquals(2, chosenMove);
    }

    @Test
    void playsInTheMiddleAtTheBeginningOfTheGame() {
        // Arrange
        int width = 5;
        int height = 5;
        MakeJarNotWarConnectFour_DrunkenMaster2000 player = new MakeJarNotWarConnectFour_DrunkenMaster2000(O);

        Counter[][] counters = new Counter[width][height];
        counters[4] = new Counter[] {null,null,null,null,null};
        counters[3] = new Counter[] {null,null,null,null,null};
        counters[2] = new Counter[] {null,null,null,null,null};
        counters[1] = new Counter[] {null,null,null,null,null};
        counters[0] = new Counter[] {null,null,null,null,null};
        counters = rotateBoard(counters);
        Board board = new Board(counters, new GameConfig(width,height,4));

        // Act
        int chosenMove = player.makeMove(board);
        // Assert
        assertEquals(3, chosenMove);
    }

    // utility method in order to convert human-readable Counter to an acceptable counter for the GameConfig class
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