package com.thg.accelerator23.connectn.ai.makejarnotwar;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class MakeJarNotWarConnectFour_v1 extends Player {
  public MakeJarNotWarConnectFour_v1(Counter counter) {
    super(counter, "Make Jar--Not War -Hafiz and Victor");
  }

  @Override
  public int makeMove(Board board) {
    int width = board.getConfig().getWidth();


    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

    return getRandomNumber(0,width);
  }

  private int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
