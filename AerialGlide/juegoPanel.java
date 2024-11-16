package juegojava;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class JuegoPanel extends JPanel implements ActionListener, KeyListener {
    private Timer tiempo;
    private Image fondo;
    private Personaje pajaro;
    private ArrayList<Obstaculos> obstaculos;
    private int contadorTiempo;
    private static final int ESPACIO_ENTRE_OBSTACULOS = 200;
    private static final int ANCHO_OBSTACULO = 120;

    public JuegoPanel() {
        fondo = new ImageIcon("C:/JAVA/juegojava/src/Resources/fondo juego.jpg").getImage();
        pajaro = new Personaje(100, 300);
        obstaculos = new ArrayList<>();
        tiempo = new Timer(20, this);
        tiempo.start();

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        pajaro.draw(g);

        // Dibujar obstáculos
        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.dibujar(g);
        }
    }

    public void actionPerformed(ActionEvent e) {
        pajaro.update();

        // Mover obstáculos y eliminar los que salen de la pantalla
        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.mover(6); // Velocidad de movimiento
        }
        obstaculos.removeIf(Obstaculos::fueraDePantalla);

        // Generar nuevos obstáculos cada cierto tiempo
        contadorTiempo++;
        if (contadorTiempo % 100 == 0) { // Ajusta para cambiar la frecuencia
            generarObstaculos();
        }

        // Detectar colisiones
        for (Obstaculos obstaculo : obstaculos) {
            if (pajaro.getBounds().intersects(obstaculo.getBounds())) {
                perder();
                return;
            }
        }

        repaint();
    }

    private void generarObstaculos() {
        int alturaVentana = getHeight();
        int espacioVertical = 200; // Espacio entre los dos obstáculos
        int alturaObstaculoSuperior = (int) (Math.random() * (alturaVentana - espacioVertical - 100)) + 60;

        // Obstáculo superior
        obstaculos.add(new Obstaculos(
            getWidth(), 
            0, 
            ANCHO_OBSTACULO, 
            alturaObstaculoSuperior, 
            "C:/JAVA/juegojava/src/Resources/obstacle - copia.png"
        ));

        // Obstáculo inferior
        obstaculos.add(new Obstaculos(
            getWidth(), 
            alturaObstaculoSuperior + espacioVertical, 
            ANCHO_OBSTACULO, 
            alturaVentana - alturaObstaculoSuperior - espacioVertical, 
            "C:/JAVA/juegojava/src/Resources/obstacle.png"
        ));
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pajaro.jump();
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    private void perder() {
        tiempo.stop();
        JOptionPane.showMessageDialog(this, "¡Perdiste! Inténtalo de nuevo.");
    }
}
