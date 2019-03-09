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

public class MSMouseListener implements MouseListener
{    
    private boolean firstClick;
    private boolean game;
    
    public  MSMouseListener()
    {
        firstClick = true;
    }   
    
    public void mouseClicked(MouseEvent e)
    {
       /* MSButton b = ((MSButton)e.getSource());
        MineSweeper ms = b.getBoard();        
        if(SwingUtilities.isRightMouseButton(e) && game && b.isEnabled())
        {
            if(firstClick)     
            {
                ms.checkFirstClick(b);
                game = true;
            }
            firstClick = false;
            if(b.isFlag())
            {
                b.setIcon(null);
                b.setIsFlag(false);
                ms.incrementFlagCount(1);
            }
            else
            {
                b.setIsFlag(true);
                b.setIcon(new ImageIcon("Flag.png"));
                ms.incrementFlagCount(-1);
            }
        }
        if(ms.checkWin() && game)
        {
            ms.stopTime();
            ms.gameOver("Congrats! You Win!", 1); 
            game = false;
        }    */        
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    
    public void mousePressed(MouseEvent e)
    {
        MSButton b = ((MSButton)e.getSource());
        MineSweeper ms = b.getBoard();
        if(b.getActive())
        {                        
            if(SwingUtilities.isLeftMouseButton(e))
            {     
                /*if (e.getClickCount() == 2 && b.isNumber() && !b.isEnabled()) {
                    System.out.println("Doubld clicked");
                    //handle double click event.
                    return;
                }*/
                
                if(!b.isFlag())
                {
                    if(firstClick)
                    {
                        game = true;
                        ms.checkFirstClick(b);  
                        ms.startTime();
                    }
                    firstClick = false;                                     
                        
                    if(b.isEmpty())
                    {
                        ms.expandEmptySpace(b);
                        b.setEnabled(false);
                    }
                            
                    if(b.isNumber())
                    {                   
                        b.setText(""+b.getFaceValue());
                        b.setEnabled(false);
                    }
                       
                    if(b.isMines()) 
                    {
                        ms.stopTime();
                        ms.showMines();
                        ms.gameOver("Sorry, You Lost!", 0);   
                        game = false;
                        return;
                    }
                }                
            }      
            else if(SwingUtilities.isRightMouseButton(e) && game && b.isEnabled())
            {
                if(firstClick)     
                {
                    ms.checkFirstClick(b);
                    game = true;
                }
                firstClick = false;
                if(b.isFlag())
                {
                    b.setIcon(null);
                    b.setIsFlag(false);
                    ms.incrementFlagCount(1);
                }
                else
                {
                    b.setIsFlag(true);
                    b.setIcon(new ImageIcon(this.getClass().getResource("/flag.png")));
                    ms.incrementFlagCount(-1);
                }
            }
            if(ms.checkWin() && game)
            {
                ms.stopTime();
                ms.gameOver("Congrats! You Win!", 1); 
                game = false;
            }            
        }
    }
    
    public void mouseReleased(MouseEvent e){}
}
