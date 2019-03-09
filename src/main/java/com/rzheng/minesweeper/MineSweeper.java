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
import java.awt.*;
import java.awt.event.*;
import java.net.*;

/**
 * Write a description of class MineSweeper here.
 * 
 * @author (Ruixiang Zheng) 
 * @version (5/25/2015)
 */

public class MineSweeper
{
    private int row, col, len, wid, mines, p1Len, p2Len, timeCount, mineLeft;
    private Timer time;    
    private JFrame frame;
    private JPanel panel, panel2;
    private MSButton[] buttons;
    private JButton timer, flagCount;
    private JMenuBar menubar;
    private JMenu game, help;
    private JMenuItem newGame, restart, exit, about, howToPlay;       
    private MSMouseListener listener;
    
    public final static String GAMENAME = "Minesweeper";
           
    public MineSweeper(int len, int wid, int row, int col, int mines, int p1Len, int p2Len,
    ActionListener newGameAction, ActionListener restartAction)
    {
        time = new Timer(1000, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(++timeCount < 999)
                    timer.setText("   "+timeCount);
                else
                    time.stop();
            }
        } );        
                
        this.row = row; this.col = col; this.len = len; this.wid = wid;
        this.mines = mines; this.p1Len = p1Len; this.p2Len = p2Len;
        frame = new JFrame(GAMENAME);   
        frame.setLayout(null);
        
            panel = new JPanel(new GridLayout(row, col));          
            panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); 
        
            panel2 = new JPanel(new GridLayout(1,2));
            timer = new JButton("   0");
            timer.setIcon(new ImageIcon(this.getClass().getResource("/timer.png")));
            timer.setEnabled(false);
            timer.setBackground(new Color(51,255,255));
            
            flagCount = new JButton("   "+mines);            
            flagCount.setIcon(new ImageIcon(this.getClass().getResource("/mines.png")));            
            flagCount.setEnabled(false);
            flagCount.setBackground(new Color(51,255,255));
            
            panel2.add(timer);
            panel2.add(flagCount);
                        
        buttons = new MSButton[row*col];
        listener = new MSMouseListener();    
        
        // MenuBar      
        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        // Game Menu
        game = new JMenu("Game(G)"); 
        
            restart = new JMenuItem("Restart", new ImageIcon(this.getClass().getResource("/restart.png")));
            restart.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            restart.addActionListener(restartAction);
        
            newGame = new JMenuItem("New Game", new ImageIcon(this.getClass().getResource("/newgame.png")));
            newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));            
            newGame.addActionListener(newGameAction);
            
            exit = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/exit.png")));
            exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                            
        exit.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if( 0 == JOptionPane.showConfirmDialog(null, "Do you really want to exit?", GAMENAME, JOptionPane.YES_NO_OPTION))
                        System.exit(0);
                    else return;
                }
            }
        );
        game.add(restart);
        game.add(newGame);
        game.add(exit);    
        
        // Help Menu        
        help = new JMenu("Help(H)");
        
            howToPlay = new JMenuItem("HowToPlay", new ImageIcon(this.getClass().getResource("/howtoplay.png")));
            howToPlay.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            
        howToPlay.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    try{
                    URI uri = new URI("http://www.minesweeper.info/wiki/Windows_Minesweeper");
                    openWebpage(uri);
                    }catch(Exception ex){}
                }
            }
        );                      
        
            about = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/about.png")));
            about.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            
        about.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog(null, "This game was made by Richard Zheng on May 26, 2015\n" +
                    "Email: ruixiangzheng95@gmail.com", GAMENAME, 1);
                }
            }
        );                                    
        help.add(howToPlay);
        help.add(about);
                
        menubar.add(game);                    
        menubar.add(help);   
        
        for(int i=0; i<buttons.length; i++)
        {
            buttons[i] = new MSButton("", this);
            buttons[i].setMargin(new Insets(0,0,0,0));
            buttons[i].addMouseListener(listener);           
            panel.add(buttons[i]);
        }
        
        ImageIcon img = new ImageIcon(this.getClass().getResource("/mines.png"));                
        frame.setIconImage(img.getImage());        
        
        // x(â†’), y(â†“), len(â†�â†’), wid(â†‘â†“)
        panel.setBounds(0, 0, p1Len, p2Len);        
        panel2.setBounds(0, p2Len, p1Len, 50);
        
        frame.add(panel);
        frame.add(panel2);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(len, wid);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    
    
    public void startTime()
    {
        time.start();
    }
    public void stopTime()
    {
        time.stop();
    }
    

    public void incrementFlagCount(int i)
    {        
        mineLeft += i;
        flagCount.setText("   "+mineLeft);
    }
    
    public int getP1Len(){return p1Len;}
    public int getP2Len(){return p2Len;}
    public int getLen(){return len;}
    public int getWid(){return wid;}
    public int getRow(){return row;}
    public int getCol(){return col;}
    public int getMines(){return mines;}
    public JFrame getFrame(){return frame;}
    
    public static void openWebpage(URI uri) 
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) 
    {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    public void checkFirstClick(MSButton b)
    {
        b.setIsEmpty(true);
        randomizeMines(b);
        assignNumbers();
        assignEmptySpace();
    } 
    
    public void randomizeMines(MSButton b)
    {
        int count = 0;
        while( count < mines)
        {
            int pos = (int)(Math.random()*buttons.length);
            if(!buttons[pos].isMines() && !buttons[pos].isEmpty() && prohibitedSpace(b, pos))
            {
                buttons[pos].setIsMines(true);                   
                count++;
            }
        }
        mineLeft = mines;
    }
    
    // All adjacent buttons of the initial clicked buttons are prohibited which means they must not be mines
    public boolean prohibitedSpace(MSButton b, int pos)
    {
        int temp = b.getValue();
        if(temp-col-1 == pos || temp-col == pos || temp-col+1 == pos || temp-1 == pos
        || temp+1 == pos || temp+col-1 == pos || temp+col == pos || temp+col+1 == pos)
            return false;
        return true;
    }
    
    public void assignNumbers()
    {
        for(int i=0; i<buttons.length; i++)
        {
            int temp = 0;
            temp += search(adjectPosition(i, locatePosition(i)));            

            if(temp == 0)            
                buttons[temp].setIsEmpty(true);            
            else if(!buttons[i].isEmpty() && !buttons[i].isMines())
            {
                buttons[i].setIsNumber(true);
                buttons[i].setFaceValue(temp);
                buttons[i].setFont(new Font("Arial Black", Font.PLAIN, 15));
                buttons[i].setForeground(setColor(temp));
            }           
        }
    }  
    
    public Color setColor(int temp)
    {
        switch(temp)
        {
            case 1 : return new Color(0,128,255);
            case 2 : return new Color(0,153,0);
            case 3 : return new Color(255,51,51);
            case 4 : return new Color(0,0,102);
            case 5 : return new Color(102,51,0);
            case 6 : return new Color(51,255,255);
            case 7 : return new Color(32,32,32);
            case 8 : return new Color(128,128,128);
            default : return Color.BLACK;
        }
    }
    
    public void assignEmptySpace()
    {
        for(MSButton i : buttons)
            if( !i.isMines() && !i.isNumber())
                i.setIsEmpty(true);
    }    
    
    public void expandEmptySpace(MSButton b)
    {       
        if(b.isEmpty() || b.isNumber())
        {                            
            int[] arr = adjectPosition(b.getValue(), locatePosition(b.getValue()));
            
            for(int i : arr)
            {
                if(buttons[i].getActive() && !buttons[i].isMines() && !buttons[i].isFlag())
                {
                    if(buttons[i].isNumber())
                    {
                        buttons[i].setText(""+buttons[i].getFaceValue());  
                        buttons[i].setEnabled(false);
                    }
                    else if(buttons[i].isEmpty())
                    {
                        buttons[i].setEnabled(false);
                        expandEmptySpace(buttons[i]);
                    }
                }                                
            }
        }
    }        
    
    public int locatePosition(int i)
    {
        if(i==0) return 0; // left upper corner     
        else if( i == col-1) return 1;// right upper corner        
        else if( i == row*col-col) return 2;// left lower corner
        else if( i == row*col-1) return 3;// right lower corner                   
        else if( i % col == 0) return 4; // left side                              
        else if( (i+1) % col == 0) return 5; // rigth side                                  
        else if( i>=1 && i <=col-2) return 6;  // top                                
        else if( i>=row*col-col+1 && i<= row*col-2 ) return 7; // bottom                                  
        else return 8;// regular
    }      
    
    // returns an array that contains all adjacent positions
    public int[] adjectPosition(int n, int side)
    {
        int[] arr = {n-col-1, n-col, (n-col)+1, n-1, n+1, (n+col)-1, n+col, n+col+1};
        switch(side)
        {
            case 0 : int[] leftUpperCorner = {n+col, n+1, n+col+1};
                arr = leftUpperCorner; break;                           
            case 1 : int[] rightUpperCorner = {n-1, n+col, n+col-1};
                arr = rightUpperCorner; break;                            
            case 2 : int[] leftLowerCorner = {n-col, n+1, n-col+1};
                arr = leftLowerCorner; break;                                      
            case 3 : int[] rigthLowerCorner = {n-1, n-col, n-col-1};
                arr = rigthLowerCorner; break;                            
            case 4 : int[] left = {n-col, (n-col)+1, n+1, n+col, n+col+1};
                arr = left; break;                            
            case 5 : int[] right = {n-col-1, n-col, n-1, (n+col)-1, n+col};
                arr = right; break;            
            case 6 : int[] top = {n-1, n+1, n+col-1, n+col, n+col+1};
                arr = top; break;                           
            case 7 : int[] bottom = {n-col-1, n-col, (n-col)+1, n-1, n+1};
                arr = bottom; break;                         
            default : break;                       
        }
        return arr;
    }
        
    // search and return how many mines are in the passed-in array
    public int search(int[] arr)
    {
        int count = 0;
        for(int i : arr)
            if( buttons[i].isMines())
                count++;
        return count;
    }
       
    public void showMines()
    {
        for(MSButton i : buttons)
            if(i.isMines())
            {
                i.setText("");
                i.setIcon(new ImageIcon(this.getClass().getResource("/mines.png")));
            }
    }
    
    public void gameOver(String str, int icon)
    {
        showMines();
        JOptionPane.showMessageDialog(null, str, GAMENAME, icon);        
        for(MSButton i : buttons)       
        {
            i.setEnabled(false);     
            if(i.isNumber())
                i.setForeground(Color.GRAY);
        }

    }
        
    public boolean checkWin()
    {
        for(MSButton i : buttons)
            if( !i.isMines() && i.getActive())
                return false;
        return true;
    }
}
