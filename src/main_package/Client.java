package main_package;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Spencer Ekstrom
 * @version May 19, 2020 
 */
public class Client implements ActionListener {

    JFrame frame;
    JPanel main, sub;
    GUI gui;
    JButton start, end;
    Container container;
    int[][] board ={{9, 4, 6, 0, 0, 0, 8, 3, 0},
                    {0, 0, 8, 9, 0, 0, 0, 4, 5},
                    {0, 7, 3, 0, 0, 2, 1, 9, 6},
                    {0, 0, 0, 2, 0, 0, 0, 0, 1},
                    {0, 9, 0, 1, 0, 0, 3, 7, 8},
                    {8, 0, 0, 0, 0, 0, 4, 0, 0},
                    {0, 0, 1, 7, 2, 6, 9, 0, 0},
                    {4, 6, 0, 0, 1, 0, 2, 0, 0},
                    {2, 8, 0, 0, 9, 0, 6, 0, 3}};;
    boolean solved = false;
    
    public Client() {
        //Creates the Frame
        frame = new JFrame("Sudoku Back-Tracking Visualized");
        frame.setSize(615, 675);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates Container from the Frame
        container = frame.getContentPane();
        //Creates the GUI
        gui = new GUI(board);
        //Creates the Start Button
        start = new JButton("Start");
        start.addActionListener(this);
        end = new JButton("End");
        end.addActionListener(this);
        //Creates the JPanels
        main = new JPanel();
        sub = new JPanel();
        //Sets sizes of panels
        main.setSize(600, 700);
        sub.setSize(600, 100);
        //Adds start button to sub
        sub.add(start);
        sub.add(end);
        //Set the Layout of of the frame
        main.setLayout(new BorderLayout());
        main.add(gui, BorderLayout.CENTER);
        main.add(sub, BorderLayout.SOUTH);
        //Add main to the container
        container.add(main);
        //Show the frame
        frame.show();
    }

    public void runSolve() {
        Thread runner = new Thread();
        while (!solved) {
            try {
                runner.sleep(1000);

                System.out.println("drawing");
                gui.repaint();
                solved = solve(board);
                printBoard(board);
                
            } catch (InterruptedException e) {}            
        }
    }
    
    public boolean solve(int[][] board) {
        Rectangle r = new Rectangle(0, 0, 700, 700);
        gui.paintImmediately(r);
        int roww;
        int coll;
        Thread runner = new Thread();
        //Base Case
        int[] findempty = findEmpty(board);
        if (findempty[0] == -1 && findempty[1] == -1) {
            return true;
        } else {
            roww = findempty[0];
            coll = findempty[1];
            gui.updateBoard(board);
        }

        for (int val = 1; val <= 9; val++) {
            try {
               runner.sleep(10);
            }catch(Exception e){}
                if (validPlacement(board, roww, coll, val)) {
                    board[roww][coll] = val;
                    if (solve(board)) {
                        return true;
                    }
                    board[roww][coll] = 0;
                    gui.updateBoard(board);
                }
            
        }
        return false;
    }
    
    public int[] findEmpty(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    int[] ret = new int[2];
                    ret[0] = i;
                    ret[1] = j;
                    return ret;
                }
            }
        }
        int[] ret = new int[2];
        ret[0] = -1;
        ret[1] = -1;
        return ret;
    }

    public boolean validPlacement(int[][] board, int row, int col, int val) {
        //Checks for invalidities in it's row
        for (int i = 0; i < 9; i++) {
            if (i != col && board[row][i] == val) {
                return false;
            }
        }
        //Checks Invalidities in it's column
        for (int i = 0; i < 9; i++) {
            if (i != row && board[i][col] == val) {
                return false;
            }
        }

        int box_x = row / 3;
        int box_y = col / 3;

        for (int ro = box_x * 3; ro < box_x * 3 + 3; ro++) {
            for (int co = box_y * 3; co < box_y * 3 + 3; co++) {
                if (board[ro][co] == val) {
                    return false;
                }
            }
        }
        //If it get's here, it's a valid placement
        return true;
    }

    public void printBoard(int[][] board) {
        System.out.println("+------+------+------+");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                if (j == 3) {
                    System.out.print("|");
                }
                if (j == 6) {
                    System.out.print("|");
                }
                System.out.print(board[i][j] + " ");
                if (j == 8) {
                    System.out.print("|");
                }
            }
            System.out.println("");
            if (i == 2) {
                System.out.println("+------+------+------+");
            }
            if (i == 5) {
                System.out.println("+------+------+------+");
            }
        }
        System.out.println("+------+------+------+");
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == start) {
            runSolve();
        }
        if(evt.getSource() == end) {
            frame.dispose();
        }
    }
}
