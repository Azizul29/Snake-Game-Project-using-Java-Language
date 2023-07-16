
package snake.game;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author MD. AZIZUL HOQUE
 */
public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame(".........................................................................Snake Game..................................................");
        frame.setBounds(10, 10, 905, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePannel panel = new GamePannel();
        panel.setBackground(Color.DARK_GRAY);
            frame.add(panel);
        frame.setVisible(true);
        
    }
}
