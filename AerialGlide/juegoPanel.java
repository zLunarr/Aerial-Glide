package juegojava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JuegoPanel extends JPanel implements ActionListener, KeyListener {
    private final Timer timer;
    private final Image fondo;
    private final Personaje personaje;
    private final ArrayList<Obstaculos> obstaculos;
    private int puntuacion;
    private final int velocidadObstaculos = 5;

    public JuegoPanel() {
        fondo = new ImageIcon("C:/JAVA/juegojava/src/Resources/fondo juego.jpg").getImage();
        personaje = new Personaje(100, 300);
        obstaculos = new ArrayList<>();
        puntuacion = 0;

        timer = new Timer(20, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
        generarObstaculos();
    }

    private void generarObstaculos() {
        obstaculos.add(new Obstaculos(800, 200, 80, 200, "C:/JAVA/juegojava/src/Resources/obstacle.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        personaje.draw(g);

        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.dibujar(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Puntuación: " + puntuacion, 10, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        personaje.update();

        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.mover(velocidadObstaculos);
        }

        obstaculos.removeIf(Obstaculos::fueraDePantalla);

        if (obstaculos.isEmpty()) {
            generarObstaculos();
            puntuacion++;
        }

        detectarColisiones();
        repaint();
    }

    private void detectarColisiones() {
        for (Obstaculos obstaculo : obstaculos) {
            if (personaje.getBounds().intersects(obstaculo.getBounds())) {
                perder();
                return;
            }
        }
    }

    private void perder() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "¡Perdiste! Puntuación final: " + puntuacion);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            personaje.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
