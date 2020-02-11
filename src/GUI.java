
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Spencer Ekstrom
 * @version December 17, 2019 This is is GUI for the Sudoku Solver
 */
public class GUI extends JPanel {

    int[][] board;
    public GUI(int[][] board) {
        this.board = board;
        setBackground(Color.white);
    }

    public void updateBoard(int[][] board) {
        this.board = board;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Draw Borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 600, 4);
        g.fillRect(0, 0, 4, 600);
        g.fillRect(0, 596, 600, 4);
        g.fillRect(596, 0, 4, 600);

        //Draw Horiontal Thicker Lines
        g.fillRect(0, 200, 600, 4);
        g.fillRect(0, 400, 600, 4);

        //Draw Vertical Thicker Lines
        g.fillRect(200, 0, 4, 600);
        g.fillRect(400, 0, 4, 600);

        //Draw Regular Horizontal Lines
        g.fillRect(0, 67, 600, 2);
        g.fillRect(0, 134, 600, 2);
        g.fillRect(0, 267, 600, 2);
        g.fillRect(0, 334, 600, 2);
        g.fillRect(0, 467, 600, 2);
        g.fillRect(0, 534, 600, 2);

        //Draw Regular Vertical Lines
        g.fillRect(67, 0, 2, 600);
        g.fillRect(134, 0, 2, 600);
        g.fillRect(267, 0, 2, 600);
        g.fillRect(334, 0, 2, 600);
        g.fillRect(467, 0, 2, 600);
        g.fillRect(534, 0, 2, 600);

        //Fill the board with numbers, 0 = empty space
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        int x = 0;
        int y = 0;
        String num = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                x = (i + 1) * 66 - 33;
                y = (j + 1) * 66 - 28;
                num = String.valueOf(board[i][j]);
                if(!(num.equals("0")))
                    g.drawString(num, x, y);
            }
        }
        
    }
}
