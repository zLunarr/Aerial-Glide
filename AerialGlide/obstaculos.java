package juegojava;

import java.awt.*;
import javax.swing.ImageIcon;

public class Obstaculos {
    private int x, y, width, height;
    private Image imagen;

    public Obstaculos(int x, int y, int width, int height, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagen = new ImageIcon(rutaImagen).getImage();
    }

    public void mover(int velocidad) {
        x -= velocidad;
    }

    public boolean fueraDePantalla() {
        return x + width < 0;
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
