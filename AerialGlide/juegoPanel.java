package juegojava;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class juegoPanel extends JPanel implements ActionListener, KeyListener {
    private Timer tiempo;
    private Image fondo;
    private personaje pajaro;

    public juegoPanel() {
        fondo = new ImageIcon("E:/JAVA/background.jpg").getImage();
        pajaro = new personaje(100, 300);

        tiempo = new Timer(20, this);
        tiempo.start();

        setFocusable(true);
        addKeyListener(this);
    }

  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        pajaro.draw(g);
    }


    public void actionPerformed(ActionEvent e) {
        pajaro.update();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pajaro.jump();
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
