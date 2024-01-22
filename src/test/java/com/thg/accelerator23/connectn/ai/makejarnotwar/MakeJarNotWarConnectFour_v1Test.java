package com.thg.accelerator23.connectn.ai.makejarnotwar;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MakeJarNotWarConnectFour_v1Test {
    private Board testBoard = new Board(new GameConfig(10,8,4));
    private MakeJarNotWarConnectFour_v1 ai = new MakeJarNotWarConnectFour_v1(Counter.O);

    @Test
    void choosesRandomInt() {
        int chosenInt = ai.makeMove(testBoard);
        assertTrue(chosenInt >= 0 && chosenInt <= 9);
    }

}