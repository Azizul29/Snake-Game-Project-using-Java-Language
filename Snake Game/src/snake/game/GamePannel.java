/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author MD. AZIZUL HOQUE
 */
public class GamePannel extends JPanel implements ActionListener, KeyListener {

    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int lengthOfSnake = 3;
//Enemy Draw Randaomly
    private int[] xPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] yPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private Random random = new Random();
    private int enemyX, enemyY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int moves = 0;
    private int score = 0;

    private boolean gameOver = false;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
    //Timer
    private Timer timer;
    private int delay = 125;

    GamePannel() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();

        newEnemy(); // NEw Enemy method

    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);

        snaketitle.paintIcon(this, g, 25, 11);

        g.setColor(Color.BLACK);
        g.fillRect(24,75, 850, 575);

        if (moves == 0) {
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;

            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;
            //  moves++;
        }

        if (left) {
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        if (right) {
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        if (up) {
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        if (down) {
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        for (int i = 1; i < lengthOfSnake; i++) {
            snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);

        }

        //Enemy
        enemy.paintIcon(this, g, enemyX, enemyY);

        if (gameOver) { //Game Over & Restart
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over!", 300, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE To RESTART ", 320, 350);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score:  " + score, 750, 30);
        g.drawString("Length: " + lengthOfSnake, 750, 50);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            snakexlength[i] = snakexlength[i - 1];
            snakeylength[i] = snakeylength[i - 1];

        }
// head position change
        if (left) {
            snakexlength[0] = snakexlength[0] - 25;
        }
        if (right) {
            snakexlength[0] = snakexlength[0] + 25;
        }
        if (up) {
            snakeylength[0] = snakeylength[0] - 25;
        }
        if (down) {
            snakeylength[0] = snakeylength[0] + 25;
        }
//Snake start runt & terminate
        if (snakexlength[0] > 850) {
            snakexlength[0] = 25;
        }
        if (snakexlength[0] < 25) {
            snakexlength[0] = 850;
        }

        if (snakeylength[0] > 625) {
            snakeylength[0] = 75;
        }
        if (snakeylength[0] < 75) {
            snakeylength[0] = 625;
        }

        collidesWithEnemy();
        collidesWithBody();
        repaint();
    }
    // snake controll by Keyboard 

    @Override
    public void keyTyped(KeyEvent ke) {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();
        }
        //Snake Movement 
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
               
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
             
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
              
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private void newEnemy() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        enemyX = xPos[random.nextInt(34)];
        enemyY = yPos[random.nextInt(23)];

        for (int i = lengthOfSnake - 1; i >= 0; i--) {
            if (snakexlength[i] == enemyX && snakeylength[i] == enemyY) {
                newEnemy();
            }

        }
    }

    private void collidesWithEnemy() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (snakexlength[0] == enemyX && snakeylength[0] == enemyY) {
            newEnemy();
            lengthOfSnake++;
           // score++;
           score=score+5; //Score Count
        }
    }

    private void collidesWithBody() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void restart() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();

    }

}
