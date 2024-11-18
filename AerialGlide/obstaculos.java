package juegojava;

import java.awt.*;
import javax.swing.ImageIcon;

public class Obstaculos {
    private int x, y, width, height;
    private final Image imagen;

    /**
     * Constructor de Obstáculos.
     * 
     * @param x          Coordenada x inicial del obstáculo.
     * @param y          Coordenada y inicial del obstáculo.
     * @param width      Ancho del obstáculo.
     * @param height     Altura del obstáculo.
     * @param rutaImagen Ruta de la imagen que representa el obstáculo.
     */
    public Obstaculos(int x, int y, int width, int height, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Cargar imagen y manejar posibles errores
        Image tempImage;
        try {
            tempImage = new ImageIcon(rutaImagen).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + rutaImagen);
            tempImage = null;
        }
        this.imagen = tempImage;
    }

    /**
     * Mueve el obstáculo hacia la izquierda.
     * 
     * @param velocidad Cantidad de píxeles que el obstáculo se mueve en cada actualización.
     */
    public void mover(int velocidad) {
        x -= velocidad;
    }

    /**
     * Verifica si el obstáculo ha salido completamente de la pantalla.
     * 
     * @return true si el obstáculo está fuera del área visible, false en caso contrario.
     */
    public boolean fueraDePantalla() {
        return x + width < 0;
    }

    /**
     * Dibuja el obstáculo en la pantalla.
     * 
     * @param g Objeto Graphics utilizado para renderizar.
     */
    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, width, height, null);
        } else {
            // Si no hay imagen, dibujar un rectángulo como marcador de posición
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Devuelve un rectángulo que representa los límites del obstáculo, utilizado para
     * la detección de colisiones.
     * 
     * @return Rectángulo con los límites del obstáculo.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
