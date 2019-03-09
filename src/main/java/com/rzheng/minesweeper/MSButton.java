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


public class MSButton extends JButton
{    
	private static final long serialVersionUID = -4220360262697732331L;
	private boolean isMines = false;
    private boolean isFlag = false;
    private boolean isEmpty = false;
    private boolean isNumber = false;
    private boolean isActive = true;
    private int faceValue;
    private MineSweeper ms;
    
    private int value;
    private static int count = 0;
    public MSButton(String text, MineSweeper ms)
    {
        super(text);
        this.ms = ms;        
        this.value = count;
        count++;
    }
    
    public static void resetCount(){count = 0;}
        
    public void setEnabled(boolean b)
    {
        super.setEnabled(b);
        this.isActive = b;
    }
    public boolean getActive(){return this.isActive;}
    
    public int getFaceValue(){return this.faceValue;}
    public void setFaceValue(int i){this.faceValue = i;}
    
    public boolean isNumber(){return this.isNumber;}
    public void setIsNumber(boolean b){this.isNumber = b;}
    
    public boolean isEmpty(){return this.isEmpty;}
    public void setIsEmpty(boolean b){this.isEmpty = b;}
    
    public MineSweeper getBoard(){return this.ms;} 
    public int getValue(){return this.value;}
    
    public boolean isMines(){return this.isMines;}
    public void setIsMines(boolean b){this.isMines = b;}
    
    public boolean isFlag(){return this.isFlag;}
    public void setIsFlag(boolean b){this.isFlag = b;}        
}
