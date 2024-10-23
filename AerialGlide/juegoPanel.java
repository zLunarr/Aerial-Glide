package juegojava;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class juegoPanel extends JPanel implements ActionListener, KeyListener {
    // Atributos para el juego
    private int birdY; // Posición vertical del pájaro
    private int birdVelocity; // Velocidad del pájaro
    private Timer timer; // Temporizador para la animación

    public juegoPanel() {
        // Configurar el color de fondo del panel
        setBackground(Color.CYAN);
        // Inicializar la posición y velocidad del pájaro
        birdY = 300;
        birdVelocity = 0;

        // Crear y comenzar el temporizador (actualización cada 20 ms)
        timer = new Timer(20, this);
        timer.start();

        // Agregar el KeyListener para detectar las teclas presionadas
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el fondo del juego (cielo)
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar el "pájaro" como un rectángulo amarillo
        g.setColor(Color.YELLOW);
        g.fillRect(100, birdY, 30, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica de movimiento del pájaro (simulando gravedad)
        birdY += birdVelocity;
        birdVelocity += 1; // Simular la gravedad aumentando la velocidad

        // Evitar que el pájaro salga por el suelo
        if (birdY > getHeight() - 30) {
            birdY = getHeight() - 30;
            birdVelocity = 0;
        }

        // Redibujar el panel para actualizar el juego
        repaint();
    }

    // Método para hacer que el pájaro "salte"
    public void jump() {
        birdVelocity = -10; // Cambiar la velocidad hacia arriba
    }

    // Métodos del KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        // Si se presiona la barra espaciadora, el pájaro salta
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No es necesario implementar nada aquí para el salto
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No es necesario implementar nada aquí para el salto
    }
}
