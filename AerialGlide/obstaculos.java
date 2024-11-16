package juegojava;

import java.awt.*;
import javax.swing.*;

public class Obstaculos {
    private int x, y, ancho, alto;
    private final Image imagen;

    public Obstaculos(int x, int y, int ancho, int alto, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = new ImageIcon(rutaImagen).getImage();
    }

    public void mover(int velocidad) {
        x -= velocidad;
    }

    public boolean fueraDePantalla() {
        return x + ancho < 0;
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
}
