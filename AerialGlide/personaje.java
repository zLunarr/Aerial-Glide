package juegojava;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class personaje {
	 // Posición del pájaro
    private int x, y;
    // Dimensiones del pájaro
    private int width, height;
    // Velocidad de movimiento vertical
    private int velocity;
    
    public personaje(int x, int y) {
        // Inicializar la posición del pájaro
        this.x = x;
        this.y = y;
        // Configurar el tamaño del pájaro
        this.width = 30;
        this.height = 30;
        // Configurar la velocidad inicial (en reposo)
        this.velocity = 0;
    }

    // Método para actualizar la posición del pájaro
    public void update() {
        // Mover el pájaro hacia abajo según la velocidad
        y += velocity;
        // Aumentar la velocidad para simular la gravedad
        velocity += 1;
        // Evitar que el pájaro salga por el suelo
        if (y > 600 - height) {
            y = 600 - height;
            velocity = 0;
        }
    }
 // Método para hacer que el pájaro salte
    public void jump() {
        // Configurar la velocidad hacia arriba
        velocity = -10;
    }
 // Dibujar el pájaro en el juego
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
 // Obtener el rectángulo de colisión del pájaro
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
