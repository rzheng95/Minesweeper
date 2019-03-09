/*
 * This file is part of MineSweeper
 * Copyright (C) 2015-2019 Richard R. Zheng
 *
 * https://github.com/rzheng95/MineSweeper
 * 
 * All Right Reserved.
 */

package com.rzheng.minesweeper;

import javax.swing.*;
import java.awt.event.*;

public class MSBoard
{
    private ActionListener newGame = new ActionListener()
    {   
        public void actionPerformed(ActionEvent e){    
            MSButton.resetCount();
            MineSweeper mss = ms;            
            menu();
            mss.getFrame().setVisible(false);
            mss.getFrame().dispose();
        }
    };
    
    private ActionListener restart = new ActionListener()
    {   
        public void actionPerformed(ActionEvent e){    
            MSButton.resetCount();
            MineSweeper mss = ms;            
            ms = new MineSweeper(mss.getLen(), mss.getWid(), mss.getRow(), mss.getCol(), mss.getMines(), 
            mss.getP1Len(), mss.getP2Len(), newGame, restart);
            
            mss.getFrame().setVisible(false);
            mss.getFrame().dispose();
        }
    };
    
    private final int[] p1Len = {300, 460, 800};
    private final int[] p2Len = {300, 460, 465};
    private final int[] LENGTH = {306, 466, 806};
    private final int[] WIDTH = {400, 560, 565};
    private final int[] MINES = {10, 40, 99};
    private final int[] ROW = {9, 16, 16};
    private final int[] COL = {9, 16, 30};
    private final Object[] POS = {"Beginner - 9x9, 10 mines", "InterMediate - 16x16, 40 mines", "Advanced 16x30, 99 mines"};
    
    private MineSweeper ms;
    public MSBoard()
    {
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch(Exception e){}
    }
    
    public void menu()
    {        
        String choice = (String)JOptionPane.showInputDialog(null, "Difficulty", MineSweeper.GAMENAME, JOptionPane.PLAIN_MESSAGE, 
        null, // logo
        POS, POS[0]);
        
        if(choice != null)
        {
            if(choice.equals(POS[0]))            
                ms = new MineSweeper(LENGTH[0], WIDTH[0], ROW[0], COL[0], MINES[0], p1Len[0], p2Len[0], newGame, restart);                        
            else if(choice.equals(POS[1]))           
                ms = new MineSweeper(LENGTH[1], WIDTH[1], ROW[1], COL[1], MINES[1], p1Len[1], p2Len[1],  newGame, restart);                           
            else           
                ms = new MineSweeper(LENGTH[2], WIDTH[2], ROW[2], COL[2], MINES[2], p1Len[2], p2Len[2], newGame, restart);                         
        }
    }        
}
